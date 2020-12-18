package hangman;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;

/**
 * Hangman Evil version
 * @author mnguyen
 *
 */
public class HangmanEvil extends Hangman{

	// constructor
	
	/**
	 * Create an instance of Hangman Evil with the given dictionary of words
	 * @param vocabulary as list of valid words
	 */
	public HangmanEvil(ArrayList<String> vocabulary) {
		this.vocabulary = vocabulary;
	}
	
	// methods
	
	/**
	 * Takes given word and sets the length of the word for Hangman Evil
	 * @param givenWord to use during the game
	 * @return selected word represented by "_" string characters
	 */
	public String chooseWord(String givenWord) {
		
		this.chosenWord = "";
		this.correctLetters = new ArrayList<String>(givenWord.length());
		
		for (int i = 0; i < givenWord.length(); i++) {
			this.chosenWord += Hangman.HIDDEN_CHAR;
			this.correctLetters.add(Hangman.HIDDEN_CHAR);
		}
		
		// divide the word list into different groups by length of the selected word
		this.groupByLength(this.chosenWord.length());
		return this.chosenWord;
	}
	
	/**
	 * Choose the random word from the vocabulary and sets the length of the word for Hangman Evil
	 * @return selected word as "_" characters with appropriate length
	 */
	@Override
	public String chooseWord() {
		
		// get random word
		Random random = new Random();
		int randInt = random.nextInt(this.vocabulary.size());
		String randWord = this.vocabulary.get(randInt).toLowerCase();
		
		this.chosenWord = "";
		this.correctLetters = new ArrayList<String>(randWord.length());
		
		for (int i = 0; i < randWord.length(); i++) {
			this.chosenWord += Hangman.HIDDEN_CHAR;
			this.correctLetters.add(Hangman.HIDDEN_CHAR);
		}
		
		// divide the word list into different groups by length of the selected word
		this.groupByLength(this.chosenWord.length());
		return this.chosenWord;
	}
	
	/**
	 * Given the length of the current chosen word, return a list of words containing words of only that length
	 * @param chosenWordLength
	 * @return updated vocabulary containing valid words of appropriate length
	 */
	private ArrayList<String> groupByLength(int chosenWordLength) {
		
		int wordLength;
		ArrayList<String> vocabulary = new ArrayList<String>();
		
		// iterate over the list of valid words
		for (String word: this.vocabulary) {
			
			// get word length
			wordLength = word.length();
			
			// add to the list only words with the same length
			if (wordLength == chosenWordLength) {
				vocabulary.add(word);
			}
		}
		
		// update our current vocabulary of valid words 
		this.vocabulary = new ArrayList<String>(vocabulary);
		
		return this.vocabulary;
		
	}
	
	/**
	 * Find the given letter in the word group and mark as correctly guessed letter.
	 * Group the word list into groups based on the position of the given letter, or if it cannot be found.
	 * Gets the largest list of these word groups and set that as the vocabulary
	 * @param letter to search for
	 * @return true if the letter was found and marked, otherwise false
	 */
	public boolean findAndMarkLetter(String letter) {
		
		/**
		 * Whether or not count is already incremented
		 */
		boolean countAlreadyIncremented = false;
		
		/**
		 * Whether or not if guessed letter is found 
		 */
		boolean found = false; 
		
		// if letter has more than 1 character, then ignore
		if (letter.length() != 1) {
			return found;
		}
		
		// convert to lower case
		letter = letter.toLowerCase();
		
		// group into word groups and get key of largest group
		String maxKey = this.groupByLetter(letter);
		
		// check to see if the guessed letter is in key
		for (int i = 0; i <= maxKey.length() - 1; i++) {
			
			// if we can find the guessed letter, set that letter at the appropriate index for correctLetters
			if (letter.equals(maxKey.charAt(i) + "")) {
				found = true;
				this.correctLetters.set(i, letter);
				
				if (countAlreadyIncremented == false) {
					// increment guess count'
					this.guessCount++;
					
					// prevent double incrementing of count
					countAlreadyIncremented = true; 
				}
			}
		}
		
		// if the letter wasn't found
		if (! found) {
			// add to list of incorrectGuesses
			if (!this.incorrectGuesses.contains(letter)) {
				this.incorrectGuesses.add(letter);
				
				// increment guess count'
				this.guessCount++;
			}
		}
		
		return found;
	}
	
	/**
	 * Group the vocabulary into word groups based on the position of the given letter, or if it cannot be found
	 * @param letter to search for and group based on
	 * @return the key of the largest word group
	 */
	private String groupByLetter(String letter) {
		
		// initiate a treemap (ordered) to store word groups
		TreeMap<String, ArrayList<String>> wordGroups = new TreeMap<String, ArrayList<String>>();
		
		// generate key for word groups
		StringBuilder keySb;
		
		// iterate over list of words in list
		for (String w: this.vocabulary) {
			
			// create key based on currently selected letters
			keySb = this.getKeySb(this.correctLetters);
			
			// compare guessed letter in each letter in word
			for (int i = 0; i <= w.length() - 1; i++) {
				
				// if we can find the guessed letter in the key, update the key
				if (letter.equals(w.charAt(i) + "")) {
					keySb.setCharAt(i,  w.charAt(i));
				}
			}
			
			ArrayList<String> wList;
			
			// if the word groups do not contain the key, add word and that word list to word groups
			if (! wordGroups.containsKey(keySb.toString())) {
				wList = new ArrayList<String>();
				wList.add(w);
				wordGroups.put(keySb.toString(), wList);
			
			// else, update the word groups
			} else {
				wList = wordGroups.get(keySb.toString());
				wList.add(w);
				wordGroups.replace(keySb.toString(), wList);
			}
		}
		
		return this.updateVocab(wordGroups);
	}
	
	/**
	 * Updates the vocabulary to the largest of the given word groups
	 * @param wordGroups
	 * @return the key of the largest word group
	 */
	private String updateVocab(TreeMap<String, ArrayList<String>> wordGroups) {
		
		int maxSize = 0;
		String maxKey = "";
		
		ArrayList<String> maxList = new ArrayList<String>();
		ArrayList<String> possibleGroups = new ArrayList<String>();
		
		// get a set of keys to iterate over
		for (String key: wordGroups.keySet()) {
			
			// get a list of words for each key
			int listSize = wordGroups.get(key).size(); // size of list of words
			
			if (listSize >= maxSize) {
			
				// if it's the largest group yet, reset possibleGroups
				if (listSize > maxSize) {
					possibleGroups.clear();
				}
				
				maxSize = listSize; 
				maxKey = key; //update key
				possibleGroups.add(maxKey); 
			}
		}
		
		// randomly get word group from all of the largest groups
		Random random = new Random();
		int keyIndex = random.nextInt(possibleGroups.size());
		
		// get key
		maxKey = possibleGroups.get(keyIndex);
		
		// get group based on key
		maxList = wordGroups.get(maxKey);
		
		// reset count
		maxSize = maxList.size();
		
		// update our current vocab
		this.vocabulary = wordGroups.get(maxKey); // returns the largest list of words as the current vocab.
		
		// update the correct letters 
		this.correctLetters = new ArrayList<String>(Arrays.asList(maxKey.split(""))); 
		
		return maxKey;
	}
	

	
	/**
	 * Returns the items in the given arrayList as a String
	 * @param arrayList of items for String
	 * @return StringBuilder with items from arrayList
	 */
	private StringBuilder getKeySb(ArrayList<String> arrayList) {
		
		StringBuilder keySb = new StringBuilder();
		
		// iterate over the arrayList and append each item to keySb
		for (int i = 0; i < arrayList.size(); i++) {
			keySb.append(arrayList.get(i));
		}
		
		return keySb;
	}
	
	/**
	 * Print current version of the game hangman
	 */
	@Override
	public String printVersion() {
		String evil = "Hangman Evil";
		return evil;
	}
	
}