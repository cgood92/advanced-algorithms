import java.util.*;
import java.lang.*;

class LongestDecreasingSubsequence {
	private static HashMap<List<Integer>, List<Integer>> memoizedMap = new HashMap<>();

	public static void main (String[] args) {
		System.out.println(longestDecreasingSubsequence(List.of(15, 27, 14, 38, 63, 55, 46, 65, 85)));
		System.out.println(longestDecreasingSubsequence(List.of(50, 3, 10, 7, 40, 80)));
	}

	private static List<Integer> longestDecreasingSubsequence(List<Integer> list) {
		List<Integer> maxDecreasing = new ArrayList<>();

		for (int i = 0; i < list.size() - maxDecreasing.size(); i++) {
			List<Integer> subSequence = longestDecreasingSubsequenceAux(list.subList(i, list.size()));

			if (subSequence.size() > maxDecreasing.size()) {
				maxDecreasing = subSequence;
			}
		}

		return maxDecreasing;
	}

	private static List<Integer> longestDecreasingSubsequenceAux(List<Integer> list) {
		List<Integer> possibleSavedEntry = memoizedMap.get(list);
		if (possibleSavedEntry != null) {
			return possibleSavedEntry;
		}

		if (list.size() == 1) {
			return list;
		}

		int startingNumber = list.get(0);
		List<Integer> decreasing = new ArrayList<>(Arrays.asList(startingNumber));

		List<Integer> longestSubArray = new ArrayList<>();

		for (int i = 1; i < list.size(); i++) {
			int numberToCompare = list.get(i);

			if (numberToCompare <= startingNumber) {
				List<Integer> subArray = longestDecreasingSubsequenceAux(list.subList(i, list.size()));

				if (subArray.size() > longestSubArray.size()) {
					longestSubArray = subArray;
				}
			}
		}

		decreasing.addAll(longestSubArray);
		memoizedMap.put(list, decreasing);

		return decreasing;
	}
}