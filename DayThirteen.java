package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import static java.util.Arrays.stream;

public class DayThirteen {
	static int timestamp;
	static ArrayList<Integer> INPUT;

	public static void main(String[] args) {
		INPUT = new ArrayList<Integer>();
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}
		System.out.println(findBestBus());
		solveElvesConvolutedProblemSmarter();
	}

	private static void solveElvesConvolutedProblemSmarter() {
		int size = 0, index = 0;
		for (int i = 0; i < INPUT.size(); i++) {
			if (INPUT.get(i) != 0)
				size++;
		}

		long[] a = new long[size];
		long[] n = new long[size];

		for (int i = 0; i < INPUT.size(); i++) {
			if (INPUT.get(i) != 0) {
				a[index] = INPUT.get(i) - i;
				n[index] = INPUT.get(i);
				index++;
			}
		}

		System.out.println(chineseRemainder(n, a));
	}

	/**
	 * Chinese Remainder implementation taken from here:
	 * https://rosettacode.org/wiki/Chinese_remainder_theorem#Java
	 * 
	 * had to change to use 64 bit types though though
	 * 
	 * @param n
	 * @param a
	 * @return
	 */
	public static long chineseRemainder(long[] n, long[] a) {

		long prod = stream(n).reduce(1, (i, j) -> i * j);

		long p, sm = 0;
		for (int i = 0; i < n.length; i++) {
			p = prod / n[i];
			sm += a[i] * mulInv(p, n[i]) * p;
		}
		return sm % prod;
	}

	private static long mulInv(long a, long b) {
		long b0 = b;
		long x0 = 0;
		long x1 = 1;

		if (b == 1)
			return 1;

		while (a > 1) {
			long q = a / b;
			long amb = a % b;
			a = b;
			b = amb;
			long xqx = x1 - q * x0;
			x1 = x0;
			x0 = xqx;
		}

		if (x1 < 0)
			x1 += b0;

		return x1;
	}

	/**
	 * Unused Brute force method
	 * 
	 * @return
	 */
	private static long solveElvesConvolutedProblemBruteForce() {
		boolean foundAnswer = false;
		long attempt = 0;
		while (!foundAnswer) {
			attempt++;
			foundAnswer = true;
			for (long i = 0; i < INPUT.size(); i++) {
				if (INPUT.get((int) i) != 0 && (attempt + i) % INPUT.get((int) i) != 0) {
					foundAnswer = false;
					break;
				}
			}
			if (!foundAnswer)
				System.out.println("Attempt " + attempt + " failed :(");
		}

		return attempt;
	}

	private static String findBestBus() {
		int bestBus = 0, waitTime = -1, r = 0;
		for (int i = 0; i < INPUT.size(); i++) {
			if (INPUT.get(i) != 0) {
				r = INPUT.get(i) - (timestamp % INPUT.get(i));
				if (r < waitTime || waitTime == -1) {
					bestBus = INPUT.get(i);
					waitTime = r;
				}
			}
		}

		return "Bus ID: " + bestBus + " Wait time: " + waitTime + " Answer: " + (waitTime * bestBus);
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day13input.txt"));
		String tmp[];
		timestamp = Integer.valueOf(scanner.nextLine());
		tmp = scanner.nextLine().split(",");

		for (int i = 0; i < tmp.length; i++) {
			if (!tmp[i].equals("x")) {
				INPUT.add(Integer.valueOf(tmp[i]));
			} else {
				INPUT.add(0);
			}
		}
		scanner.close();
	}

}
