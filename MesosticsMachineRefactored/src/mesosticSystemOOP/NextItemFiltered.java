package mesosticSystemOOP;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Martin
 * 
 * This class is an abstract decorator 
 * in keeping with the GOF Decorator Design pattern.
 * 
 * It extends the abstract NextITem by 
 * adding a decorated NextITem to the appropriate constructors
 * 
 * It overrides the NextItem interface methods 
 * with methods that call the corresponding methods in the decorated NextItem,
 * which will have new functionality added in the concrete decorated NextITem. 
 * 
 * NOT SURE ALL THESE CONSTRUCTORS ARE NEEDED HERE.
 */

public abstract class NextItemFiltered extends NextItemAbstract {

	protected NextItem DecoratedNextItem;
	protected NextWordFiltered Nwf; //for use by NextMesosticFiltered and NextChapterFiltered
	
	// NextWordFiltered primary constructor
	public NextItemFiltered(String row, String chapter, int rowArrayIndex, String chapterArrayIndex, 
							String mesostics, LineMesostic lm, NextItem decoratedNextItem, String directory) {
		super(row, chapter, rowArrayIndex, chapterArrayIndex, mesostics, lm);
		Directory = directory; 
		DecoratedNextItem = decoratedNextItem;
		
	}
	
	//NextMesosticFiltered and NextChapterFiltered primary constructor
	public NextItemFiltered(String row, String chapter, String mesostics, LineMesostic lm,
							NextWord nw, NextItem decoratedNextItem, String directory) {
		super(row, chapter, mesostics, lm, nw);
		Directory = directory; 
		DecoratedNextItem = decoratedNextItem;
		
		
	}
	
	//The abstract decorator calls all the methods of the decorated object
	@Override
	public ArrayList<String[]> Item() throws IOException, InterruptedException {
		return DecoratedNextItem.Item();
	}
	
	@Override
	public void Write(ArrayList<String[]> output) throws IOException, InterruptedException {
		DecoratedNextItem.Write(output);		
	}
	
	@Override
	public void AdvanceChapterWord(String index) {
		DecoratedNextItem.AdvanceChapterWord(index);
		
	}

	@Override
	public void AdvanceMesosticLetter(String index) {
		DecoratedNextItem.AdvanceMesosticLetter(index);
		
	}
}
