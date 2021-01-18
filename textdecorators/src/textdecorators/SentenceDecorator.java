package textdecorators;


import textdecorators.util.InputDetails;
import textdecorators.util.MyLogger;
import textdecorators.util.MyLogger.DebugLevel;

public class SentenceDecorator extends AbstractTextDecorator {
	private AbstractTextDecorator atd;
	private InputDetails id;
	private DebugLevel debugLevel;

	
	/**
	 * SentenceDecorator constructor sets next 
	 * AbstractTextDecorator and InputDetails object 
	 *
	 * @param	AbstractTextDecorator	atdIn
	 * @param	InputDetails	idIn
	 * @return 	NULL
	 * 
	 */
	public SentenceDecorator(AbstractTextDecorator atdIn, InputDetails idIn) {
		atd = atdIn;
		id = idIn;
	}

	
	/**
	 * reads output string and prefix and suffix string with 
	 * BEGIN_SENTENCE__ and __END_SENTENCE respectively
	 * calls myLogger
	 *
	 * @return 	NULL
	 * 
	 */
	@Override
	public void processInputDetails() {
		// Decorate input details.
		
		String processedString = id.getOutputString();
		processedString = "BEGIN_SENTENCE__" + processedString;
		processedString = processedString.replaceAll("[.]", "__END_SENTENCE.BEGIN_SENTENCE__");
		if(processedString.endsWith("BEGIN_SENTENCE__\n") || processedString.endsWith("BEGIN_SENTENCE__")) {
			int index = processedString.lastIndexOf('.');
			processedString = processedString.substring(0,index+1);
		}
		
		//System.out.println(processedString);
		
		id.setOutputString(processedString);
		this.debugLevel = DebugLevel.SENTENCE_DECORATOR;
		MyLogger.writeMessage("SENTENCE_DECORATOR_"+processedString+"_SENTENCE_DECORATOR", debugLevel);
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
