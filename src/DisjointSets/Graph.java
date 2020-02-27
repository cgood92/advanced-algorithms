
/**
 * A Graph data structure
 * Not optimal, but could be made to be so in a number of ways.
 * 
 * @author Radhouane
 * @version 2/24/2020
 */
import java.util.*;
public class Graph
{
    //Adjacency matrix implementation
    int[][] graph; 
    int nbrVertices;
    
    //Graph with no vertices
    public Graph(int n){
        nbrVertices = n;
        graph = new int[n][n];
    }
    
    //Assumes 0 <= i and j < nbrVertices
    public void addVertex(int i, int j){
        graph[i][j] = 1;
        graph[j][i] = 1;
    }
    

    public boolean isVertex(int i, int j){
        return (graph[i][j] == 1);
    }
    
    public String toString(){
        String s = "  a "; 
        char charac = 'a';
        for(int i = 0; i < nbrVertices - 1; i++){
            s += ++charac + " ";
        }
        s += "\n";
        charac = 'a';
        for(int i = 0; i < nbrVertices; i++){     
            s += charac++ + " ";
            for(int j = 0; j < nbrVertices; j++){
                s += graph[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }
}
