package mesosticSystemOOP;


/**
 * @author Martin Dowling
 * 
 * An interface containing the essential abstract methods
 * for traversing the mesostic row and the target chapter, 
 * selecting appropriate words from the target chapter 
 * to create appropriate mesostics, and writing the content of same to a file.
 * 
 * @see NextWord which selects the next appropriate word in the chapter
 * @see NextMesostic, which selects all the words of one mesostic row
 * @see NextChapter, which selects all the mesostics in one chapter
 * 
 * @see NextItemFiltered, an implementation of the "decorator" design pattern 
 * used embellish these classes with syllable filters and repositories.
 * 
 */

import java.io.IOException;
import java.util.ArrayList;

public interface Item {
	
	ArrayList<String[]> NextItem() throws IOException, InterruptedException;	
	
	void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException;
	
	String AdvanceChapterWord(String index);
	
}
