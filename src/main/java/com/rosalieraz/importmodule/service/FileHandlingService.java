package com.rosalieraz.importmodule.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface FileHandlingService {
	
	/**
	 * A method that inspects the extension of a file and returns it. 
	 * Also, It accommodates the fact that the admin may include files that are not of xlsx type
	 * @param fileName is a string containing the file name with extension ex: img.jpg
	 * @returns the extension string from the provided file name ex: .xlsx
	 */

	String getExtension (String fileName);

	/**
	 * List all Excel Files inside Files Directory and accepts path and extension
	 * paramaters to accommodate future use to list files in other desired path
	 * @param excelDirPath is the directory path where the excel files are placed
	 * @param extension of the desired file types to list or open ex: txt
	 * @returns list of file names with the same file type as the extension provided
	 */
	
	List<String> getFileList (String excelDirPath, String extension);
	
	
	/**
	 * Read Configuration details from config sheets per Excel File
	 * @param workbook created from the excel files
	 * @returns a hashmap that contains all the details in the config sheet of each excel file
	 * @throws IOException
	 */
	
	Map<String, Object> readingConfigDetails (XSSFWorkbook workbook ) throws IOException;
	
	
	/**
	 * Reading Excel Sheets dedicated for Enumerations
	 * @param workbook created from the excel files
	 * @param field is the field name that corresponds to the sheet to be opened
	 * @returns a hashmap containing all the enumeration options
	 */
	
	Map<String, Integer> readingEnums (XSSFWorkbook workbook, String field);

	
	/**
	 * A method for Error Logging: write into error data into excel.
	 * Also, is responsbile for writing into Log Excel File Convention: - FileName: Current_timestamp -
	 * Fields inside sheet: Table name, Identifier, Error message
	 * Object is used to accommodate expansion of error log files where different
	 * cellType will be inserted
	 * @param errors is the list of error details including the error message from ConstraintViolationException 
	 * that were encountered while validating the data
	 * @return a log of excel files
	 * @throws IOException
	 */

	List<Object[]>  writeIntoExcel (List<Object[]> errors) throws IOException;
	
	
	/**
	 * A method for reading Excel Files for Events table
	 * @param workbook a XSSFWorkbook created for each provided excel files
	 * @param eventFields is a list of all the field names under the event table
	 * @param config is a hashmap object that contains all the config details for the event table
	 * @returns a list of errors caught from reading the events table
	 * @throws ConstraintViolationException when some fields did not pass the validation restrictions defined in its respective entity/model
	 */
	
	List<Object[]> readingEventsTable (XSSFWorkbook workbook, ArrayList<String> eventFields, Map<String, Object> config) throws ConstraintViolationException;

	
	/**
	 * A method for reading Excel Files for Partners Table
	 * @param workbook a XSSFWorkbook created for each provided excel files
	 * @param partnerFields is a list of all the field names under the partners table
	 * @param config is a hashmap object that contains all the config details for the partners table
	 * @return a list of errors caught from reading the partners table
	 * @throws ConstraintViolationException when some fields did not pass the validation restrictions defined in its respective entity/model
	 */
	
	List<Object[]> readingPartnersTable (XSSFWorkbook workbook, ArrayList<String> partnerFields, Map<String, Object> config) throws ConstraintViolationException;
	
	
	/**
	 * A method for reading Emergency Numbers table
	 * @param workbook a XSSFWorkbook created for each provided excel files
	 * @param eNumFields is a list of all the field names under the emergency numbers table
	 * @param config is a hashmap object that contains all the config details for the Emergency Numbers table
	 * @return a list of errors caught from reading the Emergency Numbers table
	 * @throws ConstraintViolationException when some fields did not pass the validation restrictions defined in its respective entity/model
	 */
	
	List<Object[]> readingEmergencyNosTable (XSSFWorkbook workbook, ArrayList<String> eNumFields, Map<String, Object> config) throws ConstraintViolationException;

	
	/**
	 * A method for reading the Payments table
	 * @param workbook a XSSFWorkbook created for each provided excel files
	 * @param paymentFields is a list of all the field names under the partners table
	 * @param config is a hashmap object that contains all the config details for the Payments table
	 * @return a list of errors caught from reading the payments table
	 * @throws ConstraintViolationException when some fields did not pass the validation restrictions defined in its respective entity/model
	 */
	
	List<Object[]> readingPaymentsTable (XSSFWorkbook workbook, ArrayList<String> paymentFields, Map<String, Object> config) throws ConstraintViolationException;

	
	/**
	 * A method for reading the Interests table
	 * @param workbook a XSSFWorkbook created for each provided excel files
	 * @param interestFields is a list of all the field names under the interest table
	 * @param config is a hashmap object that contains all the config details for the partners table
	 * @return a list of errors caught from reading the interests table
	 * @throws ConstraintViolationException when some fields did not pass the validation restrictions defined in its respective entity/model
	 */
	
	List<Object[]> readingInterestsTable (XSSFWorkbook workbook, ArrayList<String> interestFields, Map<String, Object> config) throws ConstraintViolationException;
	
	
	/**
	 * A method for reading the Events Attendance Table
	 * @param workbook a XSSFWorkbook created for each provided excel files
	 * @param eAFields is a list of all the field names under the Events Attendance table
	 * @param config is a hashmap object that contains all the config details for the partners table
	 * @return a list of errors caught from reading the partners table
	 * @throws \ConstraintViolationException when some fields did not pass the validation restrictions defined in its respective entity/model
	 */
	
	List<Object[]> readingEATable (XSSFWorkbook workbook, ArrayList<String> eAFields, Map<String, Object> config) throws ConstraintViolationException;

	
	/**
	 * A method for reading the Guest Attendance table
	 * @param workbook a XSSFWorkbook created for each provided excel files
	 * @param gAFields is a list of all the field names under the Guest attendance table
	 * @param config is a hashmap object that contains all the config details for the partners table
	 * @return a list of errors caught from reading the partners table
	 * @throws ConstraintViolationException when some fields did not pass the validation restrictions defined in its respective entity/model
	 */
	
	List<Object[]> readingGATable (XSSFWorkbook workbook, ArrayList<String> gAFields, Map<String, Object> config) throws ConstraintViolationException;
	
	
	/**
	 * A utility method for keeping an error list from each table reading, preparing the list at the end of the scheduled task
	 * Check if errors list is not empty then add to errorsList
	 * Otherwise, do nothing
	 * @param errors is the list of errors from each reading table function
	 * @param log is the constantly updated list of all errors across different tables
	 * @returns the updated list of error logs
	 */
	
	List<Object[]> addToErrors (List<Object[]> errors, List<Object[]> log);
	
	
	/**
	 * A utility method that invokes the corresponding reading table functions and all other utility methods and 
	 * performs the same processes across each file
	 * @throws IOException when files are not found or null
	 */
	
	void loopThroughFiles () throws IOException;
	
}
