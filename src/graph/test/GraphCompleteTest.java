package graph.test;

import graph.Graph;

/**
* Makes it possible to check if the graph is complete.
* Utilises an additional global flag "__is_complete".
*/
public final class GraphCompleteTest {
	/**
	* Performs the check.
	*
	* @param g the graph to perform the check on.
	* @return true if the graph is complete, false otherwise. 
	*/
	public static <K, V> boolean check(Graph<K, V> g) {
		if(g == null) return false;
		g.addGlobalFlag("__is_complete", true);
		g.forEach((key, value) -> {
			if(g.neighbourSet(key).size() < g.size()-1) {
				g.setGlobalFlag("__is_complete", false);
			}
		});
		boolean result = (boolean)g.getGlobalFlag("__is_complete");
		g.removeGlobalFlag("__is_complete");
		return result;
	}
}
