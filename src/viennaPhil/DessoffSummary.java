package viennaPhil;

import java.util.HashMap;
import java.util.Map;

import cnlLab.PhDur;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class DessoffSummary {

	/**
	 * Compiles the composers in the csv file given
	 * @param compInput
	 * @return
	 */
	public static Map<Composer,Double> loadComposers(In compInput){
		Map<Composer,Double> composers = new HashMap<Composer,Double>();
		compInput.readLine();//first line is titles, skip it
		while(compInput.hasNextLine()) {
			String line = compInput.readLine();
//			System.out.println(line);
			//composer is after the fourth comma, then Nationality, Type of music, Years, Notes
			int fourthcomma = 0;
			for(int i=1; i<=3; ++i) {
				fourthcomma = line.indexOf(",", fourthcomma+1);
			}
			int fifthcomma = line.indexOf(",", fourthcomma+1);
			String composer = line.substring(fourthcomma+1, fifthcomma);
			if(line.charAt(0)!=',') {//issue when there is a year before the first comma.
				fourthcomma = fifthcomma;
				fifthcomma = line.indexOf(",", fourthcomma+1);
				composer = line.substring(fourthcomma+1, fifthcomma);
			}
			int sixthcomma = line.indexOf(",", fifthcomma+1);
			String nationality = line.substring(fifthcomma+1, sixthcomma);
			int seventhcomma = line.indexOf(",", sixthcomma+1);
			String musicType = line.substring(sixthcomma+1, seventhcomma);
			int eightcomma = line.indexOf(",", seventhcomma+1);
			String years = line.substring(seventhcomma+1, eightcomma);
			String notes = "";
			if(eightcomma<line.length()-1) {//only get notes if there are notes
				//some notes have commas in them. Just go to end of the line 
				notes = line.substring(eightcomma+1, line.length()-1);
			}
			Composer c = new Composer(composer, nationality, musicType, years, notes);
			composers.put(c, composers.getOrDefault(c, 0.0)+1);
		}
		
		return composers;
	}
	
	public static void exportToFile(String filename, Map<Composer,Double> composers) {
		Out file = new Out(filename);
		String output = "Composer,Times Programmed\n";
		for(Composer c : composers.keySet()) {
			output = output + c.getName()+","+composers.get(c)+"\n";
		}
		output = output.trim();
		file.print(output);
		System.out.println("Saved to "+filename);
	}
	
	public static void main(String[] args) {
		//code to summarize the composers Dessoff chose
		String programmingName = "C:/Users/CKPer/Box/WashU/D_Senior Spring Semester/Music Hist 3/Paper/Programming.csv";
		In programming = new In(programmingName);
		Map<Composer,Double> composers = loadComposers(programming);
		for(Composer c : composers.keySet()) {
			System.out.println(c.getName()+": "+composers.get(c));
		}
		String outFile = "C:/Users/CKPer/Box/WashU/D_Senior Spring Semester/Music Hist 3/Paper/Programming_composer counts.csv";
		exportToFile(outFile, composers);
		System.out.println("done");
	}

}
