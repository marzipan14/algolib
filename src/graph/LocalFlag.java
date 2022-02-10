package graph;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.Iterator;

/**
* Describes a way to additionally label
* graph vertices. A flag is declared for the entire
* graph, therefore its name must be unique. However,
* every vertex has its own flag value, which can be
* independently manipulated. Particularly useful for
* dfs/bfs.
*/
public final class LocalFlag<M, V> {
	private HashMap<M, V> vertices;
	private V defaultValue;

	/**
	* A default constructor.
	*
	* @param set the set of vertex labels.
	* @param defaultValue initial and default value for
	* newly created vertices.
	*/
	protected LocalFlag(Set<M> set, V defaultValue) {
		vertices = new HashMap<M, V>();
		Iterator<M> it = set.iterator();
		while(it.hasNext())
			vertices.put(it.next(), defaultValue);
		this.defaultValue = defaultValue;
	}

	/**
	* Sets the flag in the given vertex to a specific value.
	*
	* @param label vertex label.
	* @param value new flag value.
	*/
	protected void set(M label, V value) {
		vertices.put(label, value);
	}

	/**
	* Retrieves the value of the given flag in the specific
	* vertex.
	*
	* @param label vertex label.
	* @return flag value in the given vertex.
	*/
	protected V get(M label) {
		return vertices.get(label);
	}

	/**
	* Retrieves a set of all mappings of the given flag.
	*
	* @return a Set view of the mappings.
	*/
	protected Set<Map.Entry<M, V>> get() {
		return vertices.entrySet();
	}

	/**
	* Sets flag value to default for a newly created vertex.
	*
	* @param label vertex label.
	*/
	protected void extend(M label) {
		vertices.put(label, defaultValue);
	}

	/**
	* Removes flag value associated with a removed vertex.
	*
	* @param label vertex label.
	*/
	protected void shrink(Object label) {
		vertices.remove(label);		
	}
}
