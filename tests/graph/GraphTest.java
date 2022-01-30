package graph;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.Before;
import java.util.Set;

public class GraphTest {
	Graph<Integer, String> g;

	@Before
	public void init() {
		g = new Graph<Integer, String>();
		g.put(1, "a");
		g.put(2, "b");
		g.put(3, "c");
	}

	@Test
	public void testAttachIfNoEdge() {
		assertFalse(g.isAdjacent(1, 2));
		assertTrue(g.attach(1, 2));
		assertTrue(g.isAdjacent(1, 2));
	}

	@Test
	public void testAttachIfEdgeExists() {
		g.attach(1, 2);
		assertTrue(g.isAdjacent(1, 2));
		assertFalse(g.attach(1, 2));
		assertFalse(g.isAdjacent(2, 1));
	}

	@Test
	public void testAttachToTheSameVertex() {
		assertFalse(g.attach(1, 1));
	}

	@Test
	public void testAttachTwoInexistentVertices() {
		assertFalse(g.attach(4, 5));
	}

	@Test
	public void testAttachBothIfNoEdge() {
		assertFalse(g.isAdjacent(1, 2));
		assertFalse(g.isAdjacent(2, 1));
		assertEquals(g.attachBoth(1, 2), 2);
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(2, 1));
	}

	@Test
	public void testAttachBothIfLeftEdge() {
		g.attach(1, 2);
		assertTrue(g.isAdjacent(1, 2));
		assertFalse(g.isAdjacent(2, 1));
		assertEquals(g.attachBoth(1, 2), -1);
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(2, 1));
	}

	@Test
	public void testAttachBothIfRightEdge() {
		g.attach(2, 1);
		assertFalse(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(2, 1));
		assertEquals(g.attachBoth(1, 2), 1);
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(2, 1));
	}

	@Test
	public void testAttachBothIfEdgeExists() {
		g.attach(1, 2);
		g.attach(2, 1);
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(2, 1));
		assertEquals(g.attachBoth(1, 2), 0);
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(2, 1));
	}

	@Test
	public void testAttachBothToTheSameVertex() {
		assertEquals(g.attachBoth(1, 1), 0);
	}

	@Test
	public void testAttachBothInexistentVertices() {
		assertEquals(g.attachBoth(4, 5), 0);
	}

	@Test
	public void testDetachIfEdgeExists() {
		g.attach(1, 2);
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.detach(1, 2));
		assertFalse(g.isAdjacent(1, 2));
	}

	@Test
	public void testDetachIfNoEdge() {
		assertFalse(g.isAdjacent(1, 2));
		assertFalse(g.detach(1, 2));
		assertFalse(g.isAdjacent(1, 2));
	}

	@Test
	public void testDetachFromTheSameVertex() {
		assertFalse(g.detach(1, 1));
	}

	@Test
	public void testDetachBothInexistentVertices() {
		assertFalse(g.detach(4, 5));
	}

	@Test
	public void testDetachBothIfNoEdge() {
		assertFalse(g.isAdjacent(1, 2));
		assertFalse(g.isAdjacent(2, 1));
		assertEquals(g.detachBoth(1, 2), 0);
		assertFalse(g.isAdjacent(1, 2));
		assertFalse(g.isAdjacent(2, 1));
	}

	@Test
	public void testDetachBothIfLeftEdge() {
		g.attach(1, 2);
		assertTrue(g.isAdjacent(1, 2));
		assertFalse(g.isAdjacent(2, 1));
		assertEquals(g.detachBoth(1, 2), 1);
		assertFalse(g.isAdjacent(1, 2));
		assertFalse(g.isAdjacent(2, 1));
	}

	@Test
	public void testDetachBothIfRightEdge() {
		g.attach(2, 1);
		assertFalse(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(2, 1));
		assertEquals(g.detachBoth(1, 2), -1);
		assertFalse(g.isAdjacent(1, 2));
		assertFalse(g.isAdjacent(2, 1));
	}

	@Test
	public void testDetachBothIfEdgeExists() {
		g.attach(1, 2);
		g.attach(2, 1);
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(2, 1));
		assertEquals(g.detachBoth(1, 2), 2);
		assertFalse(g.isAdjacent(1, 2));
		assertFalse(g.isAdjacent(2, 1));
	}

	@Test
	public void testDetachBothFromTheSameVertex() {
		assertEquals(g.detachBoth(1, 1), 0);
	}

	@Test
	public void testDetachBothFromInexistentVertices() {
		assertEquals(g.detachBoth(4, 5), 0);
	}

	@Test
	public void testRemoveExistingVertex() {
		g.put(4, "d");
		g.attach(1, 2);
		g.attach(2, 1);
		g.detach(2, 1);
		g.attach(4, 1);
		g.attachBoth(1, 3);
		assertTrue(g.containsKey(1));
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(1, 3));
		assertTrue(g.isAdjacent(3, 1));
		assertTrue(g.isAdjacent(4, 1));
		assertFalse(g.isAdjacent(1, 4));
		assertFalse(g.isAdjacent(2, 1));
		assertTrue(g.remove(1, "a"));
		assertFalse(g.containsKey(1));
		assertFalse(g.isAdjacent(1, 2));
		assertFalse(g.isAdjacent(1, 3));
		assertFalse(g.isAdjacent(3, 1));
		assertFalse(g.isAdjacent(4, 1));
		assertFalse(g.isAdjacent(1, 4));
		assertFalse(g.isAdjacent(2, 1));
	}

	@Test
	public void testRemoveInexistentVertex() {
		assertNull(g.remove(4));
	}

	@Test
	public void testRemoveWithValueIfValueCorrect() {
		g.put(4, "d");
		g.attach(1, 2);
		g.attach(2, 1);
		g.detach(2, 1);
		g.attach(4, 1);
		g.attachBoth(1, 3);
		assertTrue(g.containsKey(1));
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(1, 3));
		assertTrue(g.isAdjacent(3, 1));
		assertTrue(g.isAdjacent(4, 1));
		assertFalse(g.isAdjacent(1, 4));
		assertFalse(g.isAdjacent(2, 1));
		assertTrue(g.remove(1, "a"));
		assertFalse(g.containsKey(1));
		assertFalse(g.isAdjacent(1, 2));
		assertFalse(g.isAdjacent(1, 3));
		assertFalse(g.isAdjacent(3, 1));
		assertFalse(g.isAdjacent(4, 1));
		assertFalse(g.isAdjacent(1, 4));
		assertFalse(g.isAdjacent(2, 1));
	}

	@Test
	public void testRemoveWithValueIfValueIncorrect() {
		g.put(4, "d");
		g.attach(1, 2);
		g.attach(2, 1);
		g.detach(2, 1);
		g.attach(4, 1);
		g.attachBoth(1, 3);
		assertTrue(g.containsKey(1));
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(1, 3));
		assertTrue(g.isAdjacent(3, 1));
		assertTrue(g.isAdjacent(4, 1));
		assertFalse(g.isAdjacent(1, 4));
		assertFalse(g.isAdjacent(2, 1));
		assertFalse(g.remove(1, "b"));
		assertTrue(g.containsKey(1));
		assertTrue(g.isAdjacent(1, 2));
		assertTrue(g.isAdjacent(1, 3));
		assertTrue(g.isAdjacent(3, 1));
		assertTrue(g.isAdjacent(4, 1));
		assertFalse(g.isAdjacent(1, 4));
		assertFalse(g.isAdjacent(2, 1));
	}

	@Test
	public void testRemoveWithValueIfVertexDoesntExist() {
		assertFalse(g.remove(4, "c"));
	}

	@Test
	public void testIsAdjacentIfVerticesDontExist() {
		assertFalse(g.isAdjacent(4, 5));
	}

	@Test
	public void testIsAdjacentForTheSameVertex() {
		assertFalse(g.isAdjacent(1, 1));
	}

	@Test
	public void testForEachNeighbourIfVertexExists() {
		g.put(4, "d");
		g.attach(1, 2);
		g.attach(1, 3);
		g.attach(4, 1);
		assertTrue(g.get(1).equals("a"));
		assertTrue(g.get(2).equals("b"));
		assertTrue(g.get(3).equals("c"));
		assertTrue(g.get(4).equals("d"));
		assertTrue(g.forEachNeighbour(1, (neighbour) -> {
			g.put(neighbour, "x");
		}));
		assertTrue(g.get(1).equals("a"));
		assertTrue(g.get(2).equals("x"));
		assertTrue(g.get(3).equals("x"));
		assertTrue(g.get(4).equals("d"));
	}

	@Test
	public void testForEachNeighbourIfVertexDoesntExist() {
		assertFalse(g.forEachNeighbour(4, (neighbour) -> {
			g.put(neighbour, "x");	
		}));
	}

	@Test
	public void testNeighbourSetIfVertexExists() {
		g.put(4, "d");
		g.attach(1, 2);
		g.attach(1, 3);
		g.attach(4, 1);
		Set<Integer> S = g.neighbourSet(1);
		assertEquals(S.size(), 2);
		assertTrue(S.contains(2));
		assertTrue(S.contains(3));
	}

	@Test
	public void testNeighbourSetIfVertexDoesntExist() {
		assertNull(g.neighbourSet(4));
	}

	@Test
	public void testReverseForArbitraryGraph() {
		g.put(4, "d");
		g.put(5, "e");
		g.attach(1, 4);
		g.attachBoth(1, 2);
		g.attach(3, 1);
		g.attach(3, 4);
		g.attach(4, 2);
		g.attach(2, 5);
		g.attach(4, 5);
		
		Graph<Integer, String> gReversed = Graph.reverse(g);
		assertTrue(gReversed.containsKey(1));
		assertTrue(gReversed.containsKey(2));
		assertTrue(gReversed.containsKey(3));
		assertTrue(gReversed.containsKey(4));
		assertTrue(gReversed.containsKey(5));
		assertTrue(gReversed.isAdjacent(1, 2));
		assertTrue(gReversed.isAdjacent(2, 1));
		assertTrue(gReversed.isAdjacent(4, 1));
		assertTrue(gReversed.isAdjacent(1, 3));
		assertTrue(gReversed.isAdjacent(4, 3));
		assertTrue(gReversed.isAdjacent(2, 4));
		assertTrue(gReversed.isAdjacent(5, 2));
		assertTrue(gReversed.isAdjacent(5, 4));
	}

	@Test
	public void testReverseForPoint() {
		Graph<Integer, String> g1 = new Graph<Integer, String>();
		g1.put(1, "a");
		Graph<Integer, String> g2 = Graph.reverse(g1);
		assertTrue(g2.containsKey(1));
	}

	@Test
	public void testReverseForNull() {
		Graph<Integer, String> g1 = Graph.reverse(null);
		assertNull(g1);
	}
}
