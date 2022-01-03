package graph.test;
import graph.Graph;

public class GraphCompleteTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.addStatusFlag("complete", true);
		g.forEach((key, value) -> {
			if(g.neighbourSet(key).size() < g.size()-1) {
				g.setStatusFlag("complete", false);
			}
		});
	return (Boolean)g.removeStatusFlag("complete");
	}
}
