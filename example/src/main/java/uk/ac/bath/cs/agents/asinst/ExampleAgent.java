package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.agent.Agent;
import org.iids.aos.service.ServiceBroker;

import uk.ac.bath.cs.agents.asinst.Log;
import uk.ac.bath.cs.agents.asinst.InstitutionFactoryService;

public class ExampleAgent extends Agent {
    private ServiceBroker _broker;
    
    @Override
    public void run() {
        Log.message("Example agent has started");
        
        Log.message(this._getService().hello());
        
        Log.message("Requested institution");
    }
    
    protected InstitutionFactoryService _getService() {
        try {
            return this._getServiceBroker().bind(InstitutionFactoryService.class);
        } catch (Exception e) {
            Log.message(String.format("Exception: %s", e.getMessage()));
            return null;
        }
    }
    
    protected ServiceBroker _getServiceBroker() {
        if (this._broker == null) {
            this._broker = this.getServiceBroker();
        }
        
        return this._broker;
    }
    
    public void cleanUp() {
        Log.message("Example agent has finished");
    }
}