package uk.ac.bath.cs.agents.asinst;

import uk.ac.bath.cs.agents.instal.Domain;
import uk.ac.bath.cs.agents.instal.Institution;
import uk.ac.bath.cs.agents.instal.asp.AnsProlog;
import uk.ac.bath.cs.agents.instal.asp.InstalASPTranslator;

/**
 * A class that holds the state of the institution
 * 
 * @author nrj
 *
 */

public class InstitutionInstance {
	protected Institution _i;
	protected Domain _d;
	
	protected FluentSet _holds = new FluentSet();
	
	public InstitutionInstance(Institution i, Domain d) {
		this._i = i;
		this._d = d;
	}
	
	public boolean doesHold(String test) {
		for(int i = 0; i < this._holds.size(); i++) {
			if (this._holds.get(i).equals(test)) {
				return true;
			}
		}
		
		return false;
	}
	
	public FluentSet getHolds() {
		return this._holds;
	}
	
	public void setHolds(String[] fluents) {
		this._holds = new FluentSet(fluents);
	}
	
	public boolean changedHolds(String[] fluents) {
		return this._holds.equals(new FluentSet(fluents));
	}
	
	/**
	 * Using the institution specification, provide the ASP
	 * 
	 * @return
	 */
	public String asAsp() {
		InstalASPTranslator translator = new AnsProlog(this._i, this._d);
		
		translator.generate();
		
		StringBuilder builder = new StringBuilder(translator.toString());
		
		builder.append("\n% Appending instance previous fluents\n");
		
		for(int i = 0; i < this._holds.size(); i++) {
			builder.append("ifluent(").append(this._holds.get(i)).append(").\n");
		}
		
		return builder.toString();
	}
	
	public String asAsp(String event) {
		StringBuilder builder = new StringBuilder(this.asAsp());
		
		builder.append("\n% The following event just occurred\n")
			   .append("observed(")
		       .append(event)
		       .append(", i00).");
		
		return builder.toString();
	}
}