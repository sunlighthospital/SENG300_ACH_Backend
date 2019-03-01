# SENG300_ACH_Backend
Back-end server + database implementation for SENG 300 Project

-- Setting up the Work Environment --
1. Install MySQL (https://dev.mysql.com/doc/refman/5.7/en/installing.html)
2. Setup Eclipse EE (this is NOT the same as base Eclipse, and this will NOT work with base Eclipse)
  (https://www.eclipse.org/downloads/packages/)
3. Set up a Tomcat Server with Eclipse (use Tomcat v.7 please)
  (http://www.coreservlets.com/Apache-Tomcat-Tutorial/tomcat-7-with-eclipse.html)
4. Import this repo using EGit for Eclipse (as 'Dynamic Web Project'. You may need to install EGit):
  (https://stackoverflow.com/questions/8070017/how-to-import-a-git-non-eclipse-java-project-into-eclipse)
5. Import the SQL dump (db_test_data/SENG300_Hospital.sql) into your MySQL database as 'SENG300_Hospital'
  (https://stackoverflow.com/questions/5535479/create-database-from-dump-file-in-mysql-5-0#5535506)

-- Running the Service --
1. In Eclipse, right click on the project and 'Run As > Run on Server' (Select the server you just created above)
2. To test various functions, place code into TestScript.Java's test() function. Others may be implimented later.
3. To see the results of the test, start the server and go to 'http://localhost:8080/com.ach_manager.rest/api/test'

-- To Request Write Access for Repo --
1. Make sure you have a valid SSH public key
2. Send it to me (Kalum Ost) over Slack, or meet me in person to quickly add it to the allowed user keys
