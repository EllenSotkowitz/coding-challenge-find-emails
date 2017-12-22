# coding-challenge-find-emails
Create a command line program that will take an internet domain name (eg. “web.mit.edu”) and print out a list of the email addresses that were found on that website only.
This program uses Jsoup open-source Java HTML parser jsoup-1.11.2. 

How to run:
In the same folder where you download the source make a folder "lib" and download jsoup-1.11.2.jar into the lib folder.

Compile the program
javac -classpath "./lib/jsoup-1.11.2.jar" com/ellen/findemailaddresses/FindEmailAddresses.java

Run the program
 java -classpath ".:./lib/jsoup-1.11.2.jar" com/ellen/findemailaddresses/FindEmailAddresses <url>
