package graph.test;

import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse; 
import org.junit.Test;
import org.junit.Before;
import graph.Graph;

public class GraphCompleteTestTest {
	private Graph<Integer, String> g;

	@Before
	public void init() {
		g = new Graph<Integer, String>();
	}

	@Test
	public void testCompleteWithSingleCompleteGraph() {
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
		g.put(4, "d");
		g.attachBoth(1, 2);
		g.attachBoth(1, 3);
		g.attachBoth(1, 4);
		g.attachBoth(2, 4);
		g.attachBoth(3, 4);
		g.attachBoth(2, 3);
		assertTrue(GraphCompleteTest.check(g));
	}

	@Test
	public void testCompleteWithTwoSeparateCompleteGraphs() {
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
		g.put(4, "d");
		g.put(5, "e");
		g.put(6, "f");
		g.put(7, "g");
		g.attachBoth(1, 2);
		g.attachBoth(1, 3);
		g.attachBoth(1, 4);
		g.attachBoth(2, 4);
		g.attachBoth(3, 4);
		g.attachBoth(2, 4);
		g.attachBoth(5, 6);
		g.attachBoth(6, 7);
		g.attachBoth(5, 7);
		assertFalse(GraphCompleteTest.check(g));	
	}

	@Test
	public void testCompleteWithSinglePoint() {
		g.put(1, "a");
		assertTrue(GraphCompleteTest.check(g));
	}

	@Test
	public void testCompleteWithTwoPoints() {
		g.put(1, "a");
		g.put(2, "b");
		assertFalse(GraphCompleteTest.check(g));
	}

	@Test
	public void testCompleteWithAlmostCompleteGraph() {
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
		g.put(4, "d");
		g.attachBoth(1, 2);
		g.attachBoth(1, 3);
		g.attachBoth(1, 4);
		g.attachBoth(3, 4);
		g.attachBoth(2, 3);
		assertFalse(GraphCompleteTest.check(g));
	}

	@Test
	public void testCompleteWithCompleteGraphAndSinglePoint() {
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
		g.put(4, "d");
		g.put(5, "e");
		g.attachBoth(1, 2);
		g.attachBoth(1, 3);
		g.attachBoth(1, 4);
		g.attachBoth(3, 4);
		g.attachBoth(2, 3);
		assertFalse(GraphCompleteTest.check(g));
	}

	@Test
	public void testCompleteWithNull() {
		assertTrue(GraphCompleteTest.check(g));
	}
}
