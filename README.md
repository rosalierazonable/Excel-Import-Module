# Excel-Import-Module Intership Task

## Tech Stack, APIs and Libraries
- Spring MVC
- SpringBoot
- JPA
- MySql
- Apache POI
- GlobalDispose


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


![Swim Lane Architecture  - RDAK Import Module](https://user-images.githubusercontent.com/80081189/130224744-de1bb620-652a-4473-a01e-ef1b1aa8c6d3.jpg)

### Scenario/ Task Product
Implementation of a Scheduled Import Feature that should be able to: 
- scan all the Excel Files in the Files directory
- read each excel file and import into the database: (1) if in case the data does not pass the validation, the error must be logged, (2) log files must be stored inside the Logs directory following a name convention: Timestamp_of_scheduled_import, (3) each log file must contain the following info: table name, identifier, field_error, error_message
- the fields in the excel file that corresponds to an uploaded image must be read so that (1) the image will be moved to the pseudo server directory
- Each processed file must be copied to the Processed directory  
