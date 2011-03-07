package uk.ac.bath.cs.agents.asinst;

import java.net.URL;

import org.iids.aos.service.Service;

public interface ClingoService extends Service {
	public ClingoResponse solve(String asp, int answer_sets);
	public ClingoResponse solve(String asp);
	public ClingoResponse solve(URL url, int answer_sets);
	public ClingoResponse solve(URL url);
}