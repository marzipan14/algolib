package graph.test;

import graph.Graph;

/**
* Makes it possible to check if the graph is connected.
* Utilises an additional global flag "__all_visited".
*/
public final class GraphConnectedTest {
	/**
	* Performs the check. Clears the "visited" flag.
	*
	* @param g the graph to perform the check on.
	* @return true if the graph is connected, false otherwise. 
	*/
	public static <K, V> boolean check(Graph<K, V> g) {
		if(g == null) return true;
		Graph<K, V> gReversed = Graph.reverse(g);
		K arbitraryKey = g.anyKey();
		g.clearVisited();
		g.dfs(arbitraryKey, null,null, null, null, null);
		gReversed.clearVisited();
		gReversed.dfs(arbitraryKey, null, null, null, null, null);
		return allVisited(g) && allVisited(gReversed);
	}

	/**
	* Checks if all vertices of the graph have been visited.
	*
	* @param g the graph to perform the check on.
	* @return true if all vertices have been marked as 'visited'.
	*/
	private static <K, V> boolean allVisited(Graph<K, V> g) {
		g.addGlobalFlag("__all_visited", true);
		g.forEach((key, value) -> {
			if(!g.hasBeenVisited(key)){
				g.setGlobalFlag("__all_visited", false);
			}
		});
		boolean result = (boolean)g.getGlobalFlag("__all_visited");
		g.removeGlobalFlag("__all_visited");
		return result;
	}
}
