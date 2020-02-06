package RodCutting;

import java.util.*;

public class RodCutting {

	public static void main(String[] args) {
		int[] prices = new int[] {1, 5, 8, 9, 10, 17, 17, 20};
		for (int i = 1; i <= prices.length; i++) {
			int solution = memoizedCutRod(prices, i);
			System.out.println(solution);
		}
	}

	public static int memoizedCutRod(int[] prices, int length){
		int[] memoizedTable;

		memoizedTable = new int[length + 1];
		Arrays.fill(memoizedTable, Integer.MIN_VALUE);

		return memoizedCutRodAux(prices, length, memoizedTable);
	}

	public static int memoizedCutRodAux(int[] prices, int length, int[] memoizedTable) {
		if (memoizedTable[length] >= 0)
			return memoizedTable[length];

		boolean isZero = length == 0;
		int q = isZero ? 0 : Integer.MIN_VALUE;

		if (!isZero) {
			for (int i = 1; i <= length; i++) {
				q = Math.max(q, prices[i - 1] + memoizedCutRodAux(prices, length - i, memoizedTable));
			}
		}

		memoizedTable[length] = q;

		return q;
	}
}