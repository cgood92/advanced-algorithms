package DisjointSets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Given a graph, Use a DisjointSet data structure
 * to hold the graph's connected components 
 * 
 * Bird's Eye View: Given a set of tems, we need to separate the users into dijoint sets
 * According to certain property (or rule for puttings users together).
 * E.g., (Book's example) Given a graph, find the connected subgraphs. Want to make future look up operations fast.
 * We are talking about look up operation involving questions like "Is this item related to this other item?"
 * 
 * @author Radhouane
 * @version 2/23/2020
 */
public class ConnectedComponents
{
    static User[] users = {
        new User("Thomas Jefferson"),
        new User("James Madison"),
        new User("George Washington"),

        new User("Harry Potter"),
        new User("Ron Weasley"),
        new User("Hermione Granger"),
        new User("Neville Longbottom"),

        new User("Donald Trump"),

        new User("Steve Jobs"),
        new User("Bill Gates")
    };

    public static void main(String...args){
        Graph g = new Graph(10);

        // Adding presidential friends
        g.addVertex(0,1);
        g.addVertex(0,2);
        g.addVertex(1,2);

        // Adding Harry Potter friends
        g.addVertex(3, 4);
        g.addVertex(3, 5);
        g.addVertex(3, 6);
        g.addVertex(4, 5);
        g.addVertex(4, 6);
        g.addVertex(5, 6);

        // Adding rich computer friends
        g.addVertex(8, 9);

        System.out.println("These are the users with their friends:");
        for (int i = 0; i < users.length; i++) {
            String user = users[i].getValue();
            int row = i;

           String friends = IntStream
                .range(0, users.length)
                .filter(j -> g.isVertex(row, j))
                .mapToObj(j -> users[j].getValue())
                .collect(Collectors.joining(", "));

           boolean hasFriends = friends.length() > 0;
           System.out.println("\t" + user + ": " + (hasFriends ? friends : "<None>"));
        }

        DisjointSet friendsDisjointSet = connected_components(g);

        System.out.println("\n");

        ArrayList<Set> sets = friendsDisjointSet.getSets();

        for(Set set: sets) {
            System.out.println("Friends cluster:");
            System.out.println(set.toString() + "\n");
        }

        // Test
        System.out.println("Should be true: " + same_components(friendsDisjointSet, users[0], users[1]));
        System.out.println("Should be true: " + same_components(friendsDisjointSet, users[8], users[9]));
        System.out.println("Should be false: " + same_components(friendsDisjointSet, users[1], users[5]));
    }

    //Mimics the algorithm from the book
    public static DisjointSet connected_components(Graph g){
        DisjointSet ds = new DisjointSet();

        //Add singleton sets to ds, one singleton set per item
        for(int i = 0; i < users.length; i++){
            ds.make_set(users[i]);
        }

        //Merge the sets
        for(int i = 0; i < users.length; i++){
            for(int j = i; j < users.length; j++){
                if(g.isVertex(i,j) && ds.find_set(users[i]) != ds.find_set(users[j])){
                    ds.union(users[i].getValue(), users[j].getValue());
                }
            }
        }

        return ds;
    }

    public static boolean same_components(DisjointSet ds, User u, User v){
        return ds.find_set(u) == ds.find_set(v);
    }
}
