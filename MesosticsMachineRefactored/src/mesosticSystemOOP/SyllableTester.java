package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

public class SyllableTester {

	public static void main(String[] args) throws IOException, InterruptedException {

		// Using secondary constructor, but also declaring what is needed for
		// primary constructor
		NextItemAbstract.RowAddress = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\Mesostics Row.txt";
		NextItemAbstract.RowArrayIndex = 0;
		NextItemAbstract.ChapterAddress = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\01 RAGLAN STREET.txt";
		NextItemAbstract.ChapterArrayIndex = "0";

		// Static variables for the output files
		NextItemAbstract.Mesostics = "C:\\Users\\Martin\\Documents\\MesosticsMachine\\MesosticsChapter.txt";

		NextItemAbstract.Directory = "C:\\Users\\Martin\\Documents\\MesosticsMachine";
		NextWord nw = new NextWord(NextWord.RowAddress, NextWord.ChapterAddress, NextItemAbstract.Mesostics);
		
		ArrayList<String[]> output = new ArrayList<String[]>();
		for (int i = 0; i < 4; i++) {

			output  = nw.Item();
			String SavedSyllable = new Syllable().SyllableSaved(output.get(0)[1]);
			SyllableRecord sr = new SyllableRecord();
			String Repository = sr.Repository();
			Boolean RepoHasSyllable = sr.RepositoryHasSyllable(Repository, SavedSyllable);
			
			if (RepoHasSyllable) {
				nw.AdvanceChapterWord(output.get(0)[0]);
				NextWord.RowArrayIndex--;

			} else

			{
				nw.Write(output);
				sr.WriteSyllableToRepository(Repository, SavedSyllable);
				nw.AdvanceChapterWord(nw.Item().get(0)[0]);
			}
		}
	}

}
