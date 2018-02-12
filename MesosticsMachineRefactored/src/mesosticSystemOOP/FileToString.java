package mesosticSystemOOP;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileToString {

	private String FileName;

	public FileToString(String fileName) {
		FileName = fileName;
	}
	
	public String output() throws IOException {
		return new String(Files.readAllBytes(Paths.get(FileName)));
	}
}
