all: shared institution factory example

factory:
	cd institution.factory && mvn clean compile install

institution:
	cd institution && mvn clean compile install

example:
	cd example && mvn clean compile install

shared:
	cd institution.shared && mvn clean compile install

clean: 
	rm -f ~/agentscape/lib/shared/institution.shared-0.0.1.jar ~/agentscape/lib/services/institution-0.0.1.jar ~/agentscape/lib/services/institution.factory-0.0.1.jar ~/agentscape/agents/example-0.0.1.jar

.PHONY: factory institution example shared clean
