package uk.ac.bath.cs.agents.asinst;

import org.iids.aos.service.Service;

import uk.ac.bath.cs.agents.instal.InitiallyFluent;
import uk.ac.bath.cs.agents.instal.Institution;

public interface InstitutionService extends Service {
	abstract public InstitutionTemplateIdentifier addInstitutionTemplate(Institution i, String description);
	abstract public InstitutionIdentifier instantiateInstitution(InstitutionTemplateIdentifier template, InitiallyFluent[] initally) throws InstitutionNotFoundException;
	abstract public Institution getInstitutionInstance(InstitutionIdentifier i) throws InstitutionNotFoundException;
	
	public class InstitutionTemplateIdentifier {
		protected String _description;
		public InstitutionTemplateIdentifier(String description) {
			this._description = description;
		}
		
		public String getDescription() {
			return this._description;
		}
	}
	
	public class InstitutionIdentifier {
		protected InstitutionTemplateIdentifier _template;
		public InstitutionIdentifier(InstitutionTemplateIdentifier template) {
			this._template = template;
		}
		
		public InstitutionTemplateIdentifier getTemplate() {
			return this._template;
		}
	}
}