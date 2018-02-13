package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This object extracts words from the target chapter 
 * that match words in a OED sound repository
 * and appends those words with the approriate array index value to
 * the "Chapter Sounds" file  
 * 
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Sounds extends NextItemAbstract{

	// secondary constructor
	public Sounds(String oedSounds, String chapter, String soundWords) throws IOException {
		this(new FileToString(OEDSounds).output(), new FileToString(ChapterAddress).output(), 
				new FileToString(SoundWords).output(), new LineMesostic(soundWords));
	}
	
	// primary constructor
	public Sounds(String oedSounds, String chapter, String soundWords, LineMesostic lm) {
		super(oedSounds, chapter, soundWords, lm); 		
	}

	@Override
	public ArrayList<String[]> Item() throws IOException, InterruptedException {
		
		ArrayList<String[]> output = new ArrayList<String[]>();
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		outputList.clear(); 

		// for loop to get chapter words that match a sound repository word
		for (int i = 0; i < ChapterArray.length; i++) {
				
			String wordInChapter = ChapterArray[i].toLowerCase().replaceAll("\\W", "").trim();
				
			for (int j = 0; j < OEDSoundsArray.length; j++) {
				
				String wordInOEDSounds = OEDSoundsArray[j];
				if (wordInChapter.startsWith(wordInOEDSounds)) {
					
					String index = String.valueOf(i);
					String[] wordIsSound = {index, wordInChapter, wordInOEDSounds};
					output.clear();
					output.add(wordIsSound);
					outputList.addAll(output);
					
				}
			}				
		}
		return outputList;
	}

	@Override
	public void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		Lm.WriteChapter(outputList);		
	}

	@Override
	public void AdvanceChapterWord(String index) {
		// no implementation required		
	}
}
