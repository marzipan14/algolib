package graph.test;
import graph.Graph;

public class GraphCompleteTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.addGFlag("__is_complete", true);
		g.forEach((key, value) -> {
			if(g.neighbourSet(key).size() < g.size()-1) {
				g.setGFlag("__is_complete", false);
			}
		});
		return (boolean)g.removeGFlag("__is_complete");
	}
}
