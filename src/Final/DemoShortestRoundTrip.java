// Based on Dijkstra's single source shortest path.
// https://en.wikipedia.org/wiki/Dijkstra's_algorithm
package Final;

import java.util.*;

public class DemoShortestRoundTrip { 
    
    static final int N = 6; 
    int[] previous_hops = new int[N];
    
    void shortest_roundtrip(int graph[][], int src, int dest){
        System.out.println("Shortest roundtrip from " + src + " to " + dest);
        dijkstra_onewaypaths(graph, src);
        print_path(previous_hops, src, dest);
        dijkstra_onewaypaths(graph, dest);
        print_path(previous_hops, dest, src);         
    }
    
    //Show shortest one-way paths from source to every other node
    void dijkstra_onewaypaths(int graph[][], int src){ 
        //Initialize the arrays
        int distances[] = new int[N]; // Shortest distances from source to each node
        boolean processed[] = new boolean[N]; //processed[i] == true if the shortest distance from source to i has been finalized    

        for (int i = 0; i < N; i++) { 
            distances[i] = Integer.MAX_VALUE; 
            processed[i] = false; 
        } 

        // Shortest distance from source to source == 0 
        distances[src] = 0; 
        // Find the remaining shortest distances (N - 1 rounds) 
        for (int count = 1; count < N; count++) { 
            int i = closest_unprocessed_node(distances, processed); 
            processed[i] = true; // i is the closed unprocessed node, let's process it
            //For each node j in the graph
            for (int j = 0; j < N; j++){
                //If j is unprocessed && We have an edge from i to j && A path that visits i leads faster to j 
                if (!processed[j] && graph[i][j] != 0 && distances[i] != Integer.MAX_VALUE && 
                    distances[i] + graph[i][j] < distances[j]) 
                    
                    {distances[j] = distances[i] + graph[i][j]; //Make i the last intersection before j
                    previous_hops[j] = i;}
            }
        }    
        
    } 
    
    // Helper method: Finds the index of the closest, unprocessed node
    int closest_unprocessed_node(int distances[], boolean processed[]) 
    { 
        int min = Integer.MAX_VALUE, min_index = -1; 

        for (int i = 0; i < N; i++) 
            if (processed[i] == false && distances[i] <= min) { 
                min = distances[i]; 
                min_index = i; 
            } 
        return min_index; 
    } 
    
   //Prints the cheapest path 
    void print_path(int[] previous_hops, int a, int b){
        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(b);
        int prev = previous_hops[b];
        while(prev != a){
            path.add(prev);
            prev = previous_hops[prev];
        }
        path.add(a);
        for(int i = path.size()-1; i >=0 ; i--) 
              System.out.print(path.get(i) + " ");
        System.out.println();
    }
} 
