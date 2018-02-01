package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

public final class NextMesostic extends NextItemAbstract {

	//secondary constructor throws exception
	public NextMesostic(String row, String chapter, String mesostics) throws IOException {
		this(new FileToString(RowAddress).output(), new FileToString(ChapterAddress).output(), 
					Mesostics, new LineMesostic(mesostics), new NextWord(row, chapter, mesostics));
	}

	//primary constructor
	public NextMesostic(String row, String chapter, String mesostics, LineMesostic lm,NextWord nw) {
		super(row, chapter, mesostics, lm, nw);			
	}

	@Override
	public final ArrayList<String[]> Item() throws IOException, InterruptedException {

		// input and output variables
		String startIndex = ChapterArrayIndex;
		System.out.println("Chapter Index: " + startIndex);
		Integer integer = new Integer(0);

		// A for loop to traverse the mesostic row
		for (int i = 0; i < RowArray.length; i++) {
			
			RowArrayIndex = i;

			/*
			 * for this mesostic letter = i, 
			 * use NextWord to find target word with its index in ChapterArray
			 */
			Output.clear();
			Output = Nw.Item();
			OutputList.add(Output.get(0));
			
			// advance and reformat the startIndex to test the next word
			//integer = new Integer(Output.get(0)[0]) + 1;
			//startIndex = integer.toString();
			//ChapterArrayIndex = String.valueOf(startIndex);
			AdvanceChapterWord(Output.get(0)[0]);
		} // end of for loop
		
		integer = new Integer(Output.get(0)[0]) + 1;
		startIndex = integer.toString();
		
		return OutputList;
		
	}
	
	@Override
	public void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		Lm.WriteMesostic(outputList);
	}

	@Override
	public final void AdvanceChapterWord(String index) {
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
