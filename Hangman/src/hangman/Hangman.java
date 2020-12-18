package hangman;

import dictionary.*;
import java.util.*;

/**
 * Abstract class Hangman holding basic game functionality. 
 * @author mnguyen
 * Name: Mai Nguyen
 */
public abstract class Hangman {
    
    //instance variables
	
	/**
	 * Current chosen word
	 */
	protected String chosenWord;

	/**
	 * Character representing hidden letters of the current word
	 */
    public static final String HIDDEN_CHAR = "_";

    /**
     * Correctly guessed letters, otherwise "_" 
     */
	public ArrayList<String> correctLetters;
	
	/**
	 * List of incorrectly guessed letters
	 */
	public ArrayList<String> incorrectGuesses = new ArrayList<String>();
	
	/**
	 * Stores list of all valid words
	 */
    public ArrayList<String> vocabulary = new ArrayList<String>();
	
	/**
	 * Count of guesses already made
	 */
	public int guessCount;


    //methods

    /**
     * Select randomly a word for the game. 
     * @return chosen word
     */
    abstract public String chooseWord();

    /**
     * Find the given letter and mark as correctly guessed letter
     * @param letter to search for
     * @return true if letter was found and marked, otherwise false
     */
    abstract public boolean findAndMarkLetter(String letter);
    
    /**
     * Print the version of hangman that the player had just played
     * @return version of the game
     */
    abstract public String printVersion();
    
    /**
     * Returns true if the given letter has been guessed already
     * @param letter that has already been guessed
     * @return true if letter was guessed
     */
    public boolean alreadyGuessed(String letter) {
    	return this.guessedIncorrectly(letter) || this.guessedCorrectly(letter);
    }
    
    /**
     * Returns true if the given letter has been guessed incorrectly
     * @param letter that was guessed
     * @return true if letter was guessed as an incorrect guess
     */
    private boolean guessedIncorrectly(String letter) {
    	return this.incorrectGuesses.contains(letter);
    }
    
    /**
     * Returns true if the given letter has been guessed correctly
     * @param letter that was guessed
     * @return true if letter was guessed as a correct guess
     */
    private boolean guessedCorrectly(String letter) {
    	return this.correctLetters.contains(letter);
    }
    
    /**
     * Determines if every letter of the random word has been guessed correctly
     * Checks the letters guessed by the player against the letters of the chosen random word. 
     * @return true if every letter has been guessed
     */
    public boolean checkLetters() {
    	boolean checked = true;
    	for (int i = 0; i < this.correctLetters.size(); i++) {
    		String correctLetter = this.correctLetters.get(i);
    		if (Hangman.HIDDEN_CHAR.equals(correctLetter)) {
    			checked = false;
    			break;
    		}
    	}
    	
    	return checked;
    }
    
    /**
     * Returns the total number of guesses
     * @return number of guesses
     */
    public int getNumberGuesses() {
    	return this.guessCount;
    }
    
    /**
     * Returns the total number of incorrect guesses
     * @return number of incorrect guesses
     */
    public int getNumberIncorrectGuesses() {
    	return this.incorrectGuesses.size();
    }
    
    
    /**
     * Returns correctly guessed letters
     * @return the correct letters as a string
     */
    public String getCorrectLetters() {
    	StringBuilder sb = new StringBuilder();
    	
    	for (String correctLetter: this.correctLetters) {
    		sb.append(correctLetter);
    	}
    	
    	return sb.toString();
    }
    
    /**
     * Returns string containing an array of incorrectly guessed letters and count the number of incorrectly guessed letters
     * @return String containing information
     */
    public String getIncorrectGuesses() {
    	
    	StringBuilder sb = new StringBuilder();
    	
    	if (this.incorrectGuesses.size() > 0) {
    		sb.append(incorrectGuesses);
    	}
    	
    	return sb.toString();
    }
    
    public String getChosenWord() {
    	
    	return this.chosenWord;
    }
    
    // for troubleshooting
    public void printWord() {
    	System.out.println("Current word: " + this.chosenWord);
    }
    
    public void printWordList() {
    	System.out.println("Current word list: " + this.vocabulary);
    }
    

}

