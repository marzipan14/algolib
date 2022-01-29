package graph.test;

import graph.Graph;

public final class GraphConnectedTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.clearVisited();
		g.dfs(g.anyKey(), null, null, null, null, null);
		return allVisited(g);
	}

	/**
	* Checks if all vertices of the graph have been visited.
	*
	* @return true if all vertices have been marked as 'visited'.
	*/
	public <K, V> boolean allVisited(Graph<K, V> g) {
		g.globalFlags.add("__allVisited", true);
		g.forEach((key, value) -> {
			if(!g.hasBeenVisited(key)){
				g.globalFlags.set("__all_visited", false);
			}
		});
		boolean result = (boolean)g.globalFlags.get("__allVisited");
		g.globalFlags.remove("__allVisited");
		return result;
	}
}
