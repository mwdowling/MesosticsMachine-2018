package mesosticSystemTesters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import mesosticSystemOOP.FileToString;
import mesosticSystemOOP.MesosticsFinished;
import mesosticSystemOOP.Places;
import mesosticSystemOOP.Sentences;
import mesosticSystemOOP.Sounds;

public class SoundsAndPlacesTester {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//static variables for inputs
		String RowAddress = "C:\\\\MesosticsMachine\\Mesostics Row.txt";
		int RowArrayIndex = 0;
		String ChapterAddress = "C:\\MesosticsMachine\\Mesostics.txt";
		String ChapterArrayIndex = "0";

		// Static variables for the output files
		String Mesostics = "C:\\MesosticsMachine\\Mesostics.txt";
		String MesosticsFinished = "C:\\MesosticsMachine\\MesosticsFinished.txt";
		String Directory = "C:\\MesosticsMachine";

		// variables for sounds
		String OEDSounds = "C:\\MesosticsMachine\\Sounds and Places\\OEDSounds Final.txt";
		String SoundWords = "C:\\MesosticsMachine\\Sounds and Places\\Chapter Sounds.txt";
		String SoundSentences = Directory + "\\Sounds and Places\\ChapterSoundsSentences.txt";

		// variables for places
		String NotPlaces = Directory + "\\Sounds and Places\\Not A Place.txt";
		ArrayList<String> NotPlaceList = new ArrayList<String>(
				Arrays.asList(new FileToString(NotPlaces).output().split("\\r\n")));
		String PlaceWords = Directory + "\\Sounds and Places\\ChapterPlaces.txt";
		String PlaceSentences = Directory + "\\Sounds and Places\\ChapterPlacesSentences.txt";
		
		
		// tests
		Sounds sound = new Sounds(OEDSounds, ChapterAddress, SoundWords);
		ArrayList<String[]> outputList = new ArrayList<String[]>();
		outputList = sound.NextItem();
		sound.Write(outputList);
		
		Places place = new Places(NotPlaces, ChapterAddress, PlaceWords);
		outputList = new ArrayList<String[]>();
		ArrayList<String[]> outputListNonPlacesRemoved = new ArrayList<String[]>();
		outputList = place.NextItem();
		outputListNonPlacesRemoved = place.NonPlacesRemoved(outputList);
		place.Write(outputListNonPlacesRemoved);
		
		Sentences soundSentences = new Sentences(SoundWords, ChapterAddress, SoundSentences);
		Sentences placeSentences = new Sentences(PlaceWords, ChapterAddress, PlaceSentences);

	}

}
