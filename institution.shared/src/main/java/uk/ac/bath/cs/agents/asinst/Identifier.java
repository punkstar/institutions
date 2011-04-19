package uk.ac.bath.cs.agents.asinst;

import java.io.Serializable;
import java.util.Random;

public abstract class Identifier implements Serializable {
	protected String _uid;
	
	public Identifier() {
		this._uid = this.__generateUid();
	}
	
	public String getUid() {
		return this._uid;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o == this) return true;
		if (this.getClass() != o.getClass()) return false;
		    
		Identifier i = (Identifier) o;
		
		if (i.getUid().equals(this.getUid())) return true;
		
		return false;
	}
	
	private String __generateUid() {
		Random r = new Random();
		return  Long.toString(Math.abs(r.nextLong()), 36);
	}
}
