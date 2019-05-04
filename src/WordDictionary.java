import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WordDictionary {
	private static ArrayList<String> dictionary = new ArrayList<>();

	public WordDictionary(String fileName) {
		File dictionaryFile = new File(fileName);
	    try {
	        Scanner s = new Scanner(dictionaryFile);
	        while (s.hasNext()) {
	            String temp = s.next();
	            dictionary.add(temp);
	        }
	    } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}

	public static ArrayList<String> getDictionary() {
		return dictionary;
	}
	
	/**
    * Checks whether a given word is in the dictionary or not.
    * Returns true if the exact string is found in the dictionary,
    * No changes are made to the input string.
    * @param wordToBeChecked	a String to be looked up in the dictionary
    * @return true if word is in dictionary, false otherwise
    */
    public static boolean wordInDictionary(String wordToBeChecked) {
	    for (int i=0;i<dictionary.size();i++) {
            if (dictionary.get(i).contentEquals(wordToBeChecked)) {
            	return true;
            }
	    }
	    return false;
    }
}
