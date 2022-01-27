package graph;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class GraphTest {
	@Test
	public void testIsAdjacent() {
		Graph<Integer, String> g = new Graph<Integer, String>();
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
		g.attach(1, 2);
		assertTrue(g.isAdjacent(1, 2));
	}
}
