import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellChecker {

	public static void main(String[] args) {

	    //initialize the function with the dictionary file
	    WordRecommender wordRec = new WordRecommender("engDictionary.txt");
	    // set variables for user input validation
	    String[] optionsATR = new String[]{"a", "t", "r"};
	    String[] optionsAT = new String[]{"a", "t"};
	    String optionTextATR = "Press 'r' for replace, 'a' for accept as is, 't' for type in manually.";
	    String optionTextAT = "Press 'a' for accept as is, 't' for type in manually.";
	    String validationNumbers = "Your word will now be replaced with one of the suggestions.\n" +
	    		"Enter the number corresponding to the word that you want to use for replacement.";

	    //ask user for file input
	    System.out.println("Welcome to the spell checker. \n\n"
	    		+ "This program scans english documents and does a spell check. \n"
	    		+ "If a word in the text is not found in the dictionary, you will provided with"
	    		+ "a suggestion of similar words. \nAlternatively, you can type your on correction, or leave the word as is."
	    		+ "\nWords written all in upppercase letters (like MCIT, IBM), will be ignored.\n");

	    // scan the file 
	    File fileToScan = FileChecker.scanFile();
	    System.out.print(fileToScan.getName()+ " found. Scanning document for errors");

    	// delay the print out of the point in order to simulate a scanning process
    	try {
			Thread.sleep(1000);
			System.out.print(".");
			Thread.sleep(1000);
			System.out.print(".");
			Thread.sleep(1000);
			System.out.println(".\n");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    //save name of the selected file
	    String inputFileName = fileToScan.getName();

	    //extract file body & file extension
	    String fileNameExtension = FileChecker.extractFileExtension(inputFileName);
	    String fileNameBody = FileChecker.extractFileNameWithoutExtension(inputFileName);

	    //create a new file name for the checked version
	    String outputFileName = fileNameBody + "_chk" + fileNameExtension;
	    FileChecker.createNewFile(outputFileName);

	    // scan the document which you want to spell check
	    try {
    		Scanner s = new Scanner(fileToScan);

	        //scan the document line by line
    		int lineNumber = 0;
	        while(s.hasNextLine()) {
	           String line = s.nextLine();
	           lineNumber++;

	           //scan the line word by word
	           Scanner s2 = new Scanner(line);
	           int wordNumber = 0;
	           while(s2.hasNext()) {
	        	   String word = s2.next();
	        	   wordNumber++;
	        	   //check if word is all upper case or if word is in the dictionary
                   if(word.contentEquals(word.toUpperCase()) || WordDictionary.wordInDictionary(word)) {
                	   //write the correct word to file
                	   wordRec.writeStringToFile(word, outputFileName, true);
                   } else {
                    	System.out.println("\nThere is an error in word " + wordNumber + " on line " + lineNumber + ":");
                        System.out.println("The word '" + word + "' is misspelled");

                        //get suggestions for spelling
                        ArrayList<String> spellingSuggestions = wordRec.getWordSuggestions(word, 3, 0.70, 4);
                        String optionMessage = "";
                        String[] optionVariant;

                        //give two options to choose if the number of suggestions is zero
                        if (spellingSuggestions.size() == 0) {
                            System.out.println("There are 0 suggestions in our dictionary for this word.");
                            optionMessage = optionTextAT;
                            optionVariant = optionsAT;

                            //give three options to choose if there are suggested words
                        } else {
                            System.out.print("The following suggestions are available.\n");
                            System.out.println(wordRec.prettyPrint(spellingSuggestions));
                            optionMessage = optionTextATR;
                            optionVariant = optionsATR;
                        }

                        //save the user's selection
                        String userSelection = wordRec.validateInputOptions(optionVariant, optionMessage);

                        // option 'r' - user chooses to replace the word
                        if (userSelection.contentEquals("r")) {

                            //try catch int & check that int is a valid number
                        	int numberOfSuggestions = spellingSuggestions.size();
                        	String[] optionsNumbers = new String[numberOfSuggestions];
                        	for (int i=0;i<numberOfSuggestions;i++) {
                        		optionsNumbers[i] = Integer.toString(i+1);
                        	}
                            int variantSelection = Integer.parseInt(wordRec.validateInputOptions(optionsNumbers, validationNumbers))-1;
			            	wordRec.writeStringToFile(spellingSuggestions.get(variantSelection), outputFileName, true);

			            //option 'a' - user chooses to accept the word as is
                        } else if (userSelection.contentEquals("a")){
			            	wordRec.writeStringToFile(word, outputFileName, true);

			            //option 't' - user wants to type a new word
			            } else if (userSelection.contentEquals("t")){
                            Scanner s3 = new Scanner(System.in);
                            System.out.println("Please type the word that will be used as the replacement in the output file. ");
                            word = s3.next();
                            wordRec.writeStringToFile(word, outputFileName, true);
			            }

                    }
                }
                //print a line break blank with no blank space
                wordRec.writeStringToFile("\n", outputFileName, false);
                s2.close();
	        }
	        s.close();

	    } catch (FileNotFoundException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
	    }

	    System.out.println("\nSpell check completed. Document has been saved as '"+outputFileName+"' "
	    		+ "in the same directory as the original file.");


	    /*
	    	wordRec.wordInDictionary("artst");

	        System.out.println(wordRec.getSimilarityMetric("oblige", "oblivion"));
	        System.out.println();
	        System.out.println(wordRec.getSimilarityMetric("aghast", "gross"));
	        System.out.println();
	        System.out.println(wordRec.getSimilarityMetric("gross", "gross"));
	        System.out.println();
	        ArrayList<String> listOfWords = new ArrayList<>();
	        listOfWords.add("biker");
	        listOfWords.add("tiger");
	        listOfWords.add("bigger");
	        System.out.println(wordRec.prettyPrint(listOfWords));

	        System.out.println();
	        ArrayList<String> listOfWordsWithCommonLetters = new ArrayList<>();
	        listOfWordsWithCommonLetters.add("ban");
	        listOfWordsWithCommonLetters.add("bang");
	        listOfWordsWithCommonLetters.add("mange");
	        listOfWordsWithCommonLetters.add("gang");
	        listOfWordsWithCommonLetters.add("cling");
	        listOfWordsWithCommonLetters.add("loo");
	        System.out.println(wordRec.getWordsWithCommonLetters("cloong", listOfWordsWithCommonLetters, 2));
	        System.out.println(wordRec.getWordsWithCommonLetters("cloong", listOfWordsWithCommonLetters, 3));
	        //System.out.println(wordRec.getWordsWithCommonLetters("artist", dictionary, 5));
	        System.out.println();
	        System.out.println("___Tests for getWordSuggestions___");
	        //ArrayList<String> returnList = wordRec.getWordSuggestions("artits", 2, 0.80, 5);
	        String word4 = "discos";
	        System.out.println("Word to look up: "+word4);
	        System.out.println(wordRec.getWordSuggestions(word4, 2, 0.80, 6));
	        ArrayList<Double> valueList = new ArrayList<>();
	        for(int i=0;i<returnList.size();i++) {
	                    valueList.add(wordRec.getSimilarityMetric(returnList.get(i), "artits"));
	        }
	        //System.out.println(valueList);
	        System.out.println();
	        //System.out.println(wordRec.calculateCommonPercent("comite", "comet"));
        */
    }
}
