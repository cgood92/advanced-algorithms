package Final;

import java.util.ArrayList;
import java.util.HashMap;

public class DemoFinal {
	/* Problem 1 */

	void avgDistance(int graph[][], int src, int dest){
		boolean[] visited = new boolean[graph.length];
		ArrayList<Integer> lengths = new ArrayList<>();

		dfs(graph, src, dest, visited, lengths, 0);

		System.out.println("Average length: " + getAverageLength(lengths));
	}

	void dfs(int graph[][], int src, int dest, boolean[] visited, ArrayList<Integer> lengths, int currentLength) {
		visited[src] = true;

		if (src == dest) {
			lengths.add(currentLength);
			visited[src] = false;
			return ;
		}

		for (int i = 0; i < graph[src].length; i++) {
			boolean isEdge = graph[src][i] != 0;
			boolean isVisited = visited[i];

			if (isEdge && !isVisited) {
				dfs(graph, i, dest, visited, lengths, currentLength + 1);
			}
		}

		visited[src] = false;
	}

	double getAverageLength(ArrayList<Integer> lengths) {
		double sum = 0;
		double size =  lengths.size();

		for (int i = 0; i < size; i++) {
			sum = sum + lengths.get(i);
		}

		double average = sum / size;

		return average;
	}

	/* Problem 2 */

	void shortest_roundtrip_pre_processed(int[][] graph, int source) {
		int preprocessedGraph[][] = new int[graph.length][graph.length];

		int highestWeight = 0;
		HashMap<Integer, ArrayList<int[]>> locationOfWeights = new HashMap<>();

		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[i].length; j++) {
				int weight = graph[i][j];

				preprocessedGraph[i][j] = weight;

				if (weight >= highestWeight) {
					highestWeight = weight;

					ArrayList<int[]> value = locationOfWeights.get(weight);
					if (value == null) {
						value = new ArrayList<>();
					}

					value.add(new int[] {i, j});

					locationOfWeights.put(weight, value);
				}
			}
		}

		ArrayList<int[]> highestValueLocations = locationOfWeights.get(highestWeight);

		for (int i = 0; i < highestValueLocations.size(); i++) {
			int[] pair = highestValueLocations.get(i);
			preprocessedGraph[pair[0]][pair[1]] = 0;
		}

		for(int j = 1; j < preprocessedGraph.length; j++){
		    (new DemoShortestRoundTrip()).shortest_roundtrip(preprocessedGraph, source, j);
		}
	}
}