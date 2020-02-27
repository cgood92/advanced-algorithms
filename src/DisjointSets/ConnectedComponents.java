package DisjointSets;

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

    static Graph g = new Graph(10);
    
    static DisjointSet ds = new DisjointSet();

    public static void main(String...args){

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

        //Showing relationships between the users
        System.out.println("Given the following undirected graph...");
        System.out.println(g);
        
        System.out.println("Find the connected components of the graph");

        connected_components();

        //Now we have these component linked to the ds structures.
        //For now on we can EFFICIENTLY query the structure to see what item is connected to another
        //via a path through the graph
        System.out.println("Show the disjoint sets");
        System.out.println(ds);

    }
    //Mimics the algorithm from the book
    public static void connected_components(){
        //Add singleton sets to ds, one singleton set per item
        for(int i = 0; i < users.length; i++){
            ds.make_set(users[i]);
        }
        System.out.println("Show the disjoint sets");
        System.out.println(ds);

        //Merge the sets
        for(int i = 0; i < users.length; i++){
            for(int j = i; j < users.length; j++){
                if(g.isVertex(i,j) && ds.find_set(users[i]) != ds.find_set(users[j])){
                    System.out.println("The graph connects " + users[i] + " and " + users[j] + ". Merging the sets...");

                    ds.union(users[i].getValue(), users[j].getValue());
                }
            }
            System.out.println(ds);
        }
    }
    
    //Finish this method as an exercise

    public static void same_components(User u, User v){
    }
}
