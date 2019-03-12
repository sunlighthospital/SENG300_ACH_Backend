# SENG300_ACH_Backend
Back-end server + database implementation for SENG 300 Project

-- Setting up the Work Environment --
1. Install MySQL (https://dev.mysql.com/doc/refman/5.7/en/installing.html)
2. Install NetBeans 8.2 (Java EE) or IntelliJ Ultimate.
3. Set up a Tomcat Server. NetBeans and IntelliJ come with built-in support.
	The prior occasionally needs to be manually reminded of it, however (https://javapointers.com/tutorial/add-tomcat-server-netbeans/)
4. Import this repo into your IDE (both IDEs have easy handles for this)
5. Import the SQL dump (db_test_data/SENG300_Hospital.sql) into your MySQL database as 'SENG300_Hospital'
	option 1; (in bash command line) 
		1). "sudo mysql -u root < SENG300_Hospital.sql"
	option 2; (in MySQL command line)
		1). create database SENG300_Hospital;
		2). use SENG300_Hospital;
		3). source SENG300_Hospital.sql;

-- Running the Service --
1. In Eclipse, right click on the project and 'Run As > Run on Server' (Select the server you just created above)
2. To test various functions, place code into TestScript.Java's test() function. Others may be implimented later.
3. To see the results of the test, start the server and go to 'http://localhost:8080/com.ach_manager.rest/api/test'

-- To Request Write Access for Repo --
1. Make sure you have a valid SSH public key
2. Send it to me (Kalum Ost) over Slack, or meet me in person to quickly add it to the allowed user keys
