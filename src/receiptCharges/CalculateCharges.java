package receiptCharges;
import java.util.HashMap;

import cse131.ArgsProcessor;

public class CalculateCharges {

	/**
	 * prompts the user for the prices of all the items on the receipt and who they belong to (only the physical items, not extra costs), and adds them to the map of people and costs
	 * @param people the map of all names and costs
	 * @param args pass in the main method args
	 * @return returns the total cost of all the items
	 */
	public static double priceJustItems(HashMap<String,Double> people, String[] args) {
		double totalPrice = 0.0;
		ArgsProcessor ap = new ArgsProcessor(args);
		int times = ap.nextInt("How many items are there? (the physical things you bought, not extra taxes/fees/etc.");
		for(int j=0; j<times; ++j) {
			int numPeopleThisItem = ap.nextInt("How many people are splitting item "+(j+1)+"? (there are "+people.size()+" total people)");
			while(numPeopleThisItem>people.size()) {
				numPeopleThisItem = ap.nextInt("That is more people than exist, try again");
			}
			double price = ap.nextDouble("What is the price of item "+(j+1)+"?");
			totalPrice = totalPrice + price;
			double pricePerPerson = price/numPeopleThisItem;
			if(numPeopleThisItem==people.size()) {
				for(String name : people.keySet()) {
					people.put(name, people.get(name)+pricePerPerson);
				}
			}
			else {
				for(int i=0; i<numPeopleThisItem;++i) {
					String name = "";
					if(numPeopleThisItem==1) {
						name = ap.nextString("Which person does item "+(j+1)+" belong to?");
					}
					else {
						name = ap.nextString("Which people does item "+(j+1)+" belong to?");
					}
					while(!people.containsKey(name)) {
						name = ap.nextString("That name doesn't exist, try another");
					}
					people.put(name, people.get(name)+pricePerPerson);
				}
			}
		}
		return totalPrice;
	}
	
	/**
	 * prompts the user for all extra fees that were a part of this order
	 * @param people
	 * @param args
	 */
	public static void priceFees(HashMap<String,Double> people, String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);
		double feePrice = ap.nextDouble("Input the price of a fee/tax/etc. (or 0 if no more fees)");
		while(feePrice!=0.0) {
			double feePerPerson = feePrice/people.size();
			for(String person : people.keySet()) {
				people.put(person, people.get(person)+feePerPerson);
			}
			feePrice = ap.nextDouble("Input the price of a fee/tax/etc. (or 0 if no more fees)");
		}
	}

	public static void main(String[] args) {
		//this is code to calculate how much people owe, usually after a food order
		ArgsProcessor ap = new ArgsProcessor(args);
		HashMap<String, Double> people = new HashMap<String,Double>();
		Double total = ap.nextDouble("What is the overall total cost? (including tip, tax, everything)");
		String name = ap.nextString("What are the names of the people you are calculating? (type enter if there is no one else)");
		while(name.isEmpty()) {
			name = ap.nextString("You must have at least one person, otherwise why are you here");
		}
		while(!name.isEmpty()) {
			people.put(name, 0.0);
			name = ap.nextString("What is the next person? (type enter if you are done)");
		}

		String calcTax = ap.nextString("Did the receipt specify tax? (if not I need to calculate it from the subtotal)");
		if(calcTax.equalsIgnoreCase("yes")) {
			//user will input the tax
			priceJustItems(people, args);
			priceFees(people, args);
		}
		else {
			//will need to calculate the tax
			double subTot = ap.nextDouble("What was the subtotal? (including tax)");
			double allItems = priceJustItems(people, args);
			double taxPerPerson = (subTot - allItems)/people.size();
			for(String person : people.keySet()) {
				people.put(person, people.get(person)+taxPerPerson);
			}
			priceFees(people, args);
		}

		double calcTotal = 0.0;
		for(String person : people.keySet()) {
			calcTotal = calcTotal + people.get(person);
			System.out.println(person+" owes $"+people.get(person));
		}
		if(total==Math.round(calcTotal*100)/100||total==calcTotal) {
			System.out.println("The total matches the amount inputted");
		}
		else {
			System.out.println("The total does not appear to match the amount inputted:");
			System.out.println("\t"+total+" vs "+calcTotal);
		}

	}

}
