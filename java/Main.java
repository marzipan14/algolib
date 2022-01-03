import graph.Graph;
import graph.NoSuchLabelException;
import graph.test.GraphConnectedTest;
import graph.test.GraphUndirectedTest;

public class Main{
	public static void main(String[] args){
		try {
			Graph<Integer, String> g = new Graph<Integer, String>(1, "MK");
			g.put(2, "MS");
			g.put(3, "KZ");
			g.put(3, "MW");
			g.put(4, "KZ");
			g.putIfAbsent(4, "KG");
			g.putIfAbsent(5, "KG");
			assert g.attach(1, 2);
			assert g.attach(2, 1);
			assert !g.attach(1, 2);
			assert g.attach(3, 1);
			assert !g.attach(3, 1);
			
			g.put(6, "ZK");
			g.attach(5, 6);
			assert g.isAdjacent(1, 2);
			assert !g.isAdjacent(1, 3);
			assert g.isAdjacent(3, 1);
			g.detach(3, 1);
			assert !g.isAdjacent(3, 1);
			g.attach(3, 1);
			g.attach(1, 4);
			assert g.isAdjacent(1, 2);
			assert g.isAdjacent(2, 1);
			assert g.isAdjacent(1, 4);
			assert g.isAdjacent(3, 1);
			g.remove(1, "KZ");
			assert g.isAdjacent(1, 2);
			assert g.isAdjacent(2, 1);
			assert g.isAdjacent(1, 4);
			assert g.isAdjacent(3, 1);
			assert g.containsKey(1);
			assert g.attachBoth(1, 2) == 0;
			assert g.attachBoth(1, 3) == 1;
			assert g.attachBoth(1, 4) == -1;
			assert g.attachBoth(4, 5) == 2;
			assert !g.isAdjacent(3, 6);
			assert !g.isAdjacent(4, 6);
			assert !g.isAdjacent(2, 6);
			g.forEachNeighbour(1, neighbour -> {
				g.attach(neighbour, 6);
			});
			assert g.isAdjacent(3, 6);
			assert g.isAdjacent(4, 6);
			assert g.isAdjacent(2, 6);
			GraphUndirectedTest test1 = new GraphUndirectedTest();
			assert !test1.check(g);
			g.attach(6, 3);
			g.attach(6, 4);
			g.attach(6, 5);
			g.attach(6, 2);
			assert test1.check(g);
			g.put(7, "SS");
			g.put(8, "ZZ");
			g.attach(7, 8);
			g.dfs(
				(key, previous) -> {
					System.out.println("Started analysing " + key.toString());
				},
				(key, neighbour) -> {
					System.out.println(neighbour.toString() + " has already been visited");
				},
				(key, neighbour) -> {
					System.out.println("Going to " + neighbour.toString());
				},
				(key, neighbour) -> {
					System.out.println("Returning from " + neighbour.toString());
				},
				(key, previous) -> {
					System.out.println("Finished analysing " + key.toString());
				}
			);
			GraphConnectedTest test2 = new GraphConnectedTest();
			System.out.println(test2.check(g));
			g.attach(6, 7);
			System.out.println(test2.check(g));
			System.out.println(test2.check(g));
			g.put(9, "JP");
			System.out.println(test2.check(g));
		}
		catch(NoSuchLabelException e){
			System.out.println(e.getMessage());
		}
	}
}
