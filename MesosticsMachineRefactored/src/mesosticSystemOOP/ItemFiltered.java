package mesosticSystemOOP;

/**
 * 
 * @author Martin Dowling
 * 
 * This class is an abstract decorator in the GOF Decorator Design pattern,
 * adding a decorated Item to appropriate constructors.
 * It overrides the Item interface methods 
 * with methods that call the corresponding methods in the decorated Item,
 * which will have new functionality added in the concrete decorated Item. 
 * 
 * TODO: Are both constructors actually in use?
 */

import java.io.IOException;
import java.util.ArrayList;

public abstract class ItemFiltered extends ItemAbstract {

	protected Item DecoratedItem;
	protected WordFiltered NextWordFiltered; //for use by MesosticFiltered and ChapterFiltered
	static String Directory;
	
	// WordFiltered primary constructor
	public ItemFiltered(String row, String chapter, int rowArrayIndex, String chapterArrayIndex, 
							String mesostics, MesosticsLineWriter lm, Item decoratedNextItem, String directory) {
		super(row, chapter, rowArrayIndex, chapterArrayIndex, mesostics, lm);
		Directory = directory; 
		DecoratedItem = decoratedNextItem;		
	}
	
	//MesosticFiltered and ChapterFiltered primary constructor
	public ItemFiltered(String row, String chapter, String mesostics, MesosticsLineWriter lm,
							Word nw, Item decoratedNextItem, String directory) {
		super(row, chapter, mesostics, lm, nw);
		Directory = directory; 
		DecoratedItem = decoratedNextItem;
			
	}
	
	//The abstract decorator calls all the methods of the decorated object:
	@Override
	public ArrayList<String[]> NextItem() throws IOException, InterruptedException {
		return DecoratedItem.NextItem();
	}
	
	@Override
	public void Write(ArrayList<String[]> output) throws IOException, InterruptedException {
		DecoratedItem.Write(output);		
	}
	
	@Override
	public String AdvanceChapterWord(String index) {
		return DecoratedItem.AdvanceChapterWord(index);
		
	}
}
