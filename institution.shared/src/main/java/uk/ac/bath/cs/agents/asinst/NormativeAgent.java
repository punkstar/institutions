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
	abstract protected void _incomingSubscription(String from, FluentSet payload);
	
    protected void _publishAction(Serializable name) {
        try {
        	this._publish(this.getAgentDataDomain(), name);
        } catch (Exception e) {
            this.__log(String.format("Unable to publish agent action: %s", e.getMessage()));
        }
    }
    
    protected void _publishInstitutionalEvent(InstitutionIdentifier inst, ExogenousEvent event, String[] variables) {
    	try {
    		this._publish(this.getInstitutionDataDomain(inst), event.asVariablesToString(variables));
    	} catch (Exception e) {
            this.__log(String.format("Unable to publish institutional event: '%s' because of %s", event.asVariablesToString(variables), e.getMessage()));
            e.printStackTrace();
    	}
    }
    
    protected void _subscribeInstitutionalChanges(InstitutionIdentifier inst) {
		String data_domain = inst.getOutboundDataDomain();
		
    	try {
			this._getBlackboardService().subscribe(new BlackboardQuery(data_domain), this);
			this.__log(String.format("Subscribing to %s", data_domain));
		} catch (Exception e) {
			this.__log(String.format("Unable to subscribe to %s", data_domain));
		}
    }
    
    protected void _publish(String data_domain, Serializable item) throws BlackboardException, Exception {
        this.__log(String.format("Published '%s' to %s", item.toString(), data_domain));
        
        BlackboardItem bbItem = new BlackboardItem(this.getPrimaryHandle().toString(), "text/plain");
        
        bbItem.setData(item);
        bbItem.setMetaValue("data_domain", data_domain);
        
    	this._getBlackboardService().publish(
    		data_domain,
    		bbItem
    	);
    }
    
    public void onData(BlackboardItem item) {
    	String data_domain = item.getMetaValue("data_domain").getValue().toString();
    	this.__log(String.format("Received blackboard subscription from %s", data_domain));

		// Fix ClassNotFoundException for ClingoResponse: http://www.agentscape.org/forums/viewtopic.php?pid=258#p258
        ClassLoader original = Thread.currentThread().getContextClassLoader();
        Thread.currentThread().setContextClassLoader(FluentSet.class.getClassLoader());
		
        FluentSet payload = (FluentSet) item.getData();
		
        // when finished put back the original class loader
        Thread.currentThread().setContextClassLoader(original);
    	
    	if (payload == null) {
    		this.__log("The message received contained no data!");
    	}
    	
    	this.__log(String.format("Received blackboard item: %s", payload.toString()));
    	
    	this._incomingSubscription(data_domain, payload);
    }
    
    protected BlackboardService _getBlackboardService() throws Exception {
    	return this.getServiceBroker().bind(BlackboardService.class);
    }
    
    public String getAgentDataDomain() {
        return String.format("Platform.Global.Agent.%s", this.getPrimaryHandle());
    }
    
    public String getInstitutionDataDomain(InstitutionIdentifier inst) {
    	return inst.getInboundDataDomain();
    }
    
    private void __log(String message) {
        Log.message(String.format("[normative_agent] %s", message));
    }
}