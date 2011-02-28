package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.service.Service;

public interface ClingoService extends Service {
	public String[] solve(String asp, int answer_sets);
	public String[] solve(String asp);
}