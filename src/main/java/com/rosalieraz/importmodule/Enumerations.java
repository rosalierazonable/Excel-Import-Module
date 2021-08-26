package com.rosalieraz.importmodule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class Enumerations {

	public Map<String, Integer> read (XSSFWorkbook workbook, String field) {
		
		XSSFSheet enumSheet = workbook.getSheet(field);
		Map<String, Integer> enums = new HashMap<>();

		Iterator<Row> configItr = enumSheet.iterator();

		while (configItr.hasNext()) {
			XSSFRow row = (XSSFRow) configItr.next();
			String keyStr = null;
			Integer value = null;

			keyStr = row.getCell(0).getStringCellValue();
			value = (int) row.getCell(1).getNumericCellValue();

			enums.put(keyStr, value);
		}
		return enums;
	}
	
}
