package mesosticSystemOOP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Martin Dowling
 * 
 * A class for the creation of objects with methods 
 * that take the outputs from NextTIem methods 
 * (ArrayLists of two-element Arrays of Strings 
 * which contain the output words paired with their indexes)
 * and write them to the Mesostics output file 
 * 
 * Each NextItem has a reference to an object of this class
 * and uses its methods in the NextItem.Write(ArrayList<String[]> outputList) method 
 * 
 * There are three methods corresponding to the three concrete NextItems:
 * 	(1) write one line at a time
 * 	(2) write all the lines of one mesostic
 *  (3) write all the lines of all the mesostics in a chapter,
 *  	adding a top and a tail 
 */

public class MesosticsLineWriter {

	private String File;
	
	public MesosticsLineWriter(String file) {	
		File = file;		
	}
	
	public final void WriteLine(ArrayList<String[]> output) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(File), true));
		bw.newLine();
		bw.write(output.get(0)[0] + "\t" + output.get(0)[1]);
		bw.close();
	}

	public void WriteMesostic(ArrayList<String[]> outputList) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(File), true));
		
		//traverse the Arraylist of index/word pairs and write them to file 
		//bw.newLine();
		for(String[] s : outputList){	
			bw.newLine();
			bw.write(s[0] + "\t" + s[1]);	
			//bw.newLine();
		}
		
		bw.close();	
	}
	
	public void WriteChapter(ArrayList<String[]> outputList) throws IOException {	
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(File), true));
			
		// write a generic buffering top line to the Mesostics file
		String[] topline = {"0", "BEGIN"};
		bw.write(topline[0] + "\t" + topline[1]);
		
		//traverse the Arraylist of index/word pairs and write them to file 
		bw.newLine();
		for(String[] s : outputList){
			for(int i = 0; i < s.length; i ++){
				bw.write(s[i] + "\t");
			}
			bw.newLine();
		}
		
		// write a generic buffering last line to the Mesostics file
		String[] bottomLine = {String.valueOf(ItemAbstract.ChapterArray.length), "END"};
		bw.write(bottomLine[0] + "\t" + bottomLine[1]);
		bw.newLine();
		bw.close();
		
	}
}
