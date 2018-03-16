package mesosticSystemTesters;

import java.io.IOException;
import java.util.ArrayList;

import mesosticSystemOOP.Item;
import mesosticSystemOOP.ItemFiltered;
import mesosticSystemOOP.MesosticFiltered;
import mesosticSystemOOP.Word;
import mesosticSystemOOP.WordFiltered;

public class MesosticFilteredTester {

	public static void main(String[] args) throws IOException, InterruptedException {

		//variables
		String RowAddress = "C:\\\\MesosticsMachine\\Mesostics Row.txt";
		int RowArrayIndex = 0;
		String ChapterAddress = "C:\\MesosticsMachine\\Mesostics.txt";//update
		String ChapterArrayIndex = "0";
		String Mesostics = "C:\\MesosticsMachine\\Mesostics.txt";
		
		//test
		Item nextWord = new Word(RowAddress, ChapterAddress, Mesostics);
		MesosticFiltered nextMesosticFiltered = new MesosticFiltered(RowAddress, ChapterAddress, Mesostics, nextWord);
		ArrayList<String[]> outputList = nextMesosticFiltered.NextItem();
		nextMesosticFiltered.Write(outputList);
		ChapterArrayIndex = nextMesosticFiltered.AdvanceChapterWord(outputList.get(8)[0]);

	}

}
