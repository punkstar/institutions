package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.blackboardservice.BlackboardItem;

public class WgridAgent extends NormativeAgent {
	protected void _incomingSubscription(String from, BlackboardItem payload, PubsubType type) {
		this.__log(String.format("Incoming subscription from %s", from));
	}
	
	public void run() {
		this.__log("Starting..");
		super.run();
	}
	
	public void cleanup() {
		this.__log("Exiting..");
	}
	
    private void __log(String message) {
        Log.message(String.format("[wgrid-%s]: %s", this.getPrimaryHandle(), message));
    }
}
