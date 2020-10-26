package cnlLab;

import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class PhSur {

	public int phonemePos;
	public String word;
	public double surprisal;

	public PhSur(String word, int phonemePos, double surprisal) {
		super();
		this.phonemePos = phonemePos;
		this.word = word;
		this.surprisal = surprisal;
	}

	/**
	 * generates map of phoneme and phoneme surprisal
	 * @param wordsInput csv file holding all words with their information (stimuli_ent_short)
	 * @return the map with words and frequencies
	 */
	public static Set<PhSur> loadSet(In wordsInput){
		//load file into map (from stim_ent_short)
		Set<PhSur> statistics = new HashSet<PhSur>();
		wordsInput.readLine();//first line is titles, skip it
		while(wordsInput.hasNextLine()) {
			String line = wordsInput.readLine();
			String word = line.substring(0, line.indexOf("_"));
			if(word.charAt(word.length()-1)=='1'||word.charAt(word.length()-1)=='2') {
				word = word.substring(0, word.length()-1);
			}
			String positionStr = line.substring(line.indexOf(",")+1, line.indexOf(",")+2);
			int position = Integer.parseInt(positionStr);
			//don't ignore zero values of phoneme surprisal
			String surLine = line.substring(0, line.lastIndexOf(","));
			String surStr = surLine.substring(surLine.lastIndexOf(",")+1,surLine.length());
			double surprisal = Double.parseDouble(surStr);
			PhSur phoneme = new PhSur(word, position, surprisal);
			statistics.add(phoneme);
		}
		return statistics;
	}

	/**
	 * gets set of all phonemes and surprisal values for these wav files
	 * @param statistics set of all phonemes and surprisal values to check against
	 * @param wavFiles array of files holding words of interest
	 * @return
	 */
	public static Set<PhSur> getPhonemesSet(Set<PhSur> statistics, In[] wavFiles) {
		Set<PhSur> words = new HashSet<PhSur>();
		for(int i=0; i<wavFiles.length;++i) {
			In wavFile = wavFiles[i];
			while(wavFile.hasNextLine()) {
				String line = wavFile.readLine();
				String word = line.substring(0, line.indexOf("."));
				boolean added = false;
				for(PhSur phoneme : statistics) {
					if(phoneme.word.equals(word)&&phoneme.phonemePos==1) {//ONLY FIRST PHONEMES
						added = true;
						words.add(phoneme);
					}
				}
				if(!added) {//should just be nonwords
					//System.out.println(word);
				}
			}
		}
		return words;
	}

	/**
	 * Takes in a set with words and surprisal values of interest, and finds the median of those word surprisals
	 * @param words set of phonemes you want the mean of
	 * @return the mean
	 * @throws Exception
	 */
	public static double getMedian(Set<PhSur> words) throws Exception {
		Median med = new Median(words.size());
		for(PhSur phoneme : words) {
			med.add(phoneme.surprisal);
		}
		if(med.add(0)) {
			throw new Exception("didn't add all words");
		}
		return med.getMedian();
	}
	
	/**
	 * exports map of words and surprisals so I can plot it
	 * @param words set of words and surprisals
	 * @param filename csv output filename
	 */
	public static void exportToFile(Set<PhSur> words, String filename) {
		Out file = new Out(filename);
		String output = "";
		for(PhSur word : words) {
			output = output + word.word + "," + word.surprisal+"\n";
		}
		output = output.trim();
		file.print(output);
		System.out.println("Saved to "+filename);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + phonemePos;
		long temp;
		temp = Double.doubleToLongBits(surprisal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhSur other = (PhSur) obj;
		if (phonemePos != other.phonemePos)
			return false;
		if (Double.doubleToLongBits(surprisal) != Double.doubleToLongBits(other.surprisal))
			return false;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
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

		Set<PhSur> stats = loadSet(stimEntShort);

		In[] primes = {list1Primes, list2Primes, list3Primes};
		Set<PhSur> setP = getPhonemesSet(stats, primes);
		try {
			System.out.println("lists 1-3, primes: "+getMedian(setP));
		} catch (Exception e) {
			e.printStackTrace();
		}

		In[] targets = {list1Targets, list2Targets, list3Targets};
		Set<PhSur> setT = getPhonemesSet(stats, targets);
		try {
			System.out.println("lists 1-3, targets: "+getMedian(setT));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//export to file so I can graph
		String primeExport = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/surprisal_primes.csv";
		exportToFile(setP, primeExport);
		
		String targetExport = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/surprisal_targets.csv";
		exportToFile(setT, targetExport);
		
	}

}
