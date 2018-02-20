package mesosticSystemOOP;

/**
 * @author Martin Dowling

 * 
 * This class returns an object which allows the user
 * to select a word from the target chapter using its 
 * ChapterArray index as a reference,
 * to return the entire sentence in which that word sits 
 * to see its semantic context.
 * 
 * For example, the user will want to know: 
 * Is this word a sound? What kind of sound?
 * The answer lies in the sentnece in which the word is used.
 * 
 * @see Sentences to write all target sentences to a file
 */

import java.io.IOException;

import java.util.ArrayList;

public final class Sentence extends NextItemAbstract{

	int WordIndex;
	
	//primary constructor 
	public Sentence(int wordIndex, String comparator, String chapter) throws IOException {
		super(wordIndex, new FileToString(ChapterAddress).output());
		WordIndex = wordIndex; 	
	}


	public final ArrayList<String[]> Item() throws IOException, InterruptedException {		
		
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		outputList.clear();
		
		// variables to identify the boundaries of the sentence
		String fullStop = ".";
		int sentenceStart = 0;
		int sentenceEnd = 0;
		
		// loop to find the end of the chapter sentence
		for (int i = WordIndex; i < ChapterArray.length; i++) {
			
			if (ChapterArray[i].contains(fullStop)) {
				sentenceEnd = i;	
				break;
			}else
				sentenceEnd = ChapterArray.length -1;//edge case: last sentence of chapter
			
		}
		
		// loop to find the beginning of the chapter sentence
		for (int i = WordIndex; i > 0; i--) {
		
			 if (ChapterArray[i].contains(fullStop)) {
				sentenceStart = i + 1;
				break;
			}
			 else sentenceStart = 0;//edge case: first sentence of chapter
			
		}

		//collect all the words into the sentence array
		String[] sentence = new String[sentenceEnd+1 - sentenceStart];
		for (int i = sentenceStart; i <= sentenceEnd; i++) {
			sentence[i- sentenceStart] = ChapterArray[i].trim();
		} 
		
		//return sentence Array
		outputList.add(sentence);
		return outputList;
	}

	@Override
	public final void Write(ArrayList<String[]> outputList) throws IOException, InterruptedException {
		
		String[] sentenceArray = outputList.get(0);
		String sentence = sentenceArray[0];
		for(int i = 1; i < sentenceArray.length; i ++){
			sentence = sentence + " " + sentenceArray[i] ;
		}	
		System.out.println(sentence);
	}
	
	public final String ReturnSentence (ArrayList<String[]> outputList) throws IOException, InterruptedException {
		
		String[] sentenceArray = outputList.get(0);
		String sentence = sentenceArray[0];
		for(int i = 1; i < sentenceArray.length; i ++){
			sentence = sentence + " " + sentenceArray[i] ;
		}	
		return sentence;
	}
}
