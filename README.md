# SENG300_ACH_Backend
Back-end server + database implementation for SENG 300 Project

## Setting up the Work Environment
1. Install MySQL (https://dev.mysql.com/doc/refman/5.7/en/installing.html)
2. Install NetBeans 8.2 (Java EE) or IntelliJ Ultimate.
3. Set up a Tomcat Server. NetBeans and IntelliJ come with built-in support.
<br>The prior occasionally needs to be manually reminded of it, however (https://javapointers.com/tutorial/add-tomcat-server-netbeans/)<br />
4. Import this repo into your IDE (both IDEs have easy handles for this)
5. Import the SQL dump (db_test_data/SENG300_Hospital.sql) into your MySQL database as 'SENG300_Hospital'

### Importing mySQL database

(all within the MySQL command line)
1. create database SENG300_Hospital;
2. use SENG300_Hospital;
3. source {path/to/your/project/location}/db_test_data/SENG300_Hospital.sql;
4. source {path/to/your/project/location}/db_test_data/setup.sql
