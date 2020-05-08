package Final;

public class Tester{
	public static void main(String[] args) 
	{ 
	    //Adjacency matrix encoding a directed graph
	    //Node 0 is the kitchen
	    //E.g., It takes 4mn to get from node 1 to node 0
	    //It takes 7mn to get node 3 to node 2
		int graph[][] = new int[][] { { 0, 0, 0, 1, 0, 0}, 
									  { 4, 0, 0, 0, 7, 0}, 
									  { 0, 0, 0, 1, 3, 0}, 
									  { 2, 1, 5, 0, 0, 0}, 
									  { 0, 2, 0, 0, 0, 2}, 
									  { 0, 0, 0, 3, 0, 0}}; 
		
		//Plain Dijkstra. Compute shortest distances
		//Uncomment to run
		//(new DemoDistancesOnly()).dijkstra(graph, 0);
		
		//Augmented Dijkstra. Print one-way path
		//Uncomment to run
//		(new DemoShortestOneWayPaths()).dijkstra_onewaypaths(graph, 1);
		
		//Part 1: 
		//Print the shortest round trip between kitchen and some node
		//Uncomment to run
//		(new DemoShortestRoundTrip()).shortest_roundtrip(graph, 0, 4);
		
		//Part 2:
		//Print all the shortest round trips between kitchen and every other node
//		for(int j = 1; j < graph.length; j++){
//		    (new DemoShortestRoundTrip()).shortest_roundtrip(graph, 0, j);
//		}

		for(int d = 1; d < graph.length; d++)(new DemoFinal()).avgDistance(graph, 0, d);

		(new DemoFinal()).shortest_roundtrip_pre_processed(graph, 0);
	} 
}