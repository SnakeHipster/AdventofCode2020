package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class DayFour {

	static ArrayList<String> INPUT;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();

		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}

		int noOfValidPasswords = 0;
		StringTokenizer tmp;
		String line, data;
		int number = 0;

		for (int i = 0; i < INPUT.size(); i++) {
			tmp = new StringTokenizer(INPUT.get(i));

			boolean currentValid = true;
			if (!(tmp.countTokens() == 8 || (tmp.countTokens() == 7 && !INPUT.get(i).contains("cid")))) {
				currentValid = false;
			}

			while (tmp.hasMoreTokens()) {
				line = tmp.nextElement().toString();
				data = line.substring(line.indexOf(":") + 1);

				switch (line.substring(0, 3)) {

				case "byr":
					number = Integer.valueOf(data);
					if (number > 2002 || number < 1920)
						currentValid = false;
					break;

				case "iyr":
					number = Integer.valueOf(data);
					if (number > 2020 || number < 2010)
						currentValid = false;
					break;

				case "eyr":
					number = Integer.valueOf(data);
					if (number > 2030 || number < 2020)
						currentValid = false;
					break;

				case "hgt":
					if (data.contains("cm")) {
						number = Integer.valueOf(data.replaceAll("cm", ""));
						if (number > 193 || number < 150)
							currentValid = false;
					} else if (data.contains("in")) {
						number = Integer.valueOf(data.replaceAll("in", ""));
						if (number > 76 || number < 59)
							currentValid = false;
					} else {
						currentValid = false;
					}
					break;

				case "hcl":
					if (!Pattern.matches("#[a-f0-9]{6}", data)) {
						currentValid = false;
					}
					break;

				case "ecl":
					boolean validEyecolour = false;
					if (data.contains("amb") || data.contains("blu") || data.contains("brn") || data.contains("gry")
							|| data.contains("grn") || data.contains("hzl") || data.contains("oth")) {
						validEyecolour = true;
					}
					if (!validEyecolour)
						currentValid = false;
					break;

				case "pid":
					if (!Pattern.matches("[0-9]{9}", data)) {
						currentValid = false;
					}
					break;
				}
			}

			if (currentValid) {
				noOfValidPasswords++;
			}
		}

		System.out.println("There are " + noOfValidPasswords + " valid passports.");

	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day4input.txt"));
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
