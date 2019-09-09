ENTRY=2018MT10742

compile:
	javac *.java

clean:
	rm *.class

handin:	
	@mkdir -p src
	@cp *.java src/
	@zip -r $(ENTRY)_assignment3.zip src/
	@base64 $(ENTRY)_assignment3.zip > $(ENTRY)_assignment3.zip.b64
	@rm $(ENTRY)_assignment3.zip
	@rm -r src
