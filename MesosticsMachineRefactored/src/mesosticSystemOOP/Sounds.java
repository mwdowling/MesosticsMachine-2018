package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This object extracts words from the target chapter 
 * that match words in a sound repository
 * and appends those words with its array index value to
 * the "sound" word repository 
 * 
 * NEEDS WORK!!
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Sounds {

	//input variables for target chapter
	private String ChapterAddress;
	private String[] ChapterArray;

	// variables for sounds repository
	private String OEDSounds;
	private String[] SoundRepositoryArray;

	// variables for output files
	private String SoundWords;



	// constructor
    
	
	// overridden method to find words matching a word in the sound repository
	public void SoundWord() throws IOException {
	
		// for loop to get chapter words that match a sound repository word
		for (int i = 0; i < ChapterArray.length; i++) {

			String wordChapter = ChapterArray[i].toLowerCase().replaceAll("\\W", "").trim();

			for (int j = 0; j < SoundRepositoryArray.length; j++) {

				String wordSound = SoundRepositoryArray[j];

				if (wordChapter.startsWith(wordSound)) {
					
					// check validity of wordSound
					System.out.println(i + "\t" + wordChapter + "\t" + wordSound);
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(SoundWords), true));
					bw.write(i + "\t" + wordChapter + "\t" + wordSound);
					// adding "\r" to this string gets rid of the exception but puts in miles of whitespace
					// need to write a file with just the found word and its index also 
					bw.newLine();
					bw.close();

				}
			} // end of soundArray loop
		} // end of chapterArray loop
	}
}
