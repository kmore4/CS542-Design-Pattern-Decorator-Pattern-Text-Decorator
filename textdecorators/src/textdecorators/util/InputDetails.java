package textdecorators.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

import textdecorators.util.MyLogger.DebugLevel;
import textdecorators.validations.EmptyFileException;
import textdecorators.validations.FileNotExistException;

public class InputDetails implements FileDisplayInterface{

	private String inputFile,outputFile;
	private List<String> sentences = new ArrayList<String>();
	private List<String> keywords = new ArrayList<String>();
	private List<String> misspells = new ArrayList<String>();
	private String keywordFile,mispelledFile;
	private String outputString;
	private DebugLevel debugLevel;

	/**
	 * InputDetails constructor sets inputFile and outputFile
	 * and calls myLogger
	 *
	 * @param	String	inputFilenameIn
	 * @param	String	outputFilenameIn
	 * @return 	NULL
	 * 
	 */
	public InputDetails(String inputFilenameIn, String outputFilenameIn) {
		inputFile = inputFilenameIn;
		outputFile = outputFilenameIn;
		
		this.debugLevel = DebugLevel.INPUT_DETAILS;
		MyLogger.writeMessage("in InputDetails class constructor", debugLevel);
	}
	
	
	/**
	 * set outputString
	 * 
	 * @return	NULL
	 * 
	 */
	public void setOutputString(String s) {
		outputString = s;
	}
	
	
	/**
	 * get outputString
	 * 
	 * @return	String
	 * 
	 */
	public String getOutputString() {
		return outputString;
	}
	
	
	/**
	 * set keywordFile
	 * 
	 * @return	NULL
	 * 
	 */
	public void setKeywordFile(String name) {
		keywordFile = name;
	}
	
	
	/**
	 * set mispelledFile
	 * 
	 * @return	NULL
	 * 
	 */
	public void setmispelledFile(String name) {
		mispelledFile = name;
	}
	
	
	/**
	 * get outputFile
	 * 
	 * @return	String
	 * 
	 */
	public String getOutputFileName() {
		return outputFile;
	}
	
	
	/**
	 * get list of keywords extracted from keyword file
	 * 
	 * @return	List<String>
	 * 
	 */
	public List<String> getKeywords() {
		return keywords;
	}
	
	
	/**
	 * get list of misspelled words extracted from misspell file
	 * 
	 * @return	List<String>
	 * 
	 */
	public List<String> getMisspells() {
		return misspells;
	}
	
	
	/**
	 * get list of sentences extracted from input file
	 * 
	 * @return	List<String>
	 * 
	 */
	public List<String> getSentences() {
		return sentences;
	}
	
	
	/**
	 * check if given file exist or not
	 *
	 * @param	String		filename
	 * @return 	Boolean
	 * 
	 */
	private static boolean checkIfFileExists(String inputfile) {
		if (inputfile == null)
			return false;
		File file = new File(inputfile);
		return file.exists();
	}
	
	
	/**
	 * File existence checking
	 *
	 * @param	String		filename
	 * @return 	NULL
	 * @exception FileNotExistException
	 */
	public void checkFile(String string) throws FileNotExistException {
		try {
			if(!checkIfFileExists(string)){
				throw new FileNotExistException(string + " does not exist.!");
			}
		} catch (FileNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}	
	}

	
	/**
	 * reads input file and stores each line from input file to a 
	 * list<String>
	 *
	 * @return 	NULL
	 * @exception EmptyFileException, FileNotExistException
	 * 
	 */
	public void readFile() throws InvalidPathException, SecurityException, FileNotFoundException, IOException {
		String line = "";
		int count = 0;
		try {
		checkFile(inputFile);
		FileProcessor fp = new FileProcessor(inputFile);
		
			
			while ((line = fp.poll()) != null)
			{
				if(line.isEmpty()) {
					sentences.add("newLine");
				}
				else {
					count++;
					sentences.add(line);
				}		
			}
			
			if(count == 0) {
				throw new EmptyFileException("Input File is Empty..!!");
			}
			fp.close();
		} catch (EmptyFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);	
		} catch (FileNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	/**
	 * reads keywordFile and stores each word to a list<String>
	 *
	 * @return 	NULL
	 * @exception EmptyFileException, FileNotExistException
	 * 
	 */
	public void readKeywordFile() throws InvalidPathException, SecurityException, FileNotFoundException, IOException {
		String line = "";
		int count = 0;
		try {
		checkFile(keywordFile);
		FileProcessor fp = new FileProcessor(keywordFile);
		
			
			while ((line = fp.poll()) != null)
			{
				count++;
				keywords.add(line);
			}
			
			if(count == 0) {
				throw new EmptyFileException("Keywords File is Empty..!!");
			}
			fp.close();
		} catch (EmptyFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);	
		} catch (FileNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
	/**
	 * reads misspelledFile and stores each word to a list<String>
	 *
	 * @return 	NULL
	 * @exception EmptyFileException, FileNotExistException
	 * 
	 */
	public void readMisspelldFile() throws InvalidPathException, SecurityException, FileNotFoundException, IOException {
		String line = "";
		int count = 0;
		try {
		checkFile(mispelledFile);
		FileProcessor fp = new FileProcessor(mispelledFile);
		
			
			while ((line = fp.poll()) != null)
			{
				count++;
				misspells.add(line);
			}
			
			if(count == 0) {
				throw new EmptyFileException("Misspelled words File is Empty..!!");
			}
			fp.close();
		} catch (EmptyFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);	
		} catch (FileNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Writes generated output to output.txt
	 *
	 * @param  null
	 * @return null
	 */
	@Override
	public void writeToFile() {
		// TODO Auto-generated method stub
		BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(outputFile, false));
            writer.write(outputString);
            writer.close();
            
        } catch(IOException e) {
            System.err.println("IO Exception: Filename was not a proper name.");
            System.exit(1);
        } finally {
            try {
                writer.close();
            } catch(IOException e) {
                System.err.println("BufferedWriter not found.");
                System.exit(1);
            }
        }
	}
	
	
	/**
     * Default toString, needed for debugging here.
     * 
     * @return String
     */
    public String toString() {
        return outputString;
    }
}
