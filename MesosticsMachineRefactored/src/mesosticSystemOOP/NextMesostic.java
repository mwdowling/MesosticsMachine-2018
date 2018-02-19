package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * A concrete implementation of NextItem 
 * which produces one full mesostics poem in a chapter
 * 
 * @see NextWord to which is delegated the task of finding successive words
 * @see MesosticsLineWriter to which is delegated the task of writing lines to a file
 * 
 */

import java.io.IOException;
import java.util.ArrayList;

public final class NextMesostic extends NextItemAbstract {

	//TODO CREATE A COMPLETE COPY OF THE SYSTEM SPECIFICALLY FOR THE GUI WITH THESE INPUTS??
	//secondary constructor throws exception
	public NextMesostic(String row, String chapter, String mesostics) throws IOException {
		this(new FileToString(MesosticsGUIOOP.RowAddress).output(), new FileToString(ChapterAddress).output(), 
					Mesostics, new MesosticsLineWriter(mesostics), new NextWord(row, chapter, mesostics));
	}

	//primary constructor
	public NextMesostic(String row, String chapter, String mesostics, MesosticsLineWriter lm, NextWord nw) {
		super(row, chapter, mesostics, lm, nw);			
	}

	@Override
	public final ArrayList<String[]> Item() throws IOException, InterruptedException {

		// input and output variables
		String startIndex = ChapterArrayIndex;		
		ArrayList<String[]> output = new ArrayList<String[]>();
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		
		// verify new startIndex on console
		System.out.println("Chapter Index: " + startIndex);
		
		// A for loop to traverse the mesostic row using NextWord
		for (int i = 0; i < RowArray.length; i++) {
			
			RowArrayIndex = i;
			output.clear();
			output = Nw.Item();
			outputList.add(output.get(0));
			AdvanceChapterWord(output.get(0)[0]);
			
		} 
		
		return outputList;	
	}
	
	@Override
	public final void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		Lm.WriteMesostic(outputList);
	}

	@Override
	public final void AdvanceChapterWord(String index) {
		super.AdvanceChapterWord(index);
	}

}
