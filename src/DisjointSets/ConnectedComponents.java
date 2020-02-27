package DisjointSets;

/**
 * Given a graph, Use a DisjointSet data structure
 * to hold the graph's connected components 
 * 
 * Bird's Eye View: Given a set of tems, we need to separate the items into dijoint sets
 * According to certain property (or rule for puttings items together).
 * E.g., (Book's example) Given a graph, find the connected subgraphs. Want to make future look up operations fast.
 * We are talking about look up operation involving questions like "Is this item related to this other item?"
 * 
 * @author Radhouane
 * @version 2/23/2020
 */
import java.util.*;
public class ConnectedComponents
{
    //The null pointers will later reference the disjoint sets and items in the disjoint sets
    static Item[] items = {new Item('a', null, null), new Item('b', null, null), new Item('c', null, null),
            new Item('d', null, null), new Item('e', null, null), new Item('f', null, null),
            new Item('g', null, null), new Item('h', null, null), new Item('i', null, null),
            new Item('j', null, null)};
    static Graph g = new Graph(10);//We will have the graph represent the pairwise raltionships between between the item. I.e, edges/vertices
    

    //Set up an empty DisjointSet structure
    static DisjointSet ds = new DisjointSet();//Look in the DisjointSet class for implementation details

    public static void main(String...args){

        g.addVertex(0,1);//(a,b) 
        g.addVertex(0,2);//(a,c)
        g.addVertex(1,2);//(b,c)
        g.addVertex(1,3);//(b,d)
        g.addVertex(4,5);//(e,f)
        g.addVertex(4,6);//(e,g)
        g.addVertex(7,8);//(h,i)
        g.addVertex(9,9);//(j,j)

        //Showing relationships between the items
        System.out.println("Given the following undirected graph...");
        System.out.println(g);
        
        System.out.println("Find the connected components of the graph");

        connected_components();

        //Now we have these componente linked to the ds structures.
        //Fro now on we can EFFICIENTLY query the structure to see what item is connected to another
        //via a path through the graph
        System.out.println("Show the disjoint sets");
        System.out.println(ds);

    }
    //Mimics the algorithm from the book
    public static void connected_components(){
        //Add singleton sets to ds, one singleton set per item
        for(int i = 0; i < items.length; i++){
            ds.make_set(items[i]);
        }
        System.out.println("Show the disjoint sets");
        System.out.println(ds);

        //Merge the sets
        for(int i = 0; i < items.length; i++){
            for(int j = i; j < items.length; j++){
                if(g.isVertex(i,j) && ds.find_set(items[i]) != ds.find_set(items[j])){
                    System.out.println("The graph connects " + items[i] + " and " + items[j] + ". Merging the sets...");
                    ds.union(items[i].getValue(), items[j].getValue());
                }
            }
            System.out.println(ds);
        }
    }
    
    //Finish this method as an exercise

    public static void same_components(Item u, Item v){
    }
}
