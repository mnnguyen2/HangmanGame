package dictionary;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class DictionaryReaderTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testReadParseDictionary() {
		String filename = "words_unclean.txt";
		ArrayList<String> words = DictionaryReader.readParseDictionary(filename);
		
		//total of 8 words with 6 words that do not meet criteria (ex. ".", "'", " ", upper case letter, digit)
		String[] a = {"happy", "good"};
		ArrayList<String> array = new ArrayList<String>(Arrays.asList(a));
		
		assertEquals(array, words);
	}
}
