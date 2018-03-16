package mesosticSystemTesters;

import java.io.IOException;

import mesosticSystemOOP.MesosticsFinished;

public class MesosticsFinishedTester {

	public static void main(String[] args) throws IOException {
		
		// Static variables for the output files
		String Mesostics = "C:\\MesosticsMachine\\Mesostics.txt";
		String MesosticsFinished = "C:\\MesosticsMachine\\MesosticsFinished.txt";
		String Directory = "C:\\MesosticsMachine";
		String ChapterAddress= "C:\\MesosticsMachine\\Target Chapter.txt";//update this to suit
		String SoundWords = "C:\\MesosticsMachine\\Sounds and Places\\Chapter Sounds.txt";
		
		MesosticsFinished mf = new MesosticsFinished(Mesostics, MesosticsFinished, ChapterAddress);
		//Adding adjacent words to mesostic lines
		mf.WithAdjacentWords();
		//Centring lines in mesostics file
		mf.WithCentredLines();
		//Collating sounds into mesostics file
		mf.WithItemsCollated(SoundWords);
		//TODO put this into the GUI
		//Removing index from mesostics file
		mf.WithIndexRemoved();
	}

}
