# Java compiler
JC = javac

# Java runtime
JR = java

# Compiler flags
JFLAGS = -g

# Source directory
SRCDIR = src/main/java/RMI-Calculator

# Output directory for class files
BINDIR = bin

# RMI stub generator
RMIC = rmic

# Source files
SOURCES = $(SRCDIR)/Calculator.java \
          $(SRCDIR)/CalculatorImplementation.java \
          $(SRCDIR)/CalculatorServer.java \
          $(SRCDIR)/CalculatorClient.java

# Class files
CLASSES = $(SOURCES:$(SRCDIR)/%.java=$(BINDIR)/%.class)

# Default target
all: $(BINDIR) $(CLASSES)

# Rule for making the bin directory
$(BINDIR):
	mkdir -p $(BINDIR)

# Rule for compiling .java files
$(BINDIR)/%.class: $(SRCDIR)/%.java
	$(JC) $(JFLAGS) -d $(BINDIR) $

# Clean up
clean:
	rm -rf $(BINDIR)

# Run the server
run-server:
	$(JR) -classpath $(BINDIR) CalculatorServer

# Run the client
run-client:
	$(JR) -classpath $(BINDIR) CalculatorClient

# Generate RMI stubs
stubs: $(BINDIR)/CalculatorImplementation.class
	$(RMIC) -classpath $(BINDIR) -d $(BINDIR) CalculatorImplementation

.PHONY: all clean run-server run-client stubs