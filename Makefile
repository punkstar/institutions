all: shared institution factory example

factory:
	cd institution.factory && mvn clean compile install

institution:
	cd institution && mvn clean compile install

example:
	cd example && mvn clean compile install

shared:
	cd institution.shared && mvn clean compile install

.PHONY: factory institution example shared
