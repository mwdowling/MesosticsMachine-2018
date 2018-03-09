package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This class allows creation of a 
 * a concrete decorator having the same contract with the interface NextItem
 * as the abstract decorator ItemFiltered, which it extends.
 * 
 * This provides the runtime ability to instantiate NextWord
 * and instantiate this class with the Word object in its constructor,
 * thereby adding the appropriate syllable filtering procedures to Word 
 * 
 */

import java.io.IOException;
import java.util.ArrayList;

public final class WordFiltered extends ItemFiltered {
	
	//reference syllable repository objects
	private Syllable Syll; 
	private SyllableStored Stored;
	private SyllableLineWriter Writer;
	
	// WordFiltered secondary constructor
	public WordFiltered(Item decoratedNextItem, String mesostics) throws IOException {
		
		this(new FileToString(MesosticsMachineGUI.RowAddress).output(), new FileToString(MesosticsMachineGUI.ChapterAddress).output(), MesosticsMachineGUI.RowArrayIndex,
				MesosticsMachineGUI.ChapterArrayIndex, MesosticsMachineGUI.Mesostics, new MesosticsLineWriter(mesostics), decoratedNextItem, Directory);
		Syll = new Syllable();
		Stored = new SyllableStored(MesosticsMachineGUI.Directory);
		Writer = new SyllableLineWriter(Syll, Stored);
	}
	
	// WordFiltered primary constructor
	public WordFiltered(String row, String chapter, int rowArrayIndex, String chapterArrayIndex, 
							String mesostics, MesosticsLineWriter lm, Item decoratedNextItem, String directory) {
		super(row, chapter, rowArrayIndex, chapterArrayIndex, mesostics, lm, decoratedNextItem, directory);
	}

	@Override
	public final ArrayList<String[]> NextItem() throws IOException, InterruptedException {
		
		//get the target word and its index using the decorated Item's method 
		ArrayList<String[]> output = new ArrayList<String[]>();
		output = super.NextItem();
		
		//filter the output through the syllable checking objects
		String wordIndex = output.get(0)[0];
		String savedSyllable = Syll.Saved(output.get(0)[1]);
		String repository = Stored.Repository();
		Boolean repoHasSyllable = Stored.RepositoryHasSyllable(repository, savedSyllable);
		
		if (repoHasSyllable) {
			
			System.out.println("Repository has Syllable.");
			System.out.println("Continuing with the current row index: " + RowArrayIndex);
			output.get(0)[0] = wordIndex;
			output.get(0)[1] = "don't write it";
			return output;
			
		} else
			
		Writer.WriteSyllable(output);	
		return output;
	}

	@Override
	public final void Write(ArrayList<String[]> output) throws IOException, InterruptedException {
		
		//if Repository has syllable, do not write to mesostic file
		if(output.get(0)[1].equals("don't write it")){
			System.out.println("don't write it");
			
		}else {
			super.Write(output);
			System.out.println("Adding to mesostics: " + output.get(0)[1]);
		}
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
