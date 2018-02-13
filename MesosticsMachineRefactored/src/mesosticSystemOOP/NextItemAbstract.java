package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Martin Dowling
 * 
 * This abstract class contains all needed variables and constructors
 * for the concrete classes which make mesostics or portions thereof. 
 * At present these are:
 * 
 * @see NextWord which creates the next word in a mesostic
 * @see NextMesostic  which creates the next whole mesostic
 * @see NextChapter which creates all the successive mesositics in a chapter 
 * 
 * @see NextItemFiltered an abstract decorator class 
 * which is extended by corresponding syllable filtering classes
 */

public abstract class NextItemAbstract implements NextItem {

	// reference classes
	protected LineMesostic Lm;
	protected LineSyllable Ls;
	static NextWord Nw;
	
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
	static String Directory;// location of syllable repositories
	
	//variables for sounds and places
	static String OEDSounds;
	static String[] OEDSoundsArray;
	static String SoundWords;

	/* 
	 * Constructors needed for each of the concrete "Next" classes
	 * Secondary constructor uses FileToString to interact with files on disk
	 */
	
	// NextWord primary constructor
	public NextItemAbstract(String row, String chapter, int rowArrayIndex, 
							String chapterArrayIndex, String mesostics, LineMesostic lm) {
		RowArray = row.split("");
		RowArrayIndex = rowArrayIndex;
		ChapterArray = chapter.split("\\s+");
		ChapterArrayIndex = chapterArrayIndex;
		Mesostics = mesostics;
		Lm = lm;
	}
	
	// nextMesostic and NextChapter primary constructor 
	public NextItemAbstract(String row, String chapter, String mesostics, LineMesostic lm, NextWord nw) {
		Row = row;
		Chapter = chapter;
		RowArray = row.split("");
		Mesostics = mesostics;
		Nw = nw;
		Lm = lm;
	}
	
	//Sounds primary constructor 
	public NextItemAbstract(String oedSounds, String chapter, String soundWords, LineMesostic lm) {
		OEDSounds = oedSounds;
		OEDSoundsArray = oedSounds.split("\t");
		Chapter = chapter;
		ChapterArray = chapter.split("\\s+");
		SoundWords = soundWords;
		Lm = lm;
	}
	
	//TODO add constructor places
	
	/*
	 * This method inherited as is in the non-filtered objects
	 * and refined in the filtered objects 
	 */
	
	@Override
	public void AdvanceChapterWord(String index) {	
		
		//advance the word index
		int wordIndex = new Integer(index).intValue();	
		ChapterArrayIndex = String.valueOf(wordIndex + 1);
		System.out.println("Advancing to new Chapter Index: " + ChapterArrayIndex);

	}
}
