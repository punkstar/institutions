package uk.ac.bath.cs.agents.asinst;

import java.io.Serializable;

import org.iids.aos.agent.Agent;
import org.iids.aos.blackboardservice.BlackboardException;
import org.iids.aos.blackboardservice.BlackboardItem;
import org.iids.aos.blackboardservice.BlackboardQuery;
import org.iids.aos.blackboardservice.BlackboardService;
import org.iids.aos.blackboardservice.BlackboardService.IBlackboardNotification;

import uk.ac.bath.cs.agents.instal.ExogenousEvent;

abstract class NormativeAgent extends Agent implements IBlackboardNotification {
	abstract protected void _incomingSubscription(String from, BlackboardItem payload, PubsubType type);
	
    protected void _publishAction(Serializable name) {
        try {
        	this._publish(this.getAgentDataDomain(), name, PubsubType.AGENT_ACTION);
        } catch (Exception e) {
            this.__log(String.format("Unable to publish agent action: %s", e.getMessage()));
        }
    }
    
    protected void _publishInstitutionalEvent(InstitutionIdentifier inst, ExogenousEvent event, String[] variables) {
    	try {
    		this._publish(this.getInstitutionDataDomain(inst), event.asVariablesToString(variables), PubsubType.INST_EVENT);
    	} catch (Exception e) {
            this.__log(String.format("Unable to publish institutional event: '%s' because of %s", event.asVariablesToString(variables), e.getMessage()));
            e.printStackTrace();
    	}
    }
    
    protected void _subscribeInstitutionalChanges(InstitutionIdentifier inst) {
		String data_domain = inst.getDataDomain();
		
    	try {
			this._getBlackboardService().subscribe(new BlackboardQuery(data_domain), this);
			this.__log(String.format("Subscribing to %s", data_domain));
		} catch (Exception e) {
			this.__log(String.format("Unable to subscribe to %s", data_domain));
		}
    }
    
    /**
     * Make an institution subscribe to your action feed.
     * 
     * @param inst
     */
    protected void _registerWithInstitution(InstitutionIdentifier inst) {
    	
    }
    
    protected void _publish(String data_domain, Serializable item, PubsubType type) throws BlackboardException, Exception {
		this.__log(String.format("Publishing %s (%s) to %s", data_domain, type, item.toString()));
        
        BlackboardItem bbItem = new BlackboardItem(this.getPrimaryHandle().toString(), type.toString());
        
        bbItem.setData(item);
        bbItem.setMetaValue("data_domain", data_domain);
        
    	this._getBlackboardService().publish(
    		data_domain,
    		bbItem
    	);
    }
    
    public void onData(BlackboardItem item) {
    	String data_domain = item.getMetaValue("data_domain").getValue().toString();
    	this.__log(String.format("Received (%s) from %s", item.getContentType(), data_domain));
    	
    	this._incomingSubscription(data_domain, item, PubsubType.valueOf(item.getContentType()));
    }
    
    protected BlackboardService _getBlackboardService() throws Exception {
    	return this.getServiceBroker().bind(BlackboardService.class);
    }
    
    protected InstitutionService _getInstitutionService() throws Exception {
		return this.getServiceBroker().bind(InstitutionService.class);
    }
    
    public String getAgentDataDomain() {
        return String.format("Platform.Global.Agent.%s", this.getPrimaryHandle());
    }
    
    public String getInstitutionDataDomain(InstitutionIdentifier inst) {
    	return inst.getDataDomain();
    }
    
    private void __log(String message) {
        Log.message(String.format("[normative_agent] %s", message));
    }
}