package ActivitySelection;
/**
 * Greedy algorithm for solving the Activity Selection Problem
 *
 * Original author: Radhouane
 * @version 2/9/2020
 *
 *
 * Revised by: Clint
 * @version 2/29/2020
 */

public class ActivitySelection
{

    public static String RECURSIVE_ACTIVITY_SELECTOR(int[] s, int[] f){
        return RECURSIVE_ACTIVITY_SELECTOR(s, f, 0, "");
    }

    public static String RECURSIVE_ACTIVITY_SELECTOR(int[] s, int[] f, int k, String result){
        if ((s.length != f.length) || (s.length % 2 != 0)) {
            throw new IllegalArgumentException("Array inputs not valid");
        }

        int sizeOfArray = s.length;

        if(k == 0 && sizeOfArray >= 2) {
            result += "(" + s[0] + ", " + f[0] + ") (" + s[1] + ", " + f[1] + ")";
        }

        int m = k + 2;
        while(m < sizeOfArray - 1 && s[m] < f[k + 1]){
            m = m + 1;
        }

        if(m < sizeOfArray - 1){
            result += " (" + s[m] + ", " + f[m] + ") (" + s[m + 1] + ", " + f[m + 1] + ")";

            return RECURSIVE_ACTIVITY_SELECTOR(s, f, m, result);
        } else {
            return result;
        }
    }

}
