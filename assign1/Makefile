all:
	mkdir -p src
	cp *.java README src/
	zip -r 2018MT10742_assignment1 src
	base64 2018MT10742_assignment1.zip > 2018MT10742_assignment1.zip.b64
	rm -r src
	rm 2018MT10742_assignment1.zip
unzip:
	base64 -d 2018MT10742_assignment1.zip.b64 > a.zip
	unzip a.zip
	rm a.zip
clean:
	rm *.class
