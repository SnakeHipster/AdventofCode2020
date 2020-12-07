package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DaySeven {
	static ArrayList<String> INPUT;
	static String SHINY_GOLD = "shiny gold";
	static String listOfBags[];
	static ArrayList<ArrayList<String>> listOfRules;
	static ArrayList<String> rules;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}

		listOfBags = new String[INPUT.size()];
		listOfRules = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < INPUT.size(); i++) {
			StringTokenizer tmp = new StringTokenizer(INPUT.get(i));

			// Bag Name
			listOfBags[i] = tmp.nextElement().toString() + " " + tmp.nextElement().toString();

			// Bags/contain skip
			tmp.nextElement();
			tmp.nextElement();

			rules = new ArrayList<String>();
			while (tmp.hasMoreElements()) {
				String number = tmp.nextElement().toString();
				if (!number.contains("no")) {
					rules.add(tmp.nextElement().toString() + " " + tmp.nextElement().toString());
					tmp.nextElement();
				} else {
					break;
				}

			}

			listOfRules.add(rules);
		}

		computeTask1();
		
		listOfBags = new String[INPUT.size()];
		listOfRules = new ArrayList<ArrayList<String>>();

		for (int i = 0; i < INPUT.size(); i++) {
			StringTokenizer tmp = new StringTokenizer(INPUT.get(i));

			// Bag Name
			listOfBags[i] = tmp.nextElement().toString() + " " + tmp.nextElement().toString();

			// Bags/contain skip
			tmp.nextElement();
			tmp.nextElement();

			rules = new ArrayList<String>();
			while (tmp.hasMoreElements()) {
				String number = tmp.nextElement().toString();
				if (!number.contains("no")) {
					rules.add(number + ":" + tmp.nextElement().toString() + " " + tmp.nextElement().toString());
					tmp.nextElement();
				} else {
					break;
				}

			}

			listOfRules.add(rules);
		}
		computeTask2();

	}

	private static void computeTask1() {
		int total = 0;
		boolean canContainShinyGold[] = new boolean[INPUT.size()];
		for (int i = 0; i < INPUT.size(); i++) {
			canContainShinyGold[i] = false;
		}

		boolean changeMade = true;
		while (changeMade) {
			changeMade = false;
			for (int i = 0; i < INPUT.size(); i++) {
				if (listOfRules.get(i).contains(SHINY_GOLD)) {
					if (canContainShinyGold[i] == false) {
						total++;
						canContainShinyGold[i] = true;
						changeMade = true;
					}
				} else {
					for (int k = 0; k < INPUT.size(); k++) {
						if (canContainShinyGold[i] == false && canContainShinyGold[k] == true
								&& listOfRules.get(i).contains(listOfBags[k])) {
							total++;
							canContainShinyGold[i] = true;
							changeMade = true;
						}
					}
				}
			}
		}

		System.out.println("Task 1 is " + total + " bags.");
	}

	private static void computeTask2() {
		int total = 0;
		total += calcBagContents(findBag(SHINY_GOLD));
		System.out.println("Task 2 is " + total + " bags.");
	}

	private static int calcBagContents(int bagNo) {
		int total = 0, badToFind, noOfSubBags = 0;
		String bagName;
		for (int i = 0; i < listOfRules.get(bagNo).size(); i++) {
			bagName = listOfRules.get(bagNo).get(i).substring(listOfRules.get(bagNo).get(i).indexOf(":") + 1);
			noOfSubBags = Integer.valueOf(listOfRules.get(bagNo).get(i).substring(0,listOfRules.get(bagNo).get(i).indexOf(":")));
			total += noOfSubBags;
			badToFind = findBag(bagName);
			total += noOfSubBags * calcBagContents(badToFind);
		}
		return total;
	}

	private static int findBag(String bagToFind) {
		for (int i = 0; i < INPUT.size(); i++) {
			if (listOfBags[i].contains(bagToFind)) {
				return i;
			}
		}
		System.out.println("Kill me now");
		return 0;
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day7input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(scanner.nextLine());
		}
		scanner.close();
	}
}
