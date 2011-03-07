package uk.ac.bath.cs.agents.asinst;

import java.io.Serializable;

import org.iids.aos.agent.Agent;
import org.iids.aos.blackboardservice.BlackboardItem;
import org.iids.aos.blackboardservice.BlackboardService;

import uk.ac.bath.cs.agents.instal.Event;
import uk.ac.bath.cs.agents.instal.ExogeneousEvent;

abstract class NormativeAgent extends Agent {
    protected void _publishAction(String name) {
        try {
            BlackboardService bbService = this.getServiceBroker().bind(BlackboardService.class);
            bbService.publish(this.getDataDomain(), new BlackboardItem(this.getPrimaryHandle().toString(), "text/plain", (Serializable) name));
            
            this.__log(String.format("Published '%s' to %s", name, this.getDataDomain()));
        } catch (Exception e) {
            this.__log(String.format("Unable to publish agent action: %s", e.getMessage()));
        }
    }
    protected void _publishInstitutionalEvent(InstitutionIdentifier inst, ExogeneousEvent e) {
    	
    }
    
    public String getDataDomain() {
        return String.format("Platform.Global.Agent.%s.Actions", this.getPrimaryHandle());
    }
    
    private void __log(String message) {
        Log.message(String.format("[normative_agent] %s", message));
    }
}