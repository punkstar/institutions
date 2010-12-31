package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.service.AbstractDefaultService;

import uk.ac.bath.cs.agents.asinst.InstitutionService;
import uk.ac.bath.cs.agents.asinst.Log;

public class InstitutionServiceImpl extends AbstractDefaultService implements InstitutionService {
    private String _message = "[no saved message]";
    
    public InstitutionServiceImpl() {
        Log.message("Institution service started");
    }
    
    public String hello() {
        return "hello from InstitutionServiceImpl";
    }
}