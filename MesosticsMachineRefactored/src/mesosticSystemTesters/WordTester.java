package mesosticSystemTesters;

import java.io.IOException;
import java.util.ArrayList;

import mesosticSystemOOP.Word;

public class WordTester {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//variables
		String RowAddress = "C:\\\\MesosticsMachine\\Mesostics Row.txt";
		int RowArrayIndex = 0;
		String ChapterAddress = "C:\\MesosticsMachine\\Target Chapter.txt";//update this to suit
		String ChapterArrayIndex = "0";
		String Mesostics = "C:\\MesosticsMachine\\Mesostics.txt";
		
		//Using Word to get one word at a time
		Word word = new Word(RowAddress, ChapterAddress, Mesostics);
		ArrayList<String[]> output = new ArrayList<String[]>();
		output = word.NextItem();
		word.Write(output);
		ChapterArrayIndex = word.AdvanceChapterWord(output.get(0)[0]);
		RowArrayIndex = word.AdvanceMesosticLetter();

	}

}
