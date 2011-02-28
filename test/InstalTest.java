import uk.ac.bath.cs.agents.asinst.Instal;
import uk.ac.bath.cs.agents.asinst.instal.*;


public class InstalTest {
    public static void main(String[] args) {
        InstalTest a = new InstalTest();
        
        System.out.println(a.output());
    }
    
    Instal i = new Instal("nicks_inst");
    
    public InstalTest() {
        // Types
        Type tHandset = new Type("Handset");
        Type tChunk = new Type("Chunk");
        Type tTime = new Type("Time");
        Type tChannel = new Type("Channel");
        
        // Exogeneous Events
        Event eeClock = new Event("exogeneous", "clock");
        Event eeDownload = new Event("exogeneous", "download");
        Event eeSend = new Event("exogeneous", "send");
        
        eeDownload.addParameter("Handset").addParameter("Chunk").addParameter("Channel");
        eeSend.addParameter("Handset").addParameter("Chunk");
        
        // Normative Events
        Event ieDownload = new Event("inst", "intDownload");
        Event ieSend = new Event("inst", "intSend");
        Event ieReceive = new Event("inst", "intReceive");
        Event ieTransition = new Event("inst", "transition");
        
        ieDownload.addParameter("Handset").addParameter("Chunk").addParameter("Channel");
        ieSend.addParameter("Handset");
        ieReceive.addParameter("Handset").addParameter("Chunk");
        
        // Violations
        Event veMisuse = new Event("violation", "misuse");
        
        veMisuse.addParameter("Handset");
        
        // Fluents
        Fluent f1 = new Fluent("downloadChunk");
        Fluent f2 = new Fluent("hasChunk");
        Fluent f3 = new Fluent("areceive");
        Fluent f4 = new Fluent("asend");
        Fluent f5 = new Fluent("creceive");
        Fluent f6 = new Fluent("csend");
        Fluent f7 = new Fluent("transmit");
        Fluent f8 = new Fluent("previous");
        
        f1.addParameter("Handset").addParameter("Chunk");
        f2.addParameter("Handset").addParameter("Chunk");
        f3.addParameter("Handset").addParameter("Time");
        f4.addParameter("Handset").addParameter("Time");
        f5.addParameter("Handset").addParameter("Time");
        f6.addParameter("Handset").addParameter("Time");
        f7.addParameter("Channel").addParameter("Time");
        f8.addParameter("Time").addParameter("Time");
        
        // Non-inertial Fluents
        Fluent nif1 = new Fluent("busyHSending");
        Fluent nif2 = new Fluent("busyHReceiving");
        Fluent nif3 = new Fluent("busyBReceiving");
        Fluent nif4 = new Fluent("busyChannel");
        
        nif1.addParameter("Handset");
        nif2.addParameter("Handset");
        nif3.addParameter("Handset");
        nif4.addParameter("Channel");
        
        // Events
        Generates g1 = new Generates(eeDownload);
        
        g1.generates(ieDownload).if(false, nif4).if(false, nif3).if(false, nif1);
        
        this.i.type(tHandset)
              .type(tChunk)
              .type(tTime)
              .type(tChannel)
              
              .event(eeClock)
              .event(eeDownload)
              .event(eeSend)
              
              .event(ieDownload)
              .event(ieSend)
              .event(ieReceive)
              .event(ieTransition)
              
              .event(veMisuse)
              
              .fluent(f1)
              .fluent(f2)
              .fluent(f3)
              .fluent(f4)
              .fluent(f5)
              .fluent(f6)
              .fluent(f7)
              .fluent(f8)
              
              .fluent(nif1)
              .fluent(nif2)
              .fluent(nif3)
              .fluent(nif4);
    }
    
    public String output() {
        return i.toString();
    }
}