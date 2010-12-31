package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.agent.Agent;
import org.iids.aos.log.Log;
import org.iids.aos.service.ServiceBroker;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import uk.ac.bath.cs.agents.asinst.InstitutionService;

public class ExampleAgent extends Agent {
    private ServiceBroker _broker;
    
    public void run() {
        _log("Example agent has started");
        
        InstitutionService institution = this._requestInstitution();
        
        _log("Requested institution");
    }
    
    protected InstitutionService _requestInstitution() {
        try {
            return this._getServiceBroker().bind(InstitutionService.class);
        } catch (Exception e) {
            _log(String.format("Exception: %s", e.getMessage()));
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
        _log("Example agent has finished");
    }
    
    
    private void _log(String message) {
        Log.console(String.format("[%s] %s", _getTime(), message));
    }
    
    private String _getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        return sdf.format(cal.getTime());
    }
}