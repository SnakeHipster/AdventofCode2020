package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DayTwoPart2 {
	static ArrayList<String> INPUT;

	public static void main(String[] args) {
		int noOfValidPasswords = 0;
		INPUT = new ArrayList<String>();

		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}

		for (int i = 0; i < INPUT.size(); i++) {
			if (validatePassword(INPUT.get(i)))
				noOfValidPasswords++;
		}

		System.out.println("There are " + noOfValidPasswords + " valid passwords out of " + INPUT.size() + ".");

	}

	private static boolean validatePassword(String dataEntry) {
		int min = 0, max = 0;
		char letter;
		String password, tmpRules;
		StringTokenizer tmp = new StringTokenizer(dataEntry);

		// Split numbers
		tmpRules = tmp.nextToken();
		min = Integer.parseInt(tmpRules.substring(0, tmpRules.indexOf("-")));
		max = Integer.parseInt(tmpRules.substring(tmpRules.indexOf("-"), tmpRules.length()).replace("-", ""));
		
		// Get letter
		letter = tmp.nextToken().charAt(0);
		
		// Get password
		password = tmp.nextToken();
			
		// Check the password given rules
		if (password.charAt(min-1) == letter)
			if (password.charAt(max-1) != letter)
				return true;
		if (password.charAt(min-1) != letter)
			if (password.charAt(max-1) == letter)
				return true;
		return false;
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day2input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(scanner.nextLine());
		}
		scanner.close();
	}

}
