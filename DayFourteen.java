package com.snakehipster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

public class DayFourteen {

	static ArrayList<String> INPUT;
	static HashMap<Long, Long> addressSpace;
	static int addressSpaceSize;
	static String mask;

	public static void main(String[] args) {
		INPUT = new ArrayList<String>();
		try {
			parseInput();
		} catch (FileNotFoundException e) {
			System.out.println("Could not find input file.");
			e.printStackTrace();
		}

		intialseAddressSpace();
		executeProgramP1();
		sumOfAllAddressSpaceValues();

		intialseAddressSpace();
		executeProgramP2();
		sumOfAllAddressSpaceValues();
	}

	private static void executeProgramP2() {
		String instruction = "";
		for (int i = 0; i < INPUT.size(); i++) {
			instruction = INPUT.get(i);
			switch (instruction.substring(0, 4)) {
			case "mask":
				updateMask(instruction.substring(instruction.indexOf('=') + 2));
				break;
			case "mem[":
				loadAddressP2(
						Integer.valueOf(instruction.substring(instruction.indexOf('[') + 1, instruction.indexOf(']'))),
						instruction.substring(instruction.indexOf('=') + 2));
				break;
			}
		}
	}
	
	private static String applyMaskP2(String addressBS) {
		for (int i = 0; i < addressBS.length(); i++) {
			switch (mask.charAt(i)) {
			case '1':
				addressBS = addressBS.substring(0, i) + '1' + addressBS.substring(i + 1);
				break;
			case '0':
				break;
			case 'X':
				addressBS = addressBS.substring(0, i) + 'X' + addressBS.substring(i + 1);
				break;
			}
		}
		return addressBS;
	}

	private static void loadAddressP2(Integer address, String valueS) {

		String addressBS = convertToBitStream(address);
		addressBS = applyMaskP2(addressBS);
		writeAddressRange(addressBS, Long.valueOf(valueS), 0);
	}

	private static void writeAddressRange(String addressBS, long value, int bitPos) {
		if (bitPos == 36) {
			addressSpace.put(convertFromBitStream(addressBS), value);
			return;
		}
		if  (mask.charAt(bitPos) == 'X') {
			addressBS = addressBS.substring(0, bitPos) + '0' + addressBS.substring(bitPos + 1);
			writeAddressRange(addressBS, value, bitPos+1);
			addressBS = addressBS.substring(0, bitPos) + '1' + addressBS.substring(bitPos + 1);
			writeAddressRange(addressBS, value, bitPos+1);
		} else {
			writeAddressRange(addressBS, value, bitPos+1);
		}

	}

	private static void executeProgramP1() {
		String instruction = "";
		for (int i = 0; i < INPUT.size(); i++) {
			instruction = INPUT.get(i);
			switch (instruction.substring(0, 4)) {
			case "mask":
				updateMask(instruction.substring(instruction.indexOf('=') + 2));
				break;
			case "mem[":
				loadAddressP1(
						Integer.valueOf(instruction.substring(instruction.indexOf('[') + 1, instruction.indexOf(']'))),
						instruction.substring(instruction.indexOf('=') + 2));
				break;
			}
		}
	}
	
	private static String applyMaskP1(String valueBS) {
		for (int i = 0; i < valueBS.length(); i++) {
			switch (mask.charAt(i)) {
			case '1':
				valueBS = valueBS.substring(0, i) + '1' + valueBS.substring(i + 1);
				break;
			case '0':
				valueBS = valueBS.substring(0, i) + '0' + valueBS.substring(i + 1);
				break;
			}
		}
		return valueBS;
	}

	private static void loadAddressP1(long address, String valueS) {
		long value = Long.valueOf(valueS);
		String valueBS = convertToBitStream(value);
		valueBS = applyMaskP1(valueBS);
		value = convertFromBitStream(valueBS);
		addressSpace.put(address, value);
	}

	private static long convertFromBitStream(String valueBS) {
		long value = 0;
		long interator = 34359738368L;
		for (int i = 0; i < 36; i++) {
			if (valueBS.charAt(i) == '1') {
				value += interator;
			}
			interator = interator / 2;
		}
		return value;
	}

	private static String convertToBitStream(long value) {
		String valueBS = "";
		long interator = 34359738368L;
		for (int i = 0; i < 36; i++) {
			if (value - interator >= 0) {
				valueBS = valueBS + "1";
				value = value - interator;
			} else {
				valueBS = valueBS + "0";
			}
			interator = interator / 2;
		}
		return valueBS;
	}

	private static void intialseAddressSpace() {
		mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
		addressSpace = new HashMap<Long, Long>();
	}

	private static void updateMask(String newMask) {
		mask = newMask;
	}
	
	private static void sumOfAllAddressSpaceValues() {
		long total = 0;
		Iterator<Entry<Long, Long>> it = addressSpace.entrySet().iterator();
	    while (it.hasNext()) {
			total += Long.valueOf(it.next().getValue());
			it.remove();
		}
		System.out.println("Address Space total is " + total + ".");
	}

	static void parseInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day14input.txt"));

		while (scanner.hasNext()) {
			INPUT.add(scanner.nextLine());
		}
		scanner.close();
	}

}
