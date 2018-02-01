package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

public final class NextMesosticFiltered extends NextItemFiltered {

	//secondary constructor throws exception
	public NextMesosticFiltered (String row, String chapter, String mesostics, NextItem decoratedNextItem) throws IOException {
		
		this(new FileToString(RowAddress).output(), new FileToString(ChapterAddress).output(), 
					Mesostics, new LineMesostic(mesostics), new NextWord(row, chapter, mesostics), decoratedNextItem, Directory);
		
		Nwf = new NextWordFiltered(decoratedNextItem, mesostics);
	}
	
	//primary constructor
	public NextMesosticFiltered(String row, String chapter, String mesostics, LineMesostic lm, NextWord nw, NextItem decoratedNextItem,
			String directory) {
		super(row, chapter, mesostics, lm, nw, decoratedNextItem, directory);		
	}

	@Override
	public ArrayList<String[]> Item() throws IOException, InterruptedException {
		
		// input and output variables
		String startIndex = NextItemAbstract.ChapterArrayIndex;
		System.out.println("Start Index: " + startIndex);
		Integer integer = new Integer(0);

		// A for loop to traverse the mesostic row
		
		while (RowArrayIndex < RowArray.length-1) {

			/*
			 * for each mesostic letter, use NextWord to find and output a
			 * target word with its index in ChapterArray
			 */
			Output.clear();
			Output = Nwf.Item();
			Nwf.Write(Output);
			Nwf.AdvanceChapterWord(Output.get(0)[0]);
			Nwf.AdvanceMesosticLetter(Output.get(0)[0]);
			OutputList.add(NextItemAbstract.Output.get(0));

			// advance and reformat the startIndex to test the next word
			//integer = new Integer(NextItemAbstract.Output.get(0)[0]) + 1;
			//startIndex = integer.toString();
			//NextItemAbstract.ChapterArrayIndex = String.valueOf(startIndex);
			
		} // end of for loop
		
		integer = new Integer(NextItemAbstract.Output.get(0)[0] + 1);
		startIndex = integer.toString();
		
		return NextItemAbstract.OutputList;
	
	}
	
	@Override
	public void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {	
		super.Write(outputList);
	}

}
