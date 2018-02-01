package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This class uses the decorator design pattern:
 * It has the same contract with the interface NextItem
 * as the abstract decorator NextItemFiltered, which it extends.
 * 
 * This provides the runtime ability to instantiate NextWord
 * and instantiate this class with the NextWord object in its constructor,
 * thereby adding the appropriate syllable filtering procedures to NextWord 
 * 
 * Alternatively, could refactor and use inheritance
 * by having this class extend NextWord rather than the decorator. 
 * 
 */

//imported Java libraries
import java.io.IOException;
import java.util.ArrayList;

public class NextWordFiltered extends NextItemFiltered {
	
	//reference syllable repository objects
	private Syllable S; 
	private SyllableRecord Sr;
	private LineSyllable Ls;
	
	// NextWordFiltered secondary constructor
	public NextWordFiltered(NextItem decoratedNextItem, String mesostics) throws IOException {
		
		this(new FileToString(RowAddress).output(), new FileToString(ChapterAddress).output(), RowArrayIndex,
				ChapterArrayIndex, Mesostics, new LineMesostic(mesostics), decoratedNextItem, Directory);
		Ls = new LineSyllable(Directory, S, Sr);
	}
	
	// NextWordFiltered primary constructor
	public NextWordFiltered(String row, String chapter, int rowArrayIndex, String chapterArrayIndex, 
							String mesostics, LineMesostic lm, NextItem decoratedNextItem, String directory) {
		super(row, chapter, rowArrayIndex, chapterArrayIndex, mesostics, lm, decoratedNextItem, directory);
		S = new Syllable();
		Sr = new SyllableRecord();
		
	}

	@Override
	public ArrayList<String[]> Item() throws IOException, InterruptedException {
		
		//get the target word and its index from the decorated NextItem's method 
		Output = super.Item();
		
		//filter the output through the syllable checking objects
		String SavedSyllable = S.SyllableSaved(Output.get(0)[1]);
		String Repository = Sr.Repository();
		Boolean RepoHasSyllable = Sr.RepositoryHasSyllable(Repository, SavedSyllable);
		if (RepoHasSyllable) {
			
			System.out.println("Continuing with the current row index: " + RowArrayIndex);
			Output.get(0)[0] = "-1";
			Output.get(0)[1] = "zero";
			return Output;
			
		} else

		return Output;
	}

	@Override
	public void Write(ArrayList<String[]> output) throws IOException, InterruptedException {
		
		//if Repository has syllable, do not write to mesostic file or syllable repository
		if(output.get(0)[0] != "-1" && output.get(0)[1] != "zero"){
			
			//add to the mesostics
			super.Write(output);
			System.out.println("Adding to mesostics: " + output.get(0)[1]);
			//add to the appropriate syllable repository
			Ls.WriteSyllable(output);	
		}
	}
	@Override
	public final void AdvanceChapterWord(String index) {
		
		//format the inputs for updating
		int currentIndex = new Integer(ChapterArrayIndex).intValue();
		int wordIndex = new Integer(index).intValue();
		
		if(wordIndex != -1){	
			//advance the word index
			ChapterArrayIndex = String.valueOf(wordIndex + 1);
			System.out.println("Advancing to new chapter index: " + ChapterArrayIndex);
		
		} else
			//move forward one chapter word
			ChapterArrayIndex = String.valueOf(currentIndex + 1);
			System.out.println("Advancing to new chapter index: " + ChapterArrayIndex);
		
	}

	@Override
	public final void AdvanceMesosticLetter(String index) {
		
		//format the inputs for updating
		int wordIndex = new Integer(index).intValue();
		if(wordIndex != -1){
			//advance the array index or continue from the beginning
			if (RowArrayIndex + 1 < RowArray.length) {
				RowArrayIndex++;
				} else
					RowArrayIndex = 0;
			System.out.println("Advancing to new row index: " + RowArrayIndex);
		}
	}
}

