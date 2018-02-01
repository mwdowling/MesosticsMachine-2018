package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This class allows for the creation of an object that does the following:
 * 
 * (1) has instance variables for the mesosticRow and the target letter in the row
 * (2) instantiates these along with a saved syllable in its constructor
 * (3) has a 2 methods, the first of which:
 *     (a) creates a HashMap of syllable repository file addresses
 *     (b) instantiates and returns the appropriate address for the target letter
 * (4) the second method
 *     (a) instantiates a boolean where true = "saved syllable is in the target repository"
 *     (b) if (4)(a) is true, returns true
 *     (c) if (4)(a) is false, writes the syllable to the last line of the repository and returns false 
 *     
 * This class requires the user to have previously established the syllable repositories
 * These are text files in a target folder
 * There is one file for each letter of the mesostic row, named "MesosticLetterX.txt"
 * Where X = the index number of the letter in the mesostic row.
 * The first line of this text has the characters XY
 * where X = the index number of the letter in the mesostic row
 * and Y = the letter so indexed.
 * 
 * In a future version of the software this target folder will be populated
 * in the Setup routine of the GUI
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



public class SyllableRecord {

	//don't I need rowarray variable here to feed first method?
	// constructor
	public SyllableRecord() {

	}

	// a method to return the correct syllable repository
	public String Repository() throws IOException {
		
		// The target letter in the row
		String Letter = NextItemAbstract.RowArray[NextItemAbstract.RowArrayIndex];
		
		// The repository for the target letter's syllables
		String Repository = null;

		/*
		 * create a HashMap repository for each existing syllable repository
		 * (NOTE: these files are created with these exact names via the GUI)
		 */
		Map<Integer, String> Repositories = new HashMap<>();
		for (int i = 0; i < NextItemAbstract.RowArray.length; i++) {
			Repositories.put(i, NextItemAbstract.Directory + "\\Syllable Repositories\\MesoticLetter" + (i + 1) + ".txt");
		}

		// get appropriate syllable repository file
		for (Integer key : Repositories.keySet()) {

			Repository = Repositories.get(key);
			BufferedReader br = new BufferedReader(new FileReader(new File(Repository)));
			String line = br.readLine();

			int n = NextItemAbstract.RowArrayIndex + 1;
			String m = n + Letter;
			if (line.startsWith(m)) {
				break;
			}
			br.close();

		}

		return Repository;

	}

	// a method to confirm whether the repository has the target syllable
	public boolean RepositoryHasSyllable(String repository, String savedSyllable) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(new File(repository)));

		boolean RepositoryHas = true;

		for (String line = br.readLine(); line != null; line = br.readLine()) {
			if (line.equals(savedSyllable)) {
				System.out.println("Repository has Syllable.");
				RepositoryHas = true;
				break;
			} else
				RepositoryHas = false;
		}
		br.close();

		return RepositoryHas;
	}

	//a method to write the saved syllable to the repository
	public void WriteSyllableToRepository(String repository, String savedSyllable) throws IOException {

		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(repository), true));
		System.out.println("Syllable not in Repository.");
		bw.newLine();
		bw.write(savedSyllable);
		System.out.println("Syllable written to Repository.");
		bw.close();
	}
}
