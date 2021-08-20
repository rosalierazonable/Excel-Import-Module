package com.rosalieraz.importmodule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosalieraz.importmodule.ImportScheduledTask;

@RestController
public class ExcelController {

	@Autowired
	private ImportScheduledTask task;
	
	
	@GetMapping("/scheduledTask")
	public void schedTask () {
		
		task.importExcel();
	}
}
