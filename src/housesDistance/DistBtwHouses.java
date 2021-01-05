package housesDistance;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cse131.ArgsProcessor;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class DistBtwHouses {
	//to fix this, would need to account for different ways of spelling addresses 
	//maybe just reprompt if you can't find it?
	//OR try to find the address name after "https://www.google.com/maps/preview/place/"
	
	/**
	 * gets distance btw to points, given latitude and longitude
	 * @param lat1 latitude of first point
	 * @param lon1 longitude of first point
	 * @param lat2 latitude of second point
	 * @param lon2 longitude of second point
	 * @return distance between two points, in km
	 */
	private static double distanceLatLon(double lat1, double lon1, double lat2, double lon2) {
		  double p = 0.017453292519943295;    // Math.PI / 180
		  double a = 0.5 - Math.cos((lat2-lat1)*p)/2 + Math.cos(lat1*p) * Math.cos(lat2*p) * (1-Math.cos((lon2-lon1)*p))/2;
		  return 12742 * Math.asin(Math.sqrt(a));
		}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//gets the distance between two addresses, maybe
		ArgsProcessor ap = new ArgsProcessor(args);

//		String num = ap.nextString("What is the four-digit location number? (i.e. 9290)");
//		String road = ap.nextString("What is the road name? (include Rd, Dr, Ave, etc.)");
//		road = road.replaceAll("\\s", "+");
//		String city = ap.nextString("What is the city name?");
//		String state = ap.nextString("What are the state initials? (i.e. OH)");
//		while(state.length()!=2) {
//			state = ap.nextString("Must be state initials? (i.e. OH)");
//		}
//		state = state.toUpperCase();
//		String zip = ap.nextString("What is the zipcode?");
//		String link = "https://www.google.com/maps/place/"+num+"+"+road+",+"+city+",+"+state+"+"+zip+"/";
		
		String address1 = ap.nextString("What is the full address of the first house?");
		String address2 = ap.nextString("What is the full address of the second house?");
		
		//house 1
//		String address1 = "9290 Joy Rd, Plymouth, MI 48170";
		//42.3477946
		address1 = address1.replaceAll("\\s", "+");
		String link1 = "https://www.google.com/maps/place/"+address1+"/";
		//https://www.google.com/maps/place/9290+Joy+Rd,+Plymouth,+MI+48170/

		Document doc = new Document("");
		try {
			doc = Jsoup.connect(link1).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String html1 = doc.toString();
		String locator1 = "https://www.google.com/maps/preview/place/"+address1+"/@";
		int startIdx = html1.indexOf(locator1)+locator1.length();
		int comma1 = html1.indexOf(",", startIdx);
		int comma2 = html1.indexOf(",", comma1+1); 
		double lat1 = Double.parseDouble(html1.substring(startIdx,comma1));
		double lon1 = Double.parseDouble(html1.substring(comma1+1,comma2));
		
		//house 2
//		String address2 = "417 N Thayer St, Ann Arbor, MI 48104";//Cam
		//42.2844885
//		String address2 = "11426 Burnham St, Los Angeles, CA 90049";//Dora
		address2 = address2.replaceAll("\\s", "+");
		String link2 = "https://www.google.com/maps/place/"+address2+"/";
		//https://www.google.com/maps/place/9290+Joy+Rd,+Plymouth,+MI+48170/

		Document doc2 = new Document("");
		try {
			doc2 = Jsoup.connect(link2).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String html2 = doc2.toString();
		String locator2 = "https://www.google.com/maps/preview/place/"+address2+"/@";
		int startIdx2 = html2.indexOf(locator2)+locator2.length()+2;
		comma1 = html2.indexOf(",", startIdx2);
		comma2 = html2.indexOf(",", comma1+1); 
		double lat2 = Double.parseDouble(html2.substring(startIdx2,comma1));
		double lon2 = Double.parseDouble(html2.substring(comma1+1,comma2));
		
		double distkm = distanceLatLon(lat1,lon1,lat2,lon2);
		distkm = (double)((int)(distkm*100))/100;
		double distm = distkm*0.6214;
		distm = (double)((int)(distm*100))/100;
		System.out.println("These houses are "+distm+" miles apart ("+distkm+" km), as the crow flies");
		
	}

}
