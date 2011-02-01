package uk.ac.bath.cs.agents.asinst;

import uk.ac.bath.cs.agents.asinst.instal.*;

import java.util.ArrayList;
import java.util.Iterator;

public class Instal {
    
    protected String _name;
    protected ArrayList<Type> _types = new ArrayList<Type>();
    protected ArrayList<Event> _events = new ArrayList<Event>();
    protected ArrayList<Fluent> _fluents = new ArrayList<Fluent>();
    protected ArrayList<Generates> _generates = new ArrayList<Generates>();
    protected ArrayList<Initiates> _initiates = new ArrayList<Initiates>();
    protected ArrayList<Terminates> _terminates = new ArrayList<Terminates>();
    
    public Instal(String name) {
        this._name = name;
    }
    
    public Instal type(String name) {
        Type t = new Type(name);
        this._types.add(t);
        
        return this;
    }
    
    public Instal event(String type, String name) {
        return this.event(new Event(type, name));
    }
    
    public Instal event(String type, String name, String ... parameters) {
        Event e = new Event(type, name);
        
        for (String parameter : parameters) {
            e.addParameter(parameter);
        }
        
        return this.event(e);
    }
    
    public Instal event(Event e) {
        this._events.add(e); return this;
    }
    
    public Instal fluent(String name) {
        Fluent f = new Fluent(name);
        return this.fluent(f);
    }
    
    public Instal fluent(String name, String ... parameters) {
        Fluent f = new Fluent(name);
        
        for (String parameter : parameters) {
            f.addParameter(parameter);
        }
        
        return this.fluent(f);
    }
    
    public Instal fluent(Fluent f) {
        this._fluents.add(f); return this;
    }
    
    public Instal obl(Event e, Event t, Event f) {
        this.fluent(Fluent.obl(e, f, t)); return this;
    }
    
    public Instal perm(Event e) {
        this.fluent(Fluent.perm(e)); return this;
    }
    
    public Instal pow(Event e) {
        this.fluent(Fluent.pow(e)); return this;
    }
    
    public Instal generates(Generates e) {
        this._generates.add(e); return this;
    }
    
    public Instal initiates(Initiates e) {
        this._initiates.add(e); return this;
    }
    
    public Instal terminates(Terminates e) {
        this._terminates.add(e); return this;
    }
    
    public String toString() {
        String instal = "";
        
        instal += String.format("institution %s;\n", this._name);
        
        Iterator<Type> iter_types = this._types.iterator();
        Iterator<Event> iter_events = this._events.iterator();
        Iterator<Fluent> iter_fluents = this._fluents.iterator();
        Iterator<Generates> iter_gen = this._generates.iterator();
        Iterator<Initiates> iter_init = this._initiates.iterator();
        Iterator<Terminates> iter_term = this._terminates.iterator();
        
        while (iter_types.hasNext()) {
            Type t = iter_types.next();
            instal += t.toString();
        }
        
        while (iter_events.hasNext()) {
            Event e = iter_events.next();
            instal += e.toStringDefinition();
        }
        
        while (iter_fluents.hasNext()) {
            Fluent f = iter_fluents.next();
            instal += f.toStringDefinition();
        }
        
        while (iter_gen.hasNext()) {
            Generates g = iter_gen.next();
            instal += g.toString();
        }
        
        while (iter_init.hasNext()) {
            Initiates i = iter_init.next();
            instal += i.toString();
        }
        
        while (iter_term.hasNext()) {
            Terminates t = iter_term.next();
            instal += t.toString();
        }
        
        return instal;
    }
}