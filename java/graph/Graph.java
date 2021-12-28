package graph;

import java.util.HashMap;

public class Graph<K, V>{
	private HashMap<K, V> vertices;
	private HashMap<K, HashMap<K, Object> > edges;
	
	private void init() {
		vertices = new HashMap<K, V>();
		edges = new HashMap<K, HashMap<K, Object> >();
	}
	
	public Graph() {
		init();
	}

	public Graph(K key, V value) {
		init();
		putIfAbsent(key, value);
	}

	public V putIfAbsent(K key, V value) {
		edges.putIfAbsent(key, new HashMap<K, Object>());
		return vertices.putIfAbsent(key, value);
	}

	public V get(K key){
		return vertices.get(key);
	}

	public boolean containsKey(K key) {
		return vertices.containsKey(key);
	}

	// should I create a new object?
	public void attach(K keyA, K keyB) throws NoSuchLabelException {
		if(!containsKey(keyA) || !containsKey(keyB)) {
			throw new NoSuchLabelException();
		}
		edges.get(keyA).putIfAbsent(keyB, null);
	}

	public void attach(K keyA, K keyB, V value) {
		putIfAbsent(keyB, value);
		attach(keyA, keyB);
	}

	public boolean isAdjacent(K keyA, K keyB) {
		try {
			HashMap<K, Object> neighbours = edges.get(keyA);
			return neighbours.containsKey(keyB);
		}
		catch(NullPointerException e) {
			return false;
		}
	}
}
