run: InstalTest

InstalTest.class: InstalTest.java
	javac -classpath /Users/nrj/Desktop/institutions/institution.shared/target/institution.shared-0.0.1.jar:./ $?

InstalTest: InstalTest.class
	java -classpath /Users/nrj/Desktop/institutions/institution.shared/target/institution.shared-0.0.1.jar:./ $@ 

clean:
	rm *.class
