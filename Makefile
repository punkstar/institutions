all: shared institution factory agent clingo instal

instal:
	cd institution.instal && mvn clean compile install

factory:
	cd institution.factory && mvn clean compile install

clingo: shared
	cd clingo && mvn clean compile install

institution:
	cd institution && mvn clean compile install

agent: instal shared
	cd example && mvn clean compile install

shared:
	cd institution.shared && mvn clean compile install

clean: 
	rm -f ~/agentscape/lib/shared/institution.shared-0.0.1.jar ~/agentscape/lib/services/institution-0.0.1.jar ~/agentscape/lib/services/institution.factory-0.0.1.jar ~/agentscape/agents/example-0.0.1.jar ~/agentscape/lib/services/clingo*

.PHONY: factory institution agent shared clean clingo open instal
