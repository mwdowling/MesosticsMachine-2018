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
		this(new FileToString(RowAddress).output(), new FileToString(ChapterAddress).output(), 
				Mesostics, new MesosticsLineWriter(mesostics), new Word(row, chapter, mesostics), decoratedNextItem, Directory);
		Nwf = new WordFiltered(decoratedNextItem, mesostics);
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
		outputList.clear(); 
		
		/* 
		 * A while loop marking the index of the target word
		 * and repeating the loop through the row
		 * until the end of the chapter is nearly reached
		 */
		int counter = 0;
		while (counter < ChapterArray.length - 100) {

			for (int i = 0; i < RowArray.length; i++) {
				
				//write a mesostic to ArrayList
				output.clear();
				output = Nwf.NextItem();
				outputList.addAll(output);
				System.out.println(outputList.size());
				
				//advance to next word in chapter
				AdvanceChapterWord(outputList.get(outputList.size()-1)[0]);			
				
				//advance counter			
				counter = Integer.parseInt(outputList.get(outputList.size()-1)[0]);
				
			} // end of for loop
		}//end of while loop
			
		return outputList;
		 
	}

	@Override
	public void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		super.Write(outputList);
	}

	@Override
	public String AdvanceChapterWord(String index) {
			return super.AdvanceChapterWord(index);
	}

}

