import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import uk.ac.bath.cs.agents.asinst.ClingoResponse;


public class ClingoTest {
	public static void main(String[] args) throws IOException {
//		String command = "/Users/nrj/bin/clingo -n 1 /Users/nrj/Desktop/institutions/clingo/src/main/asp/alltrace.lp /Users/nrj/Desktop/institutions/clingo/src/main/asp/basic.lp /Users/nrj/agentscape/asp/clingo748801100880521481.tmp";
//	
//		Process p = Runtime.getRuntime().exec(command);
//		InputStream p_out = p.getInputStream();
//		InputStream p_err = p.getErrorStream();
//		
//		BufferedReader p_out_reader = new BufferedReader(new InputStreamReader(p_out));
//		
//		StringBuilder builder = new StringBuilder();
//		
//		String line;
//		while((line = p_out_reader.readLine()) != null) {
//			System.out.println(line);
// 		}
//		
//		p_out_reader.close();
//		p_out.close();
//		p_err.close();
		
//		return builder.toString();
//		
		String raw = "% warning: auct/1 is never defined\n" + 
				"% warning: bidder/1 is never defined\n" + 
				"Answer: 1\n" + 
				"inst(dutch) ifluent(perm(badgov)) ifluent(pow(badgov)) ifluent(perm(pricedl)) ifluent(pow(pricedl)) ifluent(perm(priceto)) ifluent(perm(biddl)) ifluent(perm(bidto)) ifluent(perm(desto)) event(createdar) evtype(createdar,ex) evinst(createdar,dutch) event(viol(createdar)) evtype(viol(createdar),viol) evinst(viol(createdar),dutch) event(priceto) evtype(priceto,ex) evinst(priceto,dutch) event(viol(priceto)) evtype(viol(priceto),viol) evinst(viol(priceto),dutch) event(bidto) evtype(bidto,ex) evinst(bidto,dutch) event(viol(bidto)) evtype(viol(bidto),viol) evinst(viol(bidto),dutch) event(desto) evtype(desto,ex) evinst(desto,dutch) event(viol(desto)) evtype(viol(desto),viol) evinst(viol(desto),dutch) event(annprice) evtype(annprice,ex) evinst(annprice,dutch) event(viol(annprice)) evtype(viol(annprice),viol) evinst(viol(annprice),dutch) event(annbid) evtype(annbid,ex) evinst(annbid,dutch) event(viol(annbid)) evtype(viol(annbid),viol) evinst(viol(annbid),dutch) event(annconf) evtype(annconf,ex) evinst(annconf,dutch) event(viol(annconf)) evtype(viol(annconf),viol) evinst(viol(annconf),dutch) event(annsold) evtype(annsold,ex) evinst(annsold,dutch) event(viol(annsold)) evtype(viol(annsold),viol) evinst(viol(annsold),dutch) event(annunsold) evtype(annunsold,ex) evinst(annunsold,dutch) event(viol(annunsold)) evtype(viol(annunsold),viol) evinst(viol(annunsold),dutch) event(pricedl) evtype(pricedl,inst) evinst(pricedl,dutch) event(viol(pricedl)) evtype(viol(pricedl),viol) evinst(viol(pricedl),dutch) event(biddl) evtype(biddl,inst) evinst(biddl,dutch) event(viol(biddl)) evtype(viol(biddl),viol) evinst(viol(biddl),dutch) event(desdl) evtype(desdl,inst) evinst(desdl,dutch) event(viol(desdl)) evtype(viol(desdl),viol) evinst(viol(desdl),dutch) event(price) evtype(price,inst) evinst(price,dutch) event(viol(price)) evtype(viol(price),viol) evinst(viol(price),dutch) event(bid) evtype(bid,inst) evinst(bid,dutch) event(viol(bid)) evtype(viol(bid),viol) evinst(viol(bid),dutch) event(conf) evtype(conf,inst) evinst(conf,dutch) event(viol(conf)) evtype(viol(conf),viol) evinst(viol(conf),dutch) event(sold) evtype(sold,inst) evinst(sold,dutch) event(viol(sold)) evtype(viol(sold),viol) evinst(viol(sold),dutch) event(unsold) evtype(unsold,inst) evinst(unsold,dutch) event(viol(unsold)) evtype(viol(unsold),viol) evinst(viol(unsold),dutch) event(badgov) evtype(badgov,diss) evinst(badgov,dutch) event(viol(badgov)) evtype(viol(badgov),viol) evinst(viol(badgov),dutch) event(finished) evtype(finished,diss) evinst(finished,dutch) event(viol(finished)) evtype(viol(finished),viol) evinst(viol(finished),dutch) event(alerted) evtype(alerted,inst) evinst(alerted,dutch) event(viol(alerted)) evtype(viol(alerted),viol) evinst(viol(alerted),dutch) instant(i00) next(i00,i01) instant(i01) next(i01,i02) instant(i02) next(i02,i03) final(i03) occurred(createdar,i00) before(i01,i02) before(i00,i01) before(i00,i02) observed(annunsold,i00) observed(annunsold,i01) obs(i01) obs(i00) occurred(annunsold,i01) occurred(annunsold,i00) initiated(perm(badgov),i00) holdsat(perm(badgov),i01) holdsat(perm(badgov),i02) initiated(pow(badgov),i00) holdsat(pow(badgov),i01) holdsat(pow(badgov),i02) initiated(perm(pricedl),i00) holdsat(perm(pricedl),i01) holdsat(perm(pricedl),i02) initiated(pow(pricedl),i00) holdsat(pow(pricedl),i01) holdsat(pow(pricedl),i02) initiated(perm(priceto),i00) holdsat(perm(priceto),i01) holdsat(perm(priceto),i02) initiated(perm(biddl),i00) holdsat(perm(biddl),i01) holdsat(perm(biddl),i02) initiated(perm(bidto),i00) holdsat(perm(bidto),i01) holdsat(perm(bidto),i02) initiated(perm(desto),i00) holdsat(perm(desto),i01) holdsat(perm(desto),i02) \n" + 
				"SATISFIABLE\n" + 
				"\n" + 
				"Models      : 1+    \n" + 
				"Time        : 0.000\n" + 
				"  Prepare   : 0.000\n" + 
				"  Prepro.   : 0.000\n" + 
				"  Solving   : 0.000";
		
		ClingoResponse result = ClingoResponse.build(raw);
		String[] facts = result.getHolds();
		for (int i = 0; i < facts.length; i++) {
			System.out.println(String.format("Fact: %s", facts[i]));
		}
	}
}
