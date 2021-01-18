package textdecorators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;

import textdecorators.util.InputDetails;
import textdecorators.util.MyLogger;
import textdecorators.util.MyLogger.DebugLevel;

public class SpellCheckDecorator extends AbstractTextDecorator{

	private AbstractTextDecorator atd;
	private InputDetails id;
	private DebugLevel debugLevel;

	
	/**
	 * SpellCheckDecorator constructor sets next 
	 * AbstractTextDecorator and InputDetails object 
	 *
	 * @param	AbstractTextDecorator	atdIn
	 * @param	InputDetails	idIn
	 * @return 	NULL
	 * 
	 */
	public SpellCheckDecorator(AbstractTextDecorator atdIn, InputDetails idIn) {
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
	 * reads misspelled file and process given string accordingly
	 * calls myLogger
	 *
	 * @return 	NULL
	 * 
	 */
	@Override
	public void processInputDetails() {
		// Decorate input details.
		try {
			id.readMisspelldFile();
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
		List <String> spells = id.getMisspells();
		String[] missSpells = spells.toArray(new String[0]);
		
		for(int i = 0; i<missSpells.length;i++) {
			if(missSpells[i].contains(".")) {
				continue;
			}
			
			String match6 = "MOST_FREQUENT_"+missSpells[i]+"_MOST_FREQUENT";
			String match7 = "KEYWORD_MOST_FREQUENT_"+missSpells[i]+"_MOST_FREQUENT_KEYWORD";
			String match8 = "KEYWORD_"+missSpells[i]+"_KEYWORD";

			int index = 0;
			while(index != -1){
	            index = processedString.toLowerCase().indexOf(missSpells[i].toLowerCase(), index);
	            if (index != -1) {
	            	if(index > 0 && (isAlphaNumeric(processedString.substring(index-1, index)) == true || isAlphaNumeric(processedString.substring(index+missSpells[i].length(), index+missSpells[i].length()+1)) == true)) {
	            		index ++;
	            		continue;
	            	}
	            	else if(index == 0 && isAlphaNumeric(processedString.substring(index+missSpells[i].length(), index+missSpells[i].length()+1)) == true) {
	            		index ++;
	            		continue;
	            	}
	            	else if(index >=22 && index+missSpells[i].length()+22 < processedString.length() && processedString.substring(index-22, index+missSpells[i].length()+22).equalsIgnoreCase(match7)) {
	                	processedString = processedString.substring(0, index-22) + "SPELLCHECK_" + processedString.substring(index-22, index+missSpells[i].length()+22) + "_SPELLCHECK" + processedString.substring(index+missSpells[i].length()+22);
	                	index += missSpells[i].length()+71;
	                }
	            	else if(index >=14 && index+missSpells[i].length()+14 < processedString.length() && processedString.substring(index-14, index+missSpells[i].length()+14).equalsIgnoreCase(match6)) {
	                	processedString = processedString.substring(0, index-14) + "SPELLCHECK_" + processedString.substring(index-14, index+missSpells[i].length()+14) + "_SPELLCHECK" + processedString.substring(index+missSpells[i].length()+14);
	                	index += missSpells[i].length()+49;
	                }
	            	else if(index >=8 && index+missSpells[i].length()+8 < processedString.length() && processedString.substring(index-8, index+missSpells[i].length()+8).equalsIgnoreCase(match8)) {
	                	processedString = processedString.substring(0, index-8) + "SPELLCHECK_" + processedString.substring(index-8, index+missSpells[i].length()+8) + "_SPELLCHECK" + processedString.substring(index+missSpells[i].length()+8);
	                	index += missSpells[i].length()+37;
	                }
	            	else if(index > 0 && index+missSpells[i].length()+1<processedString.length()  && isAlphaNumeric(processedString.substring(index-1, index)) == false && isAlphaNumeric(processedString.substring(index+missSpells[i].length(), index+missSpells[i].length()+1)) == false) {
	                	processedString = processedString.substring(0, index) + "SPELLCHECK_" + processedString.substring(index, index+missSpells[i].length()) + "_SPELLCHECK" + processedString.substring(index+missSpells[i].length());
	                	index += missSpells[i].length()+21;
	                }
	            	else if(index > 0 && index+missSpells[i].length()+1>=processedString.length()  && isAlphaNumeric(processedString.substring(index-1, index)) == false) {
	                	processedString = processedString.substring(0, index) + "SPELLCHECK_" + processedString.substring(index, index+missSpells[i].length()) + "_SPELLCHECK" + processedString.substring(index+missSpells[i].length());
	                	index += missSpells[i].length()+21;
	                }
	                else if(index == 0 && isAlphaNumeric(processedString.substring(index+missSpells[i].length(), index+missSpells[i].length()+1)) == false) {
	                	processedString = "SPELLCHECK_" + processedString.substring(index, index+missSpells[i].length()) + "_SPELLCHECK" + processedString.substring(index+missSpells[i].length());
	                	index += missSpells[i].length()+21;
	                }
	                else {
	                	index ++;
	                }
	            }
	        }
		}
		//System.out.println(processedString);
		id.setOutputString(processedString);
		this.debugLevel = DebugLevel.SPELL_CHECK_DECORATOR;
		MyLogger.writeMessage("SPELL_CHECK_DECORATOR_"+processedString+"_SPELL_CHECK_DECORATOR", debugLevel);
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
