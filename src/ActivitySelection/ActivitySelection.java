
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
public class ActivitySelection
{
    public static void main(String...args){
        //Start times of activities (e.g., operating system schedulers)
        int[] s = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
        //Finish times of activities
        int[] f = {4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};//This array is *sorted* //Goof for efficiency
        //we can easily/efficiently lookup finish times
        //Want to find the maximal/largest set of activities that I can schedule within this time window
        //How many out of 11 activities I can schedule. List the activities
        RECURSIVE_ACTIVITY_SELECTOR(s, f, 0, s.length);//s.length = size of the original problem
    }    

    //We have to make a greedy choice, that we believe will be part of the final solution
    //One choice == take the activity that will finish first. Why? Because intuitive, also because it allows
    //for the largest block of time to allocate for the remaining activities
    //I.e., since activity 0 finished at t == 4, We are left with the largest possible chunk of time to allocate
    public static void RECURSIVE_ACTIVITY_SELECTOR(int[] s, int[] f, int k, int n){
        //The first recursive call prints the first activity
        if(k == 0) {
            System.out.print("(" + s[0] + ", " + f[0] + ") (" + s[1] + ", " + f[1] + ")");
        }

        int m = k + 2;
        //skip all the activities that start before activity k finishes
        while(m < n - 1 && s[m] < f[k + 1]){
            m = m + 1;
        }
        //We get here once we've skipped said activities
        if(m < n - 1){
            //Greedy Choice. Print the (s,f) of the activity m that wasn't skipped
            System.out.print(" (" + s[m] + ", " + f[m] + ") (" + s[m + 1] + ", " + f[m + 1] + ")");
            RECURSIVE_ACTIVITY_SELECTOR(s, f, m, n);//Calls itself recursively, this time k == the new value of m; i.e., moved ahead
        }
        else{
            System.out.println("Done.");
        }
    }

}
