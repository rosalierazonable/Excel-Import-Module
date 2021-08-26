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

import com.rosalieraz.importmodule.model.Partners;
import com.rosalieraz.importmodule.repository.PartnersRepository;

@Component
public class PartnersTable {

	@Autowired
	private IteratorLoading itr;
	
	@Autowired
	private Enumerations enums;
	
	
	@Autowired
	private FileManipulation file;
	
	@Autowired
	PartnersRepository pRepository;
	
	public List<Object[]> read (XSSFWorkbook workbook, List<String> partnerFields,
			Map<String, Object> config) throws ConstraintViolationException {
		
		Iterator<Row> iterator = itr.load(workbook);
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
							file.move(image.trim(), "server");

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
						isDeleted = (int) enums.read(workbook, partnerFields.get(cellIndex))
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
						isFeatured = (int) enums.read(workbook, partnerFields.get(cellIndex))
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
						v -> errors.add(new Object[] { config.get("Table"), config.get("Identifier"), v.getMessage() }));
			}
		}
		return errors;
	}
	
}
