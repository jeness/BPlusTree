JC = javac
JFLAGS = -g
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	B.java \
	BplusTree.java \
	Node.java \
	treesearch.java 
	
default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class