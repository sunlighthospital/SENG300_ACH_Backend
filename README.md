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
    - localhost:{port}/com.ach_manager/api/loginUser?username="userName"&password="password"
    - Take the following parameters:
        * username; Username for the user
        * password; Password for the user
    - Returns the following:
        * loginValid: True if the user exists in database and matches a known user, false otherwise (user does not exist or incorrect credentials entered)
        * role: Admin or Receptionist or "User does not exist"
+ editDoctorSchedule/add
    - localhost:{port}/com.ach_manager/api/editDoctorSchedule/add?title="enterTitle"&description="enterDecription"&time="01:03:54"&duration="02:03:00"&patientID=X&doctorID=Y
    - Returns a JSONObject containing a message to be displayed on screen after adding a new appointment 
    - Take the following parameters:
        * title: Title of appointment
        * description: Description of appointment
        * time: Time of appointment "hh:mm:ss" format
        * duration: Duration of appointment "hh:mm:ss" format
        * patientID: Patient's ID e.g. X that can be any integer 1-9
        * doctorID: Doctor's ID e.g. Y that can be any integer 1-9
     - Returns the following:
        * result: A JSONOBject containing:
            + message that can be any one of the following strings: {Appointment was successfully added, Appointment was successfully added, An appointment with the entered information already exists}
+ editSchedule/drop
    - localhost:{port}/com.ach_manager/api/editSchedule/drop?patientID=X&doctorID=Y&time="02:03:00"
    - Returns a JSONObject containing a message to be displayed on screen after dropping an appointment 
    - Take the following parameters in the order:
        * patientID: Patient's ID
        * doctorID: Doctor's ID
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

+ editUser/addDoctor
    - localhost:{port}/com.ach_manager/api/editUser/addDoctor?username=X&password=Y&phone=Z&name=ABC&departmentID=G&isSurgeon=boolValue
    - Returns a JSONObject containing a message to be displayed on screen after adding a doctor 
    - Take the following parameters in the order:
        * username: username to be used by doctor for logging in
        * password: String representing password for doctor's login
        * phone: Phone number of doctor
        * name: Name of doctor
        * departmentID: integer for department ID of doctor
        * isSurgeon: boolean indicating whether doctor is a surgeon
     - Returns the following:
        * result: A JSONOBject containing:
            + message that can be any one of the following strings: {Doctor was successfully added, An unknown error occured, A doctor with the entered information already exists}
            
+ editUser/addReceptionist
    - localhost:{port}/com.ach_manager/api/editUser/addReceptionist?username=X&password=Y&phone=Z&name=ABC
    - Take the following parameters in the order:
        * username: username to be used by doctor for logging in
        * password: String representing password for doctor's login
        * phone: Phone number of doctor
        * name: Name of doctor
     - Returns the following:
        * result: A JSONOBject containing:
            + message that can be any one of the following strings: {Receptionist was successfully added, An unknown error occured, A receptionist with the entered information already exists}

+ doctor/getAppointments
    - localhost:{port}/com.ach_manager/api/doctor/getAppointments?id=4
    - Take the following parameters in the order:
        * id: Doctor ID
     - Returns the following:
        * result: 
            + A JSONObject containing a JSONObjectList "schedule", with elements containing the following:
            + title: Title of the appointment
            + description: Description of the appointment
            + time: Time the appointment is schedule to take place
            + duration: Expected duration of the appointment (in minutes)
            + patient_id: Patient ID associated with this appointment (if any)
