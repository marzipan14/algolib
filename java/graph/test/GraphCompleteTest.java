package graph.test;
import graph.Graph;

public class GraphCompleteTest {
	public <K, V> boolean check(Graph<K, V> g) {
		g.setResultFlag(1);
		g.forEach((key, value) -> {
			if(g.neighbourSet(key).size() < g.size()-1) {
				g.setResultFlag(0);
			}
		});
	return g.getResultFlag() == 1;
	}
}
