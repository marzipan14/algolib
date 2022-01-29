package graph.test;

import graph.Graph;

/**
* Makes it possible to check if the graph is connected.
* Utilises an additional global flag "__all_visited".
*/
public final class GraphConnectedTest {
	/**
	* Performs the check.
	*
	* @param g the graph to perform the check on.
	* @return true if the graph is connected, false otherwise. 
	*/
	public static <K, V> boolean check(Graph<K, V> g) {
		g.clearVisited();
		g.dfs(g.anyKey(), null, null, null, null, null);
		return allVisited(g);
	}

	/**
	* Checks if all vertices of the graph have been visited.
	*
	* @param g the graph to perform the check on.
	* @return true if all vertices have been marked as 'visited'.
	*/
	private <K, V> boolean allVisited(Graph<K, V> g) {
		g.globalFlags.add("__all_visited", true);
		g.forEach((key, value) -> {
			if(!g.hasBeenVisited(key)){
				g.globalFlags.set("__all_visited", false);
			}
		});
		boolean result = (boolean)g.globalFlags.get("__all_visited");
		g.globalFlags.remove("__all_visited");
		return result;
	}
}
