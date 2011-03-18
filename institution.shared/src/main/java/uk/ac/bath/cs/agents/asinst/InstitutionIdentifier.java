package uk.ac.bath.cs.agents.asinst;

public class InstitutionIdentifier extends Identifier {
	protected InstitutionTemplateIdentifier _template;
	protected int _id;
	
	protected String _hardcodedId;
	
	public InstitutionIdentifier(InstitutionTemplateIdentifier template) {
		super();
		this._id = ++InstitutionTemplateIdentifier.instanceCount;
		this._template = template;
	}
	
	protected InstitutionIdentifier(String id) {
		this._hardcodedId = id;
	}
	
	public InstitutionTemplateIdentifier getTemplate() {
		return this._template;
	}
	
	public String toString() {
		if (this._hardcodedId != null) {
			return this._hardcodedId;
		}
		
		return String.format("%s-%d", this._template.toString(), this._id);
	}
	
	public static InstitutionIdentifier build(String identifier) {
		return new InstitutionIdentifier(identifier);
	}
	
	/**
	 * The data domain for institutions publishing events that are occurring to the institution
	 * 
	 * @return
	 */
    public String getInboundDataDomain() {
    	return String.format("Platform.Global.Institution.Inbound.%s", this.toString());
    }
    
    /**
     * The data domain for institutions publishing events
     * 
     * @return
     */
    public String getOutboundDataDomain() {
    	return String.format("Platform.Global.Institution.Outbound.%s", this.toString());
    }
}
