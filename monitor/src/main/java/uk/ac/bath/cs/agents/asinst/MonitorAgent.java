package uk.ac.bath.cs.agents.asinst;

import java.util.ArrayList;

import org.iids.aos.agent.Agent;
import org.iids.aos.blackboardservice.BlackboardException;
import org.iids.aos.blackboardservice.BlackboardItem;
import org.iids.aos.blackboardservice.BlackboardQuery;
import org.iids.aos.blackboardservice.BlackboardService;
import org.iids.aos.blackboardservice.BlackboardService.IBlackboardNotification;
import org.iids.aos.service.ServiceBroker;

public class MonitorAgent extends Agent implements IBlackboardNotification {
	
	ArrayList<String> _subscribedStreams = new ArrayList<String>();
	
	/**
	 * Main agent entry point
	 */
	public void run() {
		this.__log("Agent started..");
		
		try {
			this._getBlackboard().subscribe(new BlackboardQuery("App.Global.Streams"), this);
			
			while (true) {
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			this.__log(String.format("There was an exception: %s", e.getMessage()));
		}
	}

	/**
	 * Incoming blackboard data subscription
	 */
	public void onData(BlackboardItem item) {
		String from = item.getProducerId();
		String data_domain = item.getMetaValue("data_domain").getValue().toString();
		String payload = null;
		
		
		PubsubType type = PubsubType.valueOf(item.getContentType());
		
		switch (type) {
		case NEW_STREAM:
			this._subscribe(item.getData().toString());
			break;
		case INST_FLUENTS:
			payload = item.getData(FluentSet.class.getClassLoader()).toString();
			break;
		default:
			payload = item.getData().toString();
		}
		
		this.__log(String.format("Incoming %s on %s", payload, data_domain));
		
		this._store(from, data_domain, payload);
	}
	
	protected BlackboardService _getBlackboard() throws Exception {
		ServiceBroker broker = this.getServiceBroker();
		BlackboardService bb = broker.bind(BlackboardService.class);
		
		return bb;
	}
	
	protected void _subscribe(final String data_domain) {
		final IBlackboardNotification parent = this;
		Thread t = new Thread() {
			public void run() {
				try {
					_getBlackboard().subscribe(new BlackboardQuery(data_domain), parent);
					__log(String.format("New stream: Subscribed to %s", data_domain));
				} catch (Exception e) {
					__log(String.format("New stream: Unable to subscribe to %s", data_domain));
				}
			}
		};
		
		t.start();
	}
	
	protected void _store(String from, String data_domain, String value) {
		this.__log(String.format("Store tuple: %s,%s,%s;", from, data_domain, value));
	}
	
	protected void __log(String message) {
		Log.message(String.format("[monitor] %s", message));
	}
}
