package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class NextChapterQueue {
	
	/* TODO Integrate into GUI, 
	 * then use the ArrayList of Chapters
	 * in NextChapter to progress from one chapter to the next
	 * and one final class that rips through all chapters in one go.
	 */

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
