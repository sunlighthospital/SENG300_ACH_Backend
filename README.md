# SENG300_ACH_Backend
Back-end server + database implementation for SENG 300 Project

## Setting up the Work Environment
1. Install MySQL (https://dev.mysql.com/doc/refman/5.7/en/installing.html)
2. Install NetBeans 8.2 (Java EE) or IntelliJ Ultimate.
3. Set up a Tomcat Server. NetBeans and IntelliJ come with built-in support.  
    The prior occasionally needs to be manually reminded of it, however (https://javapointers.com/tutorial/add-tomcat-server-netbeans/)<br />
4. Import this repo into your IDE (both IDEs have easy handles for this)
5. Import the SQL dump (db_test_data/SENG300_Hospital.sql) into your MySQL database as 'SENG300_Hospital'

### Importing mySQL database

(all within the MySQL command line)
1. create database SENG300_Hospital;
2. use SENG300_Hospital;
3. source {path/to/your/project/location}/db/SENG300_Hospital.sql;
4. source {path/to/your/project/location}/db/setup.sql
* * *
### Using the API
+ All hooks exist under localhost:{port}/com.ach_manager/api
+ To add a hook (and thus use a part of the functionality) add the hook to the tail end of the above
    - I.E. To access the test hook, one would go to localhost:{port}/com.ach_manager/api/test
+ All hooks return a file in JSON format, with parameters specified below
### Currently implemented URL hooks
+ test:
    - Returns a JSON file with results for each available database call. No use to front-end coding
    - Returned Values (All have the result for the function sharing their respective name):
        * getAppointments;
        * getAllDoctors (Full)
        * getAllDoctors (Department)
        * checkCredentials (Valid)
        * checkCredentials (Invalid)
        * addAppointment (Valid)
        * addAppointment (Redundant)
        * dropAppointment (Valid)
        * dropAppointment (Missing)
+ schedule/bounded
    - Returns a JSON containing all scheduled appointments for the doctor between the two provided dates
    - Takes the following parameters:
        * id: The Doctor ID to fetch for
        * start: Date and time to begin searching from (in "yyyy-MM-dd hh:mm:ss" format)
        * end: Date and time to finish searching (in "yyyy-MM-dd hh:mm:ss" format)
    - Returns the following:
        * schedule: A list of JSON style objects, each containing:
            + title: Name of the appointment
            + description: Description of the appointment
            + time: Time at which the appointment occurs
            + duration: Expected duration (in minutes) of the appointment
+ loginUser
    - Returns a true/false value which represents whether the login was correct or not
    - Take the following parameters:
        * username; Username for the user
        * password; Password for the user
    - Returns the following:
        * loginValid: True if the credentials matched a known user, false otherwise
+ editDoctorSchedule/add
    - localhost:{port}/com.ach_manager/api/editDoctorSchedule/add?Title="enterTitle"&description="enterDecription"&time="01:03:54"&Duration="02:03:00"&PatientID=X&DoctorID=Y
    - Returns a JSONObject containing a message to be displayed on screen after adding a new appointment 
    - Take the following parameters:
        * Title: Title of appointment
        * description: Description of appointment
        * time: Time of appointment "hh:mm:ss" format
        * Duration: Duration of appointment "hh:mm:ss" format
        * PatientID: Patient's ID e.g. X that can be any integer 1-9
        * DoctorID: Doctor's ID e.g. Y that can be any integer 1-9
     - Returns the following:
        * result: A JSONOBject containing:
            + message that can be any one of the following strings: {Appointment was successfully added, Appointment was successfully added, An appointment with the entered information already exists}
+ editDoctorSchedule/drop
    - localhost:{port}/com.ach_manager/api/editDoctorSchedule/drop?PatientID=X&DoctorID=Y&time="02:03:00"
    - Returns a JSONObject containing a message to be displayed on screen after dropping an appointment 
    - Take the following parameters in the order:
        * PatientID: Patient's ID
        * DoctorID: Doctor's ID
        * time: Time of appointment "hh:mm:ss" format
     - Returns the following:
        * result: A JSONOBject containing:
            + message that can be any one of the following strings: {Appointment was successfully dropped, AAn unknown error ocurred during dropping appointment, An appointment with the entered information does not exist}        
+ doctor/all
    - Returns a list of doctor credential values (named doctors)
    - Each element contains the following:
        * id: Doctor ID (used for querying the database)
        * name: The doctor's name
        * is_surgeon: Whether the doctor is a surgeon or not
        * department: to which department the doctor belongs
