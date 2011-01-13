package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.service.AbstractDefaultService;
import org.iids.aos.systemservices.communicator.structs.ServiceID;

public class InstitutionServiceImpl extends AbstractDefaultService implements InstitutionService {
    private String _message = "[no saved message]";
    
    public InstitutionServiceImpl() {
        Log.message("Institution service started");
    }

	@Override
	public String hello() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceID getInstitutionServiceId() {
		return this.getServiceID();
	}
}