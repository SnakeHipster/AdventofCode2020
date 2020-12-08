package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DayEight {
	static ArrayList<String> INPUT;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}

		int accumulatorValue = interpretCodePart1();

		System.out.println("Part 1 value at point where code starts looping is " + accumulatorValue);

		accumulatorValue = interpretCodePart2();

		System.out.println("Part 2 value at point where code starts looping is " + accumulatorValue);
	}

	private static int interpretCodePart1() {
		boolean notInfiniteLoop = true;
		int accumulatorValue = 0, index = 0;
		boolean instructionTraveresedBefore[] = new boolean[INPUT.size()];
		StringTokenizer tokens;
		while (notInfiniteLoop) {
			tokens = new StringTokenizer(INPUT.get(index));
			instructionTraveresedBefore[index] = true;

			switch (tokens.nextElement().toString()) {
			case ("nop"):
				index++;
				break;
			case ("acc"):
				accumulatorValue += Integer.valueOf(tokens.nextElement().toString());
				index++;
				break;
			case ("jmp"):
				index += Integer.valueOf(tokens.nextElement().toString());
				break;
			}

			if (instructionTraveresedBefore[index] == true) {
				notInfiniteLoop = false;
			}
		}
		return accumulatorValue;
	}

	private static int interpretCodePart2() {
		boolean notInfiniteLoop;
		int accumulatorValue = 0, index;
		boolean instructionTraveresedBefore[];
		StringTokenizer tokens;
		String instruction = "";

		for (int i = 0; i < INPUT.size(); i++) {
			// reset progress
			instructionTraveresedBefore = new boolean[INPUT.size()];
			index = 0;
			accumulatorValue = 0;
			notInfiniteLoop = true;

			while (notInfiniteLoop) {
				instructionTraveresedBefore[index] = true;

				tokens = new StringTokenizer(INPUT.get(index));
				instruction = tokens.nextElement().toString();

				if (index == i) {
					switch (instruction) {
					case ("nop"):
						instruction = "jmp";
						break;
					case ("jmp"):
						instruction = "nop";
						break;
					}
				}

				switch (instruction) {
				case ("nop"):
					index++;
					break;
				case ("acc"):
					accumulatorValue += Integer.valueOf(tokens.nextElement().toString());
					index++;
					break;
				case ("jmp"):
					index += Integer.valueOf(tokens.nextElement().toString());
					break;
				}

				if (index >= INPUT.size()) {
					System.out.println("Instruction that needs flipping is " + i);
					return accumulatorValue;
				} else if (instructionTraveresedBefore[index] == true) {
					notInfiniteLoop = false;
				}
			}
		}

		return accumulatorValue;
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day8input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(scanner.nextLine());
		}
		scanner.close();
	}
}
