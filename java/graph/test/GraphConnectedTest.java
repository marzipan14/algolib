package graph.test;

import graph.Graph;
import graph.NoSuchLabelException;

public class GraphConnectedTest {
	public <K, V> boolean check(Graph<K, V> g) {
		try {
			g.clearVisited();
			g.dfs(g.anyKey(), null, null, null, null, null);
		}
		catch (NoSuchLabelException e) {
			System.out.println(e.getMessage());
		}
		return g.allVisited();
	}
};
