all: shared instal institution agents clingo

instal:
	cd institution.instal && mvn clean compile install

clingo: shared
	cd clingo && mvn clean compile install

institution:
	cd institution && mvn clean compile install

agents: agent monitor

agent: shared
	cd example && mvn clean compile install

monitor:
	cd monitor && mvn clean compile install

shared:
	cd institution.shared && mvn clean compile install

clean: 
	rm -f ~/agentscape/lib/shared/institution.shared-0.0.1.jar ~/agentscape/lib/services/institution-0.0.1.jar ~/agentscape/lib/services/institution.-0.0.1.jar ~/agentscape/agents/example-0.0.1.jar ~/agentscape/lib/services/clingo*

.PHONY:  institution agent shared clean clingo open instal agents monitor
