package mesosticSystemOOP;

/**
 * @author Martin Dowling
 * 
 * This abstract class contains all needed variables and constructors
 * for the concrete classes which make mesostics or portions thereof. 
 * 
 * @see Word which creates the next word in a mesostic
 * @see Mesostic  which creates the next whole mesostic
 * @see Chapter which creates all the successive mesositics in a chapter 
 * @see ItemFiltered an abstract decorator class 
 * which is extended by corresponding syllable filtering classes
 * 
 * TODO I began making static variables because 
 * 		they were requried in secondary constructors.
 * 		Clarify how these static variables integrate with the GUI.
 */

public abstract class ItemAbstract implements Item {

	// reference classes
	protected MesosticsLineWriter Lm;
	protected SyllableLineWriter Ls;
	protected Word Nw;
	protected FileToString Fs;
	
	// input variables for the mesostic row
	static String RowAddress;// = MesosticsGUIOOP.RowAddress;
	static String[] RowArray;// = MesosticsGUIOOP.RowArray;
	static int RowArrayIndex;// = MesosticsGUIOOP.RowArrayIndex;

	// input variables for the chapter
	static String ChapterAddress;// = MesosticsGUIOOP.ChapterAddress;
	static String[] ChapterArray;// = MesosticsGUIOOP.ChapterArray;
	static String ChapterArrayIndex;// = MesosticsGUIOOP.ChapterArrayIndex;
	
	// location variables for mesostics files
	static String Mesostics;// = MesosticsGUIOOP.Mesostics; //location of Mesostics file
	static String MesosticsFinished;//output of finishing methods
	
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
	public ItemAbstract(String row, String chapter, int rowArrayIndex, 
							String chapterArrayIndex, String mesostics, MesosticsLineWriter lm) {
		RowArray = row.split("");
		RowArrayIndex = rowArrayIndex;
		ChapterArray = chapter.split("\\s+");
		ChapterArrayIndex = chapterArrayIndex;
		Mesostics = mesostics;
		Lm = lm; 
	}
	
	// nextMesostic and NextChapter primary constructor 
	public ItemAbstract(String row, String chapter, String mesostics, MesosticsLineWriter lm, Word nw) {
		MesosticsMachineGUI.RowArray = row.split("");//TODO Don't understand why this prefix is needed here, but not above
		Mesostics = mesostics;
		Nw = nw;
		Lm = lm;
	}
	
	//Sounds, Places and Sentences primary constructor 
	public ItemAbstract(String comparator, String chapter, String outputFile, MesosticsLineWriter lm) {	
		ChapterArray = chapter.split("\\s+");
		Lm = lm;
	}
		
	//Sentence primary constructor
	public ItemAbstract(int wordIndex, String chapter) {
		ChapterArray = chapter.split("\\s+");
	}

	//further refinement of this in the NextItemFiltered objects
	@Override
	public String AdvanceChapterWord(String index) {	
		
		int wordIndex = new Integer(index).intValue();	
		ChapterArrayIndex = String.valueOf(wordIndex + 1);
		System.out.println("Advancing to new Chapter Index: " + ChapterArrayIndex);
		return ChapterArrayIndex;
		
	}	
}
