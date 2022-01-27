package graph;

import java.util.HashMap;

/**
* Describes a way to additionally label the graph
* with a unique value. Particularly useful in dfs/bfs.
*/
public final class GlobalFlags extends Flags {
	HashMap<String, Object> flags;

	/** 
	* A default constructor.
	*/
	public GlobalFlags() {
		flags = new HashMap<String, Object>();
	}

	/**
	* Adds a new flag with a specific value. If a flag
	* with the given name already exists, does nothing. 
	*
	* @param flag flag name.
	* @param def flag value.
	* @return true if a new flag has been added, false
	* if it already existed.
	*/
	@Override
	public boolean add(String flag, Object def) {
		if(flags.containsKey(flag)) {
			return false;
		}
		flags.put(flag, def);
		return true;
	}

	/**
	* Removes the given flag. If no such flag exists,
	* does nothing.
	*
	* @param flag flag name.
	* @return true if the flag has been removed, false
	* if it wasn't there in the first place.
	*/
	@Override
	public boolean remove(Object flag) {
		if(!flags.containsKey(flag)) {
			return false;
		}
		flags.remove(flag);
		return true;
	}

	/**
	* Sets a flag to the given value. If no such flag
	* exists, does nothing.
	*
	* @param flag flag name.
	* @param value new value.
	* @return true if the flag exists, false otherwise.
	*/
	public boolean set(String flag, Object value) {
		if(!flags.containsKey(flag)) {
			return false;
		}
		flags.put(flag, value);
		return true;
	}

	/**
	* Retrieves the value of the given flag.
	*
	* @param flag flag name.
	* @return the value of the given flag, or null
	* if the flag doesn't exist.
	*/
	public Object get(String flag) {
		if(!flags.containsKey(flag)) {
			return null;
		}
		return flags.get(flag);
	}

	/**
	* Deletes all global flags and their values.
	*/
	@Override
	public void clear() {
		flags.clear();
	}
}
