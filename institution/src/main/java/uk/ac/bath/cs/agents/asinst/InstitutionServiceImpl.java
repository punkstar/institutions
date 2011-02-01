package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.service.AbstractDefaultService;
import org.iids.aos.systemservices.communicator.structs.ServiceID;

import java.util.Hashtable;
import java.util.ArrayList;

import org.iids.aos.blackboardservice.BlackboardItem;
import org.iids.aos.blackboardservice.BlackboardQuery;
import org.iids.aos.blackboardservice.BlackboardService;
import org.iids.aos.blackboardservice.BlackboardService.IBlackboardNotification;

import org.iids.aos.systemservices.communicator.structs.AgentHandle;

public class InstitutionServiceImpl extends AbstractDefaultService implements InstitutionService, IBlackboardNotification {
    private Hashtable<String, VirtualInstitution> _insts = new Hashtable<String, VirtualInstitution>();
    private Hashtable<String, ArrayList<AgentHandle>> _participatingAgents = new Hashtable<String, ArrayList<AgentHandle>>();
    private Hashtable<String, Integer> _instCounter = new Hashtable<String, Integer>();
    
    public InstitutionServiceImpl() {
        Log.message("Institution service started");
    }

    @Override
    public String hello(String name) {
        if (!this._instCounter.containsKey(name)) {
            this._instCounter.put(name, 0);
        } else {
            this._instCounter.put(name, this._instCounter.get(name) + 1);
        }
    
        return "Hello from " + name + "(" + this._instCounter.get(name) + ")";
    }
    
    @Override
    public boolean checkExists(String name) {
        return this._insts.containsKey(name);
    }
    
    public void addAgentParticipant(String name, AgentHandle handle, String dataDomain) {
        this.__log(String.format("Subscribing to %s", dataDomain));
                
        if (this._participatingAgents.get(name) == null) {
            this._participatingAgents.put(name, new ArrayList<AgentHandle>());
        }
        
        this._participatingAgents.get(name).add(handle);
        
        try {
            BlackboardQuery bbQuery = new BlackboardQuery(dataDomain);
            this._getBlackboardService().subscribe(bbQuery, this);
        } catch (Exception e) {
            this.__log(String.format("Unable to subscribe to agent action feed: %s", e.getMessage()));
        }
    }
    
    protected BlackboardService _getBlackboardService() {
        try {
            return this.getServiceBroker().bind(BlackboardService.class);
        } catch (Exception e) { Log.message("There was an error using the service broker for the blackboard service."); }
        
        return null;
    }
    
    @Override
    public void onData(BlackboardItem item) {
        this.__log(String.format("Got bb data item: %s", item.getData().toString()));
    }
    
    private synchronized VirtualInstitution _getInst(String name) {
        if (this.checkExists(name)) {
            return this._insts.get(name);
        } else {
            return null;
            // throw new Exception("Institution does not exist.");
        }
    }
    
    private void __log(String message) {
        Log.message(String.format("[institution_service] %s", message));
    }
    
    private class VirtualInstitution {
        private String _name;
        private Instal _instal;
        private InstitutionService _instService;
        
        public VirtualInstitution(String name, Instal instal, InstitutionService instService) {
            this._instal = instal;
            this._instService = instService;
        }
        
        // Conventional generation, etc..
    }
}