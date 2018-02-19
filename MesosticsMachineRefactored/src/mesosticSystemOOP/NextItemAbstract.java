package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This abstract class contains all needed variables and constructors
 * for the concrete classes which make mesostics or portions thereof. 
 * 
 * @see NextWord which creates the next word in a mesostic
 * @see NextMesostic  which creates the next whole mesostic
 * @see NextChapter which creates all the successive mesositics in a chapter 
 * @see NextItemFiltered an abstract decorator class 
 * which is extended by corresponding syllable filtering classes
 * 
 * TODO none of these static variables integrate with the GUI. What is the point??
 * 
 */

public abstract class NextItemAbstract implements NextItem {

	// reference classes
	protected MesosticsLineWriter Lm;
	protected SyllableLineWriter Ls;
	protected NextWord Nw;
	protected FileToString Fs;
	
	// input variables for the mesostic row
	static String Row;
	static String RowAddress;
	static String[] RowArray;
	static int RowArrayIndex;

	// input variables for the chapter
	static String Chapter;
	static String ChapterAddress;
	static String[] ChapterArray;
	static String ChapterArrayIndex;
	
	// location variables for mesostics files
	static String Mesostics; //location of Mesostics file
	static String MesosticsFinished;//output of finishing methods
	static String Directory;// location of syllable repositories
	
	//variables for sounds 
	static String OEDSounds;
	static String[] OEDSoundsArray;
	static String SoundWords;
	static String SoundSentences;
	
	//variables for places
	static String NotPlace;
	static String NotPlaceArray[];
	static String PlaceWords;
	static String PlaceSentences;
	
	// Primary constructors for each of the concrete "NextX" classes
	
	// NextWord primary constructor
	public NextItemAbstract(String row, String chapter, int rowArrayIndex, 
							String chapterArrayIndex, String mesostics, MesosticsLineWriter lm) {
		RowArray = row.split("");
		RowArrayIndex = rowArrayIndex;
		ChapterArray = chapter.split("\\s+");
		ChapterArrayIndex = chapterArrayIndex;
		Mesostics = mesostics;
		Lm = lm; 
	}
	
	// nextMesostic and NextChapter primary constructor 
	public NextItemAbstract(String row, String chapter, String mesostics, MesosticsLineWriter lm, NextWord nw) {
		Row = row;
		Chapter = chapter;
		RowArray = row.split("");
		Mesostics = mesostics;
		Nw = nw;
		Lm = lm;
	}
	
	//Sounds, Places and Sentences primary constructor 
	public NextItemAbstract(String comparator, String chapter, String outputFile, MesosticsLineWriter lm) {	
		Chapter = chapter;
		ChapterArray = chapter.split("\\s+");
		Lm = lm;
	}
		
	//Sentence primary constructor
	public NextItemAbstract(int wordIndex, String chapter) {
		Chapter = chapter;
		ChapterArray = chapter.split("\\s+");
	}

	//further refinement of this in the NextITemFiltered objects
	@Override
	public void AdvanceChapterWord(String index) {	
		
		int wordIndex = new Integer(index).intValue();	
		ChapterArrayIndex = String.valueOf(wordIndex + 1);
		System.out.println("Advancing to new Chapter Index: " + ChapterArrayIndex);
	}	
}
