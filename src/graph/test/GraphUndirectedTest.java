package graph.test;

import graph.Graph;

/**
* Makes it possible to check if the graph is undirected.
* Utilises an additional global flag "__is_undirected".
*/
public final class GraphUndirectedTest {
	/**
	* Performs the check.
	*
	* @param g the graph to perform the check on.
	* @return true if the graph is undirected, false otherwise. 
	*/
	public static <K, V> boolean check(Graph<K, V> g) {
		if(g == null) return true;
		g.globalFlags.add("__is_undirected", true);
		g.bfs(
			null,
			(current, next) -> {
				if(!g.isAdjacent(next, current))
					g.globalFlags.set("__is_undirected", false);
			},
			(current, next) -> {
				if(!g.isAdjacent(next, current))
					g.globalFlags.set("__is_undirected", false);
			},
			null
		);
		boolean result = (boolean)g.globalFlags.get("__is_undirected");
		g.globalFlags.remove("__is_undirected");
		return result;
	}
}
