package uk.ac.bath.cs.agents.asinst.instal;

public class Initiates extends Causal {
    public Initiates(Event e) {
        super(e);
    }
    
    public String toString() {
        String result = String.format("%s initiates %s", this._event, this._fluentsToString());

        if (this._conditions.size() > 0) {
            result += String.format(" if %s", this._conditionsToString());
        }
        
        return String.format("%s;\n", result);
    }
}