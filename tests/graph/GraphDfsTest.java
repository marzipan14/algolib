package graph;

import static org.junit.Assert.assertEquals; 
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import java.util.ArrayList;
import java.util.List;

public class GraphDfsTest {
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
		g.put(7, "g");
		g.put(8, "h");
		g.put(9, "i");
		g.put(10, "j");
		g.attach(1, 2);
		g.attach(4, 1);
		g.attachBoth(1, 3);
		g.attach(2, 3);
		g.attach(3, 4);
		g.attachBoth(3, 6);
		g.attach(4, 6);
		g.attach(3, 7);
		g.attach(6, 5);
		g.attach(5, 1);
		g.attach(8, 9);
		g.attach(9, 10);
		g.attachBoth(8, 10);
	}

	@Test
	public void testDfsPre() {
		ArrayList<Integer> modelArray = new ArrayList<Integer>(
			List.of(1, 2, 3, 4, 6, 5, 7, 8, 9, 10)
		);
		g.globalFlags.add("visits", new ArrayList<Integer>());
		g.dfs( 
			(current, previous) -> {
				((ArrayList<Integer>)g.globalFlags.get("visits")).add(current);
			},
			null,
			null,
			null,
			null
		);
		assertEquals(g.globalFlags.get("visits"), modelArray);
	}

	@Test
	public void testDfsPost() {
		ArrayList<Integer> modelArray = new ArrayList<Integer>(
			List.of(5, 6, 4, 7, 3, 2, 1, 10, 9, 8)
		);
		g.globalFlags.add("visits", new ArrayList<Integer>());
		g.dfs( 
			null,
			null,
			null,
			null,
			(current, previous) -> {
				((ArrayList<Integer>)g.globalFlags.get("visits")).add(current);
			}
		);
		assertEquals(g.globalFlags.get("visits"), modelArray);
	}

	@Test
	public void testDfsVisited() {
		ArrayList<Integer> modelArray = new ArrayList<Integer>(
			List.of(1, 1, 3, 1, 6, 3, 8, 10)
		);
		g.globalFlags.add("visits", new ArrayList<Integer>());
		g.dfs(
			null,
			(current, next) -> {
				((ArrayList<Integer>)g.globalFlags.get("visits")).add(next);
			},
			null,
			null,
			null
		);
		assertEquals(g.globalFlags.get("visits"), modelArray);
	}

	@Test
	public void testDfsNotVisitedPre() {
		ArrayList<Integer> modelArray = new ArrayList<Integer>(
			List.of(2, 3, 4, 6, 5, 7, 9, 10)
		);
		g.globalFlags.add("visits", new ArrayList<Integer>());
		g.dfs(
			null,
			null,
			(current, next) -> {
				((ArrayList<Integer>)g.globalFlags.get("visits")).add(next);
			},
			null,
			null
		);
		assertEquals(g.globalFlags.get("visits"), modelArray);
	}

	@Test
	public void testDfsNotVisitedPost() {
		ArrayList<Integer> modelArray = new ArrayList<Integer>(
			List.of(5, 6, 4, 7, 3, 2, 10, 9)
		);
		g.globalFlags.add("visits", new ArrayList<Integer>());
		g.dfs(
			null,
			null,
			null,
			(current, next) -> {
				((ArrayList<Integer>)g.globalFlags.get("visits")).add(next);
			},
			null
		);
		assertEquals(g.globalFlags.get("visits"), modelArray);
	}
}
