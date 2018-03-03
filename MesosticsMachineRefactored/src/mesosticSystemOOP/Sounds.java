package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This object extracts words from the target chapter 
 * that match words in a OED sound repository
 * and appends those words with the approriate array index value to
 * the "Chapter Sounds" file.
 * 
 *   TODO: Train this on sample chapters
 * 
 */

import java.io.IOException;
import java.util.ArrayList;

public class Sounds extends ItemAbstract{

	// secondary constructor
	public Sounds(String comparator, String chapter, String outputFile) throws IOException {
		this(new FileToString(MesosticsMachineGUI.OEDSounds).output(), new FileToString(MesosticsMachineGUI.ChapterAddress).output(), 
				new FileToString(MesosticsMachineGUI.Sounds).output(), new MesosticsLineWriter(outputFile));
	}
	
	// primary constructor
	public Sounds(String comparator, String chapter, String outputFile, MesosticsLineWriter lm) {
		super(comparator, chapter, outputFile, lm); 	
		OEDSoundsArray = comparator.split("\t");
		
	}

	@Override
	public ArrayList<String[]> NextItem() throws IOException, InterruptedException {
		
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
	public String AdvanceChapterWord(String index) {
		// no implementation required
		System.out.println("This method does nothing");
		return index;
	}
}
