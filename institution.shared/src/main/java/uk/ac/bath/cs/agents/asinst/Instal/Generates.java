package uk.ac.bath.cs.agents.asinst.instal;

import java.util.ArrayList;
import java.util.Iterator;

public class Generates {
    protected Event _event;
    protected ArrayList<Event> _generates = new ArrayList<Event>();
    
    public Generates(Event e) {
        this._event = e;
    }
    
    public Generates generates(Event e) {
        this._generates.add(e); return this;
    }
    
    protected String _generatesToString() {
        return this.__join(this._generates, ", ");
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
    
    public String toString() {
        return String.format("%s generates %s\n", this._event, this._generatesToString());
    }
}