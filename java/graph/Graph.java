package graph;

import java.util.HashMap;
import java.util.Set;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class Graph<K, V>{
	private HashMap<K, V> vertices;
	private HashMap<K, HashMap<K, Object> > edges;
	private HashMap<K, HashMap<K, Object> > edgesInverted;
	
	private void init() {
		vertices = new HashMap<K, V>();
		edges = new HashMap<K, HashMap<K, Object> >();
		edgesInverted = new HashMap<K, HashMap<K, Object> >();
	}
	
	public Graph() {
		init();
	}

	public Graph(K key, V value) {
		init();
		putIfAbsent(key, value);
	}

	// returns true if a new edge was added
	public boolean attach(K keyA, K keyB) throws NoSuchLabelException {
		if(!containsKey(keyA)) {
			throw new NoSuchLabelException(keyA.toString());
		}
		if(!containsKey(keyB)) {
			throw new NoSuchLabelException(keyB.toString());
		}
		return edges.get(keyA).putIfAbsent(keyB, new Object()) == null &&
				edgesInverted.get(keyB).putIfAbsent(keyA, new Object()) == null;
	}

	// returns 1 if edge from keyA to keyB was created
	// returns 0 if no edge was created
	// returns -1 if edge from keyB to keyA was created
	// returns 2 if two edges were created
	public short attachBoth(K keyA, K keyB) throws NoSuchLabelException {
		if(!containsKey(keyA)) {
			throw new NoSuchLabelException(keyA.toString());
		}
		if(!containsKey(keyB)) {
			throw new NoSuchLabelException(keyB.toString());
		}
		if(attach(keyA, keyB)) {
			if(attach(keyB, keyA)) return 2;
			else return 1;
		} else {
			if(attach(keyB, keyA)) return -1;
			else return 0;
		}
	}

	public boolean isAdjacent(K keyA, K keyB) {
		if(!containsKey(keyA) || !containsKey(keyB)) {
			return false;
		}
		return edges.get(keyA).containsKey(keyB);
	}

	public boolean isUndirected() {
		for(K vertex : edges.keySet()) {
			for(K neighbour : edges.get(vertex).keySet()) {
				if(!isAdjacent(neighbour, vertex)) {
					return false;
				}
			}
		}
		return true;
	}

	public V put(K key, V value) {
		edges.putIfAbsent(key, new HashMap<K, Object>());
		edgesInverted.putIfAbsent(key, new HashMap<K, Object>());
		return vertices.put(key, value);
	}

	public V putIfAbsent(K key, V value) {
		edges.putIfAbsent(key, new HashMap<K, Object>());
		edgesInverted.putIfAbsent(key, new HashMap<K, Object>());
		return vertices.putIfAbsent(key, value);
	}

	public void detach(K keyA, K keyB) throws NoSuchLabelException {
		if(!containsKey(keyA)) {
			throw new NoSuchLabelException(keyA.toString());
		}
		if(!containsKey(keyB)) {
			throw new NoSuchLabelException(keyB.toString());
		}
		edges.get(keyA).remove(keyB);
	}

	public void detachEdgesLeadingFrom(K key) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		edges.get(key).clear();	
	}

	public void detachEdgesLeadingTo(K key) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		Set<K> neighbours = edgesInverted.get(key).keySet();
		for(K neighbour : neighbours) {
			detach(neighbour, key);
		}
	}

	public void detachAllEdges(K key) throws NoSuchLabelException {
		detachEdgesLeadingTo(key);
		detachEdgesLeadingFrom(key);
	}

	public V remove(K key) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		detachAllEdges(key);
		edges.remove(key);
		edgesInverted.remove(key);
		return vertices.remove(key);
	}

	public boolean remove(K key, V value) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		if(vertices.get(key).equals(value)) {
			remove(key);
			return true;
		} else {
			return false;
		}
	}

	public void clear() {
		vertices.clear();
		edges.clear();
		edgesInverted.clear();
	}

	public void forEachNeighbour(K key, Consumer<? super K> action) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		edges.get(key).forEach((neighbour, value) -> {
			action.accept(neighbour);
		});
	}

	public Set<K> neighbourSet(K key) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		return edges.get(key).keySet();
	}

	// HashMap-derived functions

	public boolean containsKey(K key) {
		return vertices.containsKey(key);
	}

	public boolean containsValue(V value) {
		return vertices.containsValue(value);
	}

	public void forEach(BiConsumer<? super K, ? super V> action) {
		vertices.forEach(action);
	}

	public V get(K key) {
		return vertices.get(key);
	}

	public V getOrDefault(K key, V value) {
		return vertices.getOrDefault(key, value);
	}

	public boolean isEmpty() {
		return vertices.isEmpty();
	}

	public Set<K> keySet() {
		return vertices.keySet();
	}

	public V replace(K key, V value) {
		return vertices.replace(key, value);
	}

	public boolean replace(K key, V oldValue, V newValue) {
		return vertices.replace(key, oldValue, newValue);
	}

	public int size() {
		return vertices.size();
	}

	public Collection<V> values() {
		return vertices.values();
	}
}
