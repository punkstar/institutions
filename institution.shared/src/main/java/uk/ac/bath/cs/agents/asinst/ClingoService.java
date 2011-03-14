package uk.ac.bath.cs.agents.asinst;

import java.net.URL;

import org.iids.aos.service.Service;

public interface ClingoService extends Service {
	public ClingoResponse solve(String asp, int answer_sets) throws ClingoException;
	public ClingoResponse solve(String asp) throws ClingoException;
	public ClingoResponse solve(URL url, int answer_sets) throws ClingoException;
	public ClingoResponse solve(URL url) throws ClingoException;
}