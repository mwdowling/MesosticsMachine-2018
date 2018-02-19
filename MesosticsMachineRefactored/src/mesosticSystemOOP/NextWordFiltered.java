package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This class allows creation of a 
 * a concrete decorator having the same contract with the interface NextItem
 * as the abstract decorator NextItemFiltered, which it extends.
 * 
 * This provides the runtime ability to instantiate NextWord
 * and instantiate this class with the NextWord object in its constructor,
 * thereby adding the appropriate syllable filtering procedures to NextWord 
 * 
 */

import java.io.IOException;
import java.util.ArrayList;

public final class NextWordFiltered extends NextItemFiltered {
	
	//reference syllable repository objects
	private Syllable S; 
	private SyllableRecord Sr;
	private SyllableLineWriter Ls;
	
	// NextWordFiltered secondary constructor
	public NextWordFiltered(NextItem decoratedNextItem, String mesostics) throws IOException {
		
		this(new FileToString(RowAddress).output(), new FileToString(ChapterAddress).output(), RowArrayIndex,
				ChapterArrayIndex, Mesostics, new MesosticsLineWriter(mesostics), decoratedNextItem, Directory);
		S = new Syllable();
		Sr = new SyllableRecord();
		Ls = new SyllableLineWriter(Directory, S, Sr);
	}
	
	// NextWordFiltered primary constructor
	public NextWordFiltered(String row, String chapter, int rowArrayIndex, String chapterArrayIndex, 
							String mesostics, MesosticsLineWriter lm, NextItem decoratedNextItem, String directory) {
		super(row, chapter, rowArrayIndex, chapterArrayIndex, mesostics, lm, decoratedNextItem, directory);
	}

	@Override
	public final ArrayList<String[]> Item() throws IOException, InterruptedException {
		
		//get the target word and its index from the decorated NextItem's method 
		ArrayList<String[]> output = new ArrayList<String[]>();
		output = super.Item();
		
		//filter the output through the syllable checking objects
		String wordIndex = output.get(0)[0];
		int wordIndexInt = new Integer(wordIndex).intValue();
		String SavedSyllable = S.SyllableSaved(output.get(0)[1]);
		String Repository = Sr.Repository();
		Boolean RepoHasSyllable = Sr.RepositoryHasSyllable(Repository, SavedSyllable);
		
		if (RepoHasSyllable) {
			
			System.out.println("Repository has Syllable.");
			System.out.println("Continuing with the current row index: " + RowArrayIndex);
			wordIndex = String.valueOf(wordIndexInt);
			output.get(0)[0] = wordIndex;
			output.get(0)[1] = "don't write it";
			return output;
			
		} else
			
		Ls.WriteSyllable(output);	
		return output;
	}

	@Override
	public final void Write(ArrayList<String[]> output) throws IOException, InterruptedException {
		
		//if Repository has syllable, do not write to mesostic file
		if(output.get(0)[1].equals("don't write it")){
			System.out.println("dont write it");
			AdvanceChapterWord(output.get(0)[0]);
			
		}else {
			super.Write(output);
			System.out.println("Adding to mesostics: " + output.get(0)[1]);
			AdvanceChapterWord(output.get(0)[0]);
			AdvanceMesosticLetter();
		}
	}
	
	@Override
	public final void AdvanceChapterWord(String index) {
		
		super.AdvanceChapterWord(index);
		
	}

	public final void AdvanceMesosticLetter() {
		
		//advance the array index or continue from the beginning
		if (RowArrayIndex + 1 < RowArray.length) {
			RowArrayIndex++;
			} else
				RowArrayIndex = 0;
		System.out.println("Advancing to new row index: " + RowArrayIndex);
		}
}
