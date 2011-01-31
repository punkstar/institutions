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
        this.__log("Example agent has started");
        
        ServiceBroker service_broker = this.getServiceBroker();
        
        try {
            this.__log("Connecting to inst1a");
            Institution inst1a = new Institution(this.getServiceBroker(), "inst1");
        
            this.__log("Connecting to inst2a");
            Institution inst2a = new Institution(this.getServiceBroker(), "inst2");
        
            this.__log("Connecting to inst1a as inst1b");
            Institution inst1b = new Institution(this.getServiceBroker(), "inst1");
        
            for (int i = 0; i < 100; i++) {
                this.__log("[inst1a]: " + inst1a.hello());
                this.__log("[inst2a]: " + inst2a.hello());
                this.__log("[inst1b]: " + inst1b.hello());
            }
        } catch (Exception e) {
            this.__log("There was an exception: " + e.getMessage());
        }
    }
    
    public void cleanUp() {
        this.__log("Example agent has finished");
    }
    
    private void __log(String message) {
        Log.message(String.format("[example_agent]: %s", message));
    }
}