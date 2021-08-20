package com.rosalieraz.importmodule.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosalieraz.importmodule.model.Events;
import com.rosalieraz.importmodule.repository.EventsRepository;

@Service
public class FileHandlingServiceImpl implements FileHandlingService {
	
	@Autowired
	EventsRepository eRepository;

	// Files Directory Path
	private String dirPath = ".\\src\\main\\resources\\static\\";
	
	
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
	 * List all Excel Files inside Files Directory,
	 * Accepst path and extension paramaters to accommodate future use
	 * to list files in other desired path
	 */

	@Override
	public List<String> getFileList(String path, String extension) {

		List<String> fileNames = new ArrayList<>();

		File dir = new File(path);
		File[] fileList = dir.listFiles(); // list of files inside the files directory

		for (File file : fileList) {
			if (file.isFile() && getExtension(file.getName()).equalsIgnoreCase(extension))
					fileNames.add(file.getName());
		}

		return fileNames; // list of file names inside the files directory
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
	 * Reading Corresponding Enums
	 * Parameters: workbook and the enum field to be opened
	 */
	
	@Override
	public Map<String, Integer> readingEnums (XSSFWorkbook workbook, String field) {
		
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
	 * Copying processed Excel File to Processed directory
	 */
	
	@Override
	public void copyFile(List<String> fileNames) throws IOException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Timestamp instant= Timestamp.from(Instant.now()); 
		
		String targetPath = dirPath + "processed\\";
		
		File file = new File(targetPath + dateFormat.format(instant).replaceAll("[: ]", "_"));
		// file.createNewFile();
        boolean flag = file.mkdir();
		
		for(String fileName: fileNames) {
	        if(flag) {
	        	Path sourceFile = Paths.get(dirPath + "files\\" + fileName);
	        	Path targetFile = Paths.get(targetPath + dateFormat.format(instant).replaceAll("[: ]", "_") + "\\" + fileName);
	        	Files.copy(sourceFile, targetFile,
	        			StandardCopyOption.REPLACE_EXISTING);
	        }
		}
	}

	

	/*
	 * Moving Files:
	 * - The directory is already established: ...//images 
	 * - Target directory is the destination directory
	 */
	
	@Override
	public void moveFile (String fileName, String targetDirectory) { 
	
		String srcFile = dirPath + "images//" + fileName;
		String destFile = dirPath + targetDirectory + "//" + fileName;
		
		try {
			Path temp = Files.move(Paths.get(srcFile), Paths.get(destFile), StandardCopyOption.REPLACE_EXISTING);
			
			if(temp != null)
	            System.out.println("File renamed and moved successfully");
	        else
	            System.out.println("Failed! Something went wrong!");
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	

	
	/*
	 * Writing into Log Excel File
	 * Convention: 
	 * - FileName: Current_timestamp
	 * - Fields inside sheet: Table name, Identifier, Field, Error
	 */
	
	@Override
	public List<Object[]> writeIntoExcel (List<Object[]> log ) throws IOException {
//	public void writeIntoExcel (List<String> log ) throws IOException {
		
		  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		  Timestamp instant= Timestamp.from(Instant.now()); 
	  
		  XSSFWorkbook workbook = new XSSFWorkbook(); 
		  XSSFSheet sheet = workbook.createSheet("Error Log"); 
	  
			int rowCount = 0;
			
			// Object is used to accommodate expansion of error log files where different cellType will be inserted
			for (Object[] errors : log) { 
				XSSFRow row = sheet.createRow(rowCount++);
				int columnCount = 0;
				
				for (Object value : errors) {
					XSSFCell cell = row.createCell(columnCount++);
					
						if (value instanceof String)
							cell.setCellValue((String) value);
						else if (value instanceof Integer)
							cell.setCellValue((Integer) value);
						else if (value instanceof Boolean)
							cell.setCellValue((Boolean) value);
				}
			}
			
			String logDirPath = dirPath + "logs\\";
			String filePath = logDirPath + dateFormat.format(instant).replaceAll("[: ]", "_") + ".xlsx"; // store the filepath
			
			FileOutputStream outputStream = new FileOutputStream(filePath); // create an output stream
			workbook.write(outputStream); // write into the output stream
			
			outputStream.close();
			workbook.close();
			
			return log;
	  }
	
	
	
	/*
	 * Read Table Data from Data Sheet per Event Excel File
	 */

	@Override
	public List<Object[]> readingEventsTable (XSSFWorkbook workbook, ArrayList<String> eventFields, String identifier) throws ConstraintViolationException {

		XSSFSheet dataSheet = workbook.getSheet("Data");
		Iterator<Row> iterator = dataSheet.iterator();
		iterator.next(); // skip headers
		
		
		/*
		 * for headers also, I added an ID header 
		 * since there is a possibility some rows will encounter the same error
		 */ 
		
		List<Object[]> errors = new ArrayList<>();
		
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
						break;
						
					case 1:
							type = (int) readingEnums(workbook, eventFields.get(cellIndex)).get(cell.getStringCellValue());
						break;
						
					case 2:
							title = cell.getStringCellValue();
						break;
						
					case 3:
							banner = cell.getStringCellValue();
	
							/*
							 * the banner cell value of excel should include the extension and must be
							 * present in the established image directory
							 */
							
							if(cell.getCellType() != CellType.BLANK)
									moveFile(banner.trim(), "server");
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
			
			try {
				eRepository.save(new Events(id, type, title, banner, description, startDate, endDate, regStart, regEnd, createDate, 
						updateDate, createUserId, updateUserId, isDeleted, isInternal, paymentFee, rideId, location));
			} catch (ConstraintViolationException e) {
				
				e.getConstraintViolations().forEach(v -> errors.add(new Object[] {"Events", identifier, v.getMessage()}));
			}
			
		}
		
		return errors;
	}
	
	
	

	
	
	/*
	 * Performs the same processes through files
	 */

	@Override
	 public void loopThroughFiles() throws IOException {
		
		String excelDirPath = dirPath + "files\\";
		List<String> fileNames = getFileList(excelDirPath, "xlsx");
		Map<String, Object> config; // to accommodate multiple config details in the future		
		
		List<Object[]> errors = new ArrayList<>();
		errors.add(new Object[] {"Table", "Identifier", "Error Message"}); 
		
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
					
						errors.addAll(readingEventsTable(workbook, eventFields, config.get("Identifier").toString()));
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

		writeIntoExcel(errors);
		copyFile(fileNames);
	}

}
