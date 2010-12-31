package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.service.AbstractDefaultService;

import uk.ac.bath.cs.agents.asinst.InstitutionFactoryService;
import uk.ac.bath.cs.agents.asinst.Log;

public class InstitutionFactoryServiceImpl extends AbstractDefaultService implements InstitutionFactoryService {
    public InstitutionFactoryServiceImpl() {
        Log.message("Institution factory service started");
    }
    
    public String hello() {
        return "hello from InstitutionFactoryServiceImpl";
    }
}