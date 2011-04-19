package uk.ac.bath.cs.agents.asinst;

import java.net.URL;

import org.iids.aos.service.Service;

import uk.ac.bath.cs.agents.instal.Domain;
import uk.ac.bath.cs.agents.instal.InitiallyFluent;
import uk.ac.bath.cs.agents.instal.Institution;

public interface InstitutionService extends Service {
	abstract public InstitutionTemplateIdentifier addInstitutionTemplate(URL url, String description) throws ClingoException;
	abstract public InstitutionTemplateIdentifier addInstitutionTemplate(Institution i, String description);
	abstract public InstitutionIdentifier instantiateInstitution(InstitutionTemplateIdentifier template, Domain d, InitiallyFluent[] initially_fluents) throws InstitutionNotFoundException;
	abstract public InstitutionInstance getInstitutionInstance(InstitutionIdentifier i) throws InstitutionNotFoundException;
	abstract public void evaluate(InstitutionIdentifier i) throws InstitutionNotFoundException;
	abstract public FluentSet getCurrentFluents(InstitutionIdentifier i) throws InstitutionNotFoundException;
	abstract public void subscribeToActivityStream(InstitutionIdentifier i, String data_domain);
	abstract public FluentSet getHypotheticalFluents(InstitutionIdentifier i, String action);
}
