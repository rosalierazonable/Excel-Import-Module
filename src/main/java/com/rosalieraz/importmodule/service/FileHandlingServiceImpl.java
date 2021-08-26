package com.rosalieraz.importmodule.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosalieraz.importmodule.EmergencyNosTable;
import com.rosalieraz.importmodule.Enumerations;
import com.rosalieraz.importmodule.EventAttendanceTable;
import com.rosalieraz.importmodule.FileManipulation;
import com.rosalieraz.importmodule.GuestAttendanceTable;
import com.rosalieraz.importmodule.InterestsTable;
import com.rosalieraz.importmodule.PartnersTable;
import com.rosalieraz.importmodule.PaymentsTable;
import com.rosalieraz.importmodule.properties.AppConfigReader;
import com.rosalieraz.importmodule.EventsTable;

@Service
public class FileHandlingServiceImpl implements FileHandlingService {

	@Autowired
	private Enumerations enums;

	@Autowired
	private FileManipulation file;

	@Autowired
	private EventsTable events;

	@Autowired
	private PartnersTable partners;

	@Autowired
	private EmergencyNosTable emergency;

	@Autowired
	private PaymentsTable payments;

	@Autowired
	private InterestsTable interest;

	@Autowired
	private EventAttendanceTable eventAttendance;

	@Autowired
	private GuestAttendanceTable guestAttendance;
	
	@Autowired
	AppConfigReader acReader;

	/**
	 * String constant for field names definition for each table
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


	@Override
	public String getExtension(String fileName) {
		String extension = "";

		if (fileName.contains("."))
			extension = fileName.substring(fileName.lastIndexOf(".") + 1);

		return extension;
	}

	@Override
	public List<String> getFileList(String path, String extension) {

		List<String> fileNames = new ArrayList<>();

		File dir = new File(path);
		File[] fileList = dir.listFiles();

		for (File f : fileList) {
			if (f.isFile() && getExtension(f.getName()).equalsIgnoreCase(extension))
				fileNames.add(f.getName());
		}

		return fileNames;
	}

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
						keyStr = cell.getStringCellValue().trim();
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

	@Override
	public Map<String, Integer> readingEnums(XSSFWorkbook workbook, String field) {

		// XSSFSheet enumSheet = workbook.getSheet(field);
		/*
		 * Map<String, Integer> enums = new HashMap<>();
		 * 
		 * Iterator<Row> configItr = enumSheet.iterator();
		 * 
		 * while (configItr.hasNext()) { XSSFRow row = (XSSFRow) configItr.next();
		 * String keyStr = null; Integer value = null;
		 * 
		 * keyStr = row.getCell(0).getStringCellValue(); value = (int)
		 * row.getCell(1).getNumericCellValue();
		 * 
		 * enums.put(keyStr, value); }
		 */

		return enums.read(workbook, field);
	}

	@Override
	public List<Object[]> writeIntoExcel(List<Object[]> log) throws IOException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Timestamp instant = Timestamp.from(Instant.now());

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Error Log");

		int rowCount = 0;

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

		String filePath = acReader.getLogpath() + dateFormat.format(instant).replaceAll("[: ]", "_") + ".xlsx"; 

		FileOutputStream outputStream = new FileOutputStream(filePath); 
		workbook.write(outputStream); 

		outputStream.close();
		workbook.close();

		return log;
	}

	@Override
	public List<Object[]> readingEventsTable(XSSFWorkbook workbook, ArrayList<String> eventFields,
			Map<String, Object> config) throws ConstraintViolationException {

		return events.read(workbook, eventFields, config);
	}

	@Override
	public List<Object[]> readingPartnersTable(XSSFWorkbook workbook, ArrayList<String> partnerFields,
			Map<String, Object> config) throws ConstraintViolationException {

		return partners.read(workbook, partnerFields, config);
	}

	@Override
	public List<Object[]> readingEmergencyNosTable(XSSFWorkbook workbook, ArrayList<String> eNumFields,
			Map<String, Object> config) throws ConstraintViolationException {

		return emergency.read(workbook, eNumFields, config);
	}

	@Override
	public List<Object[]> readingPaymentsTable(XSSFWorkbook workbook, ArrayList<String> paymentFields,
			Map<String, Object> config) throws ConstraintViolationException {

		return payments.read(workbook, paymentFields, config);
	}

	@Override
	public List<Object[]> readingInterestsTable(XSSFWorkbook workbook, ArrayList<String> interestFields,
			Map<String, Object> config) throws ConstraintViolationException {

		return interest.read(workbook, interestFields, config);
	}

	@Override
	public List<Object[]> readingEATable(XSSFWorkbook workbook, ArrayList<String> eAFields, Map<String, Object> config)
			throws ConstraintViolationException {

		return eventAttendance.read(workbook, eAFields, config);
	}

	@Override
	public List<Object[]> readingGATable(XSSFWorkbook workbook, ArrayList<String> gAFields, Map<String, Object> config)
			throws ConstraintViolationException {

		return guestAttendance.read(workbook, gAFields, config);
	}

	@Override
	public List<Object[]> addToErrors(List<Object[]> errors, List<Object[]> log) {
		if (!errors.isEmpty()) {
			log.addAll(errors);
		}

		return log;
	}

	@Override
	public void loopThroughFiles() throws IOException {

		List<String> fileNames = getFileList(acReader.getFilepath(), "xlsx");
		Map<String, Object> config;

		List<Object[]> errors = new ArrayList<>();
		errors.add(new Object[] { TABLE, IDENTIFIER, "Error Message" });

		for (String fName : fileNames) {

			String excelFilePath = acReader.getFilepath() + fName;
			FileInputStream inputStream = new FileInputStream(excelFilePath);

			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

			config = readingConfigDetails(workbook);

			switch (config.get(TABLE).toString()) {

			case "Events":
				ArrayList<String> eventFields = new ArrayList<>(Arrays.asList(EVENT_ID, "eventType", "eventTitle",
						"banner", DESC, "startDate", END_DATE, "regStart", "regEnd", CREATE_DATE, UPDATE_DATE,
						CREATE_USER_ID, UPDATE_USER_ID, "isDeleted", "isInternal", "paymentFee", "rideId", "location"));

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
						"nextBillingDate", CREATE_DATE, UPDATE_DATE, CREATE_USER_ID, UPDATE_USER_ID));

				errors = addToErrors(readingPaymentsTable(workbook, paymentFields, config), errors);
				break;

			case "Interests":
				ArrayList<String> interestFields = new ArrayList<>(Arrays.asList("interestId", "name", "isDeleted",
						CREATE_DATE, CREATE_USER_ID, UPDATE_DATE, UPDATE_USER_ID, DESC));

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

		writeIntoExcel(errors);
		file.copy(fileNames);
	}
}
