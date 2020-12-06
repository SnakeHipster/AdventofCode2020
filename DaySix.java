package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DaySix {
	static ArrayList<String> INPUT;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}
		int total = 0;
		for (int i =0 ; i < INPUT.size(); i++) {
			total += countGroupAnswers(INPUT.get(i));
		}
		
		System.out.println("Sum of all group answers is " + total + ".");
		
		total = 0;
		for (int i =0 ; i < INPUT.size(); i++) {
			total += countGroupAnswersAll(INPUT.get(i));
		}
		
		System.out.println("Sum of all answers every group member answered yes to is " + total + ".");

	}
	
	private static int countGroupAnswers(String group) {
		int noOfGroupAnswers = 0;
		for (char letter = 'a'; letter <= 'z'; letter++) {
			if (group.indexOf(letter) != -1) {
				noOfGroupAnswers++;
			}
		}
		return noOfGroupAnswers;
	}	
	
	private static int countGroupAnswersAll(String group) {
		StringTokenizer tmp = new StringTokenizer(group);
		boolean hasLetterInAll = true;
		int noOfGroupAnswers = 0;
		String tmpString;
		
		for (char letter = 'a'; letter <= 'z'; letter++) {
			tmp = new StringTokenizer(group);
			hasLetterInAll = true;
			while (tmp.hasMoreTokens()) {
				tmpString = tmp.nextElement().toString();
				if (tmpString.indexOf(letter) == -1) {
					hasLetterInAll = false;
				}
			}
			if (hasLetterInAll) {
				noOfGroupAnswers++;
			}
		}
		return noOfGroupAnswers;
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day6input.txt"));
		String line = "", tmp = "";
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			if (line.equals("")) {
				INPUT.add(tmp);
				tmp = "";
			} else {
				tmp = tmp + " " + line;
			}
		}
		INPUT.add(tmp);
		scanner.close();
	}

}
