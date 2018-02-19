package mesosticSystemOOP;

/**
 * @author Martin Dowling


 * 
 * A useful little object for the arguments in
 * NextItem secondary constructors which returns 
 * a String from a Filename reference to a File on disk
 */
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
