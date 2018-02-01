package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

public final class NextChapter extends NextItemAbstract {
	
	//secondary constructor throws exception
	public NextChapter(String row, String chapter, String mesostics) throws IOException {
		this(new FileToString(RowAddress).output(), new FileToString(ChapterAddress).output(), 
				Mesostics, new LineMesostic(mesostics), new NextWord(row, chapter, mesostics));

		}

	//primary constructor
	public NextChapter(String row, String chapter, String mesostics, LineMesostic lm, NextWord nw) throws IOException {
		super(row, chapter, mesostics, lm, nw);
	}

	@Override
	public final ArrayList<String[]> Item() throws IOException, InterruptedException {
		
		NextItemAbstract.OutputList.clear(); 
		
		/* 
		 * A while loop marking the index of the target word
		 * and repeating the loop through the row
		 * until the end of the chapter is nearly reached
		 */
		
		int counter = 0;
		while (counter < ChapterArray.length - 100) {
				
			//write a mesostic to ArrayList
			Output.clear();
			Output = Nw.Item();
			OutputList.addAll(Output);
			System.out.println(OutputList.size());
			
			//advance to next word in chapter
			AdvanceChapterWord(OutputList.get(OutputList.size()-1)[0]);			
			
			//advance counter			
			counter = Integer.parseInt(OutputList.get(OutputList.size()-1)[0]);
			
		}//end of while loop
	
		return OutputList;
	}

	@Override
	public void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		Lm.WriteChapter(outputList);
	}

	@Override
	public void AdvanceChapterWord(String index) {
		super.AdvanceChapterWord(index);
	}

	@Override
	public void AdvanceMesosticLetter(String index) {
		
		/*
		 *  No need for implementation 
		 *  as the Item() method iterates once 
		 *  through the mesostic row array
		 */
		
	}
}
