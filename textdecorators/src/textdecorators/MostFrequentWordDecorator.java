package textdecorators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import textdecorators.util.InputDetails;
import textdecorators.util.MyLogger;
import textdecorators.util.MyLogger.DebugLevel;

public class MostFrequentWordDecorator extends AbstractTextDecorator {
			private AbstractTextDecorator atd;
			private InputDetails id;
			HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
			String outputString = "";
			private DebugLevel debugLevel;
			
			
			/**
			 * MostFrequentWordDecorator constructor sets next 
			 * AbstractTextDecorator and InputDetails object 
			 *
			 * @param	AbstractTextDecorator	atdIn
			 * @param	InputDetails	idIn
			 * @return 	NULL
			 * 
			 */
			public MostFrequentWordDecorator(AbstractTextDecorator atdIn, InputDetails idIn) {

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
			
			public String getOutputString() {
				return outputString;
			}
			
			
			/**
			 * sorts hashmap in an ascending order of occurrence
			 * of a word
			 *
			 * @return 	HashMap<String, Integer>
			 * 
			 */
			public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) 
		    { 

		        List<Map.Entry<String, Integer> > list = 
		               new LinkedList<Map.Entry<String, Integer> >(hm.entrySet()); 
		  

		        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
		            public int compare(Map.Entry<String, Integer> o1,  
		                               Map.Entry<String, Integer> o2) 
		            { 
		                return (o1.getValue()).compareTo(o2.getValue()); 
		            } 
		        }); 
		          
		        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); 
		        for (Map.Entry<String, Integer> aa : list) { 
		            temp.put(aa.getKey(), aa.getValue()); 
		        } 
		        return temp; 
		    } 
		  
			
			/**
			 * reads input file, builds hashmap to find most frequently
			 * occurring word and process given string accordingly
			 * calls myLogger
			 *
			 * @return 	NULL
			 * 
			 */
			@Override
			public void processInputDetails() {
				// Decorate input details.
				try {
					id.readFile();
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

				List<String> s1 = id.getSentences();
				String[] sentence1 = s1.toArray(new String[0]);
				Integer counter=null;
				for(int i = 0; i<sentence1.length; i++ ) {
					sentence1[i].replaceAll(",", " ");
					String[] words = sentence1[i].split(" ");
					for(int j=0;j<words.length;j++) { 
						String w[] = words[j].split("\\.");
						
						for(int k = 0; k<w.length;k++) {
							if(w[k].equals("") || w[k].equals("\\n")) {
								continue;
							}
							counter=wordCount.get(w[k].toLowerCase());
						     if(counter == null) { 
						    	 wordCount.put(w[k].toLowerCase(), 1);
						     } else {
						          counter++;
						          wordCount.put(w[k].toLowerCase(), counter);
						     }
						}
					     
					}

				}
				
				HashMap<String, Integer> sortedWordCount = sortByValue(wordCount);
				
				String frequentWord = "";
		        for (Entry<String, Integer> entry : sortedWordCount.entrySet()) {
		        	frequentWord = entry.getKey();
		        }
/*
		        sortedWordCount.forEach((key, value) -> {
		        	System.out.print("key:"+ key);
		        	System.out.println("Value: "+ value);
		        });
*/
				String[] sentence = s1.toArray(new String[0]);
				for(String s : sentence ) {
					if(s.equalsIgnoreCase("newLine")) {
						outputString += " "+"\n";
						//System.out.println("Hi");
					}
				else {
					//System.out.println("Hi2");
					int index = 0;
			        while(index != -1){
			            index = s.toLowerCase().indexOf(frequentWord.toLowerCase(), index);
			            if (index != -1) {
			                if(index > 0 && index+frequentWord.length()+1<s.length() && isAlphaNumeric(s.substring(index-1, index)) == false && isAlphaNumeric(s.substring(index+frequentWord.length(), index+frequentWord.length()+1)) == false) {
			                	s = s.substring(0, index) + "MOST_FREQUENT_" + s.substring(index, index+frequentWord.length()) + "_MOST_FREQUENT" + s.substring(index+frequentWord.length());
			                	index += frequentWord.length()+28;
			                }
			                else if(index > 0 && index+frequentWord.length()+1>=s.length() && isAlphaNumeric(s.substring(index-1, index)) == false) {
			                	s = s.substring(0, index) + "MOST_FREQUENT_" + s.substring(index, index+frequentWord.length()) + "_MOST_FREQUENT" + s.substring(index+frequentWord.length());
			                	index += frequentWord.length()+28;
			                }
			                else if(index == 0 && isAlphaNumeric(s.substring(index+frequentWord.length(), index+frequentWord.length()+1)) == false) {
			                	s = "MOST_FREQUENT_" + s.substring(index, index+frequentWord.length()) + "_MOST_FREQUENT" + s.substring(index+frequentWord.length());
			                	index += frequentWord.length()+28;
			                }
			                else {
			                	index ++;
			                }
			            }
			        }
					outputString += s+"\n";
				}
				}
				//System.out.println(outputString);
				id.setOutputString(outputString);
				this.debugLevel = DebugLevel.MOST_FREQUENT_DECORATOR;
				MyLogger.writeMessage("MOST_FREQUENT_DECORATOR_"+outputString+"_MOST_FREQUENT_DECORATOR", debugLevel);
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

