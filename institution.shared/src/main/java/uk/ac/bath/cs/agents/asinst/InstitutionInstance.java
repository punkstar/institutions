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
	
	protected String[] _holds;
	
	public InstitutionInstance(Institution i) {
		this._i = i;
		this._holds = new String[] {};
	}
	
	public String[] getHolds() {
		return this._holds;
	}
	
	public void setHolds(String[] holds) {
		this._holds = holds;
	}
	
	public boolean doesHold(String test) {
		for(int i = 0; i < this._holds.length; i++) {
			if (this._holds[i].equals(test)) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Using the institution specification, provide the ASP
	 * 
	 * @return
	 */
	public String asAsp() {
		InstalASPTranslator translator = new AnsProlog(this._i, new Domain());
		
		translator.generate();
		
		StringBuilder builder = new StringBuilder(translator.toString());
		
		builder.append("\n% Appending instance previous fluents\n");
		
		for(int i = 0; i < this._holds.length; i++) {
			builder.append("ifluent(").append(this._holds[i]).append(").\n");
		}
		
		return builder.toString();
	}
	
	public String asAsp(String event) {
		StringBuilder builder = new StringBuilder(this.asAsp());
		
		builder.append("\n% The following event just occurred\n")
			   .append("occurred(")
		       .append(event)
		       .append(", i00).");
		
		return builder.toString();
	}
}