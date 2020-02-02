package DynamicProgramming;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.stream.*;

class DynamicProgramming {

	static int OFFSET_ONE_FOR_EASY_TABLE = 1;

	public static void main (String[] args) {
		HashSet dictionary = loadDictionary();

		walkString("Perfectly good text", dictionary);
		walkString("wh atdoe s do nald tr umpsay today", dictionary);
		walkString("Whereis colu mbusst ateuniversity", dictionary);
		walkString("w hyusedy nam icprogramming in the worldtoday", dictionary);
		walkString("somewhere overthe rainbow songisgood", dictionary);
		walkString("brea king good", dictionary);
	}

	private static HashSet loadDictionary(){
		HashSet dictionary = new HashSet();

		try {
			File file = new File("/Users/clintgoodman/development/advanced-algorithms/src/DynamicProgramming/few-words.txt");
			Scanner reader = new Scanner(file);

			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				dictionary.add(data);
			}

			reader.close();
		} catch (Exception e) {
			System.out.println("Error");
		}

		return dictionary;
	}

	private static void walkString(String searchTerm, HashSet dictionary) {
		String[] splitWords = searchTerm.split(" ");

		List<String> staging = new ArrayList<>();
		List<String> finalStrings = new ArrayList<>();

		for (int i = 0; i < splitWords.length; i++) {
			String word = splitWords[i];

			if (word == null) {
				continue;
			}

			boolean isLast = i == splitWords.length - 1;
			boolean isValidWord  = dictionary.contains(word);

			if (isValidWord || isLast) {
				if (!isValidWord || !staging.isEmpty()) {
					List<String> testWords = new ArrayList<>(staging);

					testWords.add(word);

					if (!finalStrings.isEmpty()) {
						String lastGoodWord = finalStrings.remove(finalStrings.size() - 1);
						testWords.add(0, lastGoodWord);
					}

					if (!isLast) {
						testWords.add(splitWords[i + 1]);
						splitWords[i + 1] = null;
					}

					String joined = testWords.stream()
						.map(String::valueOf)
						.collect(Collectors.joining(""));
					String[] correctedWords = wordBreak(dictionary, joined);

					if (correctedWords == null) {
						System.out.println("Bad query");
						return;
					}

					finalStrings.addAll(Arrays.asList(correctedWords));
					staging.clear();
				} else {
					finalStrings.add(word);
				}

			} else {
				staging.add(word);
			}
		}

		String joined = finalStrings.stream()
			.map(String::valueOf)
			.collect(Collectors.joining(" "));

		System.out.println("Showing results for: " + joined);
	}

	private static String[] wordBreak(HashSet dictionary, String testLine){
		int stringLength = testLine.length();

		int[][] lookupTable = new int[stringLength][stringLength];

		for (int substrLength = 0; substrLength < stringLength; substrLength++) {
			for (int row = 0; row < stringLength - substrLength; row++) {

					int column = row + substrLength;
					String substring = testLine.substring(row, column + 1);

					boolean isFullStringInDictionary = dictionary.contains(substring);
					boolean inBoundsForLeftTopCheck = column > 0 && row < stringLength;

					if (isFullStringInDictionary) {
						lookupTable[row][column] = substring.length() + OFFSET_ONE_FOR_EASY_TABLE;
					} else if(inBoundsForLeftTopCheck) {

						// Check for all possible subset combinations
						for (int i = substring.length() - 1 - 1; i >= 0; i--) {
							boolean left = lookupTable[row][i] != 0;
							boolean bottom = lookupTable[i + 1][column] != 0;

							boolean isSubsetCombinationInDictionary = left && bottom;

							if (isSubsetCombinationInDictionary) {
								lookupTable[row][column] = substring.length() - i;
								break;
							}
						}
					}
				}
		}

		int isBeginningToEndSubsetWords = lookupTable[0][stringLength - 1];
		if (isBeginningToEndSubsetWords == 0) {
			return null;
		}

		List<String> correctedStrings = new ArrayList<>();

		int[] topRow = lookupTable[0];
		int stringIndex = stringLength - 1;
		while (stringIndex > 0) {
			int columnValue = topRow[stringIndex];

			if (columnValue != 0) {
				int startIndex = stringIndex - columnValue + OFFSET_ONE_FOR_EASY_TABLE + 1;
				int endIndex = stringIndex + 1;

				String reconstructedWord = testLine.substring(startIndex, endIndex);

				correctedStrings.add(0, reconstructedWord);

				stringIndex = startIndex - 1;
			}
		}

		return correctedStrings.toArray(new String[0]);
	}
}