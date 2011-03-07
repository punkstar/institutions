package uk.ac.bath.cs.agents.asinst;

import java.io.Serializable;

public class InstitutionIdentifier extends Identifier {
	protected InstitutionTemplateIdentifier _template;
	protected int _id;
	
	public InstitutionIdentifier(InstitutionTemplateIdentifier template) {
		super();
		this._id = ++InstitutionTemplateIdentifier.instanceCount;
		this._template = template;
	}
	
	public InstitutionTemplateIdentifier getTemplate() {
		return this._template;
	}
	
	public String toString() {
		return String.format("%s-%d", this._template.toString(), this._id);
	}
}
