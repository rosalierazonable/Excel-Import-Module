package com.rosalieraz.importmodule.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface FileHandlingService {

	String getExtension (String fileName);

	List<String> getFileList (String excelDirPath, String extension);
	
	Map<String, Object> readingConfigDetails (XSSFWorkbook workbook ) throws IOException;
	
	Map<String, Integer> readingEnums (XSSFWorkbook workbook, String field);

	void copyFile (List<String> fileNames) throws IOException;
	
	void moveFile (String fileName, String targetDirectory);

	List<Object[]>  writeIntoExcel (List<Object[]> errors) throws IOException;

	List<Object[]> readingEventsTable (XSSFWorkbook workbook, ArrayList<String> eventFields, String identifier) throws IOException;
	
	void loopThroughFiles () throws IOException;
	
}
