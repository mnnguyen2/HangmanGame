package hangman;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dictionary.DictionaryReader;

/**
 * Unit testing for HangmanEvil class.
 * Name: Mai Nguyen
 * 
 */
class HangmanEvilTest {
	
	HangmanEvil hangmanEvil;
	
	//HELPER METHOD FOR TESTING ONLY: DOES NOT AFFECT GAME PLAY
	void setUpHangman(String fileName) {
		//get the list of words
		ArrayList<String> words = DictionaryReader.readParseDictionary(fileName);
		
		//create traditional hangman
		this.hangmanEvil = new HangmanEvil(words);
	}

	@Test
	void testChooseWord() {
		//sets up hangman game
		this.setUpHangman("words_test_5letters.txt");
		
		this.hangmanEvil.printWordList();
		this.hangmanEvil.printWord();
		
		//chosen word length will be 5 as train has 5 letters
		assertEquals("_____", this.hangmanEvil.chooseWord("train"));
		assertEquals(5, this.hangmanEvil.chosenWord.length());
		assertEquals(22, this.hangmanEvil.vocabulary.size());
		
		//chosen word length will be 0 as empty string has no letters
		assertEquals("", this.hangmanEvil.chooseWord(""));
		assertEquals(0, this.hangmanEvil.chosenWord.length());
		assertEquals(0, this.hangmanEvil.vocabulary.size());
		
		//chosen word length will be 3 as air has 3 letters
		assertEquals("___", this.hangmanEvil.chooseWord("air"));
		assertEquals(3, this.hangmanEvil.chosenWord.length());
		assertEquals(0, this.hangmanEvil.vocabulary.size());
	}

	@Test
	void testFindAndMarkLetter() {
		//sets up hangman game
		this.setUpHangman("words_test_5letters.txt");
		
		assertEquals("_____", this.hangmanEvil.chooseWord("train"));
		assertEquals(5, this.hangmanEvil.chosenWord.length());
		assertEquals(22, this.hangmanEvil.vocabulary.size());
		
		this.hangmanEvil.printWordList();
		this.hangmanEvil.printWord();
		
		//key of words without the letter "a" is greater than the 
		//largest group with "a" in the same index position
		//remaining words list will be words without the letter "a"
		//method returns false
		String[] a = {"_", "_", "_", "_", "_"};
		ArrayList<String> array = new ArrayList<String>(Arrays.asList(a));
		assertFalse(this.hangmanEvil.findAndMarkLetter("a"));
		assertEquals(array, this.hangmanEvil.correctLetters);
		assertEquals(9, this.hangmanEvil.vocabulary.size());
		assertFalse(this.hangmanEvil.correctLetters.contains("a"));
		assertTrue(this.hangmanEvil.incorrectGuesses.contains("a"));
		
		//key of words with letter "s" (at the end index) make up the largest group
		//remaining words list will be words ending with the letter "s"
		//method returns true
		String[] b = {"_", "_", "_", "_", "s"};
		array = new ArrayList<String>(Arrays.asList(b));
		assertTrue(this.hangmanEvil.findAndMarkLetter("s"));
		assertEquals(array, this.hangmanEvil.correctLetters);
		assertEquals(5, this.hangmanEvil.vocabulary.size());
		assertTrue(this.hangmanEvil.correctLetters.contains("s"));
		assertFalse(this.hangmanEvil.incorrectGuesses.contains("s"));
		
		//there are no words with the letter "z", nothing has changed in remaining words
		//remaining words list will be words ending with the letter "s"
		//method returns false
		String[] c = {"_", "_", "_", "_", "s"};
		array = new ArrayList<String>(Arrays.asList(c));
		assertFalse(this.hangmanEvil.findAndMarkLetter("z"));
		assertEquals(array, this.hangmanEvil.correctLetters);
		assertEquals(5, this.hangmanEvil.vocabulary.size());
		assertFalse(this.hangmanEvil.correctLetters.contains("z"));
		assertTrue(this.hangmanEvil.incorrectGuesses.contains("z"));
	
		//there are no words with the letter "az", and it is illegal with length more than 1 
		//method returns false, nothing has changed in remaining words
		//remaining words list will be words ending with the letter "s"
		assertFalse(this.hangmanEvil.findAndMarkLetter("az"));
		assertEquals(array, this.hangmanEvil.correctLetters);
		assertEquals(5, this.hangmanEvil.vocabulary.size());
		assertFalse(this.hangmanEvil.correctLetters.contains("z"));
		assertTrue(this.hangmanEvil.incorrectGuesses.contains("z"));
	}
	
	@Test
	void testPrintVersion(){
		
		//sets up hangman game
		this.setUpHangman("words_test_5letters.txt");
		
		//sets version output to variable
		String gameVersion = this.hangmanEvil.printVersion();
		
		assertEquals("Hangman Evil", gameVersion); //tests correct version output
	}

}
