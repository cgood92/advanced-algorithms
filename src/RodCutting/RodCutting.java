package RodCutting;

import java.util.*;
import java.util.stream.*;

/*
Source: I took the main example straight from the book, then modified
it to return the actual solution, not just the product.

MEMOIZED-CUT-ROD (p,n)
1 let r[0..n] be a new array
2 for i = 0 to n
3   r[i] = [-INFINITY, []]
4 return MEMOIZED-CUT-ROD-AUX(p,n,r)

MEMOIZED-CUT-ROD-AUX (p,n,r)
if r[n][0] >= 0
    return r[n]
if n == 0
    q = 0
else q = -INFINITY
    for i = 1 to n
    	t = MEMOIZED-CUT-ROD-AUX (p,n-i,r)
    	m = max(q, p[i] + t[0])
        if q != max
        	q = max
        	s = [i, ...t[1]]
r[n] = [q, s]
return [q, s]
 */

public class RodCutting {

	public static void main(String[] args) {
		// Sample prices table
		int[] prices = new int[] {1, 5, 8, 9, 10, 17, 17, 20};

		for (int i = 1; i <= prices.length; i++) {
			Object[] result = memoizedCutRod(prices, i);

			int totalPrice = (int) result[0];
			int[] lengths = (int[]) result[1];

			String stringified = Arrays.stream(lengths)
				.mapToObj(String::valueOf)
				.collect(Collectors.joining(", "));

			System.out.println("The best solution for length [" + i + "] is to have length(s) [" + stringified  + "], yielding " + totalPrice);
		}
	}

	public static Object[] memoizedCutRod(int[] prices, int length){
		Object[][] memoizedTable;

		memoizedTable = new Object[length + 1][];
		Arrays.fill(memoizedTable, new Object[] { Integer.MIN_VALUE, new int[0] });

		return memoizedCutRodAux(prices, length, memoizedTable);
	}

	public static Object[] memoizedCutRodAux(int[] prices, int length, Object[][] memoizedTable) {
		if ((int) memoizedTable[length][0] >= 0)
			return memoizedTable[length];

		boolean isZero = length == 0;
		int max = isZero ? 0 : Integer.MIN_VALUE;
		int[] optimalLength = new int[0];

		if (!isZero) {
			for (int i = 1; i <= length; i++) {
				Object[] recursedResults = memoizedCutRodAux(prices, length - i, memoizedTable);

				int localMax = Math.max(max, prices[i - 1] + (int) recursedResults[0]);

				boolean isOptimalDifferent = localMax != max;

				if (isOptimalDifferent) {
					optimalLength = concatArrays((int[]) recursedResults[1], new int[]{ i });
					max = localMax;
				}
			}
		}

		memoizedTable[length] = new Object[]{ max, optimalLength };

		return new Object[] { max, optimalLength };
	}

	// Copied from: https://www.programiz.com/java-programming/examples/concatenate-two-arrays
	public static int[] concatArrays(int[] a, int[] b){
		int aLen = a.length;
		int bLen = b.length;

		int[] result = new int[aLen + bLen];
		System.arraycopy(a, 0, result, 0, aLen);
		System.arraycopy(b, 0, result, aLen, bLen);

		return result;
	}
}