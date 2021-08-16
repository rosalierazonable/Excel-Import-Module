package com.rosalieraz.importmodule.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

@Service
public class FileHandlingServiceImpl implements FileHandlingService {

	private String excelDirPath = ".\\src\\main\\resources\\static\\excel-files";
	
	@Override
	public List<String> getFileList () {
		
		List<String> fileNames = new ArrayList<>();
		
		
		File dir = new File(excelDirPath);
		File[] fileList = dir.listFiles();
		
		for(File file: fileList) {
			if(file.isFile())
				fileNames.add(file.getName());
		}
		
		return fileNames;
		
	}

	@Override
	public void readingExcel() throws IOException {
		
		List<String> fileNames = getFileList();
		
		for(String fName: fileNames) {
			
			String excelFilePath = excelDirPath + "//" + fName;
			FileInputStream inputStream = new FileInputStream(excelFilePath);
			
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheetAt(0);
			
			Iterator<Row> iterator = sheet.iterator();
			
			while(iterator.hasNext()) {
				XSSFRow row = (XSSFRow) iterator.next(); // will return the row and store in a variable
				
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while(cellIterator.hasNext()) {
					XSSFCell cell = (XSSFCell) cellIterator.next();
				
					switch(cell.getCellType()) {
					
						 case STRING: 
							 System.out.print(cell.getStringCellValue()); 
							 break;
						 case BOOLEAN: 
							 System.out.print(cell.getBooleanCellValue()); 
							 break; 
						 case NUMERIC: 
							 System.out.print(cell.getNumericCellValue()); 
							 break; 
						default: 
							break; 
					}
					 System.out.print(" | ");
				}
				
				System.out.println(); 
			}
			System.out.println();
			System.out.println("============================================"); 
			
		}
		
	}
	
	

}
