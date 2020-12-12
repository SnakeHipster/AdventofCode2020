package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DayTwelve {
	static ArrayList<String> INPUT;
	static int northSouth, eastWest;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}
		
		
		traverseInstructionsP1();
		System.out.println(calculateManhattanDistance());
		traverseInstructionsP2();
		System.out.println(calculateManhattanDistance());
	}

	private static void traverseInstructionsP2() {
		northSouth = 0;
		eastWest = 0;
		int wNorthSouth = 1;
		int wEastWest = 10;
		char command;
		int value, tmp, iterate;
		
		for (int i = 0; i < INPUT.size(); i++) {
			command = INPUT.get(i).charAt(0);
			value = Integer.valueOf(INPUT.get(i).substring(1));
			switch (command) {
			case 'N':
				wNorthSouth += value;
				break;
			case 'S':
				wNorthSouth -= value;
				break;
			case 'E':
				wEastWest += value;
				break;
			case 'W':
				wEastWest -= value;
				break;
			case 'L':
				iterate = 0;
				if (value == 90) {
					iterate = 1;
				} else if (value == 180) {
					iterate = 2;
				} else if (value == 270) {
					iterate = 3;
				}
				
				for (int j = 0; j < iterate; j++) {
					tmp = wEastWest;
					wEastWest = wNorthSouth * -1;
					wNorthSouth = tmp;
				}
				break;
			case 'R':
				iterate = 0;
				if (value == 90) {
					iterate = 1;
				} else if (value == 180) {
					iterate = 2;
				} else if (value == 270) {
					iterate = 3;
				}
				
				for (int j = 0; j < iterate; j++) {
					tmp = wNorthSouth;
					wNorthSouth = wEastWest * -1;
					wEastWest = tmp;
				}
				break;
			case 'F':
				northSouth += value * (northSouth + wNorthSouth - northSouth);
				eastWest += value * (eastWest + wEastWest - eastWest);
			}
			System.out.println("Positions:");
			System.out.println("===============");
			System.out.println("Ship: " + northSouth + ":" + eastWest);
			System.out.println("Wayp: " + wNorthSouth + ":" + wEastWest);
		}
	}

	private static void traverseInstructionsP1() {
		northSouth = 0;
		eastWest = 0;
		int facing = 90;
		char command;
		int value;
		for (int i = 0; i < INPUT.size(); i++) {
			command = INPUT.get(i).charAt(0);
			value = Integer.valueOf(INPUT.get(i).substring(1));
			switch (command) {
			case 'N':
				northSouth += value;
				break;
			case 'S':
				northSouth -= value;
				break;
			case 'E':
				eastWest += value;
				break;
			case 'W':
				eastWest -= value;
				break;
			case 'L':
				if (facing - value < 0) {
					facing = 360 - (value - facing);
				} else {
					facing -= value;
				}
				break;
			case 'R':
				if (facing + value >= 360) {
					facing = value + facing -360;
				} else {
					facing += value;
				}
				break;
			case 'F':
				switch (facing) {
				case 0:
					northSouth += value;
					break;
				case 90:
					eastWest += value;
					break;
				case 180:
					northSouth -= value;
					break;
				case 270:
					eastWest -= value;
					break;
				}
				break;
			}
		}
	}

	private static int calculateManhattanDistance() {	
		return Math.abs(northSouth) + Math.abs(eastWest);
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day12input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(scanner.nextLine());
		}
		scanner.close();
	}

}
