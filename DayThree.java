package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DayThree {
	static ArrayList<String> INPUT;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();

		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}
			
		long total = 0;
		
		total = slideFunHappyTime(1,1);
		total = total * slideFunHappyTime(1,3);
		total = total * slideFunHappyTime(1,5);
		total = total * slideFunHappyTime(1,7);
		total = total * slideFunHappyTime(2,1);
		
		System.out.println("Total number of trees is " + total + ".");

	}
	
	private static int slideFunHappyTime(int down, int along) {
		int x = 0, noOfTrees = 0, maxWidth = INPUT.get(0).length();
		
		
		for (int y = 0; y < INPUT.size(); y = y + down) {
			// Check position
			if (checkIfTree(x,y))
				noOfTrees++;
			
			// Increment position
			if (x + along >= maxWidth) {
				x = 0 + (x + along - maxWidth);
			} else {
				x = x + along;
			}
		}
		return noOfTrees;
	}

	private static boolean checkIfTree(int x, int y) {
		if (INPUT.get(y).charAt(x) == '#') {
			return true;
		}
		return false;
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day3input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(scanner.nextLine());
		}
		scanner.close();
	}

}
