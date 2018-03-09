package mesosticSystemOOP;

/**
 * @author Martin Dowling

 * 
 * A concrete decorator that traverses a chapter 
 * creating all the mesostics of that chapter while 
 * filtering to select words whose appropriate syllable 
 * is not yet in its corresponding syllable repository
 * and maintaining the syllable repositories 
 * for each letter of the mesostic row.
 * 
 * This is not currently in the GUI because the webdriver functionality
 * is variable and unstable. Better to use the NextWordFiltered object
 * inside a small loop.  
 *   
 */

import java.io.IOException;
import java.util.ArrayList;

public final class ChapterFiltered extends ItemFiltered {
	
	//secondary constructor throws exception
	public ChapterFiltered(String row, String chapter, String mesostics, Item decoratedNextItem) throws IOException {
		this(new FileToString(MesosticsMachineGUI.RowAddress).output(), new FileToString(MesosticsMachineGUI.ChapterAddress).output(), 
				MesosticsMachineGUI.Mesostics, new MesosticsLineWriter(mesostics), new Word(row, chapter, mesostics), decoratedNextItem, Directory);
		NextWordFiltered = new WordFiltered(decoratedNextItem, mesostics);
	}
		
	//primary constructor
	public ChapterFiltered(String row, String chapter, String mesostics, MesosticsLineWriter lm, Word nw,
			Item decoratedNextItem, String directory) throws IOException {
		super(row, chapter, mesostics, lm, nw, decoratedNextItem, directory);

	}

	@Override
	public ArrayList<String[]> NextItem() throws IOException, InterruptedException {
		
		ArrayList<String[]> output = new ArrayList<String[]>();
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		
		
		/* 
		 * A while loop marking the index of the target word
		 * and repeating the loop through the row
		 * until the end of the chapter is nearly reached
		 */
		int count = 0; 
		while (count < ChapterArray.length - 100) {

			while (RowArrayIndex != RowArray.length) {
				
				output.clear();
				output = NextWordFiltered.NextItem();
				
				if(output.get(0)[1] != "don't write it"){
					outputList.add(output.get(0));
					count = new Integer(NextWordFiltered.AdvanceChapterWord(output.get(0)[0])).intValue();
					RowArrayIndex++;
					System.out.println("Advancing to row index: " + RowArrayIndex);
				}
				count = new Integer(NextWordFiltered.AdvanceChapterWord(output.get(0)[0])).intValue();	
			}// end of inner while loop
			
			Write(outputList);
			outputList.clear();
			RowArrayIndex = 0;
			
		}//end of outer while loop
			
		return outputList;
		 
	}

	@Override
	public void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		Writer.WriteMesostic(outputList);
	}

	@Override
	public String AdvanceChapterWord(String index) {
			return super.AdvanceChapterWord(index);
	}

}

