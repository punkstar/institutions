package uk.ac.bath.cs.agents.asinst.instal;

import java.util.ArrayList;
import java.util.AbstractCollection;
import java.util.Iterator;

public abstract class Causal {
    protected Event _event;
    protected ArrayList<Fluent> _fluents = new ArrayList<Fluent>();
    protected ArrayList<Condition> _conditions = new ArrayList<Condition>();
    
    abstract public String toString();
    
    public Causal(Event e) {
        this._event = e;
    }
    
    public Causal fluent(Fluent f) {
        this._fluents.add(f); return this;
    }
    
    public Causal condition(boolean condition, Fluent f) {
        this._conditions.add(new Condition(condition, f)); return this;
    }
    
    protected String _fluentsToString() {
        return this.__join(this._fluents, ", ");
    }
    
    protected String _conditionsToString() {
        return this.__join(this._conditions, ", ");
    }
    
    private String __join(ArrayList s, String delimiter) {
        if (s == null || s.isEmpty()) return "";
        Iterator<Object> iter = s.iterator();
        StringBuilder builder = new StringBuilder(iter.next().toString());
        while( iter.hasNext() )
        {
            builder.append(delimiter).append(iter.next().toString());
        }
        return builder.toString();
    }
    
    protected class Condition {
        protected boolean _condition;
        protected Fluent _fluent;
        
        public Condition(boolean condition, Fluent f) {
            this._condition = condition;
            this._fluent = f;
        }
        
        public String toString() {
            return String.format("%s%s", (this._condition) ? "" : "not ", this._fluent.toString());
        }
    }
}