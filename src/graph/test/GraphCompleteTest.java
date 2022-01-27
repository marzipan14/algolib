package graph.test;

import graph.Graph;

public final class GraphCompleteTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.globalFlags.add("__is_complete", true);
		g.forEach((key, value) -> {
			if(g.neighbourSet(key).size() < g.size()-1) {
				g.globalFlags.set("__is_complete", false);
			}
		});
		boolean result = (boolean)g.globalFlags.get("__is_complete");
		g.globalFlags.remove("__is_complete");
		return result;
	}
}
