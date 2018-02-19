package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * A class for the creation of an object
 * that writes the appropriate syllable of a target word
 * to a repository of syllables corresponding to a particular 
 * letter of the mesostics row 
 * 
 * TODO Does this object really need to call the Syllable object again??
 * 		WHy not take the saved syllable as an argument.
 * 
 */

import java.io.IOException;
import java.util.ArrayList;

public class SyllableLineWriter {
	
	private String Directory;
	private Syllable S; 
	private SyllableRecord Sr;
	
	public SyllableLineWriter (String directory, Syllable s, SyllableRecord sr) {
		Directory = directory;
		S = s;//in this application s = new Syllable(output)
		Sr = sr;
	}
	
	void WriteSyllable(ArrayList<String[]> output) throws IOException, InterruptedException{
		
		String savedSyllable = S.SyllableSaved(output.get(0)[1]);
		String Repository = Sr.Repository();
		Sr.WriteSyllableToRepository(Repository, savedSyllable);
	}

}
