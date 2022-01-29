package graph.test;

import graph.Graph;

/**
* Makes it possible to check if the graph is complete.
* Utilises an additional flag "__is_complete".
*/
public final class GraphCompleteTest {
	/**
	* Performs the check.
	*
	* @param g the graph to perform the check on.
	* @return true if the graph is complete, false otherwise. 
	*/
	public <K, V> boolean check(Graph<K, V> g) {
		if(g == null) return false;
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
