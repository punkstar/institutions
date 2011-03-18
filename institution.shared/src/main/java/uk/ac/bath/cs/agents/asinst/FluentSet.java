package uk.ac.bath.cs.agents.asinst;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A collection of strings, representing the fluents of a system
 * 
 * @author Nick Jones <nj210@bath.ac.uk>
 *
 */
public class FluentSet extends ArrayList<String> {	
	
	protected static Pattern PATTERN_POW = Pattern.compile("pow\\((.*)\\)");
	protected static Pattern PATTERN_PERM = Pattern.compile("perm\\((.*)\\)");
	
	protected ArrayList<String> _powCache = null;
	protected ArrayList<String> _permCache = null;
	
	public FluentSet() { this(new String[] {}); }
	public FluentSet(String[] fluents) {
		for (int i = 0; i < fluents.length; i++) {
			this.add(fluents[i]);
		}
	}
	
	/**
	 * Searches the collection for a fluent with the exact specified signature
	 * 
	 * @param signature
	 * @return
	 */
	protected boolean hasFluent(String signature) {
		return this.contains(signature);
	}
	
	/**
	 * Searches the collection for a fluent equaling pow($signature)
	 * 
	 * @param signature
	 * @return
	 */
	protected boolean hasPower(String signature) {
		this._computeCache();
		return this._powCache.contains(signature);
	}
	
	/**
	 * Searches the collection for a fluent equaling perm($signature)
	 * 
	 * @param signature
	 * @return
	 */
	protected boolean hasPermission(String signature) {
		this._computeCache();
		return this._permCache.contains(signature);
	}
	
	public boolean equals(FluentSet o) {
		if (o.size() == this.size()) {
			Iterator<String> iter = this.iterator();
			while (iter.hasNext()) {
				if (!o.contains(iter.next())) {
					return false;
				}
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Builds a cache of pow/perm fluents by running a simple regex over the collection
	 * contents.  Is only build once per FluentSet.
	 */
	private void _computeCache() {
		
		if (this._powCache != null && this._permCache != null) {
			return;
		}
		
		this._powCache = new ArrayList<String>();
		this._permCache = new ArrayList<String>();
		
		Iterator<String> iter = this.iterator();
		while (iter.hasNext()) {
			String i = iter.next();
			
			//this.__log(i);
			
			Matcher m_pow = FluentSet.PATTERN_POW.matcher(i);
			Matcher m_perm = FluentSet.PATTERN_PERM.matcher(i);
			
			try {
				while(m_pow.find()) {
					this._powCache.add(m_pow.group(1));
					
					//this.__log(String.format("Pow: %s -> %s", m_pow.group(), m_pow.group(1)));
				}
			} catch (Exception e) {};
			
			try {
				while(m_perm.find()) {
					this._permCache.add(m_perm.group(1));				
					
					//this.__log(String.format("Perm: %s -> %s", m_perm.group(), m_perm.group(1)));
				}
			} catch (Exception e) {};
		}
	}
	
	private void __log(String message) {
		Log.message(String.format("[fluentset] %s", message));
	}
}
