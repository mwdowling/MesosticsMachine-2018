package mesosticSystemOOP;


/**
 * @author Martin Dowling
 * 
 * TODO How does this class fit into the hierarchy
 * Should be called NextSentence with an Item, Write, and Advance method??
 * 
 * The following are needed 
 * (1) A class that takes an int value 
 * 		which represents a chapter array index reference 
 * 		in one of the output files (mesostics, sounds, or places)
 * 		and returns to the console the sentence in which the referenced word sits 
 * (2) A class that iterates through one of the output files and
 * 		for every index reference writes the appropriate chapter sentence
 * 		to a new file.  
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Sentences {
	
	//input variables for target chapter
	private static String Comparator;
	private static String ChapterAddress;
	private  String[] ChapterArray;
	
	// variables for output files
    private static String Sentences;
    
    //primary constructor
    public Sentences(String comparator, String chapterAddress, String outputFile) throws IOException {
    	Comparator = comparator;
    	ChapterAddress = new FileToString(chapterAddress).output();
		ChapterArray = ChapterAddress.split("\\s+"); 
		Sentences = outputFile;
	} 

	public void WriteSentence() throws IOException{
		
		// read and reformat the first line of the target file
		BufferedReader br = new BufferedReader(new FileReader(new File(Comparator)));
		String line = br.readLine();//the first line
		String[] lineArray = line.split("\t");//the first line as array {index, word}
		Integer lineArrayInt = new Integer(lineArray[0]);//the index as an Integer

		// for loop to get sentences
		for (int i = 0; i < ChapterArray.length; i++) {
		
			// variables to identify the boundaries of a sentence
			String fullStop = ".";
			int sentenceStart = 0;
			int sentenceEnd = 0;
		
			// read each line of the target file 
			
			for (line = br.readLine(); line != null; line = br.readLine()) {
				
				System.out.println(lineArrayInt);
				if(lineArray[1].startsWith("END")){break;}
				
				// loop to find the end of the chapter sentence
				for (int i1 = lineArrayInt; i1 < ChapterArray.length; i1++) {

					if (ChapterArray[i1].contains(fullStop)) {
						sentenceEnd = i1;
						System.out.println("End of sentence = " + ChapterArray[sentenceEnd]);
						break;
					}
				}

				// loop to find the beginning of the chapter sentence
				for (int i1 = lineArrayInt; i1 >= 0; i1--) {

					if (ChapterArray[i1].contains(fullStop)) {
						sentenceStart = i1 + 1;
						break;
					}
				}

				// Combine words in the sentence into a String
				String sentencetoFind = "";
				for (int i1 = sentenceStart; i1 <= sentenceEnd; i1++) {

					sentencetoFind = sentencetoFind + " " + ChapterArray[i1].trim();
				} 
				
				//write the sentence to the target file
				if (sentencetoFind != "") {
					System.out.println(sentencetoFind.trim());
				
					BufferedWriter bw = new BufferedWriter(
						new FileWriter(new File(Sentences), true));

					bw.newLine();
					bw.write(lineArray[0]);
					bw.newLine();
					bw.write(sentencetoFind.trim());
					bw.newLine();
					bw.close();
				}
				
				//Advance to the next line and format its index value 
				line = br.readLine();			
				lineArray = line.split("\t");
				lineArrayInt = new Integer(lineArray[0].trim());
				if (lineArrayInt < sentenceEnd) {
					lineArrayInt = sentenceEnd;
				}
				
			} 
		}br.close();
	}
}
