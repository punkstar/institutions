package uk.ac.bath.cs.agents.asinst;

import java.util.Hashtable;

import org.iids.aos.blackboardservice.BlackboardItem;
import org.iids.aos.blackboardservice.BlackboardService;
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
	
    public InstitutionServiceImpl() {
        this.__log("Service online");
    }
    
    protected BlackboardService _getBlackboardService() {
        try {
            return this.getServiceBroker().bind(BlackboardService.class);
        } catch (Exception e) { this.__log("There was an error using the service broker for the blackboard service."); }
        
        return null;
    }
    
    protected ClingoService _getClingoService() {
    	try {
			return this.getServiceBroker().bind(ClingoService.class);
    	 } catch (Exception e) { this.__log("There was an error using the service broker for the clingo service."); }
    	 
    	 return null;
    }
    
    public void onData(BlackboardItem item) {
        this.__log(String.format("Got bb data item: %s", item.getData().toString()));
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
	 * Check the existance of a template with the given identifier.
	 * 
	 * @param t
	 * @return
	 */
	protected boolean _existsInstitutionTemplate(InstitutionTemplateIdentifier t) {
		return this._templates.containsKey(t.toString());
	}

	/**
	 * Get an already created instantiated institution
	 * @throws InstitutionNotFoundException 
	 */
	public InstitutionInstance getInstitutionInstance(InstitutionIdentifier i) throws InstitutionNotFoundException {
		if (this._instances.containsKey(i.toString())) {
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
			InstitutionInstance instance = new InstitutionInstance(this.getInstitutionTemplate(template));
			this._instances.put(ident.toString(), instance);
			
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
		
		this.__log("Evaluating instance: " + instance.toString());
		synchronized(instance) {
			ClingoService c = this._getClingoService();
			
			ClingoResponse response = c.solve(instance.asAsp());
			if (response.wasSuccessful()) {
				this.__log(String.format("Evaluated in %.2fs with %d facts and %d holds", response.getTime(), response.getFacts().length, response.getHolds().length));
				instance.setHolds(response.getHolds());
			} else {
				this.__log("The clingo response was not marked successful");
			}
		}
	}
}