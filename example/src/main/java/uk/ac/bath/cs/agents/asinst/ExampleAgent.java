package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.agent.Agent;
import org.iids.aos.log.Log;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class ExampleAgent extends Agent {
    public void run() {
        _log("Example agent has started");
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