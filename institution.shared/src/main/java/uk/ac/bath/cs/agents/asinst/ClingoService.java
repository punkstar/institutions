package uk.ac.bath.cs.agents.asinst;

import java.net.URL;

import org.iids.aos.service.Service;

public interface ClingoService extends Service {
	public String[] solve(String asp, int answer_sets);
	public String[] solve(String asp);
	public String[] solve(URL url, int answer_sets);
	public String[] solve(URL url);
}