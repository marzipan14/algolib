package graph.test;

import graph.Graph;

public final class GraphConnectedTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.clearVisited();
		g.dfs(g.anyKey(), null, null, null, null, null);
		return g.allVisited();
	}
};
