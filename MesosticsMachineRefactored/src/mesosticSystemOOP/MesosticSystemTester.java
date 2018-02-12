package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

public class MesosticSystemTester {

	public static void main(String[] args) throws IOException, InterruptedException {

		/* 
		 * Using secondary constructor
		 * also declaring what is needed for primary constructor
		 * as the latter called implicitly
		 */
		
		//static variables for inputs
		NextItemAbstract.RowAddress = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\Mesostics Row.txt";
		NextItemAbstract.RowArrayIndex = 0;
		NextItemAbstract.ChapterAddress = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\01 RAGLAN STREET.txt";
		NextItemAbstract.ChapterArrayIndex = "0";

		// Static variables for the output files
		NextItemAbstract.Mesostics = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\Mesostics.txt";
		NextItemAbstract.Directory = "C:\\Users\\Martin\\Documents\\MesosticsMachine";
		
		//local variable for output
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		
		// NextItem objects
		NextWord nw = new NextWord(NextItemAbstract.RowAddress, NextItemAbstract.ChapterAddress, NextItemAbstract.Mesostics);
		NextMesostic nm = new NextMesostic(NextItemAbstract.RowAddress, NextItemAbstract.ChapterAddress, NextItemAbstract.Mesostics);
		NextChapter nc = new NextChapter(NextItemAbstract.RowAddress, NextItemAbstract.ChapterAddress, NextItemAbstract.Mesostics); 
		
		NextWordFiltered nwf = new NextWordFiltered(nw, NextItemAbstract.Mesostics);
		NextMesosticFiltered nmf = new NextMesosticFiltered(NextWord.RowAddress, NextWord.ChapterAddress, 
				NextItemAbstract.Mesostics, nw);
		NextChapterFiltered ncf = new NextChapterFiltered(NextWord.RowAddress, NextWord.ChapterAddress, 
				NextItemAbstract.Mesostics, nm);

/*
		//Using NextWord to get one word at a time (three here)
		for (int i = 0; i < 3; i++) { 	
			 outputList = nw.Item();
			 nw.Write(outputList);
			 nw.AdvanceChapterWord(outputList.get(0)[0]);
			 nw.AdvanceMesosticLetter(outputList.get(0)[0]);
		 }

*/

/*
		//Using NextMesostic to get one mesostic at a time (two here)			
		for (int i = 0; i < 3; i++) { 
			outputList = nm.Item();
			nm.Write(outputList);
			nm.AdvanceChapterWord(outputList.get(outputList.size()-1)[0]);		
		}
*/		

/*
		//Using NextChapter to get a whole chapter
		outputList = nc.Item();
		nc.Write(outputList);
*/
		
/*
		//Using NextWordFiltered to get words (searching the first 5 words in the chapter here)
		for (int i = 0; i < 5; i++) { 
			
			outputList = nwf.Item();
			nwf.Write(outputList); 
			nwf.AdvanceChapterWord(outputList.get(0)[0]);
			nwf.AdvanceMesosticLetter(outputList.get(0)[0]);
		
		}
*/
		//TODO This test failing
		//Tackling the problem one mesostic at a time with syllable filter 
		outputList = nmf.Item();
		nmf.Write(outputList);
		nmf.AdvanceChapterWord(outputList.get(outputList.size()-1)[0]);
	
	}	
}
