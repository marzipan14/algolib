package graph.test;

import static org.junit.Assert.assertTrue; 
import static org.junit.Assert.assertFalse; 
import org.junit.Test;
import org.junit.Before;
import graph.Graph;

public class GraphConnectedTestTest {
	private Graph<Integer, String> g;

	@Before
	public void init() {
		g = new Graph<Integer, String>();
	}
	
	@Test
	public void testConnectedWithSingleConnectedGraph() {
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
		g.put(4, "d");
		g.put(5, "e");
		g.put(6, "f");
		g.put(7, "g");
		g.put(8, "h");
		g.put(9, "i");
		g.attach(1, 3);
		g.attach(2, 1);
		g.attach(5, 2);
		g.attach(4, 2);
		g.attach(5, 4);
		g.attach(3, 5);
		g.attach(7, 3);
		g.attach(3, 6);
		g.attach(6, 7);
		g.attach(1, 8);
		g.attach(8, 7);
		g.attach(6, 9);
		g.attach(9, 6);
		assertTrue(GraphConnectedTest.check(g));
	}

	@Test
	public void testConnectedWithSingleUnconnectedGraph() {
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
		g.put(4, "d");
		g.put(5, "e");
		g.put(6, "f");
		g.attach(1, 2);
		g.attachBoth(1, 6);
		g.attach(6, 5);
		g.attach(6, 2);
		g.attach(3, 6);
		g.attach(3, 4);
		g.attach(4, 6);
		assertFalse(GraphConnectedTest.check(g));
	}

	@Test
	public void testConnectedWithTwoConnectedGraphs() {
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
		g.put(4, "d");
		g.put(5, "e");
		g.put(6, "f");
		g.put(7, "g");
		g.put(8, "h");
		g.put(9, "i");
		g.put(10, "j");
		g.put(11, "k");
		g.put(12, "l");
		g.attach(1, 3);
		g.attach(2, 1);
		g.attach(5, 2);
		g.attach(4, 2);
		g.attach(5, 4);
		g.attach(3, 5);
		g.attach(7, 3);
		g.attach(3, 6);
		g.attach(6, 7);
		g.attach(1, 8);
		g.attach(8, 7);
		g.attach(6, 9);
		g.attach(9, 6);
		g.attach(10, 11);
		g.attach(11, 12);
		g.attach(12, 10);
		assertFalse(GraphConnectedTest.check(g));
	}

	@Test
	public void testConnectedWithNull() {
		assertTrue(GraphConnectedTest.check(g));
	}

	@Test
	public void testConnectedWithSinglePoint() {
		g.put(1, "a");
		assertTrue(GraphConnectedTest.check(g));
	}

	@Test
	public void testConnectedWithTwoPoints() {
		g.put(1, "a");
		g.put(2, "b");
		assertFalse(GraphConnectedTest.check(g));
	}

	@Test
	public void testConnectedWithAlmostConnected() {
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
		g.put(4, "d");
		g.put(5, "e");
		g.put(6, "f");
		g.put(7, "g");
		g.put(8, "h");
		g.put(9, "i");
		g.attach(1, 3);
		g.attach(2, 1);
		g.attach(5, 2);
		g.attach(4, 2);
		g.attach(5, 4);
		g.attach(3, 5);
		g.attach(7, 3);
		g.attach(3, 6);
		g.attach(6, 7);
		g.attach(8, 7);
		g.attach(6, 9);
		g.attach(9, 6);
		assertFalse(GraphConnectedTest.check(g));
	}
}
