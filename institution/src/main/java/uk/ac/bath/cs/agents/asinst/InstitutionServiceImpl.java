package uk.ac.bath.cs.agents.asinst;

import java.util.Hashtable;

import org.iids.aos.blackboardservice.BlackboardItem;
import org.iids.aos.blackboardservice.BlackboardService;
import org.iids.aos.blackboardservice.BlackboardService.IBlackboardNotification;
import org.iids.aos.service.AbstractDefaultService;

import uk.ac.bath.cs.agents.instal.InitiallyFluent;
import uk.ac.bath.cs.agents.instal.Institution;

/**
 * 
 * Institution Service
 * 
 *  - Maintains a list of "templates" that can be used to instantiate institutions
 *  - Maintains a list of institutions that have been instantiated
 *  	- Allows for the referencing of these instances between agents
 * 
 * @author Nick Jones <nj210@bath.ac.uk>
 *
 */

public class InstitutionServiceImpl extends AbstractDefaultService implements InstitutionService, IBlackboardNotification {
	Hashtable<InstitutionTemplateIdentifier, Institution> _templates = new Hashtable<InstitutionTemplateIdentifier, Institution>();
	Hashtable<InstitutionIdentifier, Institution> _instances = new Hashtable<InstitutionIdentifier, Institution>();
	
    public InstitutionServiceImpl() {
        this.__log("Started");
    }
    
    protected BlackboardService _getBlackboardService() {
        try {
            return this.getServiceBroker().bind(BlackboardService.class);
        } catch (Exception e) { Log.message("There was an error using the service broker for the blackboard service."); }
        
        return null;
    }
    
    public void onData(BlackboardItem item) {
        this.__log(String.format("Got bb data item: %s", item.getData().toString()));
    }
    
    private void __log(String message) {
        Log.message(String.format("[institution_service] %s", message));
    }

    /**
     * Add a blueprint of an institution to the library of institutions we can instantiate
     */
	public InstitutionTemplateIdentifier addInstitutionTemplate(Institution i, String description) {
		InstitutionTemplateIdentifier ident = new InstitutionTemplateIdentifier(description);
		this._templates.put(ident, i);
		return ident;
	}
	
	/**
	 * Check the existance of a template with the given identifier.
	 * 
	 * @param t
	 * @return
	 */
	protected boolean _existsInstitutionTemplate(InstitutionTemplateIdentifier t) {
		return this._templates.containsKey(t);
	}

	/**
	 * Get an already created instantiated institution
	 * @throws InstitutionNotFoundException 
	 */
	public Institution getInstitutionInstance(InstitutionIdentifier i) throws InstitutionNotFoundException {
		if (this._instances.containsKey(i)) {
			return this._instances.get(i);
		} else {
			throw new InstitutionNotFoundException(String.format("Instantiated Institution of type '%s', with identifier '%s' not found", i.getTemplate().getDescription(), i.toString()));
		}
	}

	/**
	 * Create a new instance of an institution
	 */
	public InstitutionIdentifier instantiateInstitution(InstitutionTemplateIdentifier template, InitiallyFluent[] initially) throws InstitutionNotFoundException {
		if (this._existsInstitutionTemplate(template)) {
			InstitutionIdentifier ident = new InstitutionIdentifier(template);
			Institution instance = this._templates.get(template);
			this._instances.put(ident, instance);
			
			return ident;
		} else {
			throw new InstitutionNotFoundException(String.format("Institution template with description '%s' not found", template.getDescription()));
		}
	}
}