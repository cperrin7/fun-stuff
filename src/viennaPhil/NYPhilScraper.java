package viennaPhil;

import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cse131.ArgsProcessor;

public class NYPhilScraper {

	private static void findComposers() throws IOException {
		Document doc = Jsoup.connect("https://www.wienerphilharmoniker.at/en/konzert-archiv").get();
		//ISSUE: only gives default search, which stops at 1864?
		String wholeThing = doc.toString();
//		System.out.println(wholeThing);
		int index = wholeThing.indexOf("Mozart");
		int count = 1;
		int endInd = wholeThing.lastIndexOf("Mozart");
		while (index<endInd) {
			index = wholeThing.indexOf("Mozart", index+1);
			++count;
		}
		System.out.println(count);
		//https://realpython.com/beautiful-soup-web-scraper-python/
	}
	
	/**
	 * gets all the composers for a single concert at the moment (specifically 1842 Dec 07 / Subscription Season / Hill)
	 * @return
	 * @throws IOException
	 */
	private static LinkedList<String> findNYoneProg() throws IOException {
		//first go on the full concert list
		Document doc = Jsoup.connect("https://archives.nyphil.org/index.php/search?search-type=singleFilter&search-text=*&search-dates-from=12%2F07%2F1842&search-dates-to=03%2F04%2F2021").get();
		String wholeThing = doc.toString();
		//now get the program for one concert
		int index = wholeThing.indexOf("tooltipSoloists");//only getting the first concert at the moment
		index = wholeThing.indexOf("https", index);
		int endIndex = wholeThing.indexOf("rel", index);
		String programSite = wholeThing.substring(index, endIndex);
		//get rid of amp;
		int amp = programSite.indexOf("amp;");
		String progTemp = programSite.substring(0, amp);
		int end = programSite.lastIndexOf("amp;");
		while(amp<end) {
			int temp = programSite.indexOf("amp;",amp+1);
			progTemp = progTemp+programSite.substring(amp+4,temp);
			amp = temp;
		}
		progTemp = progTemp+programSite.substring(amp+4,programSite.length()-1);
		programSite = progTemp;
		//connect to the program
		Document program = Jsoup.connect(programSite).get();
		String wholeProg = program.toString();
		//get the list of works played
		int works = wholeProg.indexOf("Works");
		while(wholeProg.substring(works, works+9).equals("WorksCond")) {
			works = wholeProg.indexOf("Works",works+1);
		}
		int worksfull = wholeProg.indexOf("class=\"full\"",works);
		int collapse = wholeProg.indexOf("collapse", worksfull);
		String allWorks = wholeProg.substring(worksfull,collapse);//!!!!!
		//search the works for the composer name 
		int slash = allWorks.indexOf(" / ");
		int greater = allWorks.lastIndexOf(">", slash);
		end = allWorks.lastIndexOf(" / ");
		LinkedList<String> comps = new LinkedList<String>();
		comps.add(allWorks.substring(greater+1,slash));
		while(slash<end) {
			slash = allWorks.indexOf(" / ", slash+1);
			greater = allWorks.lastIndexOf(">", slash);
			comps.add(allWorks.substring(greater+1,slash));
		}
		return comps;
	}

	public static void main(String[] args) throws IOException {
		ArgsProcessor ap = new ArgsProcessor(args);
//		findComposers();
		LinkedList<String> comps = findNYoneProg();
		System.out.println(comps.toString());
	}

}
