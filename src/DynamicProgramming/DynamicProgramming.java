package DynamicProgramming;

import java.util.*;
import java.lang.*;

class DynamicProgramming {
	public static void main (String[] args) {
		Scanner in = new Scanner(System.in);

		int numOfTestCases = Integer.parseInt(in.nextLine());

		for (int testCaseIndex = 0; testCaseIndex < numOfTestCases; testCaseIndex++) {
			// Not used, simply need a in.nextLine() for the sake of GeekForGeeks unit tests, but might as well name it.
			int numOfWordsInDictionary = Integer.parseInt(in.nextLine());

			HashSet dictionary = new HashSet(Arrays.asList(in.nextLine().split(" ")));

			String testLine = in.nextLine();

			int response = wordBreak(dictionary, testLine) ? 1 : 0;

			System.out.println(response);
		}
	}

	private static boolean wordBreak(HashSet dictionary, String testLine){
		int stringLength = testLine.length();
		boolean[][] lookupTable = new boolean[stringLength][stringLength];

		for (int offset = 0; offset < stringLength; offset++) {
			for (int row = 0; row < stringLength - offset; row++) {

					int column = row + offset;
					String substring = testLine.substring(row, column + 1);

					boolean isFullStringInDictionary = dictionary.contains(substring);
					boolean inBoundsForLeftTopCheck = column > 0 && row < stringLength - 1;

					if (isFullStringInDictionary) {
						lookupTable[row][column] = true;
					} else if(inBoundsForLeftTopCheck) {

						// Check for all possible subset combinations
						for (int i = 0; i < substring.length() - 1; i++) {
							boolean left = lookupTable[row][i];
							boolean bottom = lookupTable[i + 1][column];

							boolean isSubsetCombinationInDictionary = left && bottom;

							if (isSubsetCombinationInDictionary) {
								lookupTable[row][column] = true;
								break;
							}
						}
					}
				}
		}

		boolean isBeginningToEndSubsetWords = lookupTable[0][stringLength - 1];
		return isBeginningToEndSubsetWords;
	}
}