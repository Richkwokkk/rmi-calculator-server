# Java compiler
JC = javac

# Java runtime
JR = java

# Compiler flags
JFLAGS = -g

# Source directories
MAINSRCDIR = src/main/java/RMI_Calculator
TESTSRCDIR = src/test

# Output directory for class files
BINDIR = bin

# RMI stub generator
RMIC = rmic

# Source files
MAINSOURCES = $(MAINSRCDIR)/Calculator.java \
              $(MAINSRCDIR)/CalculatorImplementation.java \
              $(MAINSRCDIR)/CalculatorServer.java

TESTSOURCES = $(TESTSRCDIR)/CalculatorClient.java

# Class files
MAINCLASSES = $(MAINSOURCES:%.java=$(BINDIR)/%.class)
TESTCLASSES = $(TESTSOURCES:%.java=$(BINDIR)/%.class)

# Default target
all: $(BINDIR) $(MAINCLASSES) $(TESTCLASSES)

# Rule for making the bin directory
$(BINDIR):
	mkdir -p $(BINDIR)/RMI_Calculator

# Rule for compiling main .java files
$(BINDIR)/%.class: %.java
	$(JC) $(JFLAGS) -d $(BINDIR) $

# Rule for compiling test .java files
$(BINDIR)/CalculatorClient.class: $(TESTSRCDIR)/CalculatorClient.java
	$(JC) $(JFLAGS) -cp $(BINDIR) -d $(BINDIR) $

# Clean up
clean:
	rm -rf $(BINDIR)

# Run the server
run-server:
	$(JR) -classpath $(BINDIR) RMI_Calculator.CalculatorServer

# Run the client
run-client:
	$(JR) -classpath $(BINDIR) CalculatorClient

# Generate RMI stubs
stubs: $(BINDIR)/RMI_Calculator/Calculator