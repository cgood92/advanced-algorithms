/* Real world problem: My daughter loves reading choose-your-own-adventure books.  However,
it is late, and she's a little cranky, so it's time to get her to bed as fast as I can.
I know, that her favorite ending is the one with the bunny rabbit dancing in the sunset on page 4.
What is the fastest way I get from the start to page 4, where there are many paths to get there?
Weight of each edge is the amount of words on the page.  Thus, the bigger the weight means more words
on the page. */

import java.util.*;
import java.util.stream.*;

/* This code is heavily based on a solution contributed by Harikrishnan Rajan
at https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/.
It has been modified to fit the needs of the real-world application below */

class DijkstrasAlgorithm {
	private static final int NO_PARENT = -1;

	public static Integer[] getShortestPath(int[][] adjacencyMatrix, int endPage) {
		int nVertices = adjacencyMatrix[0].length;

		int[] shortestDistances = new int[nVertices];
		boolean[] added = new boolean[nVertices];

		for (int vertexIndex = 0; vertexIndex < nVertices;
			 vertexIndex++)
		{
			shortestDistances[vertexIndex] = Integer.MAX_VALUE;
			added[vertexIndex] = false;
		}

		shortestDistances[0] = 0;

		int[] parents = new int[nVertices];

		parents[0] = NO_PARENT;

		for (int i = 1; i < nVertices; i++)
		{
			int nearestVertex = -1;
			int shortestDistance = Integer.MAX_VALUE;
			for (int vertexIndex = 0;
				 vertexIndex < nVertices;
				 vertexIndex++)
			{
				if (!added[vertexIndex] &&
					shortestDistances[vertexIndex] <
						shortestDistance)
				{
					nearestVertex = vertexIndex;
					shortestDistance = shortestDistances[vertexIndex];
				}
			}

			added[nearestVertex] = true;

			for (int vertexIndex = 0;
				 vertexIndex < nVertices;
				 vertexIndex++)
			{
				int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

				if (edgeDistance > 0
					&& ((shortestDistance + edgeDistance) <
					shortestDistances[vertexIndex]))
				{
					parents[vertexIndex] = nearestVertex;
					shortestDistances[vertexIndex] = shortestDistance +
						edgeDistance;
				}
			}
		}

		return buildPath(endPage, parents);
	}

	private static Integer[] buildPath(int targetPage, int[] parents) {
		List<Integer> path = new ArrayList<>();
		int currentPage = targetPage;

		while (currentPage != NO_PARENT) {
			path.add(0, currentPage);

			currentPage = parents[currentPage];
		}

		return path.toArray(new Integer[path.size()]);
	}
}

class ChooseYourOwnAdventureForTheTired {
	private enum BOOKS {
		PETER_COTTON_TAIL(new int[][] {
			{ 0, 4, 0, 0, 0, 0, 0, 8, 0 },
			{ 4, 0, 8, 0, 0, 0, 0, 11, 0 },
			{ 0, 8, 0, 7, 0, 4, 0, 0, 2 },
			{ 0, 0, 7, 0, 9, 14, 0, 0, 0 },
			{ 0, 0, 0, 9, 0, 10, 0, 0, 0 },
			{ 0, 0, 4, 0, 10, 0, 2, 0, 0 },
			{ 0, 0, 0, 14, 0, 2, 0, 1, 6 },
			{ 8, 11, 0, 0, 0, 0, 1, 0, 7 },
			{ 0, 0, 2, 0, 0, 0, 6, 7, 0 }
		});

		private int[][] matrix;
		public int[][] getMatrix(){
			return this.matrix;
		}

		BOOKS (int[][] matrix){
			this.matrix = matrix;
		}
	}

	public static void main(String[] args) {
		int[][] bookMatrix = BOOKS.PETER_COTTON_TAIL.getMatrix();

		int desiredEndPage = 4;

		Integer[] shortestPath = DijkstrasAlgorithm.getShortestPath(bookMatrix, desiredEndPage);

		String stringPath = Arrays.stream(shortestPath)
			.map(String::valueOf)
			.collect(Collectors.joining(" -> "));

		System.out.println("Shortest path to get to page " + desiredEndPage + ": " + stringPath);
	}
}