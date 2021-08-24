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
import com.rosalieraz.importmodule.model.Partners;
import com.rosalieraz.importmodule.model.EmergencyNumbers;

import com.rosalieraz.importmodule.repository.EmergencyNumbersRepository;
import com.rosalieraz.importmodule.repository.EventAttendanceRepository;
import com.rosalieraz.importmodule.repository.EventsRepository;
import com.rosalieraz.importmodule.repository.GuestAttendanceRepository;
import com.rosalieraz.importmodule.repository.InterestsRepository;
import com.rosalieraz.importmodule.repository.PartnersRepository;
import com.rosalieraz.importmodule.repository.PaymentsRepository;

@Service
public class FileHandlingServiceImpl implements FileHandlingService {

	/*
	 * Dependency Injection
	 */

	@Autowired
	EventsRepository eRepository;

	@Autowired
	PartnersRepository pRepository;

	@Autowired
	EmergencyNumbersRepository eNRepository;

	@Autowired
	PaymentsRepository paymentsRepository;

	@Autowired
	InterestsRepository iRepository;

	@Autowired
	EventAttendanceRepository eARepository;

	@Autowired
	GuestAttendanceRepository gARepository;

	
	/*
	 * String Constants
	 */

	private static final String IDENTIFIER = "Identifier";
	private static final String TABLE = "Table";
	private static final String CREATE_DATE = "createDate";
	private static final String UPDATE_DATE = "updateDate";
	private static final String CREATE_USER_ID = "createUserId";
	private static final String UPDATE_USER_ID = "updateUserId";
	private static final String EVENT_ID = "eventId";
	private static final String END_DATE = "endDate";
	private static final String DESC = "description";
	private static final String PAYMENT_ID = "paymentId";
	private static final String U_ID = "userId";

	
	// Files Directory Path
	private String dirPath = ".\\src\\main\\resources\\static\\";

	
	
	/*
	 * Get File Extension, It accommodates the fact that the admin may include files
	 * that are not of xlsx type
	 */

	@Override
	public String getExtension(String fileName) {
		String extension = "";

		if (fileName.contains("."))
			extension = fileName.substring(fileName.lastIndexOf(".") + 1);

		return extension;
	}

	
	/*
	 * List all Excel Files inside Files Directory, Accepst path and extension
	 * paramaters to accommodate future use to list files in other desired path
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
	 * Reading Corresponding Enums Parameters: workbook and the enum field to be
	 * opened
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
	 * Copying processed Excel File to Processed directory
	 */

	@Override
	public void copyFile(List<String> fileNames) throws IOException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Timestamp instant = Timestamp.from(Instant.now());

		String targetPath = dirPath + "processed\\";

		File file = new File(targetPath + dateFormat.format(instant).replaceAll("[: ]", "_"));
		// file.createNewFile();
		boolean flag = file.mkdir();

		for (String fileName : fileNames) {
			if (flag) {
				Path sourceFile = Paths.get(dirPath + "files\\" + fileName);
				Path targetFile = Paths
						.get(targetPath + dateFormat.format(instant).replaceAll("[: ]", "_") + "\\" + fileName);
				Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}

	
	/*
	 * Moving Files: - The directory is already established: ...//images - Target
	 * directory is the destination directory
	 */

	@Override
	public void moveFile(String fileName, String targetDirectory) {

		String srcFile = dirPath + "images//" + fileName;
		String destFile = dirPath + targetDirectory + "//" + fileName;

		try {
			Path temp = Files.move(Paths.get(srcFile), Paths.get(destFile), StandardCopyOption.REPLACE_EXISTING);

			if (temp != null)
				System.out.println("File renamed and moved successfully");
			else
				System.out.println("Failed! Something went wrong!");

		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	
	/*
	 * Writing into Log Excel File Convention: - FileName: Current_timestamp -
	 * Fields inside sheet: Table name, Identifier, Field, Error
	 */

	@Override
	public List<Object[]> writeIntoExcel(List<Object[]> log) throws IOException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Timestamp instant = Timestamp.from(Instant.now());

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Error Log");

		int rowCount = 0;

		// Object is used to accommodate expansion of error log files where different
		// cellType will be inserted
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
		String filePath = logDirPath + dateFormat.format(instant).replaceAll("[: ]", "_") + ".xlsx"; // store the
																										// filepath

		FileOutputStream outputStream = new FileOutputStream(filePath); // create an output stream
		workbook.write(outputStream); // write into the output stream

		outputStream.close();
		workbook.close();

		return log;
	}

	
	/*
	 * Loading Iterator
	 */

	@Override
	public Iterator<Row> loadingIterator(XSSFWorkbook workbook) {
		XSSFSheet dataSheet = workbook.getSheet("Data");
		return dataSheet.iterator();
	}

	
	/*
	 * Read Table Data from Data Sheet per Event Excel File
	 */

	@Override
	public List<Object[]> readingEventsTable(XSSFWorkbook workbook, ArrayList<String> eventFields,
			Map<String, Object> config) throws ConstraintViolationException {

		Iterator<Row> iterator = loadingIterator(workbook);
		List<Object[]> errors = new ArrayList<>();

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

				switch (cellIndex) {

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

					if (cell.getCellType() != CellType.BLANK)
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
					regStart = cell.getDateCellValue();
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
					isDeleted = (int) readingEnums(workbook, eventFields.get(cellIndex))
							.get(cell.getStringCellValue().trim());
					break;

				case 14:
					isInternal = (int) readingEnums(workbook, eventFields.get(cellIndex))
							.get(cell.getStringCellValue());
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
				eRepository.save(new Events(id, type, title, banner, description, startDate, endDate, regStart, regEnd,
						createDate, updateDate, createUserId, updateUserId, isDeleted, isInternal, paymentFee, rideId,
						location));
			} catch (ConstraintViolationException e) {

				e.getConstraintViolations().forEach(
						v -> errors.add(new Object[] { config.get(TABLE), config.get(IDENTIFIER), v.getMessage() }));
			}
		}

		if (!errors.isEmpty())
			return errors;
		else
			return null;
	}

	
	/*
	 * Read Table Data from Data Sheet per Partners Excel File
	 */
	
	@Override
	public List<Object[]> readingPartnersTable(XSSFWorkbook workbook, ArrayList<String> partnerFields,
			Map<String, Object> config) throws ConstraintViolationException {

		Iterator<Row> iterator = loadingIterator(workbook);
		List<Object[]> errors = new ArrayList<>();

		iterator.next();
		while (iterator.hasNext()) {

			Integer id = 0;
			String username = "";
			String password = "";
			String code = "";
			String name = "";
			String email = "";
			String image = "";
			String description = "";
			Double sponsorship = (double) 0;
			Date startDate = new Date();
			Date endDate = new Date();
			Integer order = 0;
			Integer isDeleted = 0;
			Date createDate = new Date();
			Date updateDate = new Date();
			Integer createUserId = 0;
			Integer updateUserId = 0;
			Integer isFeatured = 0;

			XSSFRow row = (XSSFRow) iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {

				XSSFCell cell = (XSSFCell) cellIterator.next();
				int cellIndex = cell.getColumnIndex();

				switch (cellIndex) {

				case 0:
					id = (int) cell.getNumericCellValue();
					break;

				case 1:
					username = cell.getStringCellValue();
					break;

				case 2:
					password = cell.getStringCellValue();
					break;

				case 3:
					code = cell.getStringCellValue();
					break;

				case 4:
					name = cell.getStringCellValue();
					break;

				case 5:
					email = cell.getStringCellValue();
					break;

				case 6:
					image = cell.getStringCellValue();

					if (cell.getCellType() != CellType.BLANK)
						moveFile(image.trim(), "server");

					break;

				case 7:
					description = cell.getStringCellValue();
					break;

				case 8:
					sponsorship = (double) cell.getNumericCellValue();
					break;

				case 9:
					startDate = cell.getDateCellValue();
					break;

				case 10:
					endDate = cell.getDateCellValue();
					break;

				case 11:
					order = (int) cell.getNumericCellValue();
					break;

				case 12:
					isDeleted = (int) readingEnums(workbook, partnerFields.get(cellIndex))
							.get(cell.getStringCellValue());
					break;

				case 13:
					createDate = cell.getDateCellValue();
					break;

				case 14:
					createUserId = (int) cell.getNumericCellValue();
					break;

				case 15:
					updateDate = cell.getDateCellValue();
					break;

				case 16:
					updateUserId = (int) cell.getNumericCellValue();
					break;

				case 17:
					isFeatured = (int) readingEnums(workbook, partnerFields.get(cellIndex))
							.get(cell.getStringCellValue().trim());
					break;

				default:
					break;

				}
			}

			try {

				pRepository.save(new Partners(id, username, password, code, name, email, image, description,
						sponsorship, startDate, endDate, order, createUserId, updateDate, createDate, updateUserId,
						isDeleted, isFeatured));

			} catch (ConstraintViolationException e) {
				e.getConstraintViolations().forEach(
						v -> errors.add(new Object[] { config.get(TABLE), config.get(IDENTIFIER), v.getMessage() }));
			}
		}
		if (!errors.isEmpty())
			return errors;
		else
			return null;
	}

	
	/*
	 * Read Emergency_Numbers Data from Excel File
	 */
	
	@Override
	public List<Object[]> readingEmergencyNosTable(XSSFWorkbook workbook, ArrayList<String> eNumFields,
			Map<String, Object> config) throws ConstraintViolationException {

		Iterator<Row> iterator = loadingIterator(workbook);
		List<Object[]> errors = new ArrayList<>();

		iterator.next();
		while (iterator.hasNext()) {

			Integer id = 0;
			Integer type = 0;
			Integer number = 0;

			XSSFRow row = (XSSFRow) iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {

				XSSFCell cell = (XSSFCell) cellIterator.next();
				int cellIndex = cell.getColumnIndex();

				switch (cellIndex) {

				case 0:
					id = (int) cell.getNumericCellValue();
					break;

				case 1:
					type = (int) readingEnums(workbook, eNumFields.get(cellIndex)).get(cell.getStringCellValue());
					break;

				case 2:
					number = (int) cell.getNumericCellValue(); // on design doc this is set to string but the apache poi
																// library doesnt let double
					// to be casted to string and using .toString() method would still insert double
					// value for these data
					break;

				default:
					break;
				}
			}

			try {

				eNRepository.save(new EmergencyNumbers(id, type, number));

			} catch (ConstraintViolationException e) {
				e.getConstraintViolations().forEach(
						v -> errors.add(new Object[] { config.get(TABLE), config.get(IDENTIFIER), v.getMessage() }));
			}
		}

		if (!errors.isEmpty())
			return errors;
		else
			return null;
	}
	
	
	/*
	 * Read Payments Data from Excel File
	 */
	
	@Override
	public List<Object[]> readingPaymentsTable(XSSFWorkbook workbook, ArrayList<String> paymentFields,
			Map<String, Object> config) throws ConstraintViolationException {

		Iterator<Row> iterator = loadingIterator(workbook);
		List<Object[]> errors = new ArrayList<>();

		iterator.next();
		while (iterator.hasNext()) {

			

			XSSFRow row = (XSSFRow) iterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {

				XSSFCell cell = (XSSFCell) cellIterator.next();
				int cellIndex = cell.getColumnIndex();

				switch (cellIndex) {

				case 0:
					break;
					
				case 1:
					break;
					
				case 2:
					break;
					
				case 3:
					break;
					
				case 4:
					break;
					
				case 5:
					break;
					
				case 6:
					break;
					
				case 7:
					break;
					
				case 8:
					break;
					
				case 9:
					break;
					
				case 10:
					break;
					
				case 11:
					break;
					
				case 12:
					break;
					
				default:
					break;
				
				}

			}
		}
		return null;
	}

	
	/*
	 * Read Interests Data from Excel File
	 */
	
	@Override
	public List<Object[]> readingInterestsTable(XSSFWorkbook workbook, ArrayList<String> interestFields,
			Map<String, Object> config) throws ConstraintViolationException {
		return null;
	}
	
	
	/*
	 * Read Events_attendance Data from Excel File
	 */
	
	@Override
	public List<Object[]> readingEATable(XSSFWorkbook workbook, ArrayList<String> eAFields, Map<String, Object> config)
			throws ConstraintViolationException {
		return null;
	}
	
	
	/*
	 * Read Guest_attendance Data from Excel File
	 */
	
	@Override
	public List<Object[]> readingGATable(XSSFWorkbook workbook, ArrayList<String> gAFields, Map<String, Object> config)
			throws ConstraintViolationException {
		return null;
	}
	
	
	/*
	 * Check if errors list is not empty then add to errorsList
	 * Otherwise, do nothing
	 */
	
	@Override
	public List<Object[]> addToErrors (List<Object[]> errors, List<Object[]> log) {
		if(!errors.isEmpty()) {
			log.addAll(errors);
		}

		return log;
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
		errors.add(new Object[] { TABLE, IDENTIFIER, "Error Message" });

		for (String fName : fileNames) {

			String excelFilePath = excelDirPath + fName;
			FileInputStream inputStream = new FileInputStream(excelFilePath);

			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			config = readingConfigDetails(workbook);

			switch (config.get(TABLE).toString()) {

			case "Events":
					ArrayList<String> eventFields = new ArrayList<>(Arrays.asList(EVENT_ID, "eventType", "eventTitle",
							"banner", DESC, "startDate", END_DATE, "regStart", "regEnd", CREATE_DATE, UPDATE_DATE,
							CREATE_USER_ID, UPDATE_USER_ID, "isDeleted", "isInternal", "paymentFee", "rideId", "location"));
	
					// the reading-- function returns a list of errors, call addToErrors to evaluate if it's empty or not and do action based on it
					errors = addToErrors(readingEventsTable(workbook, eventFields, config), errors); 
				break;

			case "Partners":
					ArrayList<String> partnerFields = new ArrayList<>(Arrays.asList("partnerId", "username", "password",
							"code", "name", "email", "image", DESC, "sponsorship", "startDate", END_DATE, "listOrder",
							"isdeleted", CREATE_DATE, UPDATE_DATE, CREATE_USER_ID, UPDATE_USER_ID, "isFeaturedPartner"));
	
					errors = addToErrors(readingPartnersTable(workbook, partnerFields, config), errors);
				break;

			case "Payments":
					ArrayList<String> paymentFields = new ArrayList<>(Arrays.asList(PAYMENT_ID, U_ID, "paymentType",
							"transactionCode", "isRecurring", "paymentDate", "paymentFileName", "paymentDescription",
							"nextBillingDate", CREATE_DATE, END_DATE, CREATE_USER_ID, UPDATE_USER_ID));
	
					errors = addToErrors(readingPartnersTable(workbook, paymentFields, config), errors);
				break;

			case "Interests":
					ArrayList<String> interestFields = new ArrayList<>(Arrays.asList("interestId", "name", DESC,
							"isDeleted", CREATE_DATE, UPDATE_DATE, CREATE_USER_ID, UPDATE_USER_ID));
	
					errors = addToErrors(readingInterestsTable(workbook, interestFields, config), errors);
				break;

			case "Event_Attendance":
					ArrayList<String> eAFields = new ArrayList<>(Arrays.asList(EVENT_ID, U_ID, PAYMENT_ID, "signUpDate"));
	
					errors = addToErrors(readingEATable(workbook, eAFields, config), errors);
				break;

			case "Guest_Attendance":
					ArrayList<String> gAFields = new ArrayList<>(Arrays.asList(U_ID, EVENT_ID, PAYMENT_ID, "OCRD.U_Fname",
							"OCRD.U_Lname", "OCRD.U_Mname", "OCRD.U_Suffix", "OCRD.E_Mail", "OCRD.cellular", "CRD1.street",
							"CRD1.city", "state", "CRD1.country", "CRD1.zipcode", "bikeModel", "OCRD.CreateDate",
							"OCRD.UpdateDate", CREATE_USER_ID, UPDATE_USER_ID, "OCRD.UpdateTS", "OCRD.CreateTS"));
	
					errors = addToErrors(readingGATable(workbook, gAFields, config), errors);
				break;

			case "Emergency_Numbers":
					ArrayList<String> eNumFields = new ArrayList<>(Arrays.asList("numId", "type", "contactNumber"));
	
					errors = addToErrors(readingEmergencyNosTable(workbook, eNumFields, config), errors);
				break;

			default:
				break;
			}
		}

//		writeIntoExcel(errors); // error logging
//		copyFile(fileNames); // copy files that have been processed for the current scheduled date to processed directory
	}
}
