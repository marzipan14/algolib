package graph.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import graph.Graph;

public class GraphUndirectedTestTest {
	private Graph<Integer, String> g;

	@Before
	public void init() {
		g = new Graph<Integer, String>();
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
		g.put(4, "d");
		g.put(5, "e");
		g.put(6, "f");
	}

	@Test
	public void testUndirectedWithSingleUndirectedGraph() {
		g.attachBoth(1, 3);
		g.attachBoth(1, 2);
		g.attachBoth(2, 6);
		g.attachBoth(3, 6);
		g.attachBoth(3, 5);
		g.attachBoth(5, 4);
		assertTrue(GraphUndirectedTest.check(g));
	}

	@Test
	public void testUndirectedGraphWithTwoUndirectedGraphs() {
		g.attachBoth(1, 3);
		g.attachBoth(1, 2);
		g.attachBoth(2, 6);
		g.attachBoth(3, 6);
		g.attachBoth(5, 4);
		assertTrue(GraphUndirectedTest.check(g));
	}

	@Test
	public void testUndirectedWithDirectedGraph() {
		g.attachBoth(1, 3);
		g.attach(1, 2);
		g.attachBoth(2, 6);
		g.attachBoth(3, 6);
		g.attachBoth(3, 5);
		g.attachBoth(5, 4);
		assertFalse(GraphUndirectedTest.check(g));
	}

	@Test
	public void testUndirectedWithDirectedAndUndirectedGraph() {
		g.attachBoth(1, 3);
		g.attachBoth(1, 2);
		g.attachBoth(2, 6);
		g.attachBoth(3, 6);
		g.attach(5, 4);
		assertFalse(GraphUndirectedTest.check(g));
	}

	@Test
	public void testUndirectedWithPoint() {
		Graph<Integer, String> g1 = new Graph<Integer, String>(1, "a");
		assertTrue(GraphUndirectedTest.check(g1));
	}

	@Test
	public void testUndirectedWithNull() {
		assertTrue(GraphUndirectedTest.check(null));
	}
}
