package mesosticSystemOOP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Martin
 * 
 * 
 * To write the top line:
 * output[0] = "0"
 * output[1] = "BEGIN"
 * 
 * To write the bottom line:
 * output[0] = ChapterArray.length
 * output[1] = "END"
 * 
 */
public class LineMesostic implements Line {

	private String File;
	
	//constructor for unfiltered NextTem objects
	public LineMesostic(String file) {	
		File = file;		
	}
	

	@Override
	public final void WriteLine(ArrayList<String[]> output) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(File), true));
		bw.newLine();
		bw.write(output.get(0)[0] + "\t" + output.get(0)[1]);
		bw.close();
	}

	@Override
	public void WriteMesostic(ArrayList<String[]> outputList) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(File), true));
		
		//traverse the Arraylist of String[2]'s of index/word pairs and write them to file 
		bw.newLine();
		for(String[] s : outputList){
			bw.write(s[0] + "\t" + s[1]);
			bw.newLine();
		}
		
		bw.close();	
	}
	
	@Override
	public void WriteChapter(ArrayList<String[]> outputList) throws IOException {	
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(File), true));
			
		// write a generic buffering top line to the Mesostics file
		String[] topline = {"0", "BEGIN"};
		bw.write(topline[0] + "\t" + topline[1]);
		
		//traverse the Arraylist of String[2]'s of index/word pairs and write them to file 
		bw.newLine();
		for(String[] s : outputList){
			bw.write(s[0] + "\t" + s[1]);
			bw.newLine();
		}
		
		// write a generic buffering last line to the Mesostics file
		String[] bottomLine = {String.valueOf(NextItemAbstract.ChapterArray.length), "END"};
		bw.write(bottomLine[0] + "\t" + bottomLine[1]);
		
		bw.close();
		
	}
}
