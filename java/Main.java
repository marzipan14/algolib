import graph.Graph;
import graph.NoSuchLabelException;

public class Main{
	public static void main(String[] args){
		try {
			Graph<Integer, String> g = new Graph<Integer, String>(1, "new");
			g.putIfAbsent(3, "another");
			g.putIfAbsent(5, "ok");
			System.out.println(g.get(3));
			System.out.println(g.isAdjacent(1, 3));
			g.attach(1, 3);
			System.out.println(g.isAdjacent(1, 3));
			g.attach(3, 4, "wow");
			System.out.println(g.isAdjacent(3, 4));
		}
		catch(NoSuchLabelException e){
			System.out.println(e.getMessage());
		}
	}
}
