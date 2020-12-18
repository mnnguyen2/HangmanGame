package dictionary;

import java.io.*;
import java.util.*;

/**
 * Class handles file input and parses given document text.
 * @author mnguyen
 * 
 */
public class DictionaryReader {

    /**
     * Reads in file and parses the document to only store words meeting certain requirements.
     * @param filename as name of file
     * @return words as list of words
     */
    public static ArrayList<String> readParseDictionary(String filename) {

        //initialize variables
        ArrayList<String> words = new ArrayList<String>();
        FileReader fr = null;
        BufferedReader br = null;

        try {
        	
        	//initialize filereader from filename and bufferedreader
            fr = new FileReader(new File(filename));
            br = new BufferedReader(fr);

            String word;

            //while there are lines to read (not null) keep running code inside block
            while ((word = br.readLine()) != null) {

            	//trim the word for whitespace
                word = word.trim();
                
                //regex pattern to check if certain characters are in the string word
                String regex = ".*[\\s0-9A-Z.'-].*";
                
                //checks if word is not empty or contains any characters defined in regex string
                if (!word.isEmpty() && !word.matches(regex)) {
                	
                	//add word to list of words
                    words.add(word);
                    }
            }
        } catch (FileNotFoundException e) {
            //prints error message and info about which line
            e.printStackTrace();
        } catch (IOException e) {
            //prints error message and info about which line
            e.printStackTrace();
        } finally {
            //close file objects
            try {
                fr.close();
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return words;

    }
}
