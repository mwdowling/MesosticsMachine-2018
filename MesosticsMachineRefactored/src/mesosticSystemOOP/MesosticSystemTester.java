package mesosticSystemOOP;

/**
 * @author Martin Dowling
 *  
 *  Tests of all objects using 
 *  a sample chapter from The Star Factory by Ciaran Carson
 *  sounds and place comparator files
 *  
 *  These tests have been superseded and the constructors do not compile
 *  
 *  TODO: In order to run object tests, remove the MesosticsGUIOOP prefix
 * 		  from the arguments to the secondary constructors in each object
 * 		  (The objects are now configured to take arguments from the GUI)
 * 
 */
import java.io.IOException;
import java.util.ArrayList;

public class MesosticSystemTester {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//static variables for inputs
		NextItemAbstract.RowAddress = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\Mesostics Row.txt";
		NextItemAbstract.RowArrayIndex = 0;
		NextItemAbstract.ChapterAddress = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\01 RAGLAN STREET.txt";
		NextItemAbstract.ChapterArrayIndex = "0";

		// Static variables for the output files
		NextItemAbstract.Mesostics = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\Mesostics.txt";
		NextItemAbstract.MesosticsFinished = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\MesosticsFinished.txt";
		NextItemFiltered.Directory = "C:\\Users\\Martin\\Documents\\MesosticsMachine";
		
		//variables for sounds
		NextItemAbstract.OEDSounds = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\Sounds and Places\\OEDSounds Final.txt";
		NextItemAbstract.SoundWords = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\Sounds and Places\\Chapter Sounds.txt";
		NextItemAbstract.SoundSentences = NextItemFiltered.Directory + "\\Sounds and Places\\ChapterSoundsSentences.txt";
		
		//variables for places
		NextItemAbstract.NotPlace = NextItemFiltered.Directory + "\\Sounds and Places\\Not A Place.txt";
		NextItemAbstract.PlaceWords = NextItemFiltered.Directory + "\\Sounds and Places\\ChapterPlaces.txt";
		NextItemAbstract.PlaceSentences = NextItemFiltered.Directory + "\\Sounds and Places\\ChapterPlacesSentences.txt";
				
				
		//local variable for output
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		
		// NextItem objects
		NextWord nw = new NextWord(NextItemAbstract.RowAddress, NextItemAbstract.ChapterAddress, NextItemAbstract.Mesostics);
/*		NextMesostic nm = new NextMesostic(NextItemAbstract.RowAddress, NextItemAbstract.ChapterAddress, NextItemAbstract.Mesostics);
		NextChapter nc = new NextChapter(NextItemAbstract.RowAddress, NextItemAbstract.ChapterAddress, NextItemAbstract.Mesostics); 		
		NextWordFiltered nwf = new NextWordFiltered(nw, NextItemAbstract.Mesostics);
		NextMesosticFiltered nmf = new NextMesosticFiltered(NextItemAbstract.RowAddress, NextItemAbstract.ChapterAddress, 
				NextItemAbstract.Mesostics, nw);
		NextChapterFiltered ncf = new NextChapterFiltered(MesosticsGUIOOP.RowAddress, MesosticsGUIOOP.ChapterAddress, 
				MesosticsGUIOOP.Mesostics, nm);
		Sounds s = new Sounds(NextItemAbstract.OEDSounds, NextItemAbstract.ChapterAddress, NextItemAbstract.SoundWords);
		Places p = new Places(NextItemAbstract.NotPlace, NextItemAbstract.ChapterAddress, NextItemAbstract.PlaceWords);
		Sentences soundSentences = new Sentences(NextItemAbstract.SoundWords, NextItemAbstract.ChapterAddress, NextItemAbstract.SoundSentences); 
		Sentences placeSentences = new Sentences(NextItemAbstract.PlaceWords, NextItemAbstract.ChapterAddress, NextItemAbstract.PlaceSentences);
		MesosticsFinished mf = new MesosticsFinished(NextItemAbstract.Mesostics, NextItemAbstract.MesosticsFinished, NextItemAbstract.ChapterAddress);
*/


		//Using NextWord to get one word at a time (three here)
		for (int i = 0; i < 3; i++) { 	
			 outputList = nw.Item();
			 nw.Write(outputList);
			 nw.AdvanceChapterWord(outputList.get(0)[0]);
			 nw.AdvanceMesosticLetter();
		 }


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
		//Using NextChapterQueue to get multiple chapters (here, the first one twice)
		ArrayList<String> ChapterQueue = new ArrayList<String>();
		ChapterQueue.add(NextItemAbstract.ChapterAddress);
		ChapterQueue.add(NextItemAbstract.ChapterAddress);
		
		for(String chapter : ChapterQueue){
			//reset indexes for each chapter
			NextItemAbstract.RowArrayIndex = 0;
			NextItemAbstract.ChapterArrayIndex = "0";
			//initialize and run separate NextChapter object for each chapter
			NextChapter ncq = new NextChapter(NextItemAbstract.RowAddress, chapter, NextItemAbstract.Mesostics);
			outputList.clear();
			outputList = ncq.Item();
			nc.Write(outputList);
		}
*/		
		
/*
		//Using NextWordFiltered to get words (searching the first mesostic of words in the chapter here)
		//This works with a ChromeDriver in Syllable, but not a FireFoxFriver, 13 Feb 2018
		for(int i = 0; i<14; i++){	
			outputList = nwf.Item();
			nwf.Write(outputList); 
		}
*/


		/* Tackling the problem one mesostic at a time with syllable filter
		 * This works but not a properly structured object because
		 * the returned ArrayList is never used
		 * Webdriver too fragile to accumulate data in an Arraylist of arrays
		 * as happens with NextMesostic object above
		 * 
		 * Better to use NextWordFiltered inside a for loop in the GUI,
		 * and same for nextChapter,
		 * but the process is so slow 
		 * there is no point in loading more than one mesostic at a time
		 */
		
		//nmf.Item();   

/*
		//Gathering sounds from chapter
		outputList = s.Item();
		s.Write(outputList);
*/		

/*
		//Gathering places from chapter
		outputList = p.Item();
		p.Write(outputList);
*/
	
/*		
		//creating sentences of sound and place words
		//placeSentences.WriteSentence();
		//soundSentences.WriteSentence();
		
*/

/*
 		//print a target sentence to console (edge case is first/last sentences in chapter
		Sentence sentence = new Sentence(1390, NextItemAbstract.Mesostics, NextItemAbstract.ChapterAddress);
		ArrayList<String[]> outputList2 = new ArrayList<String[]>();
		outputList2 = sentence.Item();
		sentence.Write(outputList2);
*/
		
/*
		//Adding adjacent words to mesostic lines
		mf.WithAdjacentWords();
*/
		
/*
		//Centring lines in mesostics file
		mf.WithCentredLines();
*/

/*
		//Collating sounds into mesostics file
		mf.WithItemsCollated(NextItemAbstract.SoundWords);
*/		
		//(Also can test collation of any two other indexed output files)	
	}	
}
