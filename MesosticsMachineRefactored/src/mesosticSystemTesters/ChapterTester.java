package mesosticSystemTesters;

import java.io.IOException;
import java.util.ArrayList;

import mesosticSystemOOP.Chapter;

public class ChapterTester {

	public static void main(String[] args) throws IOException, InterruptedException {

		// variables
		String RowAddress = "C:\\\\MesosticsMachine\\Mesostics Row.txt";
		int RowArrayIndex = 0;
		String ChapterAddress = "C:\\MesosticsMachine\\Target Chapter.txt";// update this to suit
		String ChapterArrayIndex = "0";
		String Mesostics = "C:\\MesosticsMachine\\Mesostics.txt";
		
		//test
		Chapter chapter = new Chapter(RowAddress, ChapterAddress, Mesostics);
		ArrayList<String[]> output = chapter.NextItem();
		chapter.Write(output);
		
		
	}

}
