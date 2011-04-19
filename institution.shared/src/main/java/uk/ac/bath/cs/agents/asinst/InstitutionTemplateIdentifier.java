package uk.ac.bath.cs.agents.asinst;

import java.io.Serializable;

public class InstitutionTemplateIdentifier extends Identifier {
	public static int instanceCount = 0;
	
	protected String _description;
	
	public InstitutionTemplateIdentifier(String description) {
		super();
		this._description = description;
	}
	
	public String getDescription() {
		return this._description;
	}
	
	public String toString() {
		return this.getDescription();
	}
	
	@Override
	public boolean equals(Object i) {
		return super.equals(i);
	}
}
