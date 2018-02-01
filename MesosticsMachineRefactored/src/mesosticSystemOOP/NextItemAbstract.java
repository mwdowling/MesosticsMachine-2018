package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Martin 
 * This abstract class contains all needed variables and constructors. 
 * for the three "NextX" concrete classes which extend it,
 * and the NextWordItemFiltered abstract decorator class
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

	// output variables for mesostics files
	static ArrayList<String[]> Output = new ArrayList<String[]>();
	static ArrayList<String[]> OutputList = new ArrayList<String[]>();
	static String Mesostics; //output for writing to Mesostics file
	static String Directory;// location of syllable repositories

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
