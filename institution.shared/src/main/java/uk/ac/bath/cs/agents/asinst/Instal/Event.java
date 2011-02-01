package uk.ac.bath.cs.agents.asinst.instal;

import java.util.ArrayList;

public class Event extends Abstract {
    protected String _type;
    protected String _name;
    
    public Event(String type, String name) {
        this._type = type;
        this._name = name;
    }
    
    public String toStringDefinition() {
        return String.format("%s event %s;\n", this._type, this.toString());
    }
    
    public String toString() {
        return String.format("%s(%s)", this._name, this._parametersToString());
    }
}