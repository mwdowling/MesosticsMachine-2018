package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

public final class NextChapterFiltered extends NextItemFiltered {
	
	//secondary constructor throws exception
	public NextChapterFiltered(String row, String chapter, String mesostics, NextItem decoratedNextItem) throws IOException {
		this(new FileToString(RowAddress).output(), new FileToString(ChapterAddress).output(), 
				Mesostics, new LineMesostic(mesostics), new NextWord(row, chapter, mesostics), decoratedNextItem, Directory);
		Nwf = new NextWordFiltered(decoratedNextItem, mesostics);
	}
		
	//primary constructor
	public NextChapterFiltered(String row, String chapter, String mesostics, LineMesostic lm, NextWord nw,
			NextItem decoratedNextItem, String directory) throws IOException {
		super(row, chapter, mesostics, lm, nw, decoratedNextItem, directory);

	}

	@Override
	public ArrayList<String[]> Item() throws IOException, InterruptedException {
		
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
				output = Nwf.Item();
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
	public void AdvanceChapterWord(String index) {
			super.AdvanceChapterWord(index);
	}

}

