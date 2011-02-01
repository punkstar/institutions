package uk.ac.bath.cs.agents.asinst.instal;

public class Fluent extends Abstract {
    protected String _name;
    
    public Fluent(String name) {
        this._name = name;
    }
    
    public static Fluent obl(Event e, Event tr, Event fa) {
        Fluent f = new Fluent("obl");
        
        f.addParameter(e.toString())
         .addParameter(tr.toString())
         .addParameter(fa.toString());
         
        return f;
    }
    
    public static Fluent perm(Event e) {
        Fluent f = new Fluent("perm");
        
        f.addParameter(e.toString());
         
        return f;
    }
    
    public static Fluent pow(Event e) {
        Fluent f = new Fluent("pow");
        
        f.addParameter(e.toString());
         
        return f;
    }
    
    public String toStringDefinition() {
        return String.format("fluent %s;\n", this.toString());
    }
    
    public String toString() {
        return String.format("%s(%s)", this._name, this._parametersToString());
    }
}