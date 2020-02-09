import java.util.*;
import java.lang.*;

/*
LONGEST-DECREASING-SUBSEQUENCE (a)
let m = []
for i = 0 to a.length - m.length
	s = a[i..n.length]
	t =  LONGEST-DECREASING-SUBSEQUENCE-AUX (s)

	if t.length > m.length
		m = t
return m

LONGEST-DECREASING-SUBSEQUENCE-AUX (a, r)
if r[a]
	return r[a]
if a.length = 1
	return a
d = [a[0]]
m = []
for i = 0 to n.length
	if a[i] <= a[0]
		s = a[i..a.length]
		n = LONGEST-DECREASING-SUBSEQUENCE-AUX(s)
		if n.length > m.length
			m = n
d = [...d, ...m]
r[a] = d
return d
 */

class LongestDecreasingSubsequence {

	public static void main (String[] args) {
		System.out.println(longestDecreasingSubsequence(List.of(15, 27, 14, 38, 63, 55, 46, 65, 85)));
		System.out.println(longestDecreasingSubsequence(List.of(50, 3, 10, 7, 40, 80)));
	}

	private static List<Integer> longestDecreasingSubsequence(List<Integer> list) {
		HashMap<List<Integer>, List<Integer>> memoizedMap = new HashMap<>();
		List<Integer> maxDecreasing = new ArrayList<>();

		for (int i = 0; i < list.size() - maxDecreasing.size(); i++) {
			List<Integer> subSequence = longestDecreasingSubsequenceAux(list.subList(i, list.size()), memoizedMap);

			if (subSequence.size() > maxDecreasing.size()) {
				maxDecreasing = subSequence;
			}
		}

		return maxDecreasing;
	}

	private static List<Integer> longestDecreasingSubsequenceAux(List<Integer> list, HashMap<List<Integer>, List<Integer>> memoizedMap) {
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
				List<Integer> subArray = longestDecreasingSubsequenceAux(list.subList(i, list.size()), memoizedMap);

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