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
	public NextMesostic(String row, String chapter, String mesostics, LineMesostic lm, NextWord nw) {
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
	public void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		Lm.WriteMesostic(outputList);
	}

	@Override
	public final void AdvanceChapterWord(String index) {
		super.AdvanceChapterWord(index);
	}

}
