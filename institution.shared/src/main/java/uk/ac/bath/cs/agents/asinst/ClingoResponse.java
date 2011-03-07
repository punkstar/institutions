package uk.ac.bath.cs.agents.asinst;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClingoResponse implements Serializable {
	protected float _time;
	protected String[] _facts;
	protected String[] _holds;
	
	protected ClingoResponse(String[] facts, String[] holds, float execution_time) {
		this._facts = facts;
		this._holds = holds;
		this._time = execution_time;
	}
	
	public float getTime() {
		return this._time;
	}
	
	public String[] getFacts() {
		return this._facts;
	}
	
	public String[] getHolds() {
		return this._holds;
	}
	
	/**
	 * Have we got an answer set to work with?
	 * 
	 * TODO Implement
	 * @return
	 */
	public boolean wasSuccessful() {
		return true;
	}
	
	public static ClingoResponse build(String raw_output) {
		ArrayList<String> holds = new ArrayList<String>();
		ArrayList<String> facts = new ArrayList<String>();
		float time = 0.0f;
		
		// Find all the facts
		Pattern p_facts = Pattern.compile("\\w+\\(.*?\\) ");
		Matcher m_facts = p_facts.matcher(raw_output);
		
		while (m_facts.find()) {
			facts.add(m_facts.group());
		}
		
		// Find all holdsats
		Pattern p_holds = Pattern.compile("holdsat\\((.*?),i\\d+\\)");
		Matcher m_holds = p_holds.matcher(raw_output);
		
		while(m_holds.find()) {
			holds.add(m_holds.group());
		}
		
		// Find the execution time
		Pattern p_time = Pattern.compile("Time\\s+:\\s+(\\d.\\d+)");
		Matcher m_time = p_time.matcher(raw_output);
		
		while(m_time.find()) {
			time = Float.valueOf(m_time.group(1));
		}
		
		ClingoResponse response = new ClingoResponse(facts.toArray(new String [] {}), holds.toArray(new String [] {}), time);
		
		return response;
	}
}
