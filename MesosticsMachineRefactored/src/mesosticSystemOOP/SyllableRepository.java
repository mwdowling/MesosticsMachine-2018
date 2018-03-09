package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This class allows for the creation of an object which
 * 
 * (1) Takes as parameters variables for the mesostic row and its file location
 * (2) Using a HashMap, creates a syllable repository file 
 * 		for each letter of the mesostic row
 * (3) Writes an identifying string to the first line of each file 
 * 		identifying each file by mesostic letter and array index of that letter,
 * 		thereby distinguishing between files 
 * 		with the same mesostic letter and different array indexes
 * 
 * This class to be used in the SetUp functionality of the MesosticMachine GUI
 */
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public final class SyllableRepository {

	private String Row;
	private String Directory;

	public SyllableRepository(String row, String directory) {
		Row = row;
		Directory = directory;
	}

	public final void RepositoryWriter() {

		// convert the row to array of letters
		String[] RowArray = Row.split("");
		// convert mesosticRow to HashMap
		Map<Integer, String> SyllableMap = new HashMap<>();

		// create a file for each letter in the row
		for (int i = 0; i < RowArray.length; i++) {
			String LetterFile = Directory + "\\Syllable Repositories\\MesoticLetter" + (i + 1) + ".txt";
			SyllableMap.put(i, LetterFile);
			try (PrintWriter out = new PrintWriter(LetterFile)) {
				// write identifying first line to the file
				out.println(i + 1 + RowArray[i]);
			} catch (FileNotFoundException e1) {
				System.out.println("Error: syllable repository not found.");
				e1.printStackTrace();
			}
		}
	}
}
