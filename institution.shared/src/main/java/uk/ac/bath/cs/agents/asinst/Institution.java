package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.service.ServiceBroker;

import org.iids.aos.blackboardservice.BlackboardService;

class Institution {
    protected ServiceBroker _broker = null;
    protected String _name = "";
    
    protected InstitutionService _instService = null;
    protected BlackboardService _bbService = null;
    
    public Institution(ServiceBroker broker, String name) {
        this._setServiceBroker(broker);
        this._name = name;
        
        this.__log(String.format("Institution '%s' created", name));
    }
    
    public boolean checkExists() {
        return this._getInstService().checkExists(this._name);
    }
    
    public String hello() {
        return this._getInstService().hello(this._name);
    }
    
    public void participate(NormativeAgent a) {
        this._getInstService().addAgentParticipant(this._name, a.getPrimaryHandle(), a.getDataDomain());
    }
    
    protected InstitutionService _getInstService() {
        try {
            if (null == this._instService) {
                this._instService = this._getServiceBroker().bind(InstitutionService.class);
            }
        } catch (Exception e) { Log.message("There was an error using the service broker for the institution service."); }
        
        return this._instService;
    }
    
    protected BlackboardService _getBlackboardService() {
        try {
            if (null == this._bbService) {
                this._bbService = this._getServiceBroker().bind(BlackboardService.class);
            }
        } catch (Exception e) { Log.message("There was an error using the service broker for the blackboard service."); }
        
        return this._bbService;
    }
    
    protected void _setServiceBroker(ServiceBroker broker) {
        this._broker = broker;
    }
    
    protected ServiceBroker _getServiceBroker() {
        return this._broker;
    }
    
    private void __log(String message) {
        Log.message(String.format("[%s] %s", this._name, message));
    }
}