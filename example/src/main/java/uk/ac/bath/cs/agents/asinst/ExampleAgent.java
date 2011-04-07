package uk.ac.bath.cs.agents.asinst;

import java.net.URL;

import org.iids.aos.blackboardservice.BlackboardItem;

import uk.ac.bath.cs.agents.instal.CreationEvent;
import uk.ac.bath.cs.agents.instal.DissolutionEvent;
import uk.ac.bath.cs.agents.instal.Domain;
import uk.ac.bath.cs.agents.instal.ExogenousEvent;
import uk.ac.bath.cs.agents.instal.Fluent;
import uk.ac.bath.cs.agents.instal.Generates;
import uk.ac.bath.cs.agents.instal.Initiates;
import uk.ac.bath.cs.agents.instal.Institution;
import uk.ac.bath.cs.agents.instal.NormativeEvent;
import uk.ac.bath.cs.agents.instal.Obligation;
import uk.ac.bath.cs.agents.instal.Terminates;
import uk.ac.bath.cs.agents.instal.Type;

public class ExampleAgent extends NormativeAgent {
    public ExampleAgent() {
        super();
    }
    
    @Override
    public void run() {
    	super.run();
    	
        this.__log("Example agent has started");
        
        try {
    	    Domain d = new Domain();
    	    Institution inst = new Institution("dutch", 3);
    	    
    	    Type bidder = new Type("Bidder");
    	    Type auct = new Type("Auct");
    	    
    	    d.concreteType(bidder, "bidder_1")
    	     .concreteType(bidder, "bidder_2")
    	     .concreteType(bidder, "bidder_3")
    	     .concreteType(auct,   "auctioneer_1");
    	    
    	    CreationEvent createdar = new CreationEvent("createdar");
    	    
    	    ExogenousEvent priceto = new ExogenousEvent("priceto");
    	    ExogenousEvent bidto = new ExogenousEvent("bidto");
    	    ExogenousEvent desto = new ExogenousEvent("desto");
    	    
    	    ExogenousEvent annprice = new ExogenousEvent("annprice");
    	    ExogenousEvent annbid = new ExogenousEvent("annbid");
    	    ExogenousEvent annconf = new ExogenousEvent("annconf");
    	    ExogenousEvent annsold = new ExogenousEvent("annsold");
    	    ExogenousEvent annunsold = new ExogenousEvent("annunsold");
    	    
    	    annprice.addParameter(auct).addParameter(bidder);
    	    annbid.addParameter(bidder).addParameter(auct);
    	    annconf.addParameter(auct).addParameter(bidder);
    	    annsold.addParameter(auct).addParameter(bidder);
    	    annunsold.addParameter(auct).addParameter(bidder);
    	    
    	    NormativeEvent pricedl = new NormativeEvent("pricedl");
    	    NormativeEvent biddl = new NormativeEvent("biddl");
    	    NormativeEvent desdl = new NormativeEvent("desdl");
    	    
    	    NormativeEvent price = new NormativeEvent("price");
    	    NormativeEvent bid = new NormativeEvent("bid");
    	    NormativeEvent conf = new NormativeEvent("conf");
    	    NormativeEvent sold = new NormativeEvent("sold");
    	    NormativeEvent unsold = new NormativeEvent("unsold");
    	    
    	    price.addParameter(auct).addParameter(bidder);
    	    bid.addParameter(bidder).addParameter(auct);
    	    conf.addParameter(auct).addParameter(bidder);
    	    sold.addParameter(auct).addParameter(bidder);
    	    unsold.addParameter(auct).addParameter(bidder);
    	    
    	    DissolutionEvent badgov = new DissolutionEvent("badgov");
    	    DissolutionEvent finished = new DissolutionEvent("finished");
    	    
    	    NormativeEvent alerted = new NormativeEvent("alerted");
    	    
    	    alerted.addParameter(bidder);
    	    
    	    Fluent onlybidder = new Fluent("onlybidder");
    	    Fluent havebid = new Fluent("havebid");
    	    Fluent conflict = new Fluent("conflict");
    	    
    	    onlybidder.addParameter(bidder);
    	    
    	    inst.type(bidder)
    	        .type(auct)
    	        .event(createdar)
    	        .event(priceto)
    	        .event(bidto)
    	        .event(desto)
    	        .event(annprice)
    	        .event(annbid)
    	        .event(annconf)
    	        .event(annsold)
    	        .event(annunsold)
    	        .event(pricedl)
    	        .event(biddl)
    	        .event(desdl)
    	        .event(price)
    	        .event(bid)
    	        .event(conf)
    	        .event(sold)
    	        .event(unsold)
    	        .event(badgov)
    	        .event(finished)
    	        .event(alerted)
    	        .fluent(onlybidder)
    	        .fluent(havebid)
    	        .fluent(conflict)
    	        .initially(price.pow("A", "B").initially())
    	        .initially(price.perm("A", "B").initially())
    	        .initially(annprice.perm("A", "B").initially())
    	        .initially(badgov.perm().initially())
    	        .initially(badgov.pow().initially())
    	        .initially(pricedl.perm().initially())
    	        .initially(pricedl.pow().initially())
    	        .initially(priceto.perm().initially())
    	        .initially(biddl.perm().initially())
    	        .initially(bidto.perm().initially())
    	        .initially(desto.perm().initially());
    	    
    	    /**
    	     * Phase 1: Pricing
    	     */
    	    Obligation obl = new Obligation();
    	    
    	    obl.act(price, "A", "B").before(pricedl).otherwise(badgov);
    	    
    	    Generates g1 = new Generates(annprice, "A", "B");
    	    
    	    g1.result(price, "A", "B");
    	    
    	    Terminates t1 = new Terminates(price, "A", "B");
    	    
    	    t1.result(price.pow(), "A", "B");
    	    
    	    Initiates i1 = new Initiates(price, "A", "B");
    	    
    	    i1.result(bid.pow(), "B", "A")
    	      .result(bid.perm(), "B", "A")
    	      .result(annbid.perm(), "B", "A");
    	    
    	    inst.obl(obl)
    	        .generates(g1)
    	        .terminates(t1)
    	        .initiates(i1);
    	    
    	    /**
    	     * Phase 2: Bidding
    	     */
    	    
    	    Generates g2 = new Generates(annbid, "A", "B");
    	    
    	    g2.result(bid, "A", "B");
    	    
    	    Terminates t2 = new Terminates(bid, "B", "A");
            Initiates i2 = new Initiates(bid, "B", "A");
            Terminates t3 = new Terminates(bid, "B", "A");
            Initiates i3 = new Initiates(bid, "B", "A");
    	    
            t2.result(bid.pow(), "B", "A")
              .result(bid.perm(), "B", "A")
              .result(annbid.perm(), "B", "A");
            
            i2.result(havebid)
              .result(onlybidder, "B")
              .condition(false, havebid);
            
            t3.result(onlybidder).condition(havebid);
            
            i3.result(conflict).condition(havebid);
            
            inst.generates(g2)
                .terminates(t2)
                .initiates(i2)
                .terminates(t3)
                .initiates(i3);
            
            /**
             * Phase 3: Resolution
             */
            
            Generates g3 = new Generates(annsold, "A", "B");
            Generates g4 = new Generates(unsold, "A", "B");
            Generates g5 = new Generates(conf, "A", "B");
            
            g3.result(sold, "A", "B");
            g4.result(unsold, "A", "B");
            g5.result(conf, "A", "B");
            
            Terminates t4 = new Terminates(biddl);
            
            t4.result(bid.pow(), "B", "A");
            
            Initiates i4 = new Initiates(biddl);
            Initiates i5 = new Initiates(biddl);
            Initiates i6 = new Initiates(biddl);
            Initiates i7 = new Initiates(biddl);
            Generates g6 = new Generates(unsold, "A", "B");
            Generates g7 = new Generates(sold, "A", "B");
            Generates g8 = new Generates(conf, "A", "B");
            Terminates t5 = new Terminates(alerted, "B");
            
            i4.result(sold.pow(), "A", "B")
              .result(unsold.pow(), "A", "B")
              .result(conf.pow(), "A", "B")
              .result(alerted.pow(), "B")
              .result(alerted.perm(), "B");
            
            Obligation obl_i5 = new Obligation();
            inst.obl(obl_i5);
            i5.result(annunsold.perm(), "A", "B")
              .result(unsold.perm(), "A", "B")
              .result(obl_i5.act(unsold, "A", "B").before(desdl).otherwise(badgov))
              .condition(false, havebid);
            
            Obligation obl_i6 = new Obligation();
            inst.obl(obl_i6);
            i6.result(annsold.perm(), "A", "B")
              .result(sold.perm(), "A", "B")
              .result(obl_i6.act(sold, "A", "B").before(desdl).otherwise(badgov))
              .condition(havebid)
              .condition(false, conflict);
            
            Obligation obl_i7 = new Obligation();
            inst.obl(obl_i7);
            i7.result(annconf.perm(), "A", "B")
              .result(conf.perm(), "A", "B")
              .result(obl_i7.act(conf, "A", "B").before(desdl).otherwise(badgov))
              .condition(havebid)
              .condition(conflict);
            
            g6.result(alerted, "B");
            g7.result(alerted, "B");
            g8.result(alerted, "B");
            
            t5.result(unsold.pow(), "A", "B")
              .result(unsold.perm(), "A", "B")
              .result(sold.pow(), "A", "B")
              .result(conf.pow(), "A", "B")
              .result(alerted.pow(), "B")
              .result(sold.perm(), "A", "B")
              .result(conf.perm(), "A", "B")
              .result(alerted.perm(), "B")
              .result(annconf.perm(), "A", "B")
              .result(annsold.perm(), "A", "B")
              .result(annunsold.perm(), "A", "B");
            
            Generates g9 = new Generates(desdl);
            Terminates t6 = new Terminates(desdl);
            Initiates i8 = new Initiates(desdl);
            
            Obligation obl_i9 = new Obligation();
            inst.obl(obl_i9);
            g9.result(finished).condition(false, conflict);
            t6.result(havebid).result(conflict).result(annconf.perm(), "A", "B");
            i8.result(price.pow(), "A", "B")
              .result(price.perm(), "A", "B")
              .result(annprice.perm(), "A", "B")
              .result(pricedl.perm())
              .result(pricedl.pow())
              .result(obl_i9.act(price, "A", "B").before(pricedl).otherwise(badgov))
              .condition(conflict);
            
            Generates g10 = new Generates(priceto);
            Terminates t7 = new Terminates(pricedl);
            Initiates i9 = new Initiates(pricedl);
            
            g10.result(pricedl);
            t7.result(pricedl.pow());
            i9.result(biddl.pow());
            
            Generates g11 = new Generates(bidto);
            Terminates t8 = new Terminates(biddl);
            Initiates i10 = new Initiates(biddl);
            
            g11.result(biddl);
            t8.result(pricedl.pow());
            i10.result(biddl.pow());
            
            Generates g12 = new Generates(desto);
            Terminates t9 = new Terminates(desdl);
            
            g12.result(desdl);
            t9.result(desdl.pow());
            
            inst.generates(g3)
                .generates(g4)
                .generates(g5)
                .generates(g6)
                .generates(g7)
                .generates(g8)
                .generates(g9)
                .generates(g10)
                .generates(g11)
                .generates(g12)
                .terminates(t4)
                .terminates(t5)
                .terminates(t6)
                .terminates(t7)
                .terminates(t8)
                .terminates(t9)
                .initiates(i4)
                .initiates(i5)
                .initiates(i6)
                .initiates(i7)
                .initiates(i8)
                .initiates(i9)
                .initiates(i10);
            
            InstitutionService service =  this._getInstitutionService();
            
//            this.__log("Registering template");
//            InstitutionTemplateIdentifier template = service.addInstitutionTemplate(inst, "dutch");
//
//            this.__log("Registered template with identifier: " + template);
//            InstitutionIdentifier instance = service.instantiateInstitution(template, d);
//
//            this._subscribeInstitutionalChanges(instance);
//            
//            for(int i = 0; i< 10; i++) {
//            	this._publishInstitutionalEvent(instance, annbid, new String[] {"bidder_1", "auctioneer_1"});
//            }
//            
//            this.__log(
//            	String.format(
//            		"Does perm(annprice(auctioneer_1,bidder_3)) hold? %s",
//            		this._getInstitutionService().getCurrentFluents(instance).hasPermission("annprice(auctioneer_1,bidder_3)")
//            	)
//            );
            
            service.addInstitutionTemplate(new URL("http://bartley.local:8080/wgrid.ial"), "wgrid from a URL");
        } catch (Exception e) {
            this.__log("There was an exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    protected ClingoService _getClingoService() {
    	try {
			return this.getServiceBroker().bind(ClingoService.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
    }
        
    public void cleanUp() {
        this.__log("Example agent has finished");
    }
    
    private void __log(String message) {
        Log.message(String.format("[example_agent]: %s", message));
    }

	protected void _incomingSubscription(String from, BlackboardItem bb_item, PubsubType type) {
		this.__log(String.format("Incoming subscription from (%s) %s", type, from));
		
		switch (type) {
			case AGENT_ACTION:
				// Fix ClassNotFoundException for ClingoResponse: http://www.agentscape.org/forums/viewtopic.php?pid=258#p258
		        ClassLoader original = Thread.currentThread().getContextClassLoader();
		        Thread.currentThread().setContextClassLoader(FluentSet.class.getClassLoader());
				
		        FluentSet fluents = (FluentSet) bb_item.getData();
				
		        // when finished put back the original class loader
		        Thread.currentThread().setContextClassLoader(original);
		        break;
		}
	}
}