package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DayEleven {
	static ArrayList<String> INPUT;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}

		// System.out.println(findFreeSeatsP1());
		System.out.println(findFreeSeatsP2());
	}

	private static int findFreeSeatsP1() {
		ArrayList<String> seatPlan = new ArrayList<String>();
		ArrayList<String> seatPlanTmp = new ArrayList<String>();

		for (String line : INPUT) {
			seatPlan.add(line);
			seatPlanTmp.add(line);
		}

		boolean chaosIsALadder = true;
		String newLine;
		int iterations = 0;

		while (chaosIsALadder) {
			chaosIsALadder = false;

			for (int i = 0; i < seatPlan.size(); i++) {
				newLine = seatPlan.get(i);
				for (int k = 0; k < seatPlan.get(i).length(); k++) {
					switch (seatPlan.get(i).charAt(k)) {
					case ('L'):
						if (checkNoOfSeatsFreeP1(seatPlan, i, k) == 8) {
							newLine = newLine.substring(0, k) + '#' + newLine.substring(k + 1);
							seatPlanTmp.set(i, newLine);
							chaosIsALadder = true;
						}
						break;
					case ('#'):
						if (checkNoOfSeatsFreeP1(seatPlan, i, k) < 5) {
							newLine = newLine.substring(0, k) + 'L' + newLine.substring(k + 1);
							seatPlanTmp.set(i, newLine);
							chaosIsALadder = true;
						}
						break;
					}
				}
			}

			if (chaosIsALadder) {
				seatPlan = new ArrayList<String>();
				for (String line : seatPlanTmp) {
					seatPlan.add(line);
				}

				// Print the seating Plan for fun
				System.out.println("After Iteration " + iterations + ":");
				for (int i = 0; i < seatPlan.size(); i++) {
					System.out.println(seatPlan.get(i));
				}
				System.out.println();
				iterations++;
			}
		}

		// Count free Seats
		int noOfFreeSeats = 0;
		for (int i = 0; i < seatPlan.size(); i++) {
			for (int k = 0; k < seatPlan.get(i).length(); k++) {
				if (seatPlan.get(i).charAt(k) == '#')
					noOfFreeSeats++;
			}
		}

		return noOfFreeSeats;
	}

	private static int checkNoOfSeatsFreeP1(ArrayList<String> seatPlan, int i, int k) {
		char seat[] = new char[8];

		for (int index = 0; index < 8; index++) {
			seat[index] = 'X';
		}

		// 012
		// 3.4
		// 567

		if (i == 0) {
			seat[0] = 'L';
			seat[1] = 'L';
			seat[2] = 'L';
		} else if (i == seatPlan.size() - 1) {
			seat[5] = 'L';
			seat[6] = 'L';
			seat[7] = 'L';
		}

		if (k == 0) {
			seat[0] = 'L';
			seat[3] = 'L';
			seat[5] = 'L';
		} else if (k == seatPlan.get(i).length() - 1) {
			seat[2] = 'L';
			seat[4] = 'L';
			seat[7] = 'L';
		}

		for (int index = 0; index < 8; index++) {
			if (seat[index] == 'X') {
				switch (index) {
				case 0:
					seat[0] = seatPlan.get(i - 1).charAt(k - 1);
					break;
				case 1:
					seat[1] = seatPlan.get(i - 1).charAt(k);
					break;
				case 2:
					seat[2] = seatPlan.get(i - 1).charAt(k + 1);
					break;
				case 3:
					seat[3] = seatPlan.get(i).charAt(k - 1);
					break;
				case 4:
					seat[4] = seatPlan.get(i).charAt(k + 1);
					break;
				case 5:
					seat[5] = seatPlan.get(i + 1).charAt(k - 1);
					break;
				case 6:
					seat[6] = seatPlan.get(i + 1).charAt(k);
					break;
				case 7:
					seat[7] = seatPlan.get(i + 1).charAt(k + 1);
					break;
				}
			}
		}

		int freeSeats = 0;
		for (int index = 0; index < 8; index++) {
			if (seat[index] == 'L' || seat[index] == '.') {
				freeSeats++;
			}
		}

		return freeSeats;
	}

	private static int findFreeSeatsP2() {
		ArrayList<String> seatPlan = new ArrayList<String>();
		ArrayList<String> seatPlanTmp = new ArrayList<String>();

		for (String line : INPUT) {
			seatPlan.add(line);
			seatPlanTmp.add(line);
		}

		boolean chaosIsALadder = true;
		String newLine;
		int iterations = 0;

		while (chaosIsALadder) {
			chaosIsALadder = false;

			for (int i = 0; i < seatPlan.size(); i++) {
				newLine = seatPlan.get(i);
				for (int k = 0; k < seatPlan.get(i).length(); k++) {
					switch (seatPlan.get(i).charAt(k)) {
					case ('L'):
						if (checkNoOfSeatsFreeP2(seatPlan, i, k) == 8) {
							newLine = newLine.substring(0, k) + '#' + newLine.substring(k + 1);
							seatPlanTmp.set(i, newLine);
							chaosIsALadder = true;
						}
						break;
					case ('#'):
						if (checkNoOfSeatsFreeP2(seatPlan, i, k) < 4) {
							newLine = newLine.substring(0, k) + 'L' + newLine.substring(k + 1);
							seatPlanTmp.set(i, newLine);
							chaosIsALadder = true;
						}
						break;
					}
				}
			}

			if (chaosIsALadder) {
				seatPlan = new ArrayList<String>();
				for (String line : seatPlanTmp) {
					seatPlan.add(line);
				}

				// Print the seating Plan for fun
				System.out.println("After Iteration " + iterations + ":");
				for (int i = 0; i < seatPlan.size(); i++) {
					System.out.println(seatPlan.get(i));
				}
				System.out.println();
				iterations++;
			}
		}

		// Count free Seats
		int noOfFreeSeats = 0;
		for (int i = 0; i < seatPlan.size(); i++) {
			for (int k = 0; k < seatPlan.get(i).length(); k++) {
				if (seatPlan.get(i).charAt(k) == '#')
					noOfFreeSeats++;
			}
		}

		return noOfFreeSeats;
	}

	private static int checkNoOfSeatsFreeP2(ArrayList<String> seatPlan, int i, int k) {
		int noOfFreeSeats = 0;
		boolean occupiedSeatFound;

		// up
		occupiedSeatFound = false;
		for (int j = i; j > 0; j--) {
			if (seatPlan.get(j - 1).charAt(k) == '#') {
				occupiedSeatFound = true;
				break;
			} else if (seatPlan.get(j - 1).charAt(k) == 'L') {
				break;
			}
		}
		if (!occupiedSeatFound)
			noOfFreeSeats++;

		// down
		occupiedSeatFound = false;
		for (int j = i + 1; j < seatPlan.size(); j++) {
			if (seatPlan.get(j).charAt(k) == '#') {
				occupiedSeatFound = true;
				break;
			} else if (seatPlan.get(j).charAt(k) == 'L') {
				break;
			}
		}
		if (!occupiedSeatFound)
			noOfFreeSeats++;

		// left
		occupiedSeatFound = false;
		for (int j = k; j > 0; j--) {
			if (seatPlan.get(i).charAt(j - 1) == '#') {
				occupiedSeatFound = true;
				break;
			} else if (seatPlan.get(i).charAt(j - 1) == 'L') {
				break;
			}
		}
		if (!occupiedSeatFound)
			noOfFreeSeats++;

		// right
		occupiedSeatFound = false;
		for (int j = k + 1; j < seatPlan.get(i).length(); j++) {
			if (seatPlan.get(i).charAt(j) == '#') {
				occupiedSeatFound = true;
				break;
			} else if (seatPlan.get(i).charAt(j) == 'L') {
				break;
			}
		}
		if (!occupiedSeatFound)
			noOfFreeSeats++;

		boolean endHit = false;
		int offset = 0;

		// top left
		endHit = false;
		offset = 0;
		while (!endHit) {
			offset++;
			if (i - offset < 0 || k - offset < 0 || seatPlan.get(i - offset).charAt(k - offset) == 'L') { // boundary
				endHit = true;
				noOfFreeSeats++;
				break;
			} else if (seatPlan.get(i - offset).charAt(k - offset) == '#') { // occupied seat
				endHit = true;
				break;
			}
		}

		// top right
		endHit = false;
		offset = 0;
		while (!endHit) {
			offset++;
			if (i - offset < 0 || k + offset >= seatPlan.get(0).length()
					|| seatPlan.get(i - offset).charAt(k + offset) == 'L') { // boundary
				endHit = true;
				noOfFreeSeats++;
				break;
			} else if (seatPlan.get(i - offset).charAt(k + offset) == '#') { // occupied seat
				endHit = true;
				break;
			}
		}

		// bottom right
		endHit = false;
		offset = 0;
		while (!endHit) {
			offset++;
			if (i + offset >= seatPlan.size() || k + offset >= seatPlan.get(0).length()
					|| seatPlan.get(i + offset).charAt(k + offset) == 'L') { // boundary
				endHit = true;
				noOfFreeSeats++;
				break;
			} else if (seatPlan.get(i + offset).charAt(k + offset) == '#') { // occupied seat
				endHit = true;
				break;
			}
		}

		// bottom left
		endHit = false;
		offset = 0;
		while (!endHit) {
			offset++;
			if (k - offset < 0 || i + offset >= seatPlan.size() || seatPlan.get(i + offset).charAt(k - offset) == 'L') { // boundary
				endHit = true;
				noOfFreeSeats++;
				break;
			} else if (seatPlan.get(i + offset).charAt(k - offset) == '#') { // occupied seat
				endHit = true;
				break;
			}
		}

		return noOfFreeSeats;
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day11input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(scanner.nextLine());
		}
		scanner.close();
	}
}
