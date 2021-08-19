package com.rosalieraz.importmodule.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rosalieraz.importmodule.model.Events;

public interface FileHandlingService {

	List<String> getFileList ();
	
	String getExtension (String fileName);
	
	Map<String, Object> readingConfigDetails (XSSFWorkbook workbook ) throws IOException;
	
	List<Events> readingEventsTable (XSSFWorkbook workbook, ArrayList<String> eventFields) throws IOException;
	
	List<List<Events>> loopThroughFiles () throws IOException;
	
	Map<String, Integer> readingEnums (XSSFWorkbook workbook, String field);
	
//	void writeIntoExcel ();
	
}
