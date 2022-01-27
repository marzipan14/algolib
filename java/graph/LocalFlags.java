package graph;

import java.util.HashMap;

/**
* Describes a way to additionally label
* graph vertices. A flag is declared for the entire
* graph, therefore its name must be unique. However,
* every vertex has its own flag value, which can be
* independently manipulated. Particularly useful for
* dfs/bfs.
*/
public final class LocalFlags<K> extends Flags {
	HashMap<K, HashMap<String, Object> > flags;
	HashMap<String, Object> defaultFlags;

	/**
	* A default constructor.
	*/
	public LocalFlags() {
		flags = new HashMap<K, HashMap<String, Object> >();
		defaultFlags = new HashMap<String, Object>();
	}

	/**
	* Adds a new flag to the graph. Performed in global
	* scope, meaning, every vertex gets a new flag with
	* the given initial value. If the flag already exists,
	* does nothing.
	*
	* @param flag flag name.
	* @param value initial flag value.
	* @return true if a new flag has been added, false
	* if it already existed.
	*/
	@Override
	public boolean add(String flag, Object value) {
		if(defaultFlags.containsKey(flag)) {
			return false;
		}
		defaultFlags.put(flag, value);
		flags.forEach((key, val) -> {
			val.put(flag, value);
		});
		return true;
	}

	/**
	* Removes a flag from the graph. Performed in global
	* scope, meaning, once the flag is gone, it's gone for
	* every single vertex. If the flag doesn't exist, does
	* nothing.
	*
	* @param flag name
	* @return true if the flag has been removed, false
	* if it wasn't there in the first place.
	*/
	@Override
	public boolean remove(Object flag) {
		if(!defaultFlags.containsKey(flag)) {
			return false;
		}
		defaultFlags.remove(flag);
		flags.forEach((key, value) -> {
			value.remove(flag);	
		});
		return true;
	}

	/**
	* Sets the given flag in the given vertex to a specific
	* value. If the flag or the vertex doesn't exist, does nothing.
	*
	* @param key vertex label.
	* @param flag flag name.
	* @param value new value.
	* @return true if the flag has been set, flase if it doesn't
	* exist, or if the given vertex doesn't exist.
	*/
	public boolean set(K key, String flag, Object value) {
		if(!flags.containsKey(key)) {
			return false;
		}
		if(!defaultFlags.containsKey(flag)) {
			return false;
		}
		flags.get(key).put(flag, value);
		return true;
	}

	/**
	* Retrieves the value of the given flag in the specific
	* vertex. If the flag or the vertex doesn't exist, does
	* nothing.
	*
	* @param key vertex label.
	* @param flag flag name.
	* @return the value of the given flag in the given vertex,
	* or null if either the flag or the vertex doesn't exist.
	*/
	public Object get(K key, String flag) {
		if(!flags.containsKey(key)) {
			return null;
		}
		if(!defaultFlags.containsKey(flag)) {
			return null;
		}
		return flags.get(key).get(flag);
	}

	/**
	* Deletes all local flags and their values.
	*/
	@Override
	public void clear() {
		flags.clear();
		defaultFlags.clear();
	}

	/**
	* Sets all flag values to default for a newly added vertex.
	*
	* @param key vertex label.
	*/
	public void expand(K key) {
		flags.putIfAbsent(key, new HashMap<String, Object>(defaultFlags));
	}
}
