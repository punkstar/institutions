package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.service.AbstractDefaultService;
import org.iids.aos.systemservices.communicator.structs.ServiceID;

import java.util.Hashtable;

public class InstitutionServiceImpl extends AbstractDefaultService implements InstitutionService {
    private Hashtable<String, VirtualInstitution> _insts = new Hashtable<String, VirtualInstitution>();
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
    
    public void addExogenousEvent(String inst_name, ExogeneousEvent event) {
        this._getInst(inst_name).addExogeneousEvent(event);
    }
    
    @Override
    public boolean checkExists(String name) {
        return this._insts.containsKey(name);
    }
    
    private synchronized VirtualInstitution _getInst(String name) {
        if (this.checkExists(name)) {
            return this._insts.get(name);
        } else {
            return null;
            // throw new Exception("Institution does not exist.");
        }
    }
    
    private class VirtualInstitution {
        private String _name;
        private Instal _instal;
        private InstitutionService _instService;
        
        public VirtualInstitution(String name, Instal instal, InstitutionService instService) {
            this._instal = instal;
            this._instService = instService;
        }
        
        public void addExogeneousEvent(Event e) {
            
        }
        
        // Conventional generation, etc..
    }
}