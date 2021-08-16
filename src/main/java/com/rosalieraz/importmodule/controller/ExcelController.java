package com.rosalieraz.importmodule.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosalieraz.importmodule.service.FileHandlingService;

@RestController
public class ExcelController {
	
	@Autowired
	private FileHandlingService fhService;
	
	@GetMapping("/file-list")
	public List<String> getFileList () {
		return fhService.getFileList();
	}
	
	@GetMapping("/log-excel")
	public void readExcel () {
		try {
			fhService.readingExcel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
