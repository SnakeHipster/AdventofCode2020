package com.snakehipster;

import java.util.HashMap;
import java.util.stream.IntStream;

public class DayFifteenP2 {

	static HashMap<Integer, NumberDeets> history;
	static int[] startingNumbers;
	static int previousNumber;

	public static void main(String[] args) {
		history = new HashMap<Integer, NumberDeets>();
		startingNumbers = IntStream.of(2,0,6,12,1,3).toArray();
		addStartingInput();
		playGame(30000000);

	}

	private static void addStartingInput() {
		NumberDeets nD;
		for (int i = 0; i < startingNumbers.length; i++) {
			nD = new NumberDeets();
			nD.secondLastTurn = 0;
			nD.latestTurn = i + 1;
			history.put(startingNumbers[i], nD);
			previousNumber = startingNumbers[i];
			System.out.println("Turn " + (i + 1) + " is " + startingNumbers[i] + ".");
		}

	}

	private static void playGame(int turns) {
		int nextNumber = 0;
		NumberDeets nD;
		for (int i = 1 + startingNumbers.length; i <= turns + startingNumbers.length; i++) {
			nextNumber = calculateDifference(previousNumber);

			nD = new NumberDeets();
			if (history.containsKey(nextNumber)) {
				nD.secondLastTurn = history.get(nextNumber).latestTurn;
			} else {
				nD.secondLastTurn = 0;
			}
			nD.latestTurn = i;

			history.put(nextNumber, nD);

			previousNumber = nextNumber;
			System.out.println("Turn " + i + " is " + nextNumber + ".");
		}
	}

	private static int calculateDifference(int previousNumber) {
		NumberDeets nD = history.get(previousNumber);

		if (nD.secondLastTurn != 0) {
			return nD.latestTurn - nD.secondLastTurn;
		} else {
			return 0;
		}
	}

	static private class NumberDeets {
		public int latestTurn, secondLastTurn;

		NumberDeets() {
			latestTurn = 0;
			secondLastTurn = 0;
		}

	}
}
