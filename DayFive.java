package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DayFive {
	static ArrayList<String> INPUT;
	static int[][] planeSeats;
	static int myRow, myColumn, mySeatID;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();
		planeSeats = new int[128][8];

		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}

		// Init planeSeats
		for (int r = 0; r < 128; r++) {
			for (int c = 0; c < 8; c++) {
				planeSeats[r][c] = 0;
			}
		}

		int row, column, seatID, highestSeatID = 0;
		for (int i = 0; i < INPUT.size(); i++) {
			row = calculateRow(INPUT.get(i).substring(0, 7));
			column = calculateColumn(INPUT.get(i).substring(7, 10));
			seatID = (row * 8) + column;
			if (seatID > highestSeatID)
				highestSeatID = seatID;

			planeSeats[row][column] = seatID;
		}

		System.out.println("Highest SeatID is: " + highestSeatID);

		findMySeat();
		System.out.println("My seat is Row: " + myRow + " Column: " + myColumn + " SeatId: " + mySeatID);
	}

	private static void findMySeat() {
		for (int r = 1; r < 127; r++) {
			for (int c = 0; c < 8; c++) {
				if (planeSeats[r][c] == 0) {
					boolean found = false;
					if (c == 0) {
						if (planeSeats[r - 1][7] != 0 && planeSeats[r][c + 1] != 0)
							found = true;
					} else if (c == 7) {
						if (planeSeats[r][c - 1] != 0 && planeSeats[r + 1][0] != 0)
							found = true;
					} else {
						if (planeSeats[r][c - 1] != 0 && planeSeats[r][c + 1] != 0)
							found = true;
					}
					if (found) {
						myRow = r;
						myColumn = c;
						mySeatID = (r * 8) + c;
					}
				}
			}
		}

	}

	private static int calculateRow(String input) {
		int min = 0, max = 127, index = 0, changeBy;

		while (min != max) {
			changeBy = ((max - min - 1) / 2) + 1;
			if (input.charAt(index) == 'F') {
				max = max - changeBy;
			} else {
				min = min + changeBy;
			}
			index++;
		}
		return min;
	}

	private static int calculateColumn(String input) {
		int min = 0, max = 7, index = 0, changeBy;

		while (min != max) {
			changeBy = ((max - min - 1) / 2) + 1;
			if (input.charAt(index) == 'L') {
				max = max - changeBy;
			} else {
				min = min + changeBy;
			}
			index++;
		}
		return min;
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day5input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(scanner.nextLine());
		}
		scanner.close();
	}

}
