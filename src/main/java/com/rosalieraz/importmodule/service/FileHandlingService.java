package com.rosalieraz.importmodule.service;

import java.io.IOException;
import java.util.List;

public interface FileHandlingService {

	List<String> getFileList ();
	
	void readingExcel () throws IOException;
}
