package mesosticSystemOOP;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

//TODO integrate into NextItem hierarchy

public class Places {

	// input variables for target chapter
	private String ChapterAddress;
	private String[] ChapterArray;

	private String NotPlaces;
	private String Places; 
	private String PlaceSentence;
	

	public Places(String chapterAddress, String notPlaces, String places, String placeSentence) {
		
		ChapterAddress = chapterAddress;
		NotPlaces = notPlaces;
		Places = places;
		PlaceSentence = placeSentence;
	}


	public void PlaceWord() throws IOException {

		/*
		 * Use a TreeMap to store the next word in the chapter as a value 
		 * along with the index of the word as the key IF: 
		 * the first letter is upper case
		 * and the word does not begin a sentence.
		 * 
		 * Recognising that this will miss places that begin sentences:
		 * "California is the place you want to be."
		 */
		
		Map<Integer, String> placeWordMap = new TreeMap<>();
		for (int i = 1; i < ChapterArray.length; i++) {

			if (Character.isUpperCase(ChapterArray[i].charAt(0)) && !ChapterArray[i - 1].contains(".")) {
				// remove punctuation and put into the TreeMap
				placeWordMap.put(i, ChapterArray[i].replaceAll("\\W", "").trim());

			} // end of if statement
		} // end of for loop

		// Remove word from TreeMap if it matches a word in the notPlacesList
		Map<Integer, String> placeWordMapCopy = new TreeMap<>();
		placeWordMapCopy.putAll(placeWordMap);

		for (String value : placeWordMapCopy.values()) {

			if (NotPlaces.contains(value.toLowerCase().trim())) {

				for (Entry<Integer, String> entry : placeWordMapCopy.entrySet()) {

					if (entry.getValue().equals(value)) {
						int key = entry.getKey();
						placeWordMap.remove(key, value);
					} // end of inner if statement
				} // end of inner for loop
			} // end of outer if statement
		} // end of outer for loop

		// Write the Treemap to the output file
		for (Entry<Integer, String> entry : placeWordMap.entrySet()) {

			int key = entry.getKey();
			String value = entry.getValue();
			System.out.printf("%s : %s\n", key, value);
			String line = key + "\t" + value;
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(Places), true));

			bw.write(line);
			bw.newLine();
			bw.close();
			
		} // end of enhanced for loop through TreeMap
	}
}
