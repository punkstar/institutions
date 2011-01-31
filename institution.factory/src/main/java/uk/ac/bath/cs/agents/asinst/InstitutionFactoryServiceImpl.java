package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.directoryservice.DSRecordIdentifier;
import org.iids.aos.directoryservice.DirectoryService;
import org.iids.aos.directoryservice.DirectoryServiceException;
import org.iids.aos.service.AbstractDefaultService;
import org.iids.aos.service.ServiceBroker;

public class InstitutionFactoryServiceImpl extends AbstractDefaultService implements InstitutionFactoryService {
        //     ServiceBroker _broker = null;
        // 
        //     public InstitutionFactoryServiceImpl() {
        //         Log.message("Institution factory service started");
        //     }
        //     
        //     public DSRecordIdentifier createInstitution(String name, String instal) {
        //         
        // try {
        //  DirectoryService ds = this.getServiceBroker().bind(DirectoryService.class);
        //          InstitutionService inst = this._getServiceBroker().bind(InstitutionService.class);
        //          
        //          DSRecordIdentifier record_id = new DSRecordIdentifier(inst.getInstitutionServiceId());
        //          
        //             ds.createRecord(record_id);
        //  ds.addEntry(record_id, "name", name);
        //  
        //  return record_id;
        // } catch (Exception e1) {
        //  // TODO Auto-generated catch block
        //  e1.printStackTrace();
        // }
        //         
        //         return null;
        //     }
        //     
        //     protected ServiceBroker _getServiceBroker() {
        //         if (this._broker == null) {
        //             this._broker = this.getServiceBroker();
        //         }
        //         
        //         return this._broker;
        //     }
}