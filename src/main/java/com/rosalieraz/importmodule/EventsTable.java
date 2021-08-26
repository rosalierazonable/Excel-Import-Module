package com.rosalieraz.importmodule;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rosalieraz.importmodule.model.Events;
import com.rosalieraz.importmodule.repository.EventsRepository;

@Component
public class EventsTable {
	
	@Autowired
	private IteratorLoading itr;
	
	@Autowired
	private Enumerations enums;
	
	
	@Autowired
	private FileManipulation file;
	
	
	@Autowired
	EventsRepository eRepository;
	

	public List<Object[]> read (XSSFWorkbook workbook, List<String> eventFields,
			Map<String, Object> config) throws ConstraintViolationException {
		
		List<Object[]> errors = new ArrayList<>();
		Iterator<Row> iterator = itr.load(workbook);

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
					type = (int) enums.read(workbook, eventFields.get(cellIndex)).get(cell.getStringCellValue());
					break;

				case 2:
					title = cell.getStringCellValue();
					break;

				case 3:
					banner = cell.getStringCellValue();

					if (cell.getCellType() != CellType.BLANK)
						file.move(banner.trim(), "server");
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
					isDeleted = (int) enums.read(workbook, eventFields.get(cellIndex))
							.get(cell.getStringCellValue().trim());
					break;

				case 14:
					isInternal = (int) enums.read(workbook, eventFields.get(cellIndex))
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
						v -> errors.add(new Object[] { config.get("Table"), config.get("Identifier"), v.getMessage()}));
			}
		}
		return errors;
	}
	
}
