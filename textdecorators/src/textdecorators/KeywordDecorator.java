package textdecorators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;

import textdecorators.util.InputDetails;
import textdecorators.util.MyLogger;
import textdecorators.util.MyLogger.DebugLevel;

public class KeywordDecorator extends AbstractTextDecorator {
	private AbstractTextDecorator atd;
	private InputDetails id;
	private DebugLevel debugLevel;
	
	
	/**
	 * KeywordDecorator constructor sets next AbstractTextDecorator 
	 * and InputDetails object 
	 *
	 * @param	AbstractTextDecorator	atdIn
	 * @param	InputDetails	idIn
	 * @return 	NULL
	 * 
	 */
	public KeywordDecorator(AbstractTextDecorator atdIn, InputDetails idIn) {
		atd = atdIn;
		id = idIn;
	}

	
	/**
	 * checks whether string has alphabets and digits
	 * calls myLogger
	 *
	 * @return 	Boolean
	 * 
	 */
	public static boolean isAlphaNumeric(String s) {
		return s != null && s.matches("^[a-zA-Z0-9]");
	}
	
	
	/**
	 * reads keyword file and process given string accordingly
	 *
	 * @return 	NULL
	 * 
	 */
	@Override
	public void processInputDetails() {
		// Decorate input details.
		try {
			id.readKeywordFile();
		} catch (InvalidPathException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String processedString = id.getOutputString();
		List <String> key = id.getKeywords();
		String[] keywords = key.toArray(new String[0]);
		
		for(int i = 0; i<keywords.length;i++) {	
			if(keywords[i].contains(".")) {
				continue;
			}
			String match6 = "MOST_FREQUENT_"+keywords[i]+"_MOST_FREQUENT";

			int index = 0;
			while(index != -1){
	            index = processedString.toLowerCase().indexOf(keywords[i].toLowerCase(), index);
	            if (index != -1) {
	            	if(index > 0 && (isAlphaNumeric(processedString.substring(index-1, index)) == true || isAlphaNumeric(processedString.substring(index+keywords[i].length(), index+keywords[i].length()+1)) == true)) {
	            		index ++;
	            		continue;
	            	}
	            	else if(index == 0 && isAlphaNumeric(processedString.substring(index+keywords[i].length(), index+keywords[i].length()+1)) == true) {
	            		index ++;
	            		continue;
	            	} 
	            	else if(index >=14 && index+keywords[i].length()+14 < processedString.length() && processedString.substring(index-14, index+keywords[i].length()+14).equalsIgnoreCase(match6)) {
	            		processedString = processedString.substring(0, index-14) + "KEYWORD_" + processedString.substring(index-14, index+keywords[i].length()+14) + "_KEYWORD" + processedString.substring(index+keywords[i].length()+14);
	                	index += keywords[i].length()+60;
	                }
	            	else if(index > 0 && index+keywords[i].length()+1<processedString.length() && isAlphaNumeric(processedString.substring(index-1, index)) == false && isAlphaNumeric(processedString.substring(index+keywords[i].length(), index+keywords[i].length()+1)) == false) {
	                	processedString = processedString.substring(0, index) + "KEYWORD_" + processedString.substring(index, index+keywords[i].length()) + "_KEYWORD" + processedString.substring(index+keywords[i].length());
	                	index += keywords[i].length()+16;
	                }
	            	else if(index > 0 && index+keywords[i].length()+1>=processedString.length() && isAlphaNumeric(processedString.substring(index-1, index)) == false) {
	            		processedString = processedString.substring(0, index) + "KEYWORD_" + processedString.substring(index, index+keywords[i].length()) + "_KEYWORD" + processedString.substring(index+keywords[i].length());
	                	index += keywords[i].length()+28;
	                }
	                else if(index == 0 && isAlphaNumeric(processedString.substring(index+keywords[i].length(), index+keywords[i].length()+1)) == false) {
	                	processedString = "KEYWORD_" + processedString.substring(index, index+keywords[i].length()) + "_KEYWORD" + processedString.substring(index+keywords[i].length());
	                	index += keywords[i].length()+16;
	                }
	                
	                else {
	                	index ++;
	                }
	            }
	        }
		}
		//System.out.println(processedString);
		id.setOutputString(processedString);
		this.debugLevel = DebugLevel.KEYWORD_DECORATOR;
		MyLogger.writeMessage("KEYWORD_DECORATOR_"+processedString+"_KEYWORD_DECORATOR", debugLevel);
		// Forward to the next decorator, if any.
		if (null != atd) {
			atd.processInputDetails();
		}
	}
	
	
	/**
     * Default toString, needed for debugging here.
     * 
     * @return String
     */
    public String toString() {
        return "The debug level has been set to the following " + debugLevel;
    }
}