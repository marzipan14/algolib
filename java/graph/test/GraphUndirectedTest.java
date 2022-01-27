package graph.test;

import graph.Graph;

public final class GraphUndirectedTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.globalFlags.add("__is_undirected", true);
		g.forEach((key, value) -> {
			g.forEachNeighbour(key, (neighbour) -> {
				if(!g.isAdjacent(neighbour, key)) {
					g.globalFlags.set("__is_undirected", false);
				}
			});
		});
		boolean result = (boolean)g.globalFlags.get("__is_undirected");
		g.globalFlags.remove("__is_undirected");
		return result;
	}
}
