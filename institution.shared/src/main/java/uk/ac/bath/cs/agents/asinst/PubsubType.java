package uk.ac.bath.cs.agents.asinst;

public enum PubsubType {
	/**
	 * An agent has performed an action
	 */
	AGENT_ACTION,
	/**
	 * An institutional event has occurred
	 */
	INST_EVENT,
	/**
	 * A change in institutional fluents has occurred
	 */
	INST_FLUENTS,
	/**
	 * There is a new stream that agents/institutions can subscribe to!
	 */
	NEW_STREAM
}
