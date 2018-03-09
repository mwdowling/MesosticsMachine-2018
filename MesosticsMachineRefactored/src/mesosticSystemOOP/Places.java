package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * @TODO Train this on sample chapters.
 */

import java.io.IOException;
import java.util.ArrayList;

public class Places extends ItemAbstract {

	public Places(String comparator, String chapter, String outputFile) throws IOException {
		this(new FileToString(MesosticsMachineGUI.NotPlaces).output(), new FileToString(MesosticsMachineGUI.ChapterAddress).output(),
				new FileToString(MesosticsMachineGUI.Places).output(), new MesosticsLineWriter(outputFile));
	}

	public Places(String comparator, String chapter, String outputFile, MesosticsLineWriter lm) {
		super(comparator, chapter, outputFile, lm);
		NotPlaceArray = comparator.split("\\r\n");
	}

	@Override
	public ArrayList<String[]> NextItem() throws IOException, InterruptedException {
		
		String index = "";
		String wordInChapter = "";
		
		ArrayList<String[]> output = new ArrayList<String[]>();
		ArrayList<String[]> outputList = new ArrayList<String[]>();

		// for loop to get chapter words that begin with uppercase letter
		for (int i = 0; i < ChapterArray.length; i++) {
			
			if (Character.isUpperCase(ChapterArray[i].charAt(0)) && !ChapterArray[i - 1].contains(".")) {

				wordInChapter = ChapterArray[i].toLowerCase().replaceAll("\\W", "").trim();	
				index = String.valueOf(i);
				System.out.println(index + " " + wordInChapter);
				String[] wordIsPlace = { index, wordInChapter };
				output.add(wordIsPlace);	
			}	
			
			outputList.addAll(output);
			output.clear();
		}
		
		//compare and remove nonPlaces from outputList
		ArrayList<String[]> outputListCopy = new ArrayList<String[]>();
		outputListCopy.addAll(outputList);
		for(String[] pair : outputListCopy){
			for (int i = 0; i < NotPlaceArray.length; i ++){
				if (pair[1].equals(NotPlaceArray[i])){
					System.out.println(pair[1] + " not a place removed.");
					outputList.remove(pair);
				}
			}
		}
		
		return outputList;
	}

	@Override
	public void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		Writer.WriteChapter(outputList);

	}
}