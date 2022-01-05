package graph.test;
import graph.Graph;

public class GraphCompleteTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.addGFlag("is_complete", true);
		g.forEach((key, value) -> {
			if(g.neighbourSet(key).size() < g.size()-1) {
				g.setGFlag("is_complete", false);
			}
		});
		boolean result = (boolean)g.getGFlag("is_complete");
		g.removeGFlag("is_complete");
		return result;
	}
}
