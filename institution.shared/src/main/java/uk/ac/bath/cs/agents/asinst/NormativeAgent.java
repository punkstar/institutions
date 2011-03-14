package uk.ac.bath.cs.agents.asinst;

import java.io.Serializable;

import org.iids.aos.agent.Agent;
import org.iids.aos.blackboardservice.BlackboardException;
import org.iids.aos.blackboardservice.BlackboardItem;
import org.iids.aos.blackboardservice.BlackboardService;

import uk.ac.bath.cs.agents.instal.ExogenousEvent;

abstract class NormativeAgent extends Agent {
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
    
    protected BlackboardService _getBlackboardService() throws Exception {
    	return this.getServiceBroker().bind(BlackboardService.class);
    }
    
    public String getAgentDataDomain() {
        return String.format("Platform.Global.Agent.%s.Actions", this.getPrimaryHandle());
    }
    
    public String getInstitutionDataDomain(InstitutionIdentifier inst) {
    	return String.format("Platform.Global.Institution.%s", inst.toString());
    }
    
    private void __log(String message) {
        Log.message(String.format("[normative_agent] %s", message));
    }
}