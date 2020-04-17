package Dijkstra;

/*
* To give credit where it is due, I borrowed parts of inspiration for this solution from:
* https://www.geeksforgeeks.org/java-program-for-dijkstras-shortest-path-algorithm-greedy-algo-7/ and
* https://www.devglan.com/datastructure/dijkstra-algorithm-java.  However, I added my own flavors, print solution, etc.
* */

public class Dijkstra {

	public static void dijkstra(int[][] graph, char[] vertexNames, int sourceVertex, int destinationVertex, boolean printAll, boolean justPath){
		int vertexCount = graph.length;

		boolean[] visitedVertex = new boolean[vertexCount];
		int[] distance = new int[vertexCount];
		int[] parents = new int[vertexCount];

		for (int i = 0; i < vertexCount; i++){
			visitedVertex[i] = false;
			distance[i] = Integer.MAX_VALUE;
			parents[i] = -1;
		}

		distance[sourceVertex] = 0;
		parents[sourceVertex] = -1;

		for (int i = 0; i < vertexCount; i++){
			int u = findNextLeastWeight(distance, visitedVertex);
			if (u == -1) continue;

			visitedVertex[u] = true;

			for (int v = 0; v < vertexCount; v++){
				boolean areConnected = graph[u][v] != 0;

				int newDistance = distance[u] + graph[u][v];
				boolean isShorterDistance = newDistance < distance[v];

				boolean haveVisited = visitedVertex[v];

				if(!haveVisited && areConnected && isShorterDistance){
					distance[v] = newDistance;
					parents[v] = u;
				}
			}
		}

		if (printAll) {
			printAllIntersections(graph, sourceVertex, vertexNames, distance, parents);
		} else {
			printAnIntersection(sourceVertex, destinationVertex, vertexNames, distance, parents, justPath);
		}
	}

	private static int findNextLeastWeight(int[] distance, boolean[] visitedVertex) {
		int minDistance = Integer.MAX_VALUE;
		int minDistanceVertex = -1;

		for (int i =0; i < distance.length; i++){
			boolean haveVisited = visitedVertex[i];
			boolean isSmaller = distance[i] < minDistance;

			if(!haveVisited && isSmaller){
				minDistance = distance[i];
				minDistanceVertex = i;
			}
		}

		return minDistanceVertex;
	}

	private static void printAnIntersection(int sourceVertex, int destinationVertex, char[] vertexNames, int[] distances, int[] parents, boolean justPath) {
		if (!justPath) {
			System.out.print("\n\nVertex\tDistance\tPath");

			System.out.print("\n" + vertexNames[sourceVertex] + " -> ");
			System.out.print(vertexNames[destinationVertex] + "\t\t ");
			System.out.print(distances[destinationVertex] + "\t\t");
		}

		printPath(vertexNames, destinationVertex, parents);
	}

	private static void printAllIntersections(int[][] graph, int sourceVertex, char[] vertexNames, int[] distances, int[] parents) {
		int vertexCount = distances.length;
		System.out.print("\n\nVertex\tDistance\tPath\tReturn path");

		for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
			if (vertexIndex != sourceVertex) {

				System.out.print("\n" + vertexNames[sourceVertex] + " -> ");
				System.out.print(vertexNames[vertexIndex] + "\t\t ");
				System.out.print(distances[vertexIndex] + "\t\t");

				printPath(vertexNames, vertexIndex, parents);

				System.out.print("\t\t");
				dijkstra(graph, vertexNames, vertexIndex, sourceVertex, false, true);
			}
		}
	}

	private static void printPath(char[] vertexNames, int currentVertex, int[] parents) {
		if (currentVertex == -1) return;

		printPath(vertexNames, parents[currentVertex], parents);

		if (parents[currentVertex] != -1) System.out.print(" -> ");

		System.out.print(vertexNames[currentVertex]);
	}

	private static void describeGraph(int[][] graph, char[] vertexNames) {
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				if (graph[i][j] != 0) {
					System.out.println(vertexNames[i] + " -> " + vertexNames[j] + " = " + graph[i][j]);
				}
			}
		}
	}

	public static void main(String[] args) {
		int graph[][] = new int[][] {
			{ 0, 3, 0, 15, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 3, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 4, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0 },
			{ 3, 0, 6, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 2, 0, 2, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 2, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 4, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 1, 2, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 2, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 4 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 4, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 18 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 4, 0 },
		};

		char vertexNames[] = new char[] {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'
		};

		Dijkstra t = new Dijkstra();

		System.out.println("The following fulfills the requirement to show the list of intersections and their costs between adjacent intersections.\n");
		t.describeGraph(graph, vertexNames);

		System.out.println("\nThe following is the solution to question 1.\n\n=========================");
		t.dijkstra(graph, vertexNames, 7, 15, false, false);
		t.dijkstra(graph, vertexNames, 15, 7, false, false);

		System.out.println("\n\nThe following is the solution to question 2.\n\n=========================");
		t.dijkstra(graph, vertexNames, 7, -1, true, false);

		System.out.println("\n");
	}
}