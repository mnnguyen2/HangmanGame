import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

import dictionary.DictionaryReader;
import hangman.*;

/**
 * Controller class for game launch. 
 * Name: Mai Nguyen
 * PennKey: mnnguyen
 * Statement of Collaboration: Collaborated with Alexandros Khor and Daniel Woo
 */

public class HangmanGame {
	
	/**
	 * Play the game with a list of words
	 * @param fileName of the dictionary word file
	 * @throws FileNotFoundException 
	 */
	private void runGame(String fileName) throws FileNotFoundException {
		
		// create a hangman vocabulary list
		ArrayList<String> wordList = DictionaryReader.readParseDictionary(fileName);
		
		/**
		 * holds the completion status of the game
		 */
		boolean gameOver = false;
		
		/**
		 * New scanner object
		 */
		Scanner scanner = new Scanner(System.in);
		
	
		//while the game is not over
		while (!gameOver) {
			//create a new hangman game (with the appropriate version)
			Hangman hangman = this.getHangmanVersion(wordList);
			
			this.printInstructions();
			
			this.playGame(hangman, scanner); //play the game
			
			System.out.println("Would you like to play again? Y/N"); //asks user if they want to play again
			String playAgain = scanner.nextLine(); //gets and stores user's response
			
			//loop to keep user in until a valid response is given with regards to playing again
			for (int i = 0; i >= 0; i++) {
				//if user says no
				if (playAgain.toLowerCase().startsWith("n")) {
					gameOver = true; //game is over
					break; //break out of loop
				}
				//if user says yes
				else if (playAgain.toLowerCase().startsWith("y")) {
					gameOver = false; //game is over, but will play again
					break; //break out of loop
				}
				//if user says something invalid
				else {
					System.out.println("Please enter a valid response."); //prompts user for valid response
					System.out.println("Would you like to play again? Y/N"); //asks user if they want to play again
					playAgain = scanner.nextLine(); //gets and stores user's response					
				}
			}			
		}
		scanner.close(); //close scanner
	}
	
	/**
	 * Gets the version of the hangman game that is to be played
	 * @param vocabulary as list of valid words to use
	 * @return hangman game version
	 */
	public Hangman getHangmanVersion(ArrayList<String> vocabulary) {
		
		// randomly decide which version the user is going to play
		boolean game = new Random().nextBoolean();
		
		// if game returns true, then play HangmanTraditional
		if (game) {
			Hangman hangman = new HangmanTraditional(vocabulary); //create traditional game
			return hangman; //return the game
		} 
		//if game returns false, play HangmanEvil
		else {
			Hangman hangman = new HangmanEvil(vocabulary); //create evil game
			return hangman; //return the game
		}
	}
	
	/**
	 * Print instructions for the game. 
	 */
	private static void printInstructions() {
        System.out.println("Welcome to the game of Hangman!\n"
                        + "The goal of the game is to guess the correct word.\n"
                        + "The fewer the guesses the better.\n"
                        + "There is a twist in that there are two versions of hangman.\n"
                        + "The \"evil\" version constantly changes the word.\n"
                        + "The intial word length stays the same.\n"
                        + "Also, any previous correctly guessed letters are retained.\n"
                        + "Good luck and have fun!"
                        );
        System.out.println(); //blank line
    }
	
	/**
	 * Plays the hangman game
	 * @param hangman
	 * @param scanner
	 */
	private void playGame(Hangman hangman, Scanner scanner) {
		
		/**
		 * holds completion status of the game
		 */
		boolean gameOver = false; 
		
		/**
		 * holds user's guess
		 */
		String chosenLetter;
		
		hangman.chooseWord(); //gets the word of the game
		
		System.out.println("Guess a letter"); //prompts the user for a guess
		
		//as long as the game is not over
		while (!gameOver) {
			//print current guess situation
			System.out.println(hangman.getCorrectLetters());
			
			//gets user's guess
			if (scanner.hasNext()) {
				chosenLetter = scanner.nextLine();
				chosenLetter = chosenLetter.trim();
				
				//if user inputs an invalid response
				if (chosenLetter.length() != 1 || !Pattern.matches("[a-z]", chosenLetter)) {
					System.out.println("Please guess a single lower-case letter"); //prompts user for valid response
				} 
				
				//if user inputs a valid response
				else {
					//if the letter was already guessed
					if (hangman.alreadyGuessed(chosenLetter)) {
						System.out.println("You already guessed that letter."); //tells user that the specific guess was already made
					}
					//if the letter was not already guessed
					else {
						//if guess was correct
						if (hangman.findAndMarkLetter(chosenLetter)) {
							//tells user that guess was correct
							System.out.println("Letter " + chosenLetter + " is correct!");
						}
						//if guess was incorrect
						else if (!hangman.findAndMarkLetter(chosenLetter)) {
							//tells user that guess was incorrect
							System.out.println("Letter " + chosenLetter + " is incorrect. Please try again");
						}
					}
					//prints list of incorrect guesses
					System.out.println("Incorrect Guesses: " + hangman.getIncorrectGuesses());
					//prints guess count
					System.out.println("Guess count: " + hangman.getNumberGuesses());
					
					//check of game is over
					gameOver = hangman.checkLetters();
					
					//if game is over
					if (gameOver) {
						System.out.println("Congratulations! You won! The word is " + hangman.getCorrectLetters()); //prints that game is over
						System.out.println("You have just played " + hangman.printVersion()); //prints what version user played
						System.out.println(); //line separator
					} 
					//if game is not over
					else {
						System.out.println("Guess a letter"); //tells user to guess a letter
					}
				}
			}
		}
	}
	

	/**
	 * Main method for launching and playing Hangman game
	 * @param args default for main method
	 * @throws FileNotFoundException if file cannot be found
	 */
	public static void main(String[] args) throws FileNotFoundException {

		/**
		 * file name
		 */
		String words = "words.txt";
		HangmanGame hangmanGame = new HangmanGame(); //creates a game
		hangmanGame.runGame(words); //calls the method to run the game
	}
	
}