package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DayEighteen {
	static ArrayList<String> INPUT;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}
		System.out.println(sumOfResultingValuesP1());
		System.out.println(sumOfResultingValuesP2());
	}

	private static long sumOfResultingValuesP1() {
		long total = 0;
		for (int i = 0; i < INPUT.size(); i++) {
			total += processLineP1(INPUT.get(i));
		}
		return total;
	}

	private static long processLineP1(String line) {
		long result, subtotal;
		boolean foundNext, parenthesesExist;
		int startIndex = 0;

		// resolve parentheses
		parenthesesExist = true;
		foundNext = false;
		while (parenthesesExist) {
			// find next sub problem
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '(') {
					foundNext = true;
					startIndex = i;
				} else if (foundNext && line.charAt(i) == ')') {
					subtotal = doSomeMath(line.substring(startIndex + 1, i));
					line = line.substring(0, startIndex) + subtotal + line.substring(i + 1);
					break;
				}
			}

			// are we done?
			parenthesesExist = false;
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '(' || line.charAt(i) == ')') {
					parenthesesExist = true;
				}
			}
		}

		// now find true answer
		result = doSomeMath(line);
		return result;
	}

	private static long sumOfResultingValuesP2() {
		long total = 0;
		for (int i = 0; i < INPUT.size(); i++) {
			total += processLineP2(INPUT.get(i));
		}
		return total;
	}

	private static long processLineP2(String line) {
		long result, subtotal;
		boolean foundNext, parenthesesExist;
		int startIndex = 0;
		String tmp;

		// resolve parentheses
		parenthesesExist = true;
		foundNext = false;
		while (parenthesesExist) {
			// find next sub problem
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '(') {
					foundNext = true;
					startIndex = i;
				} else if (foundNext && line.charAt(i) == ')') {
					tmp = resolveAddition(line.substring(startIndex + 1, i));
					subtotal = doSomeMath(tmp);
					line = line.substring(0, startIndex) + subtotal + line.substring(i + 1);
					break;
				}
			}

			// are we done?
			parenthesesExist = false;
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '(' || line.charAt(i) == ')') {
					parenthesesExist = true;
				}
			}
		}

		// now find true answer
		line = resolveAddition(line);
		result = doSomeMath(line);

		return result;
	}
	
	private static String resolveAddition(String line) {
		boolean additionExists = true, foundNext = false;
		int startIndex = -1, endIndex = -1;
		long subtotal = 0;
		line += " ";
		
		while (additionExists) {
			// find next sub problem
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '+') {
					foundNext = true;
				} else if (foundNext && line.charAt(i) == ' ' && endIndex != -1) {
					subtotal = doSomeMath(line.substring(startIndex, endIndex+1));
					line = line.substring(0, startIndex) + subtotal + line.substring(i);
					break;
				} else if (!foundNext && Character.isDigit(line.charAt(i)) && startIndex == -1) {
					startIndex = i;
				} else if (foundNext && Character.isDigit(line.charAt(i))) {
					endIndex = i;
				} else if (line.charAt(i) == '*') {
					startIndex = -1;
					endIndex = -1;
					foundNext = false;
				}
			}
			foundNext = false;
			startIndex = -1;
			endIndex = -1;

			// are we done?
			additionExists = false;
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '+') {
					additionExists = true;
				}
			}
		}
		return line;
	}

	private static long doSomeMath(String line) {
		long result;
		String tmp;
		StringTokenizer mathsProblem;
		boolean addNext;

		mathsProblem = new StringTokenizer(line);
		addNext = true;
		result = 0;

		while (mathsProblem.hasMoreTokens()) {
			tmp = mathsProblem.nextToken();

			if (tmp.equals("+")) {
				addNext = true;
			} else if (tmp.equals("*")) {
				addNext = false;
			} else {
				if (addNext) {
					result += Long.valueOf(tmp);
				} else {
					result *= Long.valueOf(tmp);
				}
			}
		}
		return result;
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day18input.txt"));
		while (scanner.hasNext()) {
			INPUT.add(scanner.nextLine());
		}
		scanner.close();
	}

	static public class Calculation {
		long value;
		boolean addNext;
		int deepness;

		Calculation(long value, boolean addNext, int deepness) {
			this.value = value;
			this.addNext = addNext;
			this.deepness = deepness;
		}
	}
}
