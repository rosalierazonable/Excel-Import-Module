package com.rosalieraz.importmodule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rosalieraz.importmodule.properties.AppConfigReader;

@Component
public class FileManipulation {
	
	@Autowired
	AppConfigReader acReader;
	
	/**
	 * Mock function for moving file images from image directory to server 
	 * @param fileName is the image file name including its extension
	 * @param targetDirectory is the directory where the image file must be moved
	 */

	public void move (String fileName, String targetDirectory) {
		String srcFile = acReader.getImagepath() + fileName;
		String destFile = acReader.getRoot() + targetDirectory + "\\" + fileName;

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
	
	
	/**
	 * A method for copying Excel Files that have been scanned during the current scheduled import scan
	 * @param fileNames is a list of string containing all the filenames that were successfully opened during the import scan
	 * @throws IOException when fileNames is null or cannot be found
	 */
	
	
	public void copy (List<String> fileNames) throws IOException {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Timestamp instant = Timestamp.from(Instant.now());

		String targetPath = acReader.getProcessedpath();

		File file = new File(targetPath + dateFormat.format(instant).replaceAll("[: ]", "_"));
		boolean flag = file.mkdir();

		for (String fileName : fileNames) {
			if (flag) {
				Path sourceFile = Paths.get(acReader.getFilepath() + fileName);
				Path targetFile = Paths
						.get(targetPath + dateFormat.format(instant).replaceAll("[: ]", "_") + "\\" + fileName);
				Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
			}
		}
		
	}
}
