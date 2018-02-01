package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

public class LineSyllable {
	
	private String Directory;
	private Syllable S; 
	private SyllableRecord Sr;
	
	
	public LineSyllable (String directory, Syllable s, SyllableRecord sr) {
		Directory = directory;
		S = s;//in this application s = new Syllable(output)
		Sr = sr;
	}
	
	void WriteSyllable(ArrayList<String[]> output) throws IOException, InterruptedException{
		
		String savedSyllable = S.SyllableSaved(output.get(0)[1]);
		String Repository = Sr.Repository();
		Sr.WriteSyllableToRepository(Repository, savedSyllable);
	}

}
