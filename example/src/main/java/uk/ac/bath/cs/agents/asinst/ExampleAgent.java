package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.agent.Agent;
import org.iids.aos.directoryservice.DSRecordIdentifier;
import org.iids.aos.service.ServiceBroker;

public class ExampleAgent extends Agent {
    private ServiceBroker _broker;
    
    public ExampleAgent() {
        super();
    }
    
    @Override
    public void run() {
        Log.message("Example agent has started");
        
        InstitutionFactoryService service = this._getService();
        
        DSRecordIdentifier rid = service.createInstitution("phonecall", "{events:{violations:[e1,e2,e3,e4],fluents:[f1,f2,f3}}");
        
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