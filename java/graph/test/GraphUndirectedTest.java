package graph.test;
import graph.Graph;

public class GraphUndirectedTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.addStatusFlag("undirected", true);
		g.forEach((key, value) -> {
			g.forEachNeighbour(key, (neighbour) -> {
				if(!g.isAdjacent(neighbour, key)) {
					g.setStatusFlag("undirected", false);
				}
			});
		});
		return (Boolean)g.removeStatusFlag("undirected");
	}
}
