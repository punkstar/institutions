package uk.ac.bath.cs.agents.asinst.instal;

public class Terminates extends Causal {
    public Terminates(Event e) {
        super(e);
    }
    
    public String toString() {
        String result = String.format("%s terminates %s", this._event, this._fluentsToString());
        
        if (this._conditions.size() > 0) {
            result += String.format(" if %s", this._conditionsToString());
        }
        
        return String.format("%s;\n", result);
    }
}