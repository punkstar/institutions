package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.directoryservice.DSRecordIdentifier;
import org.iids.aos.service.Service;

public interface InstitutionFactoryService extends Service {
    public DSRecordIdentifier createInstitution(String name, String instal);
}