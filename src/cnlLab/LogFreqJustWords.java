package cnlLab;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class LogFreqJustWords {
	//code to sort through words from semantic priming experiment to pair them with their frequencies
	//use this code
	
	/**
	 * gets all the words from the wav file names and saves them to a separate file
	 * @param input the file that holds all wav file names with words in them - a csv file
	 * @param output the file to output words to, a txt file
	 */
	public static void getWords(In input, Out output) {
		HashSet<String> allWords = new HashSet<String>();
		while(input.hasNextLine()) {
			String line = input.readLine();
			String word = line.substring(0, line.indexOf("_"));
			if(word.charAt(word.length()-1)=='1'||word.charAt(word.length()-1)=='2') {
				word = word.substring(0, word.length()-1);
			}
			allWords.add(word);
		}
		
		String wordOutput = "";
		for(String word : allWords) {
			wordOutput = wordOutput + word + "\n";
		}
		wordOutput = wordOutput.trim();//get rid of last \n
		System.out.println(wordOutput);
		output.print(wordOutput);
	}
	
	/**
	 * Takes in a file with words and frequencies, and a file with the wav file names, and matches the frequencies to the wav files, and outputs in order
	 * Also prints the mean frequency 
	 * @param wordsInput csv file holding all words with their frequencies
	 * @param wavFiles csv file holding all .wav file names, in stimuli_ent_short order
	 * @param output file to put the .wav file names and frequencies in
	 * @param outputFileName filename of output file
	 */
	public static void matchFrequencies(In wordsInput, In wavFiles, Out output, String outputFileName) {
		Map<String, Double> statistics = new HashMap<String, Double>();
		//load file into map
		while(wordsInput.hasNextLine()) {
			String line = wordsInput.readString();
			String word = line.substring(0,line.indexOf(","));
			String logfreqS = line.substring(line.indexOf(",")+1,line.length());
			double logfreq = Double.parseDouble(logfreqS);
			statistics.put(word, logfreq);
			if(word.equals("wood")) {//it tries to go past the end for some reason
				break;
			}
		}
		
		String outputGood = "";
		String outputNull = "";
		String outputFull = "";
		while(wavFiles.hasNextLine()) {
			String line = wavFiles.readLine();
			String word = line.substring(0, line.indexOf("_"));
			if(word.charAt(word.length()-1)=='1'||word.charAt(word.length()-1)=='2') {
				word = word.substring(0, word.length()-1);
			}
			if(statistics.get(word)!=null) {
				outputGood = outputGood+line+","+word+","+statistics.get(word)+"\n";
				outputFull = outputFull+line+","+word+","+statistics.get(word)+"\n";
			}
			else {
				outputNull = outputNull+line+","+word+"\n";
				outputFull = outputFull+line+","+word+"\n";
			}
		}
		//System.out.println(outputFull);
		output.print(outputFull);
		System.out.println("Output to "+outputFileName);
		double sum = 0;
		int count = 0;
		for(String word : statistics.keySet()) {
			sum = sum + statistics.get(word);
			++count;
		}
		System.out.println("\nMean frequency (for 'majorities'): "+sum/count);
	}

	public static void main(String[] args) {
		//code to sort through words from semantic priming experiment to pair them with their frequencies
		
		//get the words so I can look up frequencies on English Lexicon Project (https://elexicon.wustl.edu/index.html)
//		String logfreqName = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_setup.csv";//file of names of wav files, in order that they're in for semantic priming
//		In logfreqFile = new In(logfreqName);
//		String allWordsName = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/allwords.txt";//file to hold the words
//		Out allWordsFile = new Out(allWordsName);
//		getWords(logfreqFile, allWordsFile);
		
		//match frequencies with .wav file names so I can add to stimuli_ent_short
		String allWordsFreqName = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/allwords_freq.csv";//file with all the words and their frequencies (from English Lexicon Project)
		In allWordsFreq = new In(allWordsFreqName);
		String logfreqName = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_setup.csv";//file of names of wav files, in order that they're in for semantic priming
		In logfreqFile = new In(logfreqName);
		String outputFullFile = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_full.csv";//place to put all frequencies and wav file names. "majorities" will be empty
		Out full = new Out(outputFullFile);
		matchFrequencies(allWordsFreq, logfreqFile, full, outputFullFile);
		
	}

}
