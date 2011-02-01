package uk.ac.bath.cs.agents.asinst.instal;

public class Type {
    protected String _name;
    
    public Type (String name) {
        this._name = name;
    }
    
    public String toString() {
        return String.format("type %s;\n", this._name);
    }
}