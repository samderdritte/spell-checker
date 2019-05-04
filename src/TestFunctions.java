import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class TestFunctions {
	
	/**
	 * This function validates the user input until a valid input is given.
	 * The function asks for possible inputs and repeats until one of the two inputs are given.
	 * Additionally
	 * @param options			the accepted inputs as array
	 * @param validationText	the message which is displayed before the user prompt
	 * @return userAnswer		the selected option as String
	 */
	public static String validateInputOptions(String[] options, String validationText) {
		String userAnswer = "";
		List<String> list = Arrays.asList(options);
		Scanner s = new Scanner(System.in);
		boolean inputValidation = false;
		while (!inputValidation) {
            
            while (!list.contains(userAnswer)) {
                if(!list.contains(userAnswer)) {
                            System.out.println(validationText);
                            userAnswer = s.next();
                }
            }
            inputValidation = true;
		}
		
		
		return userAnswer;
	}
	
	public static File selectFile() {
		System.out.println("Please enter the name of a file in the root directory.");
		Scanner sc = new Scanner(System.in);
	    File fileToScan = new File("");
	    
		while(!fileToScan.exists()) {
			String fileName = sc.next();
			   
			fileToScan = new File(fileName);
		    
		    if (fileToScan.exists()) {
		    	System.out.println("File Exists");
		    } else{
		    	System.out.println("File not found. Please type the name of a file in the root directory.");
		    }
		    
		}
		return fileToScan;
	    		
	}
	
	public static void main(String[] args) {
		//String userAnswer = TestFunctions.validateInputOptions(new String[]{"a", "t", "r"}, "Press 'a' for accept as is, 't' for type in manually.");
		//System.out.println(userAnswer);
		System.out.println(selectFile());
		
	}

}
