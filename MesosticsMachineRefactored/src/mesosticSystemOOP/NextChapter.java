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
		
		ArrayList<String[]> output = new ArrayList<String[]>();
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		outputList.clear(); 
		
		//traverse chapter adding words to the output list
		//stop before the end of the chapter (here 100 words before)
		int index = 0;
		while (index < ChapterArray.length - 100) {
				
			//write a mesostic to ArrayList
			output.clear();
			output = Nw.Item();
			outputList.addAll(output);
			System.out.println(outputList.size());
			
			//advance to next word in chapter
			AdvanceChapterWord(outputList.get(outputList.size()-1)[0]);			
			
			//advance counter			
			index = Integer.parseInt(outputList.get(outputList.size()-1)[0]);
			
		}//end of while loop
	
		return outputList;
	}

	@Override
	public void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		Lm.WriteChapter(outputList);
	}

	@Override
	public void AdvanceChapterWord(String index) {
		super.AdvanceChapterWord(index);
	}

}
