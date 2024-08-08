# Java compiler
JC = javac

# Java runtime
JR = java

# Compiler flags
JFLAGS = -g

# Source directories
MAINSRCDIR = src/main/java/RMI_Calculator
TESTSRCDIR = src/test/java

# Output directory for class files
BINDIR = bin

# RMI stub generator
RMIC = rmic

# JUnit JAR
JUNIT_JAR = lib/junit-4.13.2.jar
HAMCREST_JAR = lib/hamcrest-core-1.3.jar

# Classpath
CP = $(BINDIR):$(JUNIT_JAR):$(HAMCREST_JAR)

# Source files
MAINSOURCES = $(MAINSRCDIR)/Calculator.java \
              $(MAINSRCDIR)/CalculatorImplementation.java \
              $(MAINSRCDIR)/CalculatorServer.java

TESTSOURCES = $(TESTSRCDIR)/CalculatorClient.java

# Class files
MAINCLASSES = $(MAINSOURCES:%.java=$(BINDIR)/%.class)
TESTCLASSES = $(TESTSOURCES:%.java=$(BINDIR)/%.class)

# Default target
all: $(BINDIR)
	$(JC) $(JFLAGS) -cp $(CP) -d $(BINDIR) $(MAINSOURCES) src/test/java/*.java

# Rule for making the bin directory
$(BINDIR):
	mkdir -p $(BINDIR)/RMI_Calculator

# Rule for compiling main .java files
$(BINDIR)/%.class: %.java
	$(JC) $(JFLAGS) -cp $(CP) -d $(BINDIR) $<

# Rule for compiling test .java files
$(BINDIR)/CalculatorClient.class: $(TESTSRCDIR)/CalculatorClient.java
	$(JC) $(JFLAGS) -cp $(CP) -d $(BINDIR) $<

# Clean up
clean:
	rm -rf $(BINDIR)

# Run the server
run-server:
	$(JR) -classpath $(CP) RMI_Calculator.CalculatorServer

# Run the client
run-client: all
	$(JR) -cp $(CP):$(BINDIR) org.junit.runner.JUnitCore CalculatorClient

# Generate RMI stubs
stubs: $(BINDIR)/RMI_Calculator/Calculator