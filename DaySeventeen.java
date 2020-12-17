package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DaySeventeen {
	static ArrayList<String> INPUT;
	static ArrayList<ArrayList<String>> pocketDimension;
	static int CENTER;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();
		CENTER = 20;
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}
		initPocketDimension();
		printState(0);
		simulateCycles(6);
		countActiveCubes();

		System.out.println();
	}

	private static void initPocketDimension() {
		pocketDimension = new ArrayList<ArrayList<String>>();
		ArrayList<String> slice;
		String row;

		// Set all to inactive
		for (int i = 0; i <= CENTER * 2; i++) {
			slice = new ArrayList<String>();
			for (int j = 0; j <= CENTER * 2; j++) {
				row = "";
				for (int k = 0; k < CENTER * 2; k++) {
					row += ".";
				}
				slice.add(row);
			}
			pocketDimension.add(slice);
		}

		// Setup initial slice for cycle 0
		int middleSlice = CENTER;
		int topLeftRow = CENTER - (INPUT.size()/2);
		int topLeftColumn = CENTER - (INPUT.size()/2);
		slice = pocketDimension.get(middleSlice);
		for (int i = 0; i < INPUT.size(); i++) {
			row = slice.get(i + topLeftRow);
			for (int j = 0; j < INPUT.size(); j++) {
				row = row.substring(0, j + topLeftColumn) + INPUT.get(i).charAt(j)
						+ row.substring(j + topLeftColumn + 1);
			}
			slice.set(i + topLeftRow, row);
		}
		pocketDimension.set(middleSlice, slice);
		System.out.println();
	}

	private static void simulateCycles(int cycles) {
		ArrayList<ArrayList<String>> tmpPocketDimension;
		char newState;
		ArrayList<String> slice;
		String row;

		for (int i = 0; i < cycles; i++) {
			tmpPocketDimension = new ArrayList<ArrayList<String>>();
			// Loop though and populate temp pocket dimension
			for (int z = 0; z < pocketDimension.size(); z++) {
				slice = new ArrayList<String>();
				for (int x = 0; x < pocketDimension.get(z).size(); x++) {
					row = "";
					for (int y = 0; y < pocketDimension.get(z).get(x).length(); y++) {
						newState = findNewState(z, x, y, pocketDimension.get(z).get(x).charAt(y));
						row += newState;
					}
					slice.add(row);
				}
				tmpPocketDimension.add(slice);
			}
			pocketDimension = new ArrayList<ArrayList<String>>(tmpPocketDimension);

			printState(i + 1);
		}
	}

	private static char findNewState(int zmeme, int xmeme, int ymeme, char currState) {
		int total = 0;
		for (int z = zmeme - 1; z <= zmeme + 1; z++) {
			for (int x = xmeme - 1; x <= xmeme + 1; x++) {
				for (int y = ymeme - 1; y <= ymeme + 1; y++) {
					// check if is the point in question
					if (z == zmeme && x == xmeme && y == ymeme)
						continue;

					// check if out of bounds
					if (z < 0 || z >= pocketDimension.size() || x < 0 || x >= pocketDimension.get(z).size() || y < 0
							|| y >= pocketDimension.get(z).get(x).length())
						continue;

					if (pocketDimension.get(z).get(x).charAt(y) == '#') {
						total++;
					}
				}
			}
		}

		if (total == 2 && currState == '#')
			return '#';
		if (total == 3)
			return '#';
		return '.';
	}

	private static void printState(int cycle) {
		System.out.println("After cycle " + cycle);
		for (int i = 0; i < pocketDimension.size(); i++) {
			System.out.println("z=" + (i - ((pocketDimension.size() - 1) / 2)));
			for (int j = 0; j < pocketDimension.size(); j++) {
				System.out.println(pocketDimension.get(i).get(j));
			}
		}
		System.out.println();
	}

	private static void countActiveCubes() {
		int total = 0;
		for (int i = 0; i < pocketDimension.size(); i++) {
			for (int j = 0; j < pocketDimension.get(i).size(); j++) {
				for (int k = 0; k < pocketDimension.get(i).get(j).length(); k++) {
					if (pocketDimension.get(i).get(j).charAt(k) == '#')
						total++;
				}
			}
		}
		System.out.println("There are " + total + " cubes active.");
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day17input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(scanner.nextLine());
		}
		scanner.close();
	}

}
