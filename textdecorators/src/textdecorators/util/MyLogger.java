package textdecorators.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class MyLogger {
	public static enum DebugLevel { FILE_PROCESSOR, NONE, SENTENCE_DECORATOR, SPELL_CHECK_DECORATOR, MOST_FREQUENT_DECORATOR, KEYWORD_DECORATOR, INPUT_DETAILS };

    private static DebugLevel debugLevel;


    // FIXME: Add switch cases for all the levels
    public static void setDebugValue (int levelIn) {
	switch (levelIn) {
	case 6: debugLevel = DebugLevel.INPUT_DETAILS; break;
	case 5: debugLevel = DebugLevel.KEYWORD_DECORATOR; break;
	case 4: debugLevel = DebugLevel.SENTENCE_DECORATOR; break;
	case 3: debugLevel = DebugLevel.SPELL_CHECK_DECORATOR; break;
	case 2: debugLevel = DebugLevel.MOST_FREQUENT_DECORATOR; break;
	case 1: debugLevel = DebugLevel.FILE_PROCESSOR; break;
	default: debugLevel = DebugLevel.NONE; break;
	}
    }

    
    /**
	 * set debug value
	 *
	 * @return 	NULL
	 * 
	 */
    public static void setDebugValue (DebugLevel levelIn) {
	debugLevel = levelIn;
    }

    
    /**
	 * writes given message to console
	 *
	 * @return 	NULL
	 * 
	 */
    public static void writeMessage (String message, DebugLevel levelIn ) {
    	if (levelIn == debugLevel) {
    		BufferedWriter writer = null;
    		try {
    			writer = new BufferedWriter(new FileWriter("log.txt", false));
    			writer.write(message);
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
    }

    /**
     * Default toString, needed for debugging here.
     * @return String
     */
    
    public String toString() {
	return "The debug level has been set to the following " + debugLevel;
    }
}
