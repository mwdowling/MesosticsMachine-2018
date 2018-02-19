package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * A concrete implementation of NextItem 
 * which produces all the mesostics in a chapter
 * stopping at a hard-coded number of words before 
 * the last word in the chapter
 * 
 * @see NextWord to which is delegated the task of finding successive words
 * @see MesosticsLineWriter to which is delegated the task of writing lines to a file
 * 
 */
import java.io.IOException;
import java.util.ArrayList;

public final class NextChapter extends NextItemAbstract {
	
	//secondary constructor throws exception
	public NextChapter(String row, String chapter, String mesostics) throws IOException {
		this(new FileToString(MesosticsGUIOOP.RowAddress).output(), new FileToString(ChapterAddress).output(), 
				Mesostics, new MesosticsLineWriter(mesostics), new NextWord(row, chapter, mesostics));
	}

	//primary constructor
	public NextChapter(String row, String chapter, String mesostics, MesosticsLineWriter lm, NextWord nw) throws IOException {
		super(row, chapter, mesostics, lm, nw);
	}

	@Override
	public final ArrayList<String[]> Item() throws IOException, InterruptedException {
		
		ArrayList<String[]> output = new ArrayList<String[]>();
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		outputList.clear(); 
		
		
		/* traverse chapter populating outputList
		 * stopping before the end of the chapter (here 100 words before)
		 */
		int index = 0;
		while (index < ChapterArray.length - 100) {
				
			//write a mesostic to ArrayList
			output.clear();
			output = Nw.Item();
			outputList.addAll(output);
			
			//advance to next word in chapter
			AdvanceChapterWord(outputList.get(outputList.size()-1)[0]);			
			Nw.AdvanceMesosticLetter();
			
			//advance counter			
			index = Integer.parseInt(outputList.get(outputList.size()-1)[0]);
			
		}
	
		return outputList;
	}

	@Override
	public final void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		Lm.WriteChapter(outputList);
	}

	@Override
	public final void AdvanceChapterWord(String index) {
		super.AdvanceChapterWord(index);
	}
}
