package graph.test;

import java.util.HashMap;
import graph.Graph;

public class GraphUndirectedTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.setResultFlag(1);
		g.forEach((key, value) -> {
			g.forEachNeighbour(key, (neighbour) -> {
				if(!g.isAdjacent(neighbour, key)) {
					g.setResultFlag(0);
				}
			});
		});
		return g.getResultFlag() == 1;
	}
}
