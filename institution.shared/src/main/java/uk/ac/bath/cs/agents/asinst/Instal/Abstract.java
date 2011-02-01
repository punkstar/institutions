package uk.ac.bath.cs.agents.asinst.instal;

import java.util.ArrayList;
import java.util.AbstractCollection;
import java.util.Iterator;

import java.lang.StringBuilder;

abstract class Abstract {
    protected ArrayList<String> _parameters = new ArrayList<String>();
    
    public Abstract addParameter(String param_name) {
        this._parameters.add(param_name); return this;
    }
    
    protected String _parametersToString() {
        return this.__join(this._parameters, ", ");
    }
    
    private String __join(AbstractCollection<String> s, String delimiter) {
        if (s == null || s.isEmpty()) return "";
        Iterator<String> iter = s.iterator();
        StringBuilder builder = new StringBuilder(iter.next());
        while( iter.hasNext() )
        {
            builder.append(delimiter).append(iter.next());
        }
        return builder.toString();
    }
}