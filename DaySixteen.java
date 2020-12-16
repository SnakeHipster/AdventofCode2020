package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class DaySixteen {
	static ArrayList<Rule> RULES;
	static Ticket MYTICKET;
	static ArrayList<Ticket> TICKETS;
	static String[] FIELDS;

	public static void main(String[] args) {
		RULES = new ArrayList<Rule>();
		TICKETS = new ArrayList<Ticket>();

		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}

		System.out.println("Error Rate is " + scanTickets());
		intitFields();
		findFields();
		printFields();
		System.out.println("Answer to part 2 is " + part2Answer());
	}

	private static long part2Answer() {
		long total = 1;
		for (int i = 0; i < MYTICKET.values.size(); i++) {
			if (FIELDS[i].contains("departure"))
				total *= MYTICKET.values.get(i);

		}
		return total;
	}

	private static void printFields() {
		for (int i = 0; i < FIELDS.length; i++) {
			System.out.println((i + 1) + " is " + FIELDS[i]);
		}
	}

	private static void findFields() {
		boolean foundAllFields = false, fieldMatchesValues = false;
		boolean[] potential;
		Rule tmp;
		int onlyOption;

		for (int i = 0; i < RULES.size(); i++) {
			potential = new boolean[FIELDS.length];
			for (int k = 0; k < FIELDS.length; k++) {
				fieldMatchesValues = true;
				fieldMatchesValues = isFieldValid(MYTICKET.values.get(k), RULES.get(i));
				for (int j = 0; j < TICKETS.size(); j++) {
					if (!fieldMatchesValues)
						break;
					if (TICKETS.get(j).valid) {
						fieldMatchesValues = isFieldValid(TICKETS.get(j).values.get(k), RULES.get(i));
					}
				}
				potential[k] = fieldMatchesValues;
			}
			tmp = RULES.get(i);
			tmp.potentialFields = potential;
			RULES.set(i, tmp);
		}

		while (!foundAllFields) {
			for (int i = 0; i < RULES.size(); i++) {
				if (RULES.get(i).matched)
					continue;
				onlyOption = findOnlyOption(RULES.get(i));
				if (onlyOption != -1) {
					FIELDS[onlyOption] = RULES.get(i).name;
					tmp = RULES.get(i);
					tmp.matched = true;
					RULES.set(i, tmp);
				}
			}

			// Are we done?
			foundAllFields = true;
			for (int i = 0; i < MYTICKET.values.size(); i++) {
				if (FIELDS[i].contains("UNKNOWN")) {
					foundAllFields = false;
					break;
				}
			}
		}

	}

	private static int findOnlyOption(Rule rule) {
		boolean optionFound = false;
		int option = -1;
		for (int i = 0; i < rule.potentialFields.length; i++) {
			if (rule.potentialFields[i] == true && FIELDS[i].contains("UNKNOWN")) {
				if (optionFound == true) {
					return -1;
				} else {
					option = i;
					optionFound = true;
				}
			}

		}
		return option;
	}

	private static boolean isFieldValid(int value, Rule rule) {
		if ((value <= rule.r1max && value >= rule.r1min) || (value <= rule.r2max && value >= rule.r2min))
			return true;
		return false;
	}

	private static void intitFields() {
		FIELDS = new String[MYTICKET.values.size()];
		for (int i = 0; i < MYTICKET.values.size(); i++) {
			FIELDS[i] = "UNKNOWN";
		}

		for (int i = 0; i < RULES.size(); i++) {
			Rule tmp = RULES.get(i);
			tmp.setPotentialFields(MYTICKET.values.size());
			RULES.set(i, tmp);
		}
	}

	private static long scanTickets() {
		long total = 0;
		boolean removeTicket = false;

		for (int i = 0; i < TICKETS.size(); i++) {
			removeTicket = false;
			for (int j = 0; j < TICKETS.get(i).values.size(); j++) {
				if (!isValueValid(TICKETS.get(i).values.get(j))) {
					total += TICKETS.get(i).values.get(j);
					removeTicket = true;
				}
			}
			if (removeTicket) {
				Ticket ticket = new Ticket(TICKETS.get(i).values);
				ticket.valid = false;
				TICKETS.set(i, ticket);
			}
		}

		return total;
	}

	private static boolean isValueValid(int value) {
		for (int i = 0; i < RULES.size(); i++) {
			if ((value <= RULES.get(i).r1max && value >= RULES.get(i).r1min)
					|| (value <= RULES.get(i).r2max && value >= RULES.get(i).r2min)) {
				return true;
			}
		}
		return false;
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day16input.txt"));
		String line, name, tmp;
		int r1min, r1max, r2min, r2max;
		Rule rule;
		String[] numbers;
		StringTokenizer tokens;
		ArrayList<Integer> values;

		// Rules
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			if (!line.isEmpty()) {
				tokens = new StringTokenizer(line);
				name = tokens.nextToken();
				while (!name.contains(":")) {
					name += tokens.nextToken();
				}

				tmp = tokens.nextToken();
				r1min = Integer.valueOf(tmp.substring(0, tmp.indexOf('-')));
				r1max = Integer.valueOf(tmp.substring(tmp.indexOf('-') + 1));

				tokens.nextToken();

				tmp = tokens.nextToken();

				r2min = Integer.valueOf(tmp.substring(0, tmp.indexOf('-')));
				r2max = Integer.valueOf(tmp.substring(tmp.indexOf('-') + 1));

				rule = new Rule(name, r1min, r1max, r2min, r2max);

				RULES.add(rule);
			} else {
				break;
			}
		}

		// My ticket
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			if (!line.isEmpty() && !line.contains("your ticket")) {
				numbers = line.split(",");
				values = new ArrayList<Integer>();

				for (int i = 0; i < numbers.length; i++) {
					values.add(Integer.valueOf(numbers[i]));
				}
				MYTICKET = new Ticket(values);

			} else if (line.contains("your ticket")) {
				continue;
			} else {
				break;
			}
		}

		// Other Tickets
		while (scanner.hasNext()) {
			line = scanner.nextLine();
			Ticket ticket;
			if (!line.isEmpty() && !line.contains("nearby tickets")) {
				numbers = line.split(",");
				values = new ArrayList<Integer>();

				for (int i = 0; i < numbers.length; i++) {
					values.add(Integer.valueOf(numbers[i]));
				}
				ticket = new Ticket(values);
				TICKETS.add(ticket);
			} else if (line.contains("nearby tickets")) {
				continue;
			} else {
				break;
			}
		}

		scanner.close();
	}

	static private class Rule {
		public int r1min, r1max, r2min, r2max;
		public String name;
		boolean matched;
		boolean[] potentialFields;

		Rule(String name, int r1min, int r1max, int r2min, int r2max) {
			this.name = name;
			this.r1min = r1min;
			this.r1max = r1max;
			this.r2min = r2min;
			this.r2max = r2max;
			this.matched = false;
			potentialFields = null;
		}

		void setPotentialFields(int num) {
			this.potentialFields = new boolean[num];
		}
	}

	static private class Ticket {
		public ArrayList<Integer> values;
		public boolean valid;

		Ticket(ArrayList<Integer> values) {
			this.values = values;
			this.valid = true;
		}
	}

}
