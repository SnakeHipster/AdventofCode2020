package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DayNine {
	static ArrayList<Long> INPUT;

	public static void main(String[] args) {
		INPUT = new ArrayList<Long>();
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}
		
		int noise = 25;
		long brokenNumber = findFirstBrokenNumber(noise);
		System.out.println("First broken number is " + brokenNumber + ".");
		
		System.out.println("Encryption Weakness is " + findEncryptionWeakness(brokenNumber) + ".");

	}

	private static Long findFirstBrokenNumber(int noise) {
		ArrayList<Long> preamble;
		boolean foundPair;

		for (int i = (int) noise; i < INPUT.size(); i++) {
			preamble = new ArrayList<Long>();
			for (int k = i - noise; k < i; k++) {
				preamble.add(INPUT.get(k));
			}

			foundPair = false;
			for (int j = 0; j < preamble.size(); j++) {
				for (int l = j; l < preamble.size(); l++) {
					if (preamble.get(j) + preamble.get(l) == INPUT.get(i))
						foundPair = true;
				}
			}
			if (foundPair == false)
				return INPUT.get(i);
		}

		return (long) 0;
	}

	private static long findEncryptionWeakness(long brokenNumber) {
		boolean lessThanBrokenNumber;
		long total, min, max;
		
		for (int i = 0; i < INPUT.size(); i++) {
			lessThanBrokenNumber = true;
			while (lessThanBrokenNumber) {
				total = 0;
				min = 0;
				max = 0;
				for (int k = i; k < INPUT.size(); k++) {
					if(min == 0 || INPUT.get(k) < min) {
						min = INPUT.get(k);
					} else if (max == 0 || INPUT.get(k) > max) {
						max = INPUT.get(k);
					}

					total += INPUT.get(k);
					if (total == brokenNumber) {
						return (min + max);
					} else if (total > brokenNumber) {
						lessThanBrokenNumber = false;
						break;
					}
				}
			}
			
		}
		return 0;
	}
	
	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day9input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(Long.valueOf(scanner.nextLine()));
		}
		scanner.close();
	}
}
