package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

public class NextWord extends NextItemAbstract {
	
	//secondary constructor throws exception
	public NextWord(String row, String chapter, String mesostics) throws IOException {	
		this(new FileToString(RowAddress).output(), new FileToString(ChapterAddress).output(), RowArrayIndex,
				ChapterArrayIndex, Mesostics, new LineMesostic(mesostics));
	}
	
	//primary constructor
	public NextWord(String row, String chapter, int rowArrayIndex, String chapterArrayIndex, 
					String mesostics, LineMesostic lm) {
		super(row, chapter, rowArrayIndex, chapterArrayIndex, mesostics, lm);	
	}

	@Override
	public final ArrayList<String[]> Item() throws IOException, InterruptedException {
		
		// variables for the mesostic row targets
		String TargetLetter = RowArray[RowArrayIndex];
		String NextLetter;

		// reformatted variables for the words in the chapter
		int startIndexInt = new Integer(ChapterArrayIndex).intValue();									
		String startIndex = String.valueOf(startIndexInt);
		String word = ChapterArray[startIndexInt];// the target word

		// variables for searching and reformatting target word
		String letter = "";
		int letterIndex = 0;
		String wordSegment = word.substring(letterIndex);
		
		
		/*
		 * A loop to find, reformat, split, and recombine the next word
		 * following the starting index word that contains Letter
		 */	
		for (int i = 0 + startIndexInt; i < ChapterArray.length; i++) {

			if (ChapterArray[i].toLowerCase().contains(TargetLetter)) {
				// assign and reformat wordIndex
				startIndexInt = i;
				startIndex = String.valueOf(i);
				
				// assign word, change to lower case, and remove all non-words
				word = ChapterArray[i].toLowerCase().replaceAll("\\W", "").trim();
				// split word at thisMesosticLetter
				for (int j = 0; j < word.length(); j++) {
					letter = String.valueOf(word.charAt(j));
					if (letter.equals(TargetLetter)) {
						letterIndex = word.indexOf(letter);
						wordSegment = word.substring(letterIndex);
						break;
					}
				}

				/*
				 * Following the requirements, format the word variable for
				 * output with only TargetLetter capitalised.
				 * 
				 * Do so only if the substring following the target letter does
				 * not contain the next mesostic letter,
				 * 
				 * else advance the wordIndex and word to next element in the
				 * array
				 * 
				 * Following the requirements, if "this" letter is the last in
				 * the row it is paired with the first letter of the mesostic
				 * 
				 */

				// declare the next letter
				if (RowArrayIndex + 1 < RowArray.length) {
					NextLetter = RowArray[RowArrayIndex + 1];
				} else
					NextLetter = RowArray[0];
				
				// format the word and advance to next mesostic letter
				if (!wordSegment.contains(NextLetter)) {
					letter = letter.toUpperCase();
					word = word.substring(0, letterIndex) + letter + word.substring(letterIndex + 1);
					TargetLetter = NextLetter;
					break;
				} else
					startIndexInt = startIndexInt + 1;
				
				word = ChapterArray[startIndexInt];

			} else
				/*
				 * reset the array to the current word to begin the next loop
				 */
				word = ChapterArray[i];
		} // end of for loop

		// return a two-element string array of the found word and its index
		String[] output = { startIndex, word };
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		outputList.clear();
		outputList.add(output);

		// return the output
		return outputList;
		
	}
	
	@Override
	public void Write(ArrayList<String[]> output) throws IOException {
		Lm.WriteLine(output);	
	}	
	
	@Override
	public final void AdvanceChapterWord(String index) {	
		super.AdvanceChapterWord(index);
	}

	public void AdvanceMesosticLetter() {
		
		//advance the array index or continue from the beginning
		if (RowArrayIndex + 1 < RowArray.length) {
			RowArrayIndex++;
		} else
			RowArrayIndex = 0;
		System.out.println("Advancing to new row index: " + RowArrayIndex);
		
	}
}
