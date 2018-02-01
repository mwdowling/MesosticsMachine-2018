package mesosticSystemOOP;

import java.io.IOException;

public class MesosticSystemTesterTemplate {

	public static void main(String[] args) throws IOException {

		//static variables for inputs
		NextItemAbstract.RowAddress = "C:\\Users\\...location and name of file with mesostic row";
		NextItemAbstract.RowArrayIndex = 0;
		NextItemAbstract.ChapterAddress = "C:\\Users\\... location and name of target chapter txt file in UTF8 format";
		NextItemAbstract.ChapterArrayIndex = "0";

		// Static variables for the output files
		NextItemAbstract.Mesostics = "C:\\Users\\... location and name of empty file for writing mesostic words";
		NextItemAbstract.Directory = "C:\\Users\\... location of the directory holding all the above and more";
		
		// Unfiltered NextItem objects
		NextWord nw = new NextWord(NextItemAbstract.RowAddress, NextItemAbstract.ChapterAddress, NextItemAbstract.Mesostics);
		NextMesostic nm = new NextMesostic(NextItemAbstract.RowAddress, NextItemAbstract.ChapterAddress, NextItemAbstract.Mesostics);
		NextChapter nc = new NextChapter(NextItemAbstract.RowAddress, NextItemAbstract.ChapterAddress, NextItemAbstract.Mesostics); 
		//Syllable filtered NextITem objects
		NextWordFiltered nwf = new NextWordFiltered(nw, NextItemAbstract.Mesostics);
		NextMesosticFiltered nmf = new NextMesosticFiltered(NextWord.RowAddress, NextWord.ChapterAddress, 
				NextItemAbstract.Mesostics, nw);
		NextChapterFiltered ncf = new NextChapterFiltered(NextWord.RowAddress, NextWord.ChapterAddress, 
				NextItemAbstract.Mesostics, nm);
		
		//A selection of runs:
/*
		//Using NextWord to get one word at a time (three here)
		for (int i = 0; i < 3; i++) { 
			 NextItemAbstract.Output = nw.Item();
			 nw.Write(NextItemAbstract.Output);
			 nw.AdvanceChapterWord(NextItemAbstract.Output.get(0)[0]);
			 nw.AdvanceMesosticLetter(NextItemAbstract.Output.get(0)[0]);
		 }
*/
		
/*	
		//Using NextMesostic to get one mesostic at a time (two here)
		for (int i = 0; i < 3; i++) { 
			NextItemAbstract.OutputList = nm.Item();
			//write first mesostic to file
			//advance to next word in chapter
			nm.AdvanceChapterWord(NextItemAbstract.OutputList.get(NextItemAbstract.OutputList.size()-1)[0]);			
		}
		nm.Write(NextItemAbstract.OutputList);
*/
		
/*
		//Using NextChapter to get a whole chapter
		NextItemAbstract.OutputList = nc.Item();
		nc.Write(NextItemAbstract.OutputList);
*/	
		
/*
		//Using NextWordFiltered to get words (searching the first 5 words in the chapter here)
		for (int i = 0; i < 5; i++) { 
			NextItemAbstract.Output = nwf.Item();
			//write first mesostic to file
			nwf.Write(NextItemAbstract.Output); 
			//advance to next word in chapter
			nwf.AdvanceChapterWord(NextItemAbstract.Output.get(0)[0]);
			//advance to next letter of mesostic row
			nwf.AdvanceMesosticLetter(NextItemAbstract.Output.get(0)[0]);
		}
*/

/*
		//Tackling the problem one mesostic at a time with syllable filter 
		NextItemAbstract.OutputList = nmf.Item();
		//write first mesostic to file
		//nmf.Write(NextItemAbstract.OutputList);
		//advance to next word in chapter
		nmf.AdvanceChapterWord(NextItemAbstract.OutputList.get(NextItemAbstract.OutputList.size()-1)[0]);	
	}
*/
	}

}
