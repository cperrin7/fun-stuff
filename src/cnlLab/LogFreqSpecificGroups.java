package cnlLab;

import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class LogFreqSpecificGroups {

	/**
	 * Takes in a map with all words and frequencies, and a file with some wav file names, matches the frequencies to the wav files, and finds the median of those wav file word frequencies
	 * @param statistics map holding all words and frequencies
	 * @param wavFiles csv file holding the .wav file names
	 * @param numWavWords the number of .wav words
	 * @return the median found of the wav file words
	 * @throws Exception 
	 */
	public static double getMedian(Map<String,Double> statistics, In wavFiles, int numWavWords) throws Exception {
		Median med = new Median(numWavWords);
		while(wavFiles.hasNextLine()) {
			String line = wavFiles.readLine();
			String word = line.substring(0, line.indexOf("."));
			if(statistics.get(word)!=null) {
				med.add(statistics.get(word));
			}
			else {
				//nonword, ignore
				//System.out.println(word);
				//throw new Exception("word not in map: "+word);
			}
		}
		if(med.add(0)) {
			throw new Exception("didn't add all words");
		}
		return med.getMedian();
	}
	
	/**
	 * Takes in a map with words and frequencies of interest, and finds the median of those word frequencies
	 * @param words map of words you want median of
	 * @return the median
	 * @throws Exception
	 */
	public static double getMedianMultipleFiles(Map<String,Double> words) throws Exception {
		Median med = new Median(words.size());
		for(String word : words.keySet()) {
			med.add(words.get(word));
		}
		if(med.add(0)) {
			throw new Exception("didn't add all words");
		}
		return med.getMedian();
	}
	
	/**
	 * creates a set of unique words with their frequencies
	 * @param statistics map of all words and frequencies
	 * @param wavFiles1 csv file holding the .wav file names
	 * @param wavFiles2 csv file holding the .wav file names
	 * @param wavFiles3 csv file holding the .wav file names
	 * @return
	 */
	public static Map<String,Double> listUniqueWords(Map<String,Double> statistics, In wavFiles1, In wavFiles2, In wavFiles3) {
		Map<String,Double> wordSet = new HashMap<String,Double>();
		while(wavFiles1.hasNextLine()) {
			String line = wavFiles1.readLine();
			String word = line.substring(0, line.indexOf("."));
			if(statistics.get(word)!=null) {//only add actual words, will not add word twice because set
				wordSet.put(word, statistics.get(word));
			}
			else {
				//nonword, ignore
				//System.out.println(word);
				//throw new Exception("word not in map: "+word);
			}
		}
		while(wavFiles2.hasNextLine()) {
			String line = wavFiles2.readLine();
			String word = line.substring(0, line.indexOf("."));
			if(statistics.get(word)!=null) {
				wordSet.put(word, statistics.get(word));
			}
		}
		while(wavFiles3.hasNextLine()) {
			String line = wavFiles3.readLine();
			String word = line.substring(0, line.indexOf("."));
			if(statistics.get(word)!=null) {
				wordSet.put(word, statistics.get(word));
			}
		}
		return wordSet;
	}

	/**
	 * generates map of word and log frequencies
	 * @param wordsInput csv file holding all words with their frequencies (stimuli_ent_short)
	 * @return the map with words and frequencies
	 */
	public static Map<String, Double> loadMap(In wordsInput){
		//load file into map (from stim_ent_short)
		Map<String, Double> statistics = new HashMap<String, Double>();
		wordsInput.readLine();//first line is titles, skip it
		while(wordsInput.hasNextLine()) {
			String line = wordsInput.readLine();
			String word = line.substring(0, line.indexOf("_"));
			if(word.charAt(word.length()-1)=='1'||word.charAt(word.length()-1)=='2') {
				word = word.substring(0, word.length()-1);
			}
			if(statistics.get(word)==null) {//new word, get frequency and put in map
				String logfreqS = line.substring(line.lastIndexOf(",")+1,line.length());
				double logfreq = Double.parseDouble(logfreqS);
				statistics.put(word, logfreq);
			}
		}
		return statistics;
	}
	
	/**
	 * exports map of words and frequencies so I can plot it
	 * @param words map of words and frequencies
	 * @param filename csv output filename
	 */
	public static void exportToFile(Map<String, Double> words, String filename) {
		Out file = new Out(filename);
		String output = "";
		for(String word : words.keySet()) {
			output = output + word + "," + words.get(word)+"\n";
		}
		output = output.trim();
		file.print(output);
		System.out.println("Saved to "+filename);
	}

	public static void main(String[] args){
		String stimEntName = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/stimuli_ent_short.csv";//file with all the words and their frequencies
		In stimEntShort = new In(stimEntName);
		String list1NameP = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/list1_wav files_primes.csv";//list 1, primes (wav files)
		In list1Primes = new In(list1NameP);
		String list1NameT = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/list1_wav files_targets.csv";//list 1, targets (wav files)
		In list1Targets = new In(list1NameT);
		String list2NameP = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/list2_wav files_primes.csv";//list 2, primes (wav files)
		In list2Primes = new In(list2NameP);
		String list2NameT = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/list2_wav files_targets.csv";//list 2, targets (wav files)
		In list2Targets = new In(list2NameT);
		String list3NameP = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/list2_wav files_primes.csv";//list 3, primes (wav files)
		In list3Primes = new In(list3NameP);
		String list3NameT = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/list2_wav files_targets.csv";//list 3, targets (wav files)
		In list3Targets = new In(list3NameT);
//		int listLengthPrimes = 83;//all prime lists have 83 words
		
		Map<String, Double> stats = loadMap(stimEntShort);
		
//		try {
//			System.out.println("list 1, primes: "+getMedian(stats, list1Primes, listLengthPrimes));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("list 1, targets: "+getMedian(stats, list1Targets, listLengthPrimes-28));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		try {
//			System.out.println("list 2, primes: "+getMedian(stats, list2Primes, listLengthPrimes));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("list 2, targets: "+getMedian(stats, list2Targets, listLengthPrimes-27));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

//		try {
//			System.out.println("list 3, primes: "+getMedian(stats, list3Primes, listLengthPrimes));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			System.out.println("list 3, targets: "+getMedian(stats, list3Targets, listLengthPrimes-27));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		
		System.out.println("Prime lists 1-3:");
		Map<String, Double> primeWords = listUniqueWords(stats, list1Primes, list2Primes, list3Primes);
		try {
			System.out.println(getMedianMultipleFiles(primeWords));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Target lists 1-3:");
		Map<String, Double> targetWords = listUniqueWords(stats, list1Targets, list2Targets, list3Targets);
		try {
			System.out.println(getMedianMultipleFiles(targetWords));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//if nonword, then IGNORE. If need to populate spreadsheet, freq = 0 tho
		
		//export words and frequencies so I can plot
		String primeExport = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_primes.csv";
		exportToFile(primeWords, primeExport);
		
		String targetExport = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/logfreq_targets.csv";
		exportToFile(targetWords, targetExport);
	}

}
