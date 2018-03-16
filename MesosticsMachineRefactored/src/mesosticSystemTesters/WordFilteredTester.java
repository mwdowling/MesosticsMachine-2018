package mesosticSystemTesters;

import java.io.IOException;
import java.util.ArrayList;

import mesosticSystemOOP.ItemFiltered;
import mesosticSystemOOP.Word;
import mesosticSystemOOP.WordFiltered;

public class WordFilteredTester {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//variables
		String Directory = "C:\\MesosticsMachine";
		String RowAddress = "C:\\\\MesosticsMachine\\Mesostics Row.txt";
		int RowArrayIndex = 0;
		String ChapterAddress = "C:\\MesosticsMachine\\Mesostics.txt";//update
		String ChapterArrayIndex = "0";
		String Mesostics = "C:\\MesosticsMachine\\Mesostics.txt";

		//test
		Word nextWord = new Word(RowAddress, ChapterAddress, Mesostics);
		ItemFiltered nextWordFiltered = new WordFiltered(nextWord, Directory);
		ArrayList<String[]> output = nextWordFiltered.NextItem();
		nextWordFiltered.Write(output);
		ChapterArrayIndex = nextWordFiltered.AdvanceChapterWord(output.get(0)[0]);
		if (output.get(0)[1] != "don't write it") {
			RowArrayIndex = nextWord.AdvanceMesosticLetter();
		}
	}
}
