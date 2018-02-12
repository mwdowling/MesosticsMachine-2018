package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This class contains three methods for properly 
 * formatting the files produced by the NextItem objects in the system
 * according to the requirements specification by:
 * 
 * (1) adding adjacent words from the text to each side
 * 	of the words in the mesostics 
 * 	to allow the user to create poems "according to taste"
 * 
 * (2) centring each line on the capitalised letter of the mesostic row
 * 
 * (3) collating into the mesostics text the sound and place words
 * 	appropriately according to their index value in the target chapter
 * 
 * TODO this is a first pass at refactoring 12 February 2018 and requires testing
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MesosticsFinished {

	private String Mesostics;
	private String[] ChapterArray;

	public MesosticsFinished(String mesostics, String[] chapterArray) {
		Mesostics = mesostics;
		ChapterArray = chapterArray;
	}

	public final void WithAdjacentWords() throws IOException {

		// file reader with identifiers for lines in mesostics file
		BufferedReader br = new BufferedReader(new FileReader(new File(Mesostics)));
		String previousLine = br.readLine();
		String line = br.readLine();
		br.mark(1000);
		String nextLine = br.readLine();
		String lastLine = br.readLine();
		br.reset();

		/*
		 * a variable to find the index of the last word used in a line so as
		 * not to repeat words in the next line (scope is crucial here: this
		 * must stay outside the while loop)
		 */
		Integer lastWord = new Integer(-1);

		// while the buffer line has not been reached
		while (nextLine != null) {

			// two-element arrays for the three mesostic words and indexes
			String[] previousLineContent = previousLine.split("\\t");
			String[] lineContent = line.split("\\t");
			String[] nextLineContent = nextLine.split("\\t");

			// the indexes as Integers
			Integer indexPrevious = new Integer(previousLineContent[0]);
			Integer index = new Integer(lineContent[0]);
			Integer indexNext = new Integer(nextLineContent[0]);

			// variables for adding adjacent words before the mesostic word
			String wordsToAddBefore = "";
			int length = 0;

			/*
			 * TODO: This fails to give words before "Street" (i.e. the first
			 * extracted word) DO-WHILE SOLVES THIS BUT CREATES NEW INDEX
			 * PROBLEMS
			 */
			while (length <= 43 && (index - indexPrevious) > 1 && index > lastWord + 1) {

				wordsToAddBefore = ChapterArray[index - 1].toLowerCase().replaceAll("\\W", "").trim() + " "
						+ wordsToAddBefore;
				index--;
				length = wordsToAddBefore.length();

			}

			/*
			 * Add words after the mesostic word, ensuring that the process
			 * stops when (1) 43 characters have been added (2) the mesostic
			 * word from the next line has been reached
			 */
			String wordsToAddAfter = "";
			length = 0;
			index = new Integer(lineContent[0]);
			while (length <= 43 && (indexNext - index) > 1) {

				wordsToAddAfter = wordsToAddAfter + " "
						+ ChapterArray[index + 1].toLowerCase().replaceAll("\\W", "").trim();
				index++;
				length = wordsToAddAfter.length();

			}

			// save the index of the last word in this line
			lastWord = index;

			// combine BEFORE and AFTER to create full mesostic line with
			// original index
			String mesosticLine = lineContent[0] + "\t" + wordsToAddBefore + lineContent[1] + wordsToAddAfter;

			// write line to new line in file
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Mesostics), true));
			bw.write(mesosticLine);
			bw.newLine();
			bw.close();

			// prepare while loop for next mesostic line
			previousLine = line;
			line = br.readLine();
			br.mark(1000);
			nextLine = br.readLine();
			lastLine = br.readLine();
			br.reset();
		}
		br.close();// close reader

	}

	public final void WithCentredLines() throws IOException {

		final int spacesToAddMax = 35;
		int spaceAdjuster = 0;

		// read and reformat the first line of the input file
		BufferedReader br = new BufferedReader(new FileReader(new File(Mesostics)));
		String line = br.readLine();// initially the first line
		String[] lineContent = line.split("\\t");

		while (line != null) {
			/*
			 * split the line into a two-element array: element [0] is the index
			 * value element [1] is the sentence
			 */
			lineContent = line.split("\\t");

			/*
			 * loop through the sentence and assign the spaceAdjuster variable
			 * to the value of the position of the mesostic letter
			 * 
			 */
			for (int i = 0; i < lineContent[1].length(); i++) {

				if (Character.isUpperCase(lineContent[1].charAt(i))) {

					spaceAdjuster = i;
				}
			}

			/*
			 * center the text by adding to the line the appropriate net number
			 * of spaces = (spacesToAddMax - spaceAdjuster)
			 * 
			 */
			for (int i = 0; i < spacesToAddMax - spaceAdjuster; i++) {

				lineContent[1] = " " + lineContent[1];
			}

			// write the newly spaced line to the output file
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Mesostics), true));
			bw.newLine();
			bw.write(lineContent[0] + "\t" + lineContent[1]);
			bw.close();

			// move to next line
			line = br.readLine();

		} // end of while loop

		br.close();// close reader

	}

	public final void WithItemsCollated(String FiletoCollate) throws IOException {

		// Reader of Mesostics file reader
		BufferedReader br1 = new BufferedReader(new FileReader(new File(Mesostics)));
		String line1 = br1.readLine();// first line of first mesostic
		String[] line1Array = line1.split("\t");
		Integer line1Index = new Integer(line1Array[0]);

		// Reader of file to collate with Mesostics file
		BufferedReader br2 = new BufferedReader(new FileReader(new File(FiletoCollate)));
		String line2 = br2.readLine();
		String[] line2Array = line2.split("\t");
		Integer line2Index = new Integer(line2Array[0]);

		while (line1 != null && line2 != null) {

			// If Line1 is smaller clause
			if (line1Index < line2Index) {
				// write line1 to file
				System.out.println(line1);
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Mesostics), true));
				bw.write(line1);
				bw.newLine();
				bw.close();

				// advance line1 to next line and reformat
				try {

					line1 = br1.readLine();
					line1Array = line1.split("\t");
					line1Index = new Integer(line1Array[0]);

				} catch (NullPointerException e) {

					/*
					 * TODO if no more of line1, continue with line2
					 * Note: his exception should never occur 
					 * because the index of the last line of the 
					 * Mesostics file has beenset at 10000
					 */

					while (line2 != null) {

						System.out.println(line2);
						BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File(Mesostics), true));
						bw2.write(line2);
						bw2.newLine();
						bw2.close();

						try {

							// advance and reformat line2
							line2 = br1.readLine();
							line2Array = line2.split("\t");
							line2Index = new Integer(line2Array[0]);

						} catch (NullPointerException e1) {

							/*
							 * TODO if no more line1 AND line2 END
							 */
						}
					} // end of try/catch writing line2 to finish
				} // end of try/catch writing line1
			} // end of IF CLAUSE

			// Else line2 is smaller clause
			else

			// write line2 to MesosticsCollated
			System.out.println(line2);
			BufferedWriter bw2 = new BufferedWriter(new FileWriter(new File(Mesostics), true));
			bw2.write(line2);
			bw2.newLine();
			bw2.close();

			// advance line2 to next line and reformat
			try {
				line2 = br2.readLine();
				line2Array = line2.split("\t");
				line2Index = new Integer(line2Array[0]);

			} catch (NullPointerException e1) {

				// if no more of line2 write line1 to finish

				while (line1 != null) {

					System.out.println(line1);
					BufferedWriter bw1 = new BufferedWriter(new FileWriter(new File(Mesostics), true));
					bw1.write(line1);
					bw1.newLine();
					bw1.close();
				}
			} // end of try/catch writing line 2
		} // end of else clause writing line2
		// close resources
		br1.close();
		br2.close();
	} 
}
