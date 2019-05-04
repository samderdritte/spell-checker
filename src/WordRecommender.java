import java.io.*;
import java.util.*;

public class WordRecommender {
	private String fileName;
	private static ArrayList<String> dictionary = new ArrayList<>();
	
	public WordRecommender(String fileName) {
	    this.fileName = fileName;
	    WordDictionary dict = new WordDictionary(fileName);
	    dictionary = dict.getDictionary();
	}
	
	public String getFileName() {
		return fileName;
	}
	
    /**
     * Given a word and a list of words from a dictionary, returns the list of words in the
     * dictionary that have at least (>=) n letters in common.
     * @param word			a word to compare against the dictionary
     * @param listOfWords	a list of words from the dictionary
     * @param n				number of letters in common
     * @return				an list of words with common letters
     */
    public ArrayList<String> getWordsWithCommonLetters(String word, ArrayList<String> listOfWords, int n){
    	word = word.toLowerCase();
    	//initialize an array we will use for the return values
	    ArrayList<String> wordsWithCommonLetters = new ArrayList<>();

	    //remove duplicate characters from input word
	    String wordWithoutDuplicate = "";
	    for (int i=0;i<word.length();i++) {
            if(wordWithoutDuplicate.indexOf(word.charAt(i)) == -1) {
                wordWithoutDuplicate += Character.toString(word.charAt(i));
            }
	    }

	    //loop over all the words in the listOfWords
	    for (int i=0;i<listOfWords.size();i++) {

            // loop over the i-th word and remove duplicate letters
            String lookupWordWithoutDuplicate = "";
            for (int h=0;h<listOfWords.get(i).length();h++) {
                if (lookupWordWithoutDuplicate.indexOf(listOfWords.get(i).charAt(h)) == -1) {
                    lookupWordWithoutDuplicate += Character.toString(listOfWords.get(i).charAt(h));
                }
            }

            //compare the word with the input word and count number of common letters
            int countOfCommonLetters = 0;
            for (int j=0;j<wordWithoutDuplicate.length();j++) {
                if(lookupWordWithoutDuplicate.indexOf(wordWithoutDuplicate.charAt(j)) > -1) {
                    countOfCommonLetters++;
                    if (countOfCommonLetters == n) {
                        wordsWithCommonLetters.add(listOfWords.get(i));
                        break;
                    }
                }
            }
	    }
	    return wordsWithCommonLetters;
    }

    /**
    * Calculates the average similarity for two words (Strings).
    * leftSimilarity - the number of letters that match up from left to right
    * rightSimilarity - the number of letters that match up from right to left
    * @param word1
    * @param word2
    * @return (double) the average of leftSimilarity and rightSimilarity
    */
    public double getSimilarityMetric(String word1, String word2) {
    	word1 = word1.toLowerCase();
    	word2 = word2.toLowerCase();
        int leftSimilarity = 0;
        int rightSimilarity = 0;
        int overlappingChars = 0;
        int lengthDifference = word1.length()-word2.length();

        //set length of overlappingChars to avoid outOfIndex error in loops
        if (lengthDifference < 0) { //word2 is longer than word1
            overlappingChars = word2.length()-Math.abs(lengthDifference);
        } else { //word1 is longer than word2
            overlappingChars = word1.length()-Math.abs(lengthDifference);
        }
        //calculate leftSimilarity
        for(int i=0;i<overlappingChars;i++) {
            if (word1.charAt(i) == word2.charAt(i)) {
                leftSimilarity++;
            }
        }
        //calculate rightSimilarity
        for(int i=1;i<overlappingChars+1;i++) {
            if (word1.charAt(word1.length()-i) == word2.charAt(word2.length()-i)) {
                rightSimilarity++;
            }
        }
        // return the average of leftSimilarity and rightSimilarity
        return (leftSimilarity+rightSimilarity)/2.0;
    }

    /**
     * Calculates the percentage of common letters in two words.
     * Each letter from word1 is checked whether it exists in word2.
     * @param word1		the first word
     * @param word2		the second word
     * @return			the percentage of the common letters, rounded to the full percent
     */
    public static double calculateCommonPercent(String word1, String word2) {
    	word1 = word1.toLowerCase();
    	word2 = word2.toLowerCase();
    	if(word1.length() == 0 && word2.length() == 0) {
    		return 100.0;
    	}
    
    	Map<String, Integer> distinctLettersInBothWords = new HashMap<String, Integer>();
   
    	ArrayList<String> lettersWord1 = new ArrayList<>();
    	for(int i = 0;i < word1.length();i++) {
    		lettersWord1.add(word1.substring(i, i+1));
    		distinctLettersInBothWords.put(Character.toString(word1.charAt(i)), i);
    	}
    	ArrayList<String> lettersWord2 = new ArrayList<>();
    	for(int i = 0;i < word2.length();i++) {
            lettersWord2.add(word2.substring(i, i+1));
            distinctLettersInBothWords.put(Character.toString(word2.charAt(i)), i);
    	}
    	//int count = 0;
    	//System.out.println(lettersWord1);
    	//System.out.println(lettersWord2);
    
    	ArrayList<String> distinctCommonLettersInBothWords = new ArrayList<String>();
    
    	for(Map.Entry<String, Integer> entry : distinctLettersInBothWords.entrySet()){
    		if(word1.indexOf(entry.getKey()) !=  -1 && word2.indexOf(entry.getKey()) !=  -1 ) {
    			distinctCommonLettersInBothWords.add(entry.getKey());
            }
    	}
    
/*    	for(int i = 0; i < word2.length();i++) {
    		//System.out.println(lettersWord1);
    		//System.out.println(lettersWord2);
    		for(int j = 0;j < lettersWord1.size();j++) {
    			if(word2.charAt(i) == lettersWord1.get(j).charAt(0)) {
    				//System.out.println("XX: "+lettersWord2.get(i));
                    lettersWord1.remove(j);                              
                    count++;
    			}
    		}
    	}*/
    	//return rounded percentage
    	//return Math.round(count / (double) word1.length()*100);
    	return Math.round(distinctCommonLettersInBothWords.size() / (double) distinctLettersInBothWords.size()*100);
    }

    /**
     * Returns a list with suggested words from the dictionary.
     * @param word				the candidate word to check against the dictionary
     * @param n					length of suggestions +/- n of the length of the candidate word
     * @param commonPercent		limits the suggestion to a certain percentage of common letters
     * @param topN				the number of suggestions to return
     * @return					a list of suggested words
     */
    public ArrayList<String> getWordSuggestions(String word, int n, double commonPercent, int topN){
    	word = word.toLowerCase();
    	
        ArrayList<String> suggestions = new ArrayList<>();
        suggestions.clear();
        //go through every word in the the dictionary
        for (int i=0;i<dictionary.size();i++) {
            //filter words whose length is +/- n
            if(Math.abs(dictionary.get(i).length() - word.length()) < n+1) {
                // and word has at least commonPercent of letters in common
                if(calculateCommonPercent(dictionary.get(i), word) >= commonPercent*100) {
                    //take this word, compare it to the other words in suggestions and add it into the array
                    // if the list of suggestions is empty, just add the word
                	if(suggestions.size() == 0) {
                        suggestions.add(dictionary.get(i));
                    // otherwise insert the word at the correct spot
                	} else {
                        boolean wordAdded = false;
                		for(int j=0;j<suggestions.size();j++) {
                            if(getSimilarityMetric(suggestions.get(j),word) < getSimilarityMetric(dictionary.get(i),word)) {
                                //insert the current word of the dictionary at position j of the suggestions
                                suggestions.add(j, (dictionary.get(i)));
                                wordAdded = true;
                                break;
                        	}
                        }
                		if (!wordAdded) {
                			suggestions.add(dictionary.get(i));
                		}

                    }
                }
            }
        }
        
        //return the topN elements of the found array
        ArrayList<String> returnList = new ArrayList<>();

        int returnCount = 0;
        if (topN < suggestions.size()) {
                    returnCount = topN;
        } else {
                    returnCount = suggestions.size();
        }
        for (int i=0;i<returnCount;i++) {
                    returnList.add(suggestions.get(i));
        }
        return returnList;
    }

    /**
    * Takes an ArrayList of words and outputs them nicely formatted
    * @param list
    * @return String of nicely formatted words
    */
    public String prettyPrint(ArrayList<String> list) {
	    int numberOfStrings = list.size();
	    String prettyText = "";
	    for (int i=0;i<numberOfStrings;i++) {
            prettyText += String.valueOf(i+1) + ". " + list.get(i) + "\n";
	    }

	    return prettyText;
    }

    /**
	 * This method validates the user input until a valid input is given.
	 * The method asks for possible inputs and repeats until one of the two inputs are given.
	 * Additionally
	 * @param options			the accepted inputs as array
	 * @param validationText	the message which is displayed before the user prompt
	 * @return userAnswer		the selected option as String
	 */
	public String validateInputOptions(String[] options, String validationText) {
		String userAnswer = "";
		List<String> list = Arrays.asList(options);
		Scanner s = new Scanner(System.in);
		boolean inputValidation = false;
		boolean firstRound = false;
		while (!inputValidation) {

            while (!list.contains(userAnswer)) {
            	if(firstRound) {
            		System.out.println("This is not a valid option.");
            	}
                System.out.println(validationText);
                userAnswer = s.next();
                firstRound = true;
            }
            inputValidation = true;
		}


		return userAnswer;
	}

	/**
	 * Write a word to the output file, optionally followed by a blank space.
	 *
	 * @param wordToWrite			The word which you want to add to the output file.
	 * @param fileToWriteTo			Name of the file to which you want to write.
	 * @param blackSpaceAfterWord	true if a blank space should be inserted after the word.
	 */
	public void writeStringToFile(String wordToWrite, String fileToWriteTo, boolean blankSpaceAfterWord) {
        try {
            FileWriter fw = new FileWriter(fileToWriteTo,true);
            PrintWriter pw = new PrintWriter(fw);
            if(blankSpaceAfterWord) {
            	pw.print(wordToWrite+" ");
            } else {
            	pw.print(wordToWrite);
            }
            pw.flush();
            pw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public static void main(String[] args) {
		WordRecommender wordRec = new WordRecommender("engDictionary.txt");
		
		String incorrectWord = "starf";
		// Adds all of these to a HashSet
		String expectedWords[] = {"staff", "stars", "afars", "start", "staffs", "star", "staffer", "starts"};
		HashSet<String> answers = new HashSet<>();
		for( String str : expectedWords ) {
			answers.add(str);
		}
		System.out.println(answers);
		ArrayList<String> actuals = wordRec.getWordSuggestions(incorrectWord, 2, 0.80, 8);
		
		System.out.println(actuals);
		System.out.println(answers.contains("staff"));
		
		System.out.println(wordRec.getWordSuggestions("woman", 0, 1, 50));
		/*
		System.out.println(wordRec.getSimilarityMetric("woman", "woodman"));
		System.out.println(wordRec.getSimilarityMetric("woman", "bowman"));
		System.out.println(wordRec.getSimilarityMetric("woman", "woman’s"));
		System.out.println(wordRec.getSimilarityMetric("woman", "womanly"));
		System.out.println(wordRec.getSimilarityMetric("woman", "workman"));
		System.out.println(wordRec.calculateCommonPercent("woman", "woodman"));
		System.out.println(wordRec.calculateCommonPercent("woman", "bowman"));
		System.out.println(wordRec.calculateCommonPercent("woman", "woman’s"));
		System.out.println(wordRec.calculateCommonPercent("woman", "womanly"));
		System.out.println(wordRec.calculateCommonPercent("woman", "workman"));
		System.out.println(wordRec.calculateCommonPercent("it", "ir"));
		System.out.println(wordRec.getWordSuggestions("errrors",3 ,0.70 ,4));
		System.out.println(wordRec.getWordSuggestions("tetx",3 ,0.70 ,4));
		*/
	
	} 
}
