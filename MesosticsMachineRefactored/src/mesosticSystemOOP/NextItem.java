package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

public interface NextItem {
	
	ArrayList<String[]> Item() throws IOException, InterruptedException;	
	
	void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException;
	
	void AdvanceChapterWord(String index);
	
	void AdvanceMesosticLetter(String index);
	
	/* 
	 * possible additional method:
	 * void AdvanceNewChapter(File[]);
	 * queue up multiple chapters in an Array of files
	 * to process a succession of chapters at runtime
	 */
}
