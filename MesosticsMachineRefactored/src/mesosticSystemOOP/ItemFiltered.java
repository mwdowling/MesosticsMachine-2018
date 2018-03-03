package mesosticSystemOOP;

/**
 * 
 * @author Martin Dowling
 * 
 * This class is an abstract decorator in the GOF Decorator Design pattern,
 * adding a decorated NextITem to appropriate constructors.
 * It overrides the NextItem interface methods 
 * with methods that call the corresponding methods in the decorated NextItem,
 * which will have new functionality added in the concrete decorated NextITem. 
 * 
 * TODO: Are both constructors needed?
 */

import java.io.IOException;
import java.util.ArrayList;

public abstract class ItemFiltered extends ItemAbstract {

	protected Item DecoratedNextItem;
	protected WordFiltered Nwf; //for use by NextMesosticFiltered and NextChapterFiltered
	static String Directory;
	
	// NextWordFiltered primary constructor
	public ItemFiltered(String row, String chapter, int rowArrayIndex, String chapterArrayIndex, 
							String mesostics, MesosticsLineWriter lm, Item decoratedNextItem, String directory) {
		super(row, chapter, rowArrayIndex, chapterArrayIndex, mesostics, lm);
		Directory = directory; 
		DecoratedNextItem = decoratedNextItem;		
	}
	
	//NextMesosticFiltered and NextChapterFiltered primary constructor
	public ItemFiltered(String row, String chapter, String mesostics, MesosticsLineWriter lm,
							Word nw, Item decoratedNextItem, String directory) {
		super(row, chapter, mesostics, lm, nw);
		Directory = directory; 
		DecoratedNextItem = decoratedNextItem;
			
	}
	
	//The abstract decorator calls all the methods of the decorated object:
	@Override
	public ArrayList<String[]> NextItem() throws IOException, InterruptedException {
		return DecoratedNextItem.NextItem();
	}
	
	@Override
	public void Write(ArrayList<String[]> output) throws IOException, InterruptedException {
		DecoratedNextItem.Write(output);		
	}
	
	@Override
	public String AdvanceChapterWord(String index) {
		return DecoratedNextItem.AdvanceChapterWord(index);
		
	}
}
