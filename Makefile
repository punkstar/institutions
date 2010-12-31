all: institution factory example

factory:
	cd institution.factory && mvn install

institution:
	cd institution && mvn install

example:
	cd example && mvn install

.PHONY: factory institution example