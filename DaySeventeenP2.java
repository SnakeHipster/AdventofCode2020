package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DaySeventeenP2 {
	static ArrayList<String> INPUT;
	static ArrayList<ArrayList<ArrayList<String>>> IVDPocketDimension;
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
		IVDPocketDimension = new ArrayList<ArrayList<ArrayList<String>>>();
		ArrayList<ArrayList<String>> IIIDPocketDimension;
		ArrayList<String> slice;
		String row;

		// Set all to inactive
		for (int dank = 0; dank <= CENTER * 2; dank++) {
			IIIDPocketDimension = new ArrayList<ArrayList<String>>();
			for (int i = 0; i <= CENTER * 2; i++) {
				slice = new ArrayList<String>();
				for (int j = 0; j <= CENTER * 2; j++) {
					row = "";
					for (int k = 0; k < CENTER * 2; k++) {
						row += ".";
					}
					slice.add(row);
				}
				IIIDPocketDimension.add(slice);
			}
			IVDPocketDimension.add(IIIDPocketDimension);
		}

		// Setup initial slice for cycle 0
		int topLeftRow = CENTER - (INPUT.size() / 2);
		int topLeftColumn = CENTER - (INPUT.size() / 2);

		IIIDPocketDimension = IVDPocketDimension.get(CENTER);
		slice = IIIDPocketDimension.get(CENTER);
		for (int i = 0; i < INPUT.size(); i++) {
			row = slice.get(i + topLeftRow);
			for (int j = 0; j < INPUT.size(); j++) {
				row = row.substring(0, j + topLeftColumn) + INPUT.get(i).charAt(j)
						+ row.substring(j + topLeftColumn + 1);
			}
			slice.set(i + topLeftRow, row);
		}

		IIIDPocketDimension.set(CENTER, slice);
		IVDPocketDimension.set(CENTER, IIIDPocketDimension);
	}

	private static void simulateCycles(int cycles) {
		ArrayList<ArrayList<ArrayList<String>>> tmpPocketDimension;
		char newState;
		ArrayList<ArrayList<String>> IIIDPocketDimension;
		ArrayList<String> slice;
		String row;

		for (int i = 0; i < cycles; i++) {
			tmpPocketDimension = new ArrayList<ArrayList<ArrayList<String>>>();
			// Loop though and populate temp pocket dimension
			for (int dank = 0; dank < IVDPocketDimension.size(); dank++) {
				IIIDPocketDimension = new ArrayList<ArrayList<String>>();
				for (int z = 0; z < IVDPocketDimension.get(dank).size(); z++) {
					slice = new ArrayList<String>();
					for (int x = 0; x < IVDPocketDimension.get(dank).get(z).size(); x++) {
						row = "";
						for (int y = 0; y < IVDPocketDimension.get(dank).get(z).get(x).length(); y++) {
							newState = findNewState(dank, z, x, y,
									IVDPocketDimension.get(dank).get(z).get(x).charAt(y));
							row += newState;
						}
						slice.add(row);
					}
					IIIDPocketDimension.add(slice);
				}
				tmpPocketDimension.add(IIIDPocketDimension);
			}
			IVDPocketDimension = new ArrayList<ArrayList<ArrayList<String>>>(tmpPocketDimension);

			printState(i + 1);

		}
	}

	private static char findNewState(int dankmeme, int zmeme, int xmeme, int ymeme, char currState) {
		int total = 0;
		for (int dank = dankmeme - 1; dank <= dankmeme + 1; dank++) {
			for (int z = zmeme - 1; z <= zmeme + 1; z++) {
				for (int x = xmeme - 1; x <= xmeme + 1; x++) {
					for (int y = ymeme - 1; y <= ymeme + 1; y++) {
						// check if is the point in question
						if (dank == dankmeme && z == zmeme && x == xmeme && y == ymeme)
							continue;

						// check if out of bounds
						if (dank < 0 || dank >= IVDPocketDimension.size() || z < 0 || z >= IVDPocketDimension.get(dank).size() || x < 0 || x >= IVDPocketDimension.get(dank).get(z).size()
								|| y < 0 || y >= IVDPocketDimension.get(dank).get(z).get(x).length())
							continue;

						if (IVDPocketDimension.get(dank).get(z).get(x).charAt(y) == '#') {
							total++;
						}
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
		for (int dank = 0; dank < IVDPocketDimension.size(); dank++) {
			System.out.println("w=" + dank);
			for (int i = 0; i < IVDPocketDimension.get(dank).size(); i++) {
				System.out.println("z=" + (i - ((IVDPocketDimension.get(dank).size() - 1) / 2)));
				for (int j = 0; j < IVDPocketDimension.get(dank).size(); j++) {
					System.out.println(IVDPocketDimension.get(dank).get(i).get(j));
				}
			}
		}
		System.out.println();
	}

	private static void countActiveCubes() {
		int total = 0;
		for (int dank = 0; dank < IVDPocketDimension.size(); dank++) {
			for (int i = 0; i < IVDPocketDimension.get(dank).size(); i++) {
				for (int j = 0; j < IVDPocketDimension.get(dank).get(i).size(); j++) {
					for (int k = 0; k < IVDPocketDimension.get(dank).get(i).get(j).length(); k++) {
						if (IVDPocketDimension.get(dank).get(i).get(j).charAt(k) == '#')
							total++;
					}
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
