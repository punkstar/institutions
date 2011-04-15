package uk.ac.bath.cs.agents.asinst;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.iids.aos.blackboardservice.BlackboardException;
import org.iids.aos.blackboardservice.BlackboardItem;
import org.iids.aos.blackboardservice.BlackboardQuery;
import org.iids.aos.blackboardservice.BlackboardService;
import org.iids.aos.blackboardservice.DataHandle;
import org.iids.aos.blackboardservice.BlackboardService.IBlackboardNotification;
import org.iids.aos.service.AbstractDefaultService;

import uk.ac.bath.cs.agents.instal.Domain;
import uk.ac.bath.cs.agents.instal.Institution;

/**
 * 
 * Institution Service
 * 
 *  - Maintains a list of "templates" that can be used to instantiate institutions
 *  - Maintains a list of institutions that have been instantiated
 *  	- Allows for the referencing of these instances between agents
 * 
 * @author Nick Jones <nj210@bath.ac.uk>
 *
 */

public class InstitutionServiceImpl extends AbstractDefaultService implements InstitutionService, IBlackboardNotification {
	Hashtable<String, Institution> _templates = new Hashtable<String, Institution>();
	Hashtable<String, InstitutionInstance> _instances = new Hashtable<String, InstitutionInstance>();
	
	Pattern _pattern_instFromDataDomain = Pattern.compile("([\\w-]+)$");
	
    public InstitutionServiceImpl() {
        this.__log("Service online");
        
        ClingoResponse load_response = new ClingoResponse(null, null, 0);
    }
    
    protected BlackboardService _getBlackboardService() {
        try {
            return this.getServiceBroker().bind(BlackboardService.class);
        } catch (Exception e) { e.printStackTrace(); this.__log("There was an error using the service broker for the blackboard service."); }
        
        return null;
    }
    
    protected ClingoService _getClingoService() {
    	try {
			return this.getServiceBroker().bind(ClingoService.class);
    	 } catch (Exception e) { this.__log("There was an error using the service broker for the clingo service."); }
    	 
    	 return null;
    }
    
    public void onData(BlackboardItem item) {
    	String data_domain = item.getMetaValue("data_domain").getValue().toString();
    	
    	this.__log(String.format("Received (%s) from %s", item.getContentType(), data_domain));
    	
    	this._handleBlackboardEvent(data_domain, item, PubsubType.valueOf(item.getContentType()));
    }
    
    private void __log(String message) {
        Log.message(String.format("[institution_service] %s", message));
    }

    /**
     * Add a blueprint of an institution to the library of institutions we can instantiate
     */
	public InstitutionTemplateIdentifier addInstitutionTemplate(Institution i, String description) {
		InstitutionTemplateIdentifier ident = new InstitutionTemplateIdentifier(description);
		this._templates.put(ident.toString(), i);
		
		this.__log(String.format("Adding '%s' institution template to our library", ident.toString()));
		
		return ident;
	}
	
	public Institution getInstitutionTemplate(InstitutionTemplateIdentifier key) throws InstitutionNotFoundException {
		if (this._existsInstitutionTemplate(key)) {
			return this._templates.get(key.toString());
		} else {
			throw new InstitutionNotFoundException(String.format("Institution template, %s, not found", key.getDescription()));
		}
	}
	
	/**
	 * Check the existence of a template with the given identifier.
	 * 
	 * @param t
	 * @return
	 */
	protected boolean _existsInstitutionTemplate(InstitutionTemplateIdentifier t) {
		return this._templates.containsKey(t.toString());
	}
	
	/**
	 * Check the existence of an instance with a given identifier
	 * 
	 * @param i
	 * @return
	 */
	protected boolean _existsInstitutionInstance(InstitutionIdentifier i) {
		return this._instances.containsKey(i.toString());
	}

	/**
	 * Get an already created instantiated institution
	 * @throws InstitutionNotFoundException 
	 */
	public InstitutionInstance getInstitutionInstance(InstitutionIdentifier i) throws InstitutionNotFoundException {
		if (this._existsInstitutionInstance(i)) {
			return this._instances.get(i.toString());
		} else {
			throw new InstitutionNotFoundException(String.format("Instantiated Institution of type '%s', with identifier '%s' not found", i.getTemplate().getDescription(), i.toString()));
		}
	}

	/**
	 * Create a new instance of an institution
	 */
	public InstitutionIdentifier instantiateInstitution(InstitutionTemplateIdentifier template, Domain d) throws InstitutionNotFoundException {
		if (this._existsInstitutionTemplate(template)) {
			InstitutionIdentifier ident = new InstitutionIdentifier(template);
			InstitutionInstance instance = new InstitutionInstance(this.getInstitutionTemplate(template), d);
			this._instances.put(ident.toString(), instance);
			
			this.evaluate(ident);
			
			String blackboard_query = ident.getDataDomain();
			
	        try {
				this._getBlackboardService().subscribe(new BlackboardQuery(blackboard_query), this);
				this.__log(String.format("Subscribed to %s", blackboard_query));
				this._notifyNewStream(blackboard_query);
	        } catch (BlackboardException e) {
				this.__log(String.format("There was an error subscribing to the blackboard (%s)", blackboard_query));
			}
			
			this.__log(String.format("Creating an instance of %s", template.getDescription()));
			
			return ident;
		} else {
			throw new InstitutionNotFoundException(String.format("Institution template with description '%s' not found", template.getDescription()));
		}
	}
	
	public InstitutionIdentifier instantiateInstitution(InstitutionTemplateIdentifier template) throws InstitutionNotFoundException {
		return this.instantiateInstitution(template, null);
	}
	
	public void evaluate(InstitutionIdentifier ident) throws InstitutionNotFoundException {
		InstitutionInstance instance = this.getInstitutionInstance(ident);
		
		this.__log("Evaluating instance: " + ident.toString());
		synchronized(instance) {
			try {
				ClingoResponse response = this._sendClingo(instance.asAsp());
				if (response.wasSuccessful()) {
					instance.setHolds(response.getHolds());
					this.__log("Instance holds updated");
				}
			} catch (ClingoException e) {}
		}
	}
	
	public FluentSet getCurrentFluents(InstitutionIdentifier ident) throws InstitutionNotFoundException {
		InstitutionInstance instance = this.getInstitutionInstance(ident);
		
		// This might not always hold, but I'd bet it's a pretty good indication.
		if (instance.getHolds().size() == 0) {
			this.evaluate(ident);
		}
		
		return instance.getHolds();
	}
	
	protected ClingoResponse _sendClingo(String asp) throws ClingoException {
		ClingoService c = this._getClingoService();
		ClingoResponse response;
		
		try {
			response = (ClingoResponse) c.solve(asp);
		} catch (ClingoException e) {
			this.__log("Received exception from clingo: " + e.getMessage());
			throw e;
		}
		
		
		if (response == null) {
			this.__log("The response was null");
		} else if (response.wasSuccessful()) {
			this.__log(String.format("Evaluated in %.2fs with %d facts and %d holds", response.getTime(), response.getFacts().length, response.getHolds().length));
		} else {
			this.__log("The clingo response was not marked successful");
		}

		return response;
	}
	
	protected void _handleBlackboardEvent(String data_domain, BlackboardItem bb_item, PubsubType type) {
        this.__log(String.format("Subscription received (%s) from %s", type, data_domain));
        
        switch (type) {
        	case INST_EVENT:
        		String payload = (String) bb_item.getData();
        		
	            // Now that we've received an event occurrance, we need to set it to occurred($event, i00) and rerun
	            this.__log("Regenerating institutional state");
	            
	            final InstitutionIdentifier inst_key = InstitutionIdentifier.build(
	            	this._extractInstitutionInstanceFromDataDomain(data_domain)
	            );
	            
	            this.__log(String.format("Institution key: %s", inst_key));
	
	            if (this._existsInstitutionInstance(inst_key)) {
	    			try {
	    				final InstitutionInstance inst = this.getInstitutionInstance(inst_key);
	    				synchronized(inst) {
	    					try {
	    						// Fix ClassNotFoundException for ClingoResponse: http://www.agentscape.org/forums/viewtopic.php?pid=258#p258
	    				        ClassLoader original = Thread.currentThread().getContextClassLoader();
	    				        Thread.currentThread().setContextClassLoader(ClingoResponse.class.getClassLoader());
	    						
	    				        ClingoResponse response = this._sendClingo(inst.asAsp(payload.toString()));
	    						
	    				        // when finished put back the original class loader
	    				        Thread.currentThread().setContextClassLoader(original);
	    				        
	    						if (response.wasSuccessful()) {
	    							if (inst.changedHolds(response.getHolds())) {
	    								// Update the holdsats
	    								inst.setHolds(response.getHolds());
	    								
	    								// Publish the change
	    								try {
	    									this._publish(inst_key.getDataDomain(), inst.getHolds(), PubsubType.INST_FLUENTS);
	
	    								} catch (Exception e) {
	    									this.__log(String.format("Unable to post institutional change event: %s", e.getMessage()));
	    								}
	    							}
	    						}
	    					} catch (Exception e) {
	    						this.__log(String.format("Exception: %s", e.getMessage()));
	    					}
	    				}
	    			} catch (InstitutionNotFoundException e) {
	    				this.__log(String.format("Unable to load instance, despite every indication that it exists: %s", inst_key.toString()));
	    			}
	            } else {
	            	this.__log(String.format("Unknown institution %s", inst_key.toString()));
	            }
	            break;
        }
	}
	
	/**
	 * Called to allow an agent's actions to be monitored by an institution
	 */
	public void subscribeToActivityStream(InstitutionIdentifier i, String domain) {
		try {
			this._getBlackboardService().subscribe(new BlackboardQuery(domain), this);
			this.__log(String.format("Subscribed to activity stream on %s", domain));
		} catch (BlackboardException e) {
			this.__log(String.format("Unable to subscribe to activity stream on %s", domain));
		}
	}
	
	/**
	 * Give a string like Platform.Global.Institution.dutch-1, will return dutch-1
	 * 
	 * @param data_domain
	 * @return
	 */
	private String _extractInstitutionInstanceFromDataDomain(String data_domain) {
		Matcher m = this._pattern_instFromDataDomain.matcher(data_domain);
		
		if (m.find()) {
			return m.group();
		}
		
		return null;
	}
	
	public FluentSet getHypotheticalFluents(final InstitutionIdentifier inst_key, String action) {
        // Now that we've received an event occurrance, we need to set it to occurred($event, i00) and rerun
        this.__log("Regenerating institutional state");

        this.__log(String.format("Institution key: %s", inst_key));

        if (this._existsInstitutionInstance(inst_key)) {
			try {
				final InstitutionInstance inst = this.getInstitutionInstance(inst_key);
				synchronized(inst) {
					try {
						// Fix ClassNotFoundException for ClingoResponse: http://www.agentscape.org/forums/viewtopic.php?pid=258#p258
				        ClassLoader original = Thread.currentThread().getContextClassLoader();
				        Thread.currentThread().setContextClassLoader(ClingoResponse.class.getClassLoader());
						
				        ClingoResponse response = this._sendClingo(inst.asAsp(action));
						
				        // when finished put back the original class loader
				        Thread.currentThread().setContextClassLoader(original);
				        
						if (response.wasSuccessful()) {
							return new FluentSet(response.getHolds());
						}
					} catch (Exception e) {
						this.__log(String.format("Exception: %s", e.getMessage()));
					}
				}
			} catch (InstitutionNotFoundException e) {
				this.__log(String.format("Unable to load instance, despite every indication that it exists: %s", inst_key.toString()));
			}
        } else {
        	this.__log(String.format("Unknown institution %s", inst_key.toString()));
        }
        
        return new FluentSet();
	}
	
    protected void _publish(final String data_domain, final Serializable item, final PubsubType type) throws BlackboardException, Exception {
        final BlackboardItem bbItem = new BlackboardItem(getServiceID().toString(), type.toString());
        
        bbItem.setData(item);
        bbItem.setMetaValue("data_domain", data_domain);
    	
    	// This wouldn't work unless it was in it's own thread..
		Thread t = new Thread() {
			public void run() {
				try {
					__log(String.format("Publishing %s (%s) to %s", data_domain, type, item.toString()));
			        
					// Fix ClassNotFoundException for ClingoResponse: http://www.agentscape.org/forums/viewtopic.php?pid=258#p258
			        ClassLoader original = Thread.currentThread().getContextClassLoader();
			        Thread.currentThread().setContextClassLoader(DataHandle.class.getClassLoader());
					
			    	_getBlackboardService().publish(
				    		data_domain,
				    		bbItem
				    	);
					
			        // when finished put back the original class loader
			        Thread.currentThread().setContextClassLoader(original);
			        
			        __log(String.format("Published '%s' to %s", item.toString(), data_domain));
				} catch (BlackboardException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		t.start();
    }
    
    /**
     * Send a notification to the stream notification stream that there is a new feed available for subscription
     */
    protected void _notifyNewStream(String data_domain) {
    	try {
			this._publish("App.Global.Streams", data_domain, PubsubType.NEW_STREAM);
		} catch (Exception e) {
			this.__log("Unable to publish notification of new stream: " + e.getMessage());
		}
    }
}