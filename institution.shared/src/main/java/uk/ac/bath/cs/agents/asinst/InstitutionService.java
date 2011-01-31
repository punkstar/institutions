package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.service.Service;
import org.iids.aos.systemservices.communicator.structs.ServiceID;
import org.iids.aos.systemservices.communicator.structs.AgentHandle;

public interface InstitutionService extends Service {
    public String hello(String name);

    public boolean checkExists(String name);
    public void addAgentParticipant(String name, AgentHandle handle, String dataDomain);
}