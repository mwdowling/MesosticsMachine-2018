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

public final class Mesostic extends ItemAbstract {

	//secondary constructor throws exception
	public Mesostic(String row, String chapter, String mesostics) throws IOException {
		this(new FileToString(MesosticsMachineGUI.RowAddress).output(), new FileToString(MesosticsMachineGUI.ChapterAddress).output(), 
				MesosticsMachineGUI.Mesostics, new MesosticsLineWriter(mesostics), new Word(row, chapter, mesostics));
	}

	//primary constructor
	public Mesostic(String row, String chapter, String mesostics, MesosticsLineWriter lm, Word nw) {
		super(row, chapter, mesostics, lm, nw);			
	}

	@Override
	public final ArrayList<String[]> NextItem() throws IOException, InterruptedException {

		// input and output variables
		String startIndex = ChapterArrayIndex;		
		ArrayList<String[]> output = new ArrayList<String[]>();
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		
		// verify new startIndex on console
		System.out.println("Chapter Index: " + startIndex);
		System.out.println("Row length: " + RowArray.length );
		// A for loop to traverse the mesostic row using NextWord
		for (int i = 0; i < RowArray.length; i++) {
			
			RowArrayIndex = i;
			output.clear();
			/*
			 * TODO This method adds a blank "next line" to the bottom of each mesostic, 
			 * 		which is nice but needs to be removed before using the finishing objects
			 * 		(finishers can't read a blank line)
			 */
			output = Nw.NextItem();
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
	public final String AdvanceChapterWord(String index) {
		return super.AdvanceChapterWord(index);
	}

}
