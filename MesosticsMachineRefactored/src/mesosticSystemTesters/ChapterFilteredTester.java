package mesosticSystemTesters;

import java.io.IOException;

import mesosticSystemOOP.ChapterFiltered;
import mesosticSystemOOP.Item;
import mesosticSystemOOP.Word;

public class ChapterFilteredTester {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//variables
		String RowAddress = "C:\\\\MesosticsMachine\\Mesostics Row.txt";
		int RowArrayIndex = 0;
		String ChapterAddress = "C:\\MesosticsMachine\\Target Chapter.txt";//update this to suit
		String ChapterArrayIndex = "0";
		String Mesostics = "C:\\MesosticsMachine\\Mesostics.txt";
		
		// test
		Item nextWord = new Word(RowAddress, ChapterAddress, Mesostics);
		ChapterFiltered nextChapterFiltered = new ChapterFiltered(RowAddress, ChapterAddress, Mesostics, nextWord);
		nextChapterFiltered.NextItem();	
	}

}
