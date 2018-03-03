package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * The core concrete implementation of NextItem 
 * which finds and returns the appopriate word for the next letter
 * of the mesostic row, and provides methods to then advance 
 * to the next word in the chapter
 * and the next letter of the mesostic row
 * 
 */


import java.io.IOException;
import java.util.ArrayList;

public final class NextWord extends NextItemAbstract {
	
	//secondary constructor throws exception
	public NextWord(String row, String chapter, String mesostics) throws IOException {	
		this(new FileToString(MesosticsGUIOOP.RowAddress).output(), new FileToString(MesosticsGUIOOP.ChapterAddress).output(), MesosticsGUIOOP.RowArrayIndex,
				MesosticsGUIOOP.ChapterArrayIndex, MesosticsGUIOOP.Mesostics, new MesosticsLineWriter(mesostics));
	}
	
	//primary constructor
	public NextWord(String row, String chapter, int rowArrayIndex, String chapterArrayIndex, 
					String mesostics, MesosticsLineWriter lm) {
		super(row, chapter, rowArrayIndex, chapterArrayIndex, mesostics, lm);	
	}

	@Override
	public final ArrayList<String[]> Item() throws IOException, InterruptedException {
		
		// variables for the mesostic row targets
		String targetLetter = RowArray[RowArrayIndex];
		String nextLetter;

		// reformatted variables for the words in the chapter
		int startIndexInt = new Integer(ChapterArrayIndex).intValue();									
		String startIndex = String.valueOf(startIndexInt);
		String targetWord = ChapterArray[startIndexInt];

		// variables for searching and reformatting target word
		String letter = "";
		int letterIndex = 0;
		String wordSegment = targetWord.substring(letterIndex);
		
		
		/* Traverse chapter, 
		 * find, reformat, split, and recombine the target word, 
		 * or advance to next target word
		 */ 	
		for (int i = 0 + startIndexInt; i < ChapterArray.length; i++) {

			if (ChapterArray[i].toLowerCase().contains(targetLetter)) {
				
				// assign and reformat wordIndex
				startIndexInt = i;
				startIndex = String.valueOf(i);
				
				// assign word, change to lower case, and remove all non-words
				targetWord = ChapterArray[i].toLowerCase().replaceAll("\\W", "").trim();
				
				// split word at thisMesosticLetter
				for (int j = 0; j < targetWord.length(); j++) {
					letter = String.valueOf(targetWord.charAt(j));
					if (letter.equals(targetLetter)) {
						letterIndex = targetWord.indexOf(letter);
						wordSegment = targetWord.substring(letterIndex);
						break;
					}
				}
				
				/* declare the the target letter:  
				 * IF target letter is the last in the mesostic row,  
				 * pair it with the first letter of the mesostic row 
				 */
				if (RowArrayIndex + 1 < RowArray.length) {
					nextLetter = RowArray[RowArrayIndex + 1];
				} else
					nextLetter = RowArray[0];
				 
				/*
				 * If the substring following the target letter 
				 * does not contain the next mesostic letter, 
				 * capitalize TargetLetter 
				 */
				if (!wordSegment.contains(nextLetter)) {
					letter = letter.toUpperCase();
					targetWord = targetWord.substring(0, letterIndex) + letter + targetWord.substring(letterIndex + 1);
					targetLetter = nextLetter;
					break;
				 // advance to next target word
				} else
					startIndexInt = startIndexInt + 1;
					targetWord = ChapterArray[startIndexInt];

			} else
				//reset the array to the current word to begin the next loop
				targetWord = ChapterArray[i];
			
		} // end of for loop

		// return a two-element string array of the found word and its index
		String[] output = { startIndex, targetWord };
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		outputList.clear();
		outputList.add(output);

		// return the output
		return outputList;
		
	}
	
	@Override
	public final void Write(ArrayList<String[]> output) throws IOException {
		Lm.WriteLine(output);	
	}	
	
	@Override
	public final String AdvanceChapterWord(String index) {	
		return super.AdvanceChapterWord(index);
	}

	public final int AdvanceMesosticLetter() {
		
		//advance the array index or continue from the beginning
		if (RowArrayIndex + 1 < RowArray.length) {
			RowArrayIndex++;
		} else
			RowArrayIndex = 0;
		System.out.println("Advancing to new row index: " + RowArrayIndex);
		return RowArrayIndex;		
	}
}
