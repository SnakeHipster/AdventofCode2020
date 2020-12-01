package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DayOnePart1 {
	static ArrayList<Integer> INPUT;

	public static void main(String[] args) {
		boolean foundNumbers = false;
		int currentNumber, index = 0;
		INPUT = new ArrayList<Integer>();

		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}

		while (foundNumbers == false & index < INPUT.size()) {
			currentNumber = INPUT.get(index);
			foundNumbers = searchList(currentNumber, index);
			index++;
		}
		
		if (!foundNumbers) {
			System.out.println("Waaa did not find number :(");
		}

	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day1input.txt"));
		while(scanner.hasNextInt()){
			INPUT.add(scanner.nextInt());
		}
		scanner.close();
	}

	static boolean searchList(int currentNumber, int index) {
		boolean found = false;
		for (int k = index; k < INPUT.size(); k++) {
			if (2020 - INPUT.get(k) - currentNumber == 0) {
				found = true;
				printResult(currentNumber, INPUT.get(k));
			}
		}
		return found;
	}

	static void printResult(int firstNumber, int secondNumber) {
		System.out.println("Numbers are " + firstNumber + " & " + secondNumber + ".");
		System.out.println("Multipled together they are " + (firstNumber * secondNumber) + ".");
	}

}
