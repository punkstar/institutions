package uk.ac.bath.cs.agents.asinst;

import java.io.Serializable;
import java.util.Random;

public abstract class Identifier implements Serializable, Comparable {
	protected String _uid;
	
	public Identifier() {
		this._uid = this.__generateUid();
	}
	
	public String getUid() {
		return this._uid;
	}
	
	public int compareTo(Object o) {
		Identifier i = (Identifier) o;
		
		if (i.getUid().equals(this.getUid())) {
			return 0;
		}
		
		return 1;
	}
	
	private String __generateUid() {
		Random r = new Random();
		return  Long.toString(Math.abs(r.nextLong()), 36);
	}
}
