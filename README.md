# Excel-Import-Module Intership Task

### Tech Stack and Libraries
- Spring MVC
- SpringBoot
- JPA
- MySql
- Apache POI

### Task Breakdown
- Task/Job Scheduler
- Reading and Writing into Excel File
- Data Validations
- File Listing
- Copy File to other directory
- Move File to other directory
- Exception Handling


## Task Full Description

### Architecture
Assume a particular directory has five subdirectories:
- Files// : for excel files
- Logs// : for error logging files
- Images// : established image directory
- Processed// : directory which holds the logging of processed excel files
- Server// : mocks the server directory where images should be moved

### Scenario/ Task Product
Implementation of a Scheduled Import Feature that should be able to: 
- scan all the Excel Files in the Files directory
- read each excel file and import into the database
  - if in case the data does not pass the validation, the error must be logged 
     -log files must be stored inside the Logs directory, name convention: Timestamp_of_scheduled_import
     -each log file must contain the following info: table name, identifier, field_error, error_message
- the fields in the excel file that corresponds to an uploaded image must be read so that,
  - the image will be moved to the pseudo server directory
- Each processed file must be copied to the Processed directory  
