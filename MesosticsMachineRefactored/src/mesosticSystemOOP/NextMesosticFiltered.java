package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * A concrete decorator that creates one full mesostic poem
 * filtering to select words whose appropriate syllable 
 * is not yet in its corresponding syllable repository
 * while maintaining the syllable repositories 
 * for each letter of the mesostic row.
 *   
 */
import java.io.IOException;
import java.util.ArrayList;

public final class NextMesosticFiltered extends NextItemFiltered {

	//secondary constructor accesses files on disk 
	public NextMesosticFiltered (String row, String chapter, String mesostics, NextItem decoratedNextItem) throws IOException {
		
		this(new FileToString(MesosticsGUIOOP.RowAddress).output(), new FileToString(MesosticsGUIOOP.ChapterAddress).output(), 
				MesosticsGUIOOP.Mesostics, new MesosticsLineWriter(mesostics), new NextWord(row, chapter, mesostics), decoratedNextItem, Directory);
		//The NextMesostic object creates and uses a NextWord object
		Nwf = new NextWordFiltered(decoratedNextItem, mesostics);
	}
	
	//primary constructor
	public NextMesosticFiltered(String row, String chapter, String mesostics, MesosticsLineWriter lm, NextWord nw, NextItem decoratedNextItem,
			String directory) {
		super(row, chapter, mesostics, lm, nw, decoratedNextItem, directory);		
	}

	
	@Override
	public final ArrayList<String[]> Item() throws IOException, InterruptedException {
		
		// input and output variables
		String startIndex = ChapterArrayIndex;
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		
		// verify new startIndex on console
		System.out.println("Chapter Index: " + startIndex);
		
		/* 
		 * For each mesostic letter, 
		 * use NextWord to find and output 
		 * a target word with its index in ChapterArray 
		 */
		for (int i = 0; i < RowArray.length; i++){
			
			RowArrayIndex = i;
			outputList = Nwf.Item();
			Nwf.Write(outputList); 
			Nwf.AdvanceChapterWord(outputList.get(0)[0]);
			
		}
		
		return outputList;	
	}
	
	@Override
	public final void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {	
		Lm.WriteMesostic(outputList);
	}
}
