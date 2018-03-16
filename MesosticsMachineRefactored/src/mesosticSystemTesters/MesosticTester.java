package mesosticSystemTesters;

import java.io.IOException;
import java.util.ArrayList;

import mesosticSystemOOP.Mesostic;

public class MesosticTester {

	public static void main(String[] args) throws IOException, InterruptedException {

		
		String RowAddress = "C:\\\\MesosticsMachine\\Mesostics Row.txt";
		String[]  RowArray = new String[13];//update to suit
		int RowArrayIndex = 0;
		String ChapterAddress = "C:\\MesosticsMachine\\Target File.txt";//update to suit
		String ChapterArrayIndex = "0";
		String Mesostics = "C:\\MesosticsMachine\\Mesostics.txt";
			
		Mesostic nextMesostic = new Mesostic(RowAddress, ChapterAddress, Mesostics);
		ArrayList<String[]> output = nextMesostic.NextItem();
		System.out.println("Output length is: " + output.size());
		nextMesostic.Write(output);
		ChapterArrayIndex = nextMesostic
				.AdvanceChapterWord(output.get(new Integer(RowArray.length).intValue() - 1)[0]);

		
	}

}
