package graph;

/**
* Describes a way to additionally label the graph
* with a unique value. Particularly useful in dfs/bfs.
*/
public final class GlobalFlag<V> {
	private V value;

	/** 
	* A default constructor.
	*
	* @param value initial flag value.
	*/
	protected GlobalFlag(V value) {
		this.value = value;
	}

	/**
	* Sets the flag to the given value.
	*/
	protected void set(V value) {
		this.value = value;
	}

	/**
	* Retrieves the flag value.
	*
	* @return flag value.
	*/
	protected V get() {
		return value;
	}
}
