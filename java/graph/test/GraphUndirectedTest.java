package graph.test;
import graph.Graph;

public class GraphUndirectedTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.addGFlag("is_undirected", true);
		g.forEach((key, value) -> {
			g.forEachNeighbour(key, (neighbour) -> {
				if(!g.isAdjacent(neighbour, key)) {
					g.setGFlag("is_undirected", false);
				}
			});
		});
		boolean result = (boolean)g.getGFlag("is_undirected");
		g.removeGFlag("is_undirected");
		return result;
	}
}
