package com.rosalieraz.importmodule.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosalieraz.importmodule.model.Events;

@Service
public class FileHandlingServiceImpl implements FileHandlingService {

	// Files Directory Path
	private String excelDirPath = ".\\src\\main\\resources\\static\\files\\";

	@Autowired
	private EventsUtilityService validator;
	
	/*
	 * Get File Extension,
	 * It accommodates the fact that the admin may include files that are not of xlsx type
	 */
	
	@Override
	public String getExtension(String fileName) {
		String extension = "";

		if (fileName.contains("."))
		     extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		
		return extension;
	}
	
	
	
	/*
	 * List all Excel Files inside Files Directory
	 */

	@Override
	public List<String> getFileList() {

		List<String> fileNames = new ArrayList<>();

		File dir = new File(excelDirPath);
		File[] fileList = dir.listFiles(); // list of files inside the files directory

		for (File file : fileList) {
			if (file.isFile() && getExtension(file.getName()).equalsIgnoreCase("xlsx"))
					fileNames.add(file.getName());
		}

		return fileNames; // list of file names inside the files directory
	}

	
	
	/*
	 * Performs the same processes through files
	 */

	@Override
	 public List<List<Events>> loopThroughFiles() throws IOException {

		List<String> fileNames = getFileList();
		List<List<Events>> eventsList = new ArrayList<>();
		Map<String, Object> config; // to accommodate multiple config details in the future		
		
		for (String fName : fileNames) {

			String excelFilePath = excelDirPath + fName;
			FileInputStream inputStream = new FileInputStream(excelFilePath);

			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			config = readingConfigDetails(workbook);

			switch (config.get("Table ").toString()) {

				case "Events":
					// Set Fields so that, enum fields can be traced easier
					ArrayList<String> eventFields = new ArrayList<>(Arrays.asList("eventId", "eventType", "eventTitle", "banner", "description", "startDate",
																	"endDate", "regStart", "regEnd", "createDate", "updateDate", "createUserId", 
																	"updateUserId", "isDeleted", "isInternal", "paymentFee", "rideId", "location"));
					
					eventsList.add(readingEventsTable(workbook, eventFields));
					break;
	
				case "Members": // call corresponding reading function for members
					break;
	
				case "Partners": // call corresponding reading function for partners here
					break;
	
				// add cases here for additional tables	
					
				default:
					break;

			}
			 
		}

		return eventsList;
	}

	
	
	/*
	 * Read Config details from config sheets per Excel File
	 */

	@Override
	public Map<String, Object> readingConfigDetails(XSSFWorkbook workbook) throws IOException {

		XSSFSheet configSheet = workbook.getSheet("Config");
		Map<String, Object> configDetails = new HashMap<>();

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
					value = cell.getNumericCellValue();
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
	public List<Events> readingEventsTable (XSSFWorkbook workbook, ArrayList<String> eventFields) throws IOException {

		List<Events> eventsList = new ArrayList<>();
		XSSFSheet dataSheet = workbook.getSheet("Data");
		
		Iterator<Row> iterator = dataSheet.iterator();
		iterator.next(); // skip headers
		
		validator.setValidationRules("eventId");
		
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
						// if(validator.validation(0, cell.getNumericCellValue()) != null )
							id = (int) cell.getNumericCellValue();
						break;
						
					case 1:
							type = (int) readingEnums(workbook, eventFields.get(cellIndex)).get(cell.getStringCellValue());
						break;
						
					case 2:
						// if(validator.validation(2, cell.getStringCellValue()) != null)
								title = cell.getStringCellValue();
						break;
						
					case 3:
						// if(validator.validation(3, cell.getStringCellValue()) != null && cell.getCellType() != CellType.BLANK)
								banner = cell.getStringCellValue();
						break;
					
					case 4:
						description = cell.getStringCellValue();
						break;
						
					case 5: 
						startDate = cell.getDateCellValue();
						break;
				
					case 6: 
						endDate = cell.getDateCellValue();
						break;
						
					case 7: 
						regStart= cell.getDateCellValue();
						break;

					case 8: 
						regEnd = cell.getDateCellValue();
						break;

					case 9: 
						createDate = cell.getDateCellValue();
						break;
						
					case 10: 
						updateDate = cell.getDateCellValue();
						break;
						
					case 11:
						createUserId = (int) cell.getNumericCellValue();
						break;
						
					case 12:
						updateUserId = (int) cell.getNumericCellValue();
						break;
						
					case 13:
						isDeleted = (int) cell.getNumericCellValue();
						break;
						
					case 14:
						isInternal = (int) cell.getNumericCellValue();
						break;
						
					case 15:
						paymentFee = (float) cell.getNumericCellValue();
						break;
						
					case 16:
						rideId = cell.getStringCellValue();
						break;
					
					case 17:
						location = cell.getStringCellValue();
						break;
						
					default:
						break;
				}
			}
			
			eventsList.add(new Events(id, type, title, banner, description, startDate, endDate, regStart, regEnd, createDate, 
						updateDate, createUserId, updateUserId, isDeleted, isInternal, paymentFee, rideId, location));
		}
		
		return eventsList;

	}


	/*
	 * Reading Corresponding Enums
	 */
	
	@Override
	public Map<String, Integer> readingEnums(XSSFWorkbook workbook, String field) {
		
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
	
	

	/*
	 * @Override public void writeIntoExcel() {
	 * 
	 * 
	 * }
	 */
}
