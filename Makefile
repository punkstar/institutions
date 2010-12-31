all: shared institution factory example

factory:
	cd institution.factory && mvn install

institution:
	cd institution && mvn install -e

example:
	cd example && mvn install

shared:
	cd institution.shared && mvn install

.PHONY: factory institution example shared
