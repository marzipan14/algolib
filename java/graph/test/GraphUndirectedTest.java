package graph.test;
import graph.Graph;

public class GraphUndirectedTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.addGFlag("__is_undirected", true);
		g.forEach((key, value) -> {
			g.forEachNeighbour(key, (neighbour) -> {
				if(!g.isAdjacent(neighbour, key)) {
					g.setGFlag("__is_undirected", false);
				}
			});
		});
		return (boolean)g.removeGFlag("__is_undirected");
	}
}
