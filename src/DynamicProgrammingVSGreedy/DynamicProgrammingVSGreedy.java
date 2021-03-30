package DynamicProgrammingVSGreedy;

/**
 * Greedy algorithm for solving the Activity Selection Problem
 *
 * Is almost tail recursive, b.c., it ends with a recursive call to itself.
 * Such recursions can be easily converted to iterations.
 * Runs in Theta(n) because each activity is examined once
 *
 * @author Radhouane
 * @version 2/9/2020
 */

/**
 * Recursively solves the activity selection problem
 *
 * s = start times
 * f = finish times
 * k = index of the subproblem, 0 for the original problem
 * n = size of the original problem
 */
class GreedyMethod
{
	//We have to make a greedy choice, that we believe will be part of the final solution
	//One choice == take the activity that will finish first. Why? Because intuitive, also because it allows
	//for the largest block of time to allocate for the remaining activities
	//I.e., since activity 0 finished at t == 4, We are left with the largest possible chunk of time to allocate
	public static void RECURSIVE_ACTIVITY_SELECTOR(int[] s, int[] f, int k, int n){
		//The first recursive call prints the first activity
		if(k == 0) System.out.print("(" + s[0] + ", " + f[0] + ") ");//Greedy choice WILL be part of the solution
		//This is the greedy choice when we first process the problem

		int m = k + 1;
		//skip all the activities that start before activity k finishes
		while(m < n && s[m] < f[k]){
			m = m + 1;
		}
		//We get here once we've skipped said activities
		if(m < n){
			//Greedy Choice. Print the (s,f) of the activity m that wasn't skipped
			System.out.print("(" + s[m] + ", " + f[m] + ") ");
			RECURSIVE_ACTIVITY_SELECTOR(s, f, m, n);//Calls itself recursively, this time k == teh new value of m; i.e., moved ahead
		}
		else{
			System.out.println("Done. ");
		}
	}
}

class DynamicProgramming {
	public static void ACTIVITY_SELECTOR(int[] s, int[] f, int n){
		int[][] c = new int[n + 2][n + 2];
		int[][] act = new int[n + 2][n + 2];
		for (int i = 0; i <= n; i++) {
			c[i][i] = 0;
			c[i][i + 1] = 0;
		}
		c[n + 1][n + 1] = 0;

		for (int l = 2; l <= n + 1; l++) {
			for (int i = 0; n <= n - l + 1; i++) {
				int j = i + l;
				c[i][j] = 0;
				int k = j - 1;
				while (f[i] < f[k]) {
					if (f[i] <= s[k] && f[k] <= s[j] && c[i][k] + c[k][j] + 1 > c[i][j]) {
						c[i][j] = c[i][k] + c[k][j] + 1;
						act[i][j] = k;
					}
					k--;
				}
			}
		}
		PRINT_ACTIVITIES(c, act, 0, n + 1);
	}

	public static void PRINT_ACTIVITIES(int[][] c, int[][] act, int i, int j) {
		if (c[i][j] > 0) {
			int k = act[i][j];
			System.out.print(k);
			PRINT_ACTIVITIES(c, act, i, k);
			PRINT_ACTIVITIES(c, act, k, j);
		}
	}

}

public class DynamicProgrammingVSGreedy {
	public static void main(String...args){
		int[] s = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
		int[] f = {4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
		long time1 = System.currentTimeMillis();
		GreedyMethod.RECURSIVE_ACTIVITY_SELECTOR(s, f, 0, s.length);
		long time2 = System.currentTimeMillis();
		DynamicProgramming.ACTIVITY_SELECTOR(s, f, s.length);
		long time3 = System.currentTimeMillis();

		System.out.println("Time for greedy: " + (time2 - time1));
		System.out.println("Time for dynamic programming: " + (time3 - time2));
	}
}

