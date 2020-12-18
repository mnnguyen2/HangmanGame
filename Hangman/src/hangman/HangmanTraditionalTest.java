package hangman;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dictionary.DictionaryReader;

/**
 * Unit testing for HangmanTraditional
 * @author mnguyen
 *
 */
class HangmanTraditionalTest {

	private HangmanTraditional hangmanTraditional;
	
    ArrayList<String> words = new ArrayList<String>();
    String fileName;
    
    // Helper method to set up game for testing
    void setUpHangman(String fileName) {
        
    	// get our list of words to use during the game
        words = DictionaryReader.readParseDictionary(fileName);
        
        // create an instance of the traditional hangman game
        this.hangmanTraditional = new HangmanTraditional(words);
    }

    @Test
    void testChooseWord() {
    	
		//sets up hangman game
		this.setUpHangman("words_clean.txt");
		//1. let hangman game choose word
		this.hangmanTraditional.chooseWord();
		
		// test to make sure chosen word length = correct letters size
		assertEquals(this.hangmanTraditional.chosenWord.length(), this.hangmanTraditional.correctLetters.size()); 
		
		//2. choose a second word
		this.hangmanTraditional.chooseWord();
		
		// check that the length of word and correct letters size are updated accordingly
		assertEquals(this.hangmanTraditional.chosenWord.length(), this.hangmanTraditional.correctLetters.size());
	} 

    @Test
    void testFindAndMarkLetter() {
		//sets up hangman game
		this.setUpHangman("words_clean.txt");
		
		//sets chosenWord
		this.hangmanTraditional.setWord("testword");
		
		assertFalse(this.hangmanTraditional.findAndMarkLetter("y")); // "y" is not in "testword"
		assertEquals("y", this.hangmanTraditional.incorrectGuesses.get(0)); //tests storing of incorrect guess
		
		assertTrue(this.hangmanTraditional.findAndMarkLetter("w")); //tests correct guess
		assertTrue(this.hangmanTraditional.findAndMarkLetter("t")); //tests correct guess
		assertFalse(this.hangmanTraditional.findAndMarkLetter(" ")); //tests incorrect input
		assertFalse(this.hangmanTraditional.findAndMarkLetter("0")); //tests incorrect input
		assertFalse(this.hangmanTraditional.findAndMarkLetter("-")); //tests incorrect input
		assertFalse(this.hangmanTraditional.findAndMarkLetter("'")); //tests incorrect input
		assertFalse(this.hangmanTraditional.findAndMarkLetter("qw")); //tests incorrect input
		
		assertEquals("t", this.hangmanTraditional.correctLetters.get(0)); //guessed this position correctly
		assertEquals("_", this.hangmanTraditional.correctLetters.get(1)); //haven't guessed this position yet
		assertEquals("t", this.hangmanTraditional.correctLetters.get(3)); //guessed this position correctly
    }
    
	@Test
	void testPrintVersion(){
		//sets up hangman game
		this.setUpHangman("words_clean.txt");
		
		//sets version output to variable
		String gameVersion = this.hangmanTraditional.printVersion();
		
		assertEquals("Hangman Traditional", gameVersion); //tests correct version output
	}
    
	@Test
	void testAlreadyGuessed() {
		
		//sets up hangman game
		this.setUpHangman("words_clean.txt");
		
		//sets chosenWord
		this.hangmanTraditional.setWord("testword");
		
		assertFalse(this.hangmanTraditional.alreadyGuessed("t")); //nothing has been guessed yet
		
		this.hangmanTraditional.findAndMarkLetter("t"); //guess "t"
		assertTrue(this.hangmanTraditional.alreadyGuessed("t")); //"t" has been guessed
		
		assertFalse(this.hangmanTraditional.alreadyGuessed("e")); //"e" has not been guessed yet
		
		this.hangmanTraditional.findAndMarkLetter("e"); //guess "e"
		this.hangmanTraditional.findAndMarkLetter("s"); //guess "s"
		assertTrue(this.hangmanTraditional.alreadyGuessed("e")); //"e" has been guessed
		assertTrue(this.hangmanTraditional.alreadyGuessed("s")); //"e" has not been guessed
	}

	@Test
	void testCheckLetters(){
		
		//sets up hangman game
		this.setUpHangman("words_clean.txt");
		
		//sets chosenWord
		this.hangmanTraditional.setWord("testword");
		
		this.hangmanTraditional.findAndMarkLetter("t"); //guess "t"
		this.hangmanTraditional.findAndMarkLetter("e"); //guess "e"
		this.hangmanTraditional.findAndMarkLetter("s"); //guess "s"
		this.hangmanTraditional.findAndMarkLetter("w"); //guess "w"
		this.hangmanTraditional.findAndMarkLetter("o"); //guess "o"
		this.hangmanTraditional.findAndMarkLetter("r"); //guess "r"
		
		assertFalse(this.hangmanTraditional.checkLetters()); //not every letter has been guessed correctly yet
		
		this.hangmanTraditional.findAndMarkLetter("d"); //guess "d"
		
		assertTrue(this.hangmanTraditional.checkLetters()); //every letter has now been guessed correctly
	}
	
	@Test
	void testGetNumberGuesses() {
		
		//sets up hangman game
		this.setUpHangman("words_clean.txt");
				
		//sets chosenWord
		this.hangmanTraditional.setWord("testword");	
		
		this.hangmanTraditional.findAndMarkLetter("t"); //guess "t"
		assertEquals(1, this.hangmanTraditional.getNumberGuesses()); //1 guess made so far
		
		this.hangmanTraditional.findAndMarkLetter("e"); //guess "e" 
		this.hangmanTraditional.findAndMarkLetter("s"); //guess "s"
		this.hangmanTraditional.findAndMarkLetter("w"); //guess "w"
		assertEquals(4, this.hangmanTraditional.getNumberGuesses()); //4 guesses made so far
		
		this.hangmanTraditional.findAndMarkLetter("l"); //guess "l"
		this.hangmanTraditional.findAndMarkLetter("p"); //guess "p"
		assertEquals(6, this.hangmanTraditional.getNumberGuesses()); //6 guesses made so far
		
		this.hangmanTraditional.findAndMarkLetter("o"); //guess "o"
		this.hangmanTraditional.findAndMarkLetter("r"); //guess "r"
		assertEquals(8, this.hangmanTraditional.getNumberGuesses()); //8 guesses made so far
	}
	
	@Test
	void testGetNumberIncorrectGuesses() {
		
		//sets up hangman game
		this.setUpHangman("words_clean.txt");
				
		//sets chosenWord
		this.hangmanTraditional.setWord("testword");	
		
		this.hangmanTraditional.findAndMarkLetter("l"); //guess "t"
		assertEquals(1, this.hangmanTraditional.getNumberIncorrectGuesses()); //1 incorrect guess made so far
		
		this.hangmanTraditional.findAndMarkLetter("e"); //guess "e" 
		this.hangmanTraditional.findAndMarkLetter("s"); //guess "s"
		this.hangmanTraditional.findAndMarkLetter("w"); //guess "w"
		assertEquals(1, this.hangmanTraditional.getNumberIncorrectGuesses()); //1 incorrect guesses made so far
		
		this.hangmanTraditional.findAndMarkLetter("l"); //guess "l"
		this.hangmanTraditional.findAndMarkLetter("p"); //guess "p"
		assertEquals(2, this.hangmanTraditional.getNumberIncorrectGuesses()); //2 incorrect guesses made so far
		//line above also tests an identical wrong guess being made twice (second guess should not be counted overall)
		
		this.hangmanTraditional.findAndMarkLetter("o"); //guess "o"
		this.hangmanTraditional.findAndMarkLetter("r"); //guess "r"
		assertEquals(2, this.hangmanTraditional.getNumberIncorrectGuesses()); //2 incorrect guesses made so far
	}
	
	@Test
	void testGetCorrectLetters() {

		//sets up hangman game
		this.setUpHangman("words_clean.txt");
				
		//sets chosenWord
		this.hangmanTraditional.setWord("testword");	
		
		assertEquals("________", this.hangmanTraditional.getCorrectLetters()); //show nothing, no guesses yet
		
		this.hangmanTraditional.findAndMarkLetter("t"); //guess "t"
		assertEquals("t__t____", this.hangmanTraditional.getCorrectLetters()); //show "t"
		
		this.hangmanTraditional.findAndMarkLetter("e"); //guess "e" 
		this.hangmanTraditional.findAndMarkLetter("s"); //guess "s"
		this.hangmanTraditional.findAndMarkLetter("w"); //guess "w"
		assertEquals("testw___", this.hangmanTraditional.getCorrectLetters()); //show "t" "e" "s" "w"
		
		this.hangmanTraditional.findAndMarkLetter("l"); //guess "l"
		this.hangmanTraditional.findAndMarkLetter("p"); //guess "p"
		assertEquals("testw___", this.hangmanTraditional.getCorrectLetters());  //show "t" "e" "s" "w"
		
		this.hangmanTraditional.findAndMarkLetter("o"); //guess "o"
		this.hangmanTraditional.findAndMarkLetter("r"); //guess "r"
		assertEquals("testwor_", this.hangmanTraditional.getCorrectLetters()); 	//show "t" "e" "s" "w" "o" "r"
		
		this.hangmanTraditional.findAndMarkLetter("d"); //guess "d"
		assertEquals("testword", this.hangmanTraditional.getCorrectLetters()); 	//show "t" "e" "s" "w" "o" "r" "d"
	}
	
	@Test
	void testGetIncorrectGuesses() {
	
		//sets up hangman game
		this.setUpHangman("words_clean.txt");
				
		//sets chosenWord
		this.hangmanTraditional.setWord("testword");	
		
		this.hangmanTraditional.findAndMarkLetter("t"); //guess "t"
		assertEquals("", this.hangmanTraditional.getIncorrectGuesses()); //1 guess made so far
		
		this.hangmanTraditional.findAndMarkLetter("e"); //guess "e" 
		this.hangmanTraditional.findAndMarkLetter("l"); //guess "l"
		this.hangmanTraditional.findAndMarkLetter("w"); //guess "w"		
		this.hangmanTraditional.findAndMarkLetter("l"); //guess "l"
		this.hangmanTraditional.findAndMarkLetter("p"); //guess "p"
		this.hangmanTraditional.findAndMarkLetter("o"); //guess "o"
		this.hangmanTraditional.findAndMarkLetter("r"); //guess "r"
		this.hangmanTraditional.findAndMarkLetter("p"); //guess "p" (duplicate incorrect guess)
		this.hangmanTraditional.findAndMarkLetter("l"); //guess "l" (duplicate incorrect guess)
		this.hangmanTraditional.findAndMarkLetter("k"); //guess "k"
		this.hangmanTraditional.findAndMarkLetter("m"); //guess "m"
		
		StringBuilder incorrects = new StringBuilder(); //create new StringBuilder
		incorrects.append("[l, p, k, m]"); //all the incorrect guesses
		String str = incorrects.toString(); //turn the StringBuilder into a string
		assertEquals(str, this.hangmanTraditional.getIncorrectGuesses()); //should contain 2 incorrect guesses
	}
}

