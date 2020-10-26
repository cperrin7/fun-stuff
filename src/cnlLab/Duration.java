package cnlLab;

import java.util.HashMap;
import java.util.Map;


import edu.princeton.cs.introcs.In;

public class Duration {

	/**
	 * generates map of words and duration
	 * @param wordsInput csv file holding all words with their information (stimuli_ent_short)
	 * @return the map with words and frequencies
	 */
	public static Map<String,Double> loadMap(In wordsInput){
		//load file into map (from stim_ent_short)
		Map<String,Double> statistics = new HashMap<String,Double>();
		wordsInput.readLine();//first line is titles, skip it
		while(wordsInput.hasNextLine()) {
			String line = wordsInput.readLine();
			String word = line.substring(0, line.indexOf("_"));
			if(word.charAt(word.length()-1)=='1'||word.charAt(word.length()-1)=='2') {
				word = word.substring(0, word.length()-1);
			}
			//find start and end
			String one = line.substring(0, line.lastIndexOf(","));
			String two = one.substring(0,one.lastIndexOf(","));
			String three = two.substring(0,two.lastIndexOf(","));
			String four = three.substring(0,three.lastIndexOf(","));
			String endStrLine = four.substring(0,four.lastIndexOf(","));
			String endStr = endStrLine.substring(endStrLine.lastIndexOf(",")+1,endStrLine.length());
			double end = Double.parseDouble(endStr);
			
			statistics.put(word, end);//this will update each time, so will end up holding the last value of end which is what we want
		}
		return statistics;
	}
	
	/**
	 * gets set of all words and duration values for these wav files
	 * @param statistics set of all words and duration values to check against
	 * @param wavFiles array of files holding words of interest
	 * @return
	 */
	public static Map<String,Double> getWordsMap(Map<String,Double> statistics, In[] wavFiles) {
		Map<String,Double> words = new HashMap<String,Double>();
		for(int i=0; i<wavFiles.length;++i) {
			In wavFile = wavFiles[i];
			while(wavFile.hasNextLine()) {
				String line = wavFile.readLine();
				String word = line.substring(0, line.indexOf("."));
				if(statistics.containsKey(word)) {
					words.put(word, statistics.get(word));
				}
			}
		}
		return words;
	}
	
	/**
	 * Takes in a set with words and duration values of interest, and finds the median of those word durations
	 * @param words set of words you want the mean of
	 * @return the mean
	 * @throws Exception
	 */
	public static double getMedian(Map<String,Double> words) throws Exception {
		Median med = new Median(words.size());
		for(String word : words.keySet()) {
			med.add(words.get(word));
		}
		if(med.add(0)) {
			throw new Exception("didn't add all words");
		}
		return med.getMedian();
	}
	
	public static void main(String[] args) {
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

		Map<String,Double> stats = loadMap(stimEntShort);
		
		In[] primes = {list1Primes, list2Primes, list3Primes};
		Map<String,Double> mapP = getWordsMap(stats, primes);
		try {
			System.out.println("lists 1-3, primes: "+getMedian(mapP));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		In[] targets = {list1Targets, list2Targets, list3Targets};
		Map<String,Double> mapT = getWordsMap(stats, targets);
		try {
			System.out.println("lists 1-3, targets: "+getMedian(mapT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//export values so I can graph
		String primeExport = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/duration_primes.csv";
		LogFreqSpecificGroups.exportToFile(mapP, primeExport);
		
		String targetExport = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/duration_targets.csv";
		LogFreqSpecificGroups.exportToFile(mapT, targetExport);
	}

}
