package mesosticSystemOOP;


/**
 * @author Martin Dowling
 * 
 * A class which creates an object 
 * to queue up an ArrayList of Chapters
 * allowing the NextChapter object 
 * to traverse multiple chapters
 * or indeed all the chapters in the entire target text in one go.
 * 
 * @TODO reset the ChapterArrayIndex of the next chapter
 * equal to 1 + the ChapterArrayIndex of the last word of the previous chapter
 *  
 */
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class ChapterQueue {

	JFileChooser JFC = new JFileChooser();
	ArrayList<String> Addresses = new ArrayList<String>();
	ArrayList<String> Chapters = new ArrayList<String>();
	
	
	public void AddAddress(){
		
		if (JFC.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {	
			Addresses.add(JFC.getSelectedFile().getAbsolutePath());
		}
	}
	
	public void AddNext(String address) throws IOException{
		Chapters.add(new FileToString(address).output());
	}
	
	public ArrayList<String> ChapterList (ArrayList<String> addresses) throws IOException{
		
		for(int i = 0; i < addresses.size(); i ++){	
			AddNext(addresses.get(i));
		}

		return Chapters;
		
	}
}
