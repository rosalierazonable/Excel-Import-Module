package com.rosalieraz.importmodule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rosalieraz.importmodule.model.EmergencyNumbers;
import com.rosalieraz.importmodule.repository.EmergencyNumbersRepository;

@Component
public class EmergencyNosTable {
	
	@Autowired
	private IteratorLoading itr;
	
	@Autowired
	private Enumerations enums;
	
	@Autowired
	EmergencyNumbersRepository eNRepository;
	
	
	public List<Object[]> read (XSSFWorkbook workbook, List<String> eNumFields,
			Map<String, Object> config) throws ConstraintViolationException {
		
		Iterator<Row> iterator = itr.load(workbook);
		List<Object[]> errors = new ArrayList<>();
		
		DataFormatter dataFormatter= new DataFormatter();

		iterator.next();
		while (iterator.hasNext()) {

			Integer id = 0;
			Integer type = 0;
			String number = "";

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
						type = (int) enums.read(workbook, eNumFields.get(cellIndex)).get(cell.getStringCellValue());
					break;

				case 2:
						number = dataFormatter.formatCellValue(cell); 
					break;

				default:
					break;
				}
			}

			try {

				eNRepository.save(new EmergencyNumbers(id, type, number));

			} catch (ConstraintViolationException e) {
				e.getConstraintViolations().forEach(
						v -> errors.add(new Object[] { config.get("Table"), config.get("Identifier"), v.getMessage() }));
			}
		}
		return errors;
	}
	
}
