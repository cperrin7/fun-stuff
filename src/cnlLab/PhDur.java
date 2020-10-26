package cnlLab;

import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class PhDur {

	public int phonemePos;
	public String word;
	public double duration;

	public PhDur(String word, int phonemePos, double start, double end) {
		super();
		this.phonemePos = phonemePos;
		this.word = word;
		this.duration = end-start;
	}

	/**
	 * generates map of phoneme and phoneme duration
	 * @param wordsInput csv file holding all words with their information (stimuli_ent_short)
	 * @return the map with words and frequencies
	 */
	public static Set<PhDur> loadSet(In wordsInput){
		//load file into map (from stim_ent_short)
		Set<PhDur> statistics = new HashSet<PhDur>();
		wordsInput.readLine();//first line is titles, skip it
		while(wordsInput.hasNextLine()) {
			String line = wordsInput.readLine();
			String word = line.substring(0, line.indexOf("_"));
			if(word.charAt(word.length()-1)=='1'||word.charAt(word.length()-1)=='2') {
				word = word.substring(0, word.length()-1);
			}
			String positionStr = line.substring(line.indexOf(",")+1, line.indexOf(",")+2);
			int position = Integer.parseInt(positionStr);
			//find start and end
			String one = line.substring(0, line.lastIndexOf(","));
			String two = one.substring(0,one.lastIndexOf(","));
			String three = two.substring(0,two.lastIndexOf(","));
			String four = three.substring(0,three.lastIndexOf(","));

			String endStrLine = four.substring(0,four.lastIndexOf(","));
			String endStr = endStrLine.substring(endStrLine.lastIndexOf(",")+1,endStrLine.length());
			double end = Double.parseDouble(endStr);

			String startStrLine = endStrLine.substring(0,endStrLine.lastIndexOf(","));
			String startStr = startStrLine.substring(startStrLine.lastIndexOf(",")+1,startStrLine.length());
			double start = Double.parseDouble(startStr);
			PhDur phoneme = new PhDur(word, position, start, end);
			statistics.add(phoneme);
		}
		return statistics;
	}

	/**
	 * gets set of all phonemes and duration values for these wav files
	 * @param statistics set of all phonemes and duration values to check against
	 * @param wavFiles array of files holding words of interest
	 * @return
	 */
	public static Set<PhDur> getPhonemesSet(Set<PhDur> statistics, In[] wavFiles) {
		Set<PhDur> words = new HashSet<PhDur>();
		for(int i=0; i<wavFiles.length;++i) {
			In wavFile = wavFiles[i];
			while(wavFile.hasNextLine()) {
				String line = wavFile.readLine();
				String word = line.substring(0, line.indexOf("."));
				for(PhDur phoneme : statistics) {
					if(phoneme.word.equals(word)&&phoneme.phonemePos==1) {//ONLY FIRST PHONEMES 
						words.add(phoneme);
					}
				}
			}
		}
		return words;
	}

	/**
	 * Takes in a set with words and duration values of interest, and finds the median of those word durations
	 * @param words set of phonemes you want the mean of
	 * @return the mean
	 * @throws Exception
	 */
	public static double getMedian(Set<PhDur> words) throws Exception {
		Median med = new Median(words.size());
		for(PhDur phoneme : words) {
			med.add(phoneme.duration);
		}
		if(med.add(0)) {
			throw new Exception("didn't add all words");
		}
		return med.getMedian();
	}

	/**
	 * exports map of words and durations so I can plot it
	 * @param words set of words and durations
	 * @param filename csv output filename
	 */
	public static void exportToFile(Set<PhDur> words, String filename) {
		Out file = new Out(filename);
		String output = "";
		for(PhDur word : words) {
			output = output + word.word + "," + word.duration+"\n";
		}
		output = output.trim();
		file.print(output);
		System.out.println("Saved to "+filename);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(duration);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + phonemePos;
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
		PhDur other = (PhDur) obj;
		if (Double.doubleToLongBits(duration) != Double.doubleToLongBits(other.duration))
			return false;
		if (phonemePos != other.phonemePos)
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

		Set<PhDur> stats = loadSet(stimEntShort);

		In[] primes = {list1Primes, list2Primes, list3Primes};
		Set<PhDur> setP = getPhonemesSet(stats, primes);
		try {
			System.out.println("lists 1-3, primes: "+getMedian(setP));
		} catch (Exception e) {
			e.printStackTrace();
		}

		In[] targets = {list1Targets, list2Targets, list3Targets};
		Set<PhDur> setT = getPhonemesSet(stats, targets);
		try {
			System.out.println("lists 1-3, targets: "+getMedian(setT));
		} catch (Exception e) {
			e.printStackTrace();
		}

		//export to file so I can graph
		String primeExport = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/phoneme duration_primes.csv";
		exportToFile(setP, primeExport);

		String targetExport = "C:/Users/CKPer/Box/WashU/CNLlab/Matlab/analysis-scripts/phoneme duration_targets.csv";
		exportToFile(setT, targetExport);

	}

}
