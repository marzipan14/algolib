package graph;

import java.util.HashMap;
import java.util.Set;
import java.util.Collection;
import java.util.function.BiConsumer;

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

	public void attach(K keyA, K keyB) throws NoSuchLabelException {
		if(!containsKey(keyA) || !containsKey(keyB)) {
			throw new NoSuchLabelException();
		}
		edges.get(keyA).putIfAbsent(keyB, null);
	}

	public void attach(K keyA, K keyB, V value) throws NoSuchLabelException {
		putIfAbsent(keyB, value);
		attach(keyA, keyB);
	}

	public boolean isAdjacent(K keyA, K keyB) throws NoSuchLabelException {
		if(!containsKey(keyA) || !containsKey(keyB)) {
			throw new NoSuchLabelException();
		}
		return edges.get(keyA).containsKey(keyB);
	}

	public V put(K key, V value) {
		edges.putIfAbsent(key, new HashMap<K, Object>());
		return vertices.put(key, value);
	}

	public V putIfAbsent(K key, V value) {
		edges.putIfAbsent(key, new HashMap<K, Object>());
		return vertices.putIfAbsent(key, value);
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

	public Collection<V> values() {
		return vertices.values();
	}
}
