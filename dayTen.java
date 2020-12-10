package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class dayTen {
	static ArrayList<Long> INPUT;
	static List<Long> SUBLIST;
	static int maxJolts = 0;

	public static void main(String[] args) {
		INPUT = new ArrayList<Long>();
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}

		System.out.println(computePart1());
		System.out.println(computePart2());

	}

	private static int computePart1() {
		int oneJolt = 0, twoJolt = 0, threeJolt = 0;
		long currentJolts = 0;
		ArrayList<Long> adapters = new ArrayList<Long>();
		boolean allAdaptersUsed = false, currentFound = false;

		for (int i = 0; i < INPUT.size(); i++) {
			adapters.add(INPUT.get(i));
		}

		while (!allAdaptersUsed) {
			for (int i = 0; i < adapters.size(); i++) {
				if (currentJolts + 1 == adapters.get(i)) {
					currentJolts = adapters.get(i);
					oneJolt++;
					adapters.remove(i);
					currentFound = true;
					break;
				}
			}
			if (currentFound != true) {
				for (int i = 0; i < adapters.size(); i++) {
					if (currentJolts + 2 == adapters.get(i)) {
						currentJolts = adapters.get(i);
						twoJolt++;
						adapters.remove(i);
						currentFound = true;
						break;
					}
				}
			}
			if (currentFound != true) {
				for (int i = 0; i < adapters.size(); i++) {
					if (currentJolts + 3 == adapters.get(i)) {
						currentJolts = adapters.get(i);
						threeJolt++;
						adapters.remove(i);
						break;
					}
				}
			}

			if (adapters.isEmpty())
				allAdaptersUsed = true;

			currentFound = false;
		}
		// Always three higher meme
		threeJolt++;
		return oneJolt * threeJolt;
	}

	private static long computePart2() {
		long total = 0, currentJolts = 0, grandTotal = 1;

		// Sort list
		INPUT.add(new Long(0));
		Collections.sort(INPUT);
		maxJolts = Math.toIntExact(INPUT.get(INPUT.size() - 1));

		ArrayList<Integer> checkpoints = findCheckpoints();
		checkpoints.add(maxJolts);

		long startCheckpoint = 0, endCheckpoint = 0;

		for (int i = 0; i < checkpoints.size(); i++) {
			startCheckpoint = endCheckpoint;
			endCheckpoint = checkpoints.get(i);

			SUBLIST = INPUT.subList(INPUT.indexOf(startCheckpoint), INPUT.indexOf(endCheckpoint) + 1);
			currentJolts = SUBLIST.get(0);

			for (int k = 1; k < 4; k++) {
				if (SUBLIST.contains(currentJolts + k)) {
					total = followAdapter(currentJolts + k, total, endCheckpoint);
				}
			}
			System.out.println("Checkpoint " + (i+1) + " of " + checkpoints.size() + " between " + startCheckpoint + " & " + endCheckpoint + " total is " + total);
			grandTotal = grandTotal * total;
			total = 0;

		}

		return grandTotal;
	}

	private static long followAdapter(long currentJolts, long total, long checkpoint) {
		//System.out.println("I'm still kicking! " + currentJolts + " " + total + " " + checkpoint);

		if (checkpoint == currentJolts) {
			return total + 1;
		}

		for (int i = 1; i < 4; i++) {
			if (SUBLIST.contains(currentJolts + i)) {
				total = followAdapter(currentJolts + i, total, checkpoint);
			}
		}

		return total;
	}

	private static ArrayList<Integer> findCheckpoints() {
		ArrayList<Integer> checkpoints = new ArrayList<Integer>();
		long num;
		for (int i = 0; i < INPUT.size(); i++) {
			num = INPUT.get(i);
			if (!INPUT.contains(num + 1) && !INPUT.contains(num + 2) && num != maxJolts) {
				checkpoints.add(INPUT.get(i).intValue());
			}
		}
		return checkpoints;
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day10input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(Long.valueOf(scanner.nextLine()));
		}
		scanner.close();
	}
}
