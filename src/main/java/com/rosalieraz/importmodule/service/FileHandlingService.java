package com.rosalieraz.importmodule.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rosalieraz.importmodule.model.Events;

public interface FileHandlingService {

	List<String> getFileList ();
	
	HashMap<String, Object> readingConfigDetails (XSSFWorkbook workbook ) throws IOException;
	
	Object readingData (XSSFWorkbook workbook) throws IOException;
	
	List<List<Events>> loopThroughFiles () throws IOException;
	
//	List<HashMap<String, Object>> loopThroughFiles () throws IOException;
}
