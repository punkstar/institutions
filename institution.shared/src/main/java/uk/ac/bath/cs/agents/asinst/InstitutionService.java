package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.service.Service;
import org.iids.aos.systemservices.communicator.structs.ServiceID;

public interface InstitutionService extends Service {
    public String hello();

    public ServiceID getInstitutionServiceId();
}