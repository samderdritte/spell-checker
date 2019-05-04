import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileChecker {	
	/**
	 * This method asks the user, which file he wants to scan.
	 * The user has to type a filename including file extension.
	 * The file must be found in the root directory of the project.
	 * A validation is thrown, if there is no matching file in the directory.
	 * @return fileToScan	The file which the user wants to scan
	 */
	public static File scanFile() {
		System.out.println("Please enter the name of a file which you want to load: ");
		Scanner s = new Scanner(System.in);
	    String fileName = s.next();

	    File fileToScan = new File(fileName);
	    
		while(!fileToScan.exists()) {

			fileToScan = new File(fileName);

		    if (!fileToScan.exists()) {
		    	System.out.println("File not found. Please type the name of a file in the root directory of the java project.");
		    	fileName = s.next();
		    }
		}
		return fileToScan;
	}
	
	/**
	 * Extracts the fileExtension from a given fileName.
	 * The function extracts the extension from the last dot (.) of the filename.
	 * @param fileName				A filename with file extension.
	 * @return fileNameExtension	The file extension including dot (.)
	 */
	public static String extractFileExtension(String fileName) {
		String fileExtension = "";
	    for(int i=fileName.length()-1;i>0;i--) {
            if(Character.toString(fileName.charAt(i)).contains(".")) {
                fileExtension = fileName.substring(i, fileName.length());
            }
	    }
	    return fileExtension;
	}
	
	/**
	 * Extracts the file name without extension from a given fileName.
	 * Extracts everything until (not including) the last dot (.) of the filename.
	 * @param fileName		A filename with file extension.
	 * @return fileNameBody	The body of the filename without extension.
	 */
	public static String extractFileNameWithoutExtension(String fileName) {
		String fileNameBody = "";
	    for(int i=fileName.length()-1;i>0;i--) {
            if(Character.toString(fileName.charAt(i)).contains(".")) {
                fileNameBody = fileName.substring(0, i);
            }
	    }
	    return fileNameBody;
	}

	/**
	 * Creates a new file with a given fileName.
	 * The new file will be in the root directory of the project.
	 * If a file of the same name already exists, it will be overwritten.
	 * @param fileName	The file name of the new file.
	 */
	public static void createNewFile(String fileName) {
		File fileChecked = new File(fileName);
	    try {
	    	//make sure that the corrected file is always empty at start
	    	if(fileChecked.exists()) {
	    		fileChecked.delete();
	    	}
	    	fileChecked.createNewFile();
	    } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	    }
	}
}