package com.rosalieraz.importmodule.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface FileHandlingService {

	String getExtension (String fileName);

	List<String> getFileList (String excelDirPath, String extension);
	
	Map<String, Object> readingConfigDetails (XSSFWorkbook workbook ) throws IOException;
	
	Map<String, Integer> readingEnums (XSSFWorkbook workbook, String field);

	void copyFile (List<String> fileNames) throws IOException;
	
	void moveFile (String fileName, String targetDirectory);

	List<Object[]>  writeIntoExcel (List<Object[]> errors) throws IOException;
	
	Iterator<Row> loadingIterator (XSSFWorkbook workbook);

	List<Object[]> readingEventsTable (XSSFWorkbook workbook, ArrayList<String> eventFields, Map<String, Object> config) throws ConstraintViolationException;

	List<Object[]> readingPartnersTable (XSSFWorkbook workbook, ArrayList<String> partnerFields, Map<String, Object> config) throws ConstraintViolationException;
	
	List<Object[]> readingEmergencyNosTable (XSSFWorkbook workbook, ArrayList<String> eNumFields, Map<String, Object> config) throws ConstraintViolationException;

	List<Object[]> readingPaymentsTable (XSSFWorkbook workbook, ArrayList<String> paymentFields, Map<String, Object> config) throws ConstraintViolationException;

	List<Object[]> readingInterestsTable (XSSFWorkbook workbook, ArrayList<String> interestFields, Map<String, Object> config) throws ConstraintViolationException;
	
	List<Object[]> readingEATable (XSSFWorkbook workbook, ArrayList<String> eAFields, Map<String, Object> config) throws ConstraintViolationException;

	List<Object[]> readingGATable (XSSFWorkbook workbook, ArrayList<String> gAFields, Map<String, Object> config) throws ConstraintViolationException;
	
	void loopThroughFiles () throws IOException;
	
}
