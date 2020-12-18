package hangman;
import java.util.*;

import dictionary.*;

/**
 * Hangman traditional version
 * @author mnguyen.
 */
public class HangmanTraditional extends Hangman {
    
	// constructor
	/**
	 * Create an instance of traditional hangman with the given word list.
	 * @param vocabulary as list of valid words
	 */
	public HangmanTraditional(ArrayList<String> vocabulary) {
		this.vocabulary = vocabulary; //sets list of words
	}
	
	// methods
	
	/**
	 * Choose 1 random word out of the given vocabulary
	 * @return chosenWord as randomly chosen word
	 */
	@Override
	public String chooseWord() {
		
		// randomize to get the chosen word from the vocabulary list of words
		Random random = new Random();
		int randInt = random.nextInt(this.vocabulary.size()); //gets index
		this.chosenWord = this.vocabulary.get(randInt).toLowerCase(); //assign word
		
		// set the correct letters list to be "_" with the correct length of word
		this.correctLetters = new ArrayList<String>(this.chosenWord.length());
		//traverses through list
		for (int i = 0; i < this.chosenWord.length(); i ++) {
			this.correctLetters.add(Hangman.HIDDEN_CHAR); //assign value to be seen
		}
		return this.chosenWord; //return current view of word
	}

	/**
	 * Find the given letter in the chosen word and mark as correctly guessed letter
	 * @param letter to search for
	 * @return true if letter was found and marked
	 */
	@Override
	public boolean findAndMarkLetter(String letter) {
		/**
		 * Whether or not count is already incremented
		 */
		boolean countAlreadyIncremented = false;
		
		/**
		 * Whether or not if letter is found
		 */
		boolean found = false;
		
		// if letter is not a single character, ignore
		if (letter.length() != 1) {
			return found; //return if letter is found or not
		}
		
		// convert all letters to lower case;
		letter = letter.toLowerCase();
		
		//traverses through list
		for (int i = 0; i <= this.chosenWord.length() - 1; i++) {
			Character c = this.chosenWord.charAt(i); //sets current letter to char
			//if a match occurs
			if (letter.equals(c.toString())) {
				this.correctLetters.set(i, letter); //set correct
				found = true; //found is labeled true
				//if the count was already incremented
				if (countAlreadyIncremented == false) {
					// increment guess count
					this.guessCount+=1;
					countAlreadyIncremented = true; //count is now already incremented
					//(Prevents double incrementing)
				}
			}
		}
		
		// if letter wasn't found
		if (!found && !this.incorrectGuesses.contains(letter)) {
			this.incorrectGuesses.add(letter); //add to incorrect
			
			// increment guess count
			this.guessCount+=1;
		}
		return found; //return found status
	}
	
	/**
	 * Print current version of the game hangman
	 */
	@Override
	public String printVersion() {
		String traditional = "Hangman Traditional"; //creates the string for game version
		return traditional; //returns game version
	}
	
	
	//FOR TESTING PURPOSES
	public void setWord(String someWordForTesting) {
		this.chosenWord = someWordForTesting;
		
		// set the correct letters list to be "_" with the correct length of word
		this.correctLetters = new ArrayList<String>(this.chosenWord.length());
		for (int i = 0; i < this.chosenWord.length(); i ++) {
			this.correctLetters.add(Hangman.HIDDEN_CHAR);
		}		
	}
}


