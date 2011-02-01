package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.directoryservice.DSRecordIdentifier;
import org.iids.aos.service.ServiceBroker;
import uk.ac.bath.cs.agents.asinst.instal.*;

public class ExampleAgent extends NormativeAgent {
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
            
            inst1a.participate(this); inst2a.participate(this);
            
            this._publishAction("connected_to_inst1");
            this._publishAction("connected_to_inst2");
            this._publishAction("connected_to_inst1_2nd_time");
            
            for (int i = 0; i < 10; i++) {
                this.__log("[inst1a]: " + inst1a.hello());
                this.__log("[inst2a]: " + inst2a.hello());
                this.__log("[inst1b]: " + inst1b.hello());
            }
            
            Instal inst = new Instal("nicks_inst");
            
            Terminates t1 = new Terminates(new Event("exogeneous", "event_name"));
            Terminates t2 = new Terminates(new Event("exogeneous", "another_event_name"));
            Initiates i1 = new Initiates(new Event("exogeneous", "event_name"));
            Initiates i2 = new Initiates(new Event("exogeneous", "another_event_name"));
            Generates g1 = new Generates(new Event("inst", "i_am_a_generated_event"));
            
            Fluent f1 = new Fluent("fluent_name");
            
            t1.fluent(f1);
            t2.fluent(f1).condition(true, f1);
            i1.fluent(f1).condition(false, f1);
            i2.fluent(f1);
            
            g1.generates(new Event("inst", "i_am_generated_by_the_generated_event")).generates(new Event("inst", "me_too"));
            
            inst.event("exogeneous", "exo_one")
                .event("create", "create_one", "param1", "param2")
                .type("Boolean")
                .type("Integer")
                .type("String")
                .fluent("alive", "Nick")
                .fluent("notrich", "Nick")
                .terminates(t1)
                .terminates(t2)
                .initiates(i1)
                .initiates(i2)
                .generates(g1);
                
            this.__log(inst.toString());
            
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