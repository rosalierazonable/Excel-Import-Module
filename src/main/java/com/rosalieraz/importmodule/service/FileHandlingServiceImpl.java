package com.rosalieraz.importmodule.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import com.rosalieraz.importmodule.model.Events;

@Service
public class FileHandlingServiceImpl implements FileHandlingService {

	// Files Directory Path
	private String excelDirPath = ".\\src\\main\\resources\\static\\files\\";

	
	
	/*
	 * List all Excel Files inside Files Directory
	 */

	@Override
	public List<String> getFileList() {

		List<String> fileNames = new ArrayList<>();

		File dir = new File(excelDirPath);
		File[] fileList = dir.listFiles(); // list of files inside the files directory

		for (File file : fileList) {
			if (file.isFile())
				fileNames.add(file.getName());
		}

		return fileNames; // list of file names inside the files directory

	}

	
	
	/*
	 * Performs the same processes through files
	 */

	@Override
	 public List<List<Events>> loopThroughFiles() throws IOException {
//	public void loopThroughFiles() throws IOException {

		List<String> fileNames = getFileList();
		List<List<Events>> eventsList = new ArrayList<>();
		
		HashMap<String, Object> config = new HashMap<>();

//		List<HashMap<String, Object>> configs = new ArrayList<>();

		for (String fName : fileNames) {

			String excelFilePath = excelDirPath + fName;
			FileInputStream inputStream = new FileInputStream(excelFilePath);

			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			config = readingConfigDetails(workbook);
//			configs.add(readingConfigDetails(workbook));

			eventsList.add(readingData(workbook));
			
		}
		System.out.println("size: " + eventsList.size());
		return eventsList;
	}

	
	
	/*
	 * Read Config details from config sheets per Excel File
	 */

	@Override
	public HashMap<String, Object> readingConfigDetails(XSSFWorkbook workbook) throws IOException {

		// Reading the Config Details
		XSSFSheet configSheet = workbook.getSheet("Config");
		HashMap<String, Object> configDetails = new HashMap<>();

		Iterator<Row> configItr = configSheet.iterator();

		while (configItr.hasNext()) {
			XSSFRow row = (XSSFRow) configItr.next();
			String keyStr = null;
			Object value = null;

			for (int i = 0; i < 2; i++) {
				XSSFCell cell = row.getCell(i);

				switch (cell.getCellType()) {

				case STRING:
					if (i == 0) {
						keyStr = cell.getStringCellValue();
					} else {
						value = cell.getStringCellValue();
					}
					break;

				case BOOLEAN:
					value = cell.getBooleanCellValue();
					break;

				case NUMERIC:
					value = (int) cell.getNumericCellValue();
					break;

				default:
					break;
				}

				configDetails.put(keyStr, value);
			}
		}

		return configDetails;

	}

	
	
	/*
	 * Read Table Data from Data Sheet per Excel File
	 */

	@Override
	public List<Events> readingData(XSSFWorkbook workbook) throws IOException {

		List<Events> eventsList = new ArrayList<>();
//		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		XSSFSheet dataSheet = workbook.getSheet("Data");
		
		/*
		 * int rowNum = dataSheet.getLastRowNum(); System.out.println(rowNum);
		 */
		Iterator<Row> iterator = dataSheet.iterator();
		
		iterator.next();
		while (iterator.hasNext()) {
			
			Integer id = 0;
			Integer type = 0;
			String title = "";
			String banner = "";
			String description = "";
			Date startDate = new Date();
			Date endDate = new Date();
			Date regStart = new Date();
			Date regEnd = new Date();
			Date createDate = new Date();
			Date updateDate = new Date();
			Integer createUserId = 0;
			Integer updateUserId = 0;
			Integer isDeleted = 0;
			Integer isInternal = 0;
			float paymentFee = 0;
			String rideId = "";
			String location = "";
			
			XSSFRow row = (XSSFRow) iterator.next();
			
			Iterator<Cell> cellIterator = row.cellIterator();
			
			while (cellIterator.hasNext()) {
				XSSFCell cell = (XSSFCell) cellIterator.next();
				int cellIndex = cell.getColumnIndex();
				
				switch(cellIndex) {
					
					case 0:
						id = (int) cell.getNumericCellValue();
						System.out.println("id: " + id);
						break;
						
					case 1:
						type = (int) cell.getNumericCellValue();
						System.out.println("type: " + type);
						break;
						
					case 2:
						title = cell.getStringCellValue();
						System.out.println("title: " + title);
						break;
						
					case 3:
						banner = cell.getStringCellValue();
						System.out.println("banner: " + banner);
						break;
					
					case 4:
						description = cell.getStringCellValue();
						System.out.println("description: " + description);
						break;
						
					case 5: 
						startDate = cell.getDateCellValue();
						System.out.println("startDate: " + startDate);
						break;
				
					case 6: 
						endDate = cell.getDateCellValue();
						System.out.println("endDate: " + endDate);
						break;
						
					case 7: 
						regStart= cell.getDateCellValue();
						System.out.println("regStart: " + regStart);
						break;

					case 8: 
						regEnd = cell.getDateCellValue();
						System.out.println("regEnd: " + regEnd);
						break;

					case 9: 
						createDate = cell.getDateCellValue();
						System.out.println("createDate: " + createDate);
						break;
						
					case 10: 
						updateDate = cell.getDateCellValue();
						System.out.println("updateDate: " + updateDate);
						break;
						
					case 11:
						createUserId = (int) cell.getNumericCellValue();
						System.out.println("createUserId: " + createUserId);
						break;
						
					case 12:
						updateUserId = (int) cell.getNumericCellValue();
						System.out.println("updateUserId: " + updateUserId);
						break;
						
					case 13:
						isDeleted = (int) cell.getNumericCellValue();
						System.out.println("isDeleted: " + isDeleted);
						break;
						
					case 14:
						isInternal = (int) cell.getNumericCellValue();
						System.out.println("isInternal: " + isInternal);
						break;
						
					case 15:
						paymentFee = (float) cell.getNumericCellValue();
						System.out.println("paymentFee: " + paymentFee);
						break;
						
					case 16:
						rideId = cell.getStringCellValue();
						System.out.println("rideId: " + rideId);
						break;
					
					case 17:
						location = cell.getStringCellValue();
						System.out.println("location: " + location);
						break;
						
					default:
						break;
				}
				
				
				/*
				 * switch (cell.getCellType()) {
				 * 
				 * case STRING: System.out.print(cell.getStringCellValue()); break; case
				 * BOOLEAN: System.out.print(cell.getBooleanCellValue()); break; case NUMERIC:
				 * System.out.print(cell.getNumericCellValue()); break; case BLANK: // NULL
				 * break; default: break; } System.out.print(" | ");
				 */
			}
			eventsList.add(new Events(id, type, title, banner, description, startDate, endDate, regStart, regEnd, createDate, 
						updateDate, createUserId, updateUserId, isDeleted, isInternal, paymentFee, rideId, location));
		}
		
		return eventsList;

	}

}
