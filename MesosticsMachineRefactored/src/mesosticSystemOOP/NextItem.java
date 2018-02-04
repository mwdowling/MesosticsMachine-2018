package mesosticSystemOOP;


/**
 * @author Martin Dowling
 * 
 * This interface contains in the abstract the four basic methods
 * for traversing the mesostic row and the target chapter, 
 * selecting appropriate words from the target chapter 
 * to create appropriate mesostics, and writing the content of same to a file.
 * 
 * Item() contains the appropriate algorithm for selecting a word from the target text.
 * The concrete classes implementing this interface are:
 * 
 * NextWord, which selects the next appropriate word in the chapter
 * NextMesostic, which selects all the words of one mesostic row
 * NextChapter, which selects all the mesostics in one chapter
 * 
 * The "decorator" design pattern is used embellish these classes with
 * syllable repositories and filters. See the interface NextItemFiltered.
 * 
 * 
 */
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
