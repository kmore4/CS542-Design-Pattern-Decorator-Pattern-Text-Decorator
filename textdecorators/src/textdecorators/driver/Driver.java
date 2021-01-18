package textdecorators.driver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

import textdecorators.AbstractTextDecorator;
import textdecorators.KeywordDecorator;
import textdecorators.MostFrequentWordDecorator;
import textdecorators.SentenceDecorator;
import textdecorators.SpellCheckDecorator;
import textdecorators.util.FileDisplayInterface;
import textdecorators.util.InputDetails;
import textdecorators.util.MyLogger;
import textdecorators.validations.SameFileException;

/**
 * @author Kasturi T. More
 *
 */

/**
 * The Driver class
 */
public class Driver {
	/**
	 * An int variable to acceptable number of command line arguments
	 */
	private static final int REQUIRED_NUMBER_OF_CMDLINE_ARGS = 5;
	static int debugLevel = -1;

	public static void main(String[] args) throws InvalidPathException, SecurityException, FileNotFoundException, IOException {

		/*
		 * As the build.xml specifies the arguments as input,output or metrics, in case the
		 * argument value is not given java takes the default value specified in
		 * build.xml. To avoid that, below condition is used
		 */
		if ((args.length != 5) || (args[0].equals("${input}")) || (args[1].equals("${misspelled}")) || (args[2].equals("${keywords}")) || (args[3].equals("${output}")) || (args[4].equals("${debug}"))) {
			System.err.printf("Error: Incorrect number of arguments. Program accepts %d arguments.", REQUIRED_NUMBER_OF_CMDLINE_ARGS);
			System.exit(0);
		}
		try {
			if(args[0].equals(args[1]) || args[0].equals(args[2]) || args[0].equals(args[3])) {
				throw new SameFileException("File Path is same for 2 more files..!!");
			}
			if(args[1].equals(args[2]) || args[1].equals(args[3])) {
				throw new SameFileException("File Path is same for 2 more files..!!");
			}
			if(args[2].equals(args[3])) {
				throw new SameFileException("File Path is same for 2 more files..!!");
			}
		} catch(SameFileException e) {
			e.printStackTrace();
			System.exit(1);
		}
		debugLevel = Integer.parseInt(args[4]);
		
		MyLogger.setDebugValue(debugLevel);
		
		InputDetails inputD = new InputDetails(args[0], args[3]);
		AbstractTextDecorator sentenceDecorator = new SentenceDecorator(null, inputD);
		AbstractTextDecorator spellCheckDecorator = new SpellCheckDecorator(sentenceDecorator, inputD);
		AbstractTextDecorator keywordDecorator = new KeywordDecorator(spellCheckDecorator, inputD);
		AbstractTextDecorator mostFreqWordDecorator = new MostFrequentWordDecorator(keywordDecorator, inputD);
		inputD.setKeywordFile(args[2]);
		inputD.setmispelledFile(args[1]);
		mostFreqWordDecorator.processInputDetails();	
		
		((FileDisplayInterface) inputD).writeToFile();
	}
}


