package com.rosalieraz.importmodule;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class IteratorLoading {
	
	/**
	 * A utility method for loading a row iterator
	 * @param workbook created for each excel files
	 * @returns a row iterator
	 */
	
	public java.util.Iterator<Row> load (XSSFWorkbook workbook) {
		XSSFSheet dataSheet = workbook.getSheet("Data");
		return dataSheet.iterator();
	}
	
}
