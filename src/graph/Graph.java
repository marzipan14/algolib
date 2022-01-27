package graph;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

/**
* A class that allows for creating a graph, that is,
* a set of connected vertices. Each vertex has a unique
* label and a not-necessarily-unique value associated
* with it. Every vertex can be connected to any other vertex,
* with the exception of itself, with at most one directed edge.
*/
public class Graph<K, V> extends HashMap<K, V> {
	private HashMap<K, HashMap<K, Object> > edges;
	private HashMap<K, HashMap<K, Object> > edgesInverted;
	private HashMap<K, Boolean> visited;
	public LocalFlags<K> localFlags;
	public GlobalFlags globalFlags;

	/**
	* Initialises private variables.
	*/
	private void init() {
		edges = new HashMap<K, HashMap<K, Object> >();
		edgesInverted = new HashMap<K, HashMap<K, Object> >();
		visited = new HashMap<K, Boolean>();
		localFlags = new LocalFlags<K>();
		globalFlags = new GlobalFlags();
	}
	
	/**
	* A default constructor.
	*/
	public Graph() {
		super();
		init();
	}

	/**
	* A constructor with an initial vertex.
	* 
	* @param key vertex label.
	* @param value vertex value.
	*/
	public Graph(K key, V value) {
		this();
		put(key, value);
	}

	/**
	* Adds a new vertex to the graph; if the vertex with the 
	* given label already exists, the function updates its value.
	* The 'visited' value remains unchanged.
	*
	* @param key vertex label.
	* @param value vertex value.
	* @return the previous value associated with key, or null if
	* there was no mapping for key.
	*/
	@Override
	public V put(K key, V value) {
		edges.putIfAbsent(key, new HashMap<K, Object>());
		edgesInverted.putIfAbsent(key, new HashMap<K, Object>());
		visited.putIfAbsent(key, false);
		localFlags.expand(key);
		return super.put(key, value);
	}

	/**
	* Adds a new vertex to the graph, if there was no key
	* label before.
	*
	* @param key vertex label.
	* @param value vertex value.
	* @return the previous value associated with the specified key,
	* or null if there was no mapping for the key.
	*/
	@Override
	public V putIfAbsent(K key, V value) {
		edges.putIfAbsent(key, new HashMap<K, Object>());
		edgesInverted.putIfAbsent(key, new HashMap<K, Object>());
		visited.putIfAbsent(key, false);
		localFlags.expand(key);
		return super.putIfAbsent(key, value);
	}

	/**
	* Removes the given vertex, along with all edges leading
	* from and to it.
	*
	* @param key vertex label.
	* @return the previous value associated with key, or null
	* if there was no mapping for key.
	*/
	@Override
	public V remove(Object key) {
		detachAllEdges(key);
		edges.remove(key);
		edgesInverted.remove(key);
		visited.remove(key);
		localFlags.remove(key);
		return super.remove(key);
	}

	/**
	* Removes the given vertex, along with all edges leading
	* from and to it, if it was mapped to the given value.
	*
	* @param key vertex label.
	* @param value vertex value.
	* @return true if the key existed and was mapped to the
	* given value, false otherwise.
	*/
	@Override
	public boolean remove(Object key, Object value) {
		if(!containsKey(key)) {
			return false;
		}
		if(get(key).equals(value)) {
			remove(key);
			return true;
		} else {
			return false;
		}
	}

	/**
	* Adds a new edge from keyA to keyB; does not permit self-edges.
	*
	* @param keyA first vertex label.
	* @param keyB second vertex label.
	* @return true if the edge was added, false if it 
	* was a self-edge or it already existed.
	*/
	public boolean attach(K keyA, K keyB) {
		if(keyA.equals(keyB)) return false;
		return edges.get(keyA).putIfAbsent(keyB, new Object()) == null &&
				edgesInverted.get(keyB).putIfAbsent(keyA, new Object()) == null;
	}

	/**
	* Adds two edges - from keyA to keyB and from keyB to keyA.
	*
	* @param keyA first vertex label.
	* @param keyB second vertex label.
	* @return 2 if two edges were added, 1 if an edge from
	* keyA to keyB was added, -1 if an edge from keyB to
	* keyA was added, 0 if no edge was added.
	*/
	public short attachBoth(K keyA, K keyB) {
		if(attach(keyA, keyB)) {
			if(attach(keyB, keyA)) return 2;
			else return 1;
		} else {
			if(attach(keyB, keyA)) return -1;
			else return 0;
		}
	}

	/**
	* Removes an edge from keyA to keyB.
	*
	* @param keyA first vertex label.
	* @param keyB second vertex label.
	* @return true if the edge was removed, false otherwise
	*/
	public boolean detach(Object keyA, Object keyB) {
		if(!containsKey(keyA) || !containsKey(keyB)) {
			return false;
		}
		return edges.get(keyA).remove(keyB) != null;
	}

	/**
	* Removes both edges - from keyA to keyB and from keyB to keyA.
	*
	* @param keyA first vertex label.
	* @param keyB second vertex label.
	* @return 2 if both edges were removed, 1 if an edge from
	* keyA to keyB was removed, -1 if an edge from keyB to
	* keyA was removed, 0 if no edge was removed.
	*/
	public short detachBoth(Object keyA, Object keyB) {
		if(detach(keyA, keyB)) {
			if(detach(keyB, keyA)) return 2;
			else return 1;
		} else {
			if(detach(keyB, keyA)) return -1;
			else return 0;
		}
	}

	/**
	* Removes all edges leading from vertex labelled as key.
	*
	* @param key vertex label.
	* @return true if the vertex exists, false otherwise
	*/
	private boolean detachEdgesLeadingFrom(Object key) {
		if(!containsKey(key)) {
			return false;
		}
		edges.get(key).forEach((neighbour, value) -> {
			edgesInverted.get(neighbour).remove(key);
		});
		edges.get(key).clear();	
		return true;
	}

	/**
	* Removes all edges leading to vertex labelled as key.
	*
	* @param key vertex label.
	* @return true if the vertex exists, false otherwise
	*/
	private boolean detachEdgesLeadingTo(Object key) {
		if(!containsKey(key)) {
			return false;
		}
		edgesInverted.get(key).forEach((neighbour, value) -> {
			edges.get(neighbour).remove(key);
		});
		edgesInverted.get(key).clear();
		return true;
	}

	/**
	* Removes all edges leading to and from vertex labelled as key.
	*
	* @param key vertex label.
	* @return true if the vertex exists, false otherwise
	*/
	private boolean detachAllEdges(Object key) {
		return detachEdgesLeadingTo(key) && detachEdgesLeadingFrom(key);
	}

	/**
	* Returns true if there is an edge from keyA to keyB.
	*
	* @param keyA first vertex label.
	* @param keyB second vertex label.
	* @return true if there is an edge from keyA to keyB,
	* false otherwise.
	*/
	public final boolean isAdjacent(K keyA, K keyB) {
		if(!containsKey(keyA) || !containsKey(keyB)) {
			return false;
		}
		return edges.get(keyA).containsKey(keyB);
	}

	/**
	* Removes all flags, vertices, and connections between them.
	*/
	@Override
	public void clear() {
		edges.clear();
		edgesInverted.clear();
		visited.clear();
		localFlags.clear();
		globalFlags.clear();
		super.clear();
	}

	/**
	* Iterates over all vertices connected with an edge to the the given vertex.
	*
	* @param key vertex label.
	* @param action the action to be performed for each neighbour.
	* @return true if the vertex exists, false otherwise.
	*/
	public final boolean forEachNeighbour(K key, Consumer<? super K> action) {
		if(!containsKey(key)) {
			return false;
		}
		edges.get(key).forEach((neighbour, value) -> {
			action.accept(neighbour);
		});
		return true;
	}

	/**
	* Returns the set of all vertices connected with an edge to the given vertex.
	* 
	* @param key vertex label.
	* @return the set of all neighbours of the given vertex, null if the 
	* vertex doesn't exist.
	*/
	public final Set<K> neighbourSet(K key) {
		if(!containsKey(key)) {
			return null;
		}
		return edges.get(key).keySet();
	}

	/**
	* Marks the given vertex as 'visited'.
	*
	* @param key vertex label.
	* @return true if the vertex exists, false otherwise.
	*/ 
	private final boolean markAsVisited(K key) {
		if(!containsKey(key)) {
			return false;
		}
		visited.put(key, true);
		return true;
	}

	/**
	* Marks the given vertex as 'unvisited'.
	*
	* @param key vertex label.
	* @return true if the vertex exists, false otherwise.
	*/ 
	private final boolean markAsUnvisited(K key) {
		if(!containsKey(key)) {
			return false;
		}
		visited.put(key, false);
		return true;
	}

	/**
	* Checks if the given vertex is marked as 'visited'.
	*
	* @param key vertex label.
	* @return true if the vertex exists and is marked as
	* 'visited', false otherwise.
	*/
	public final boolean hasBeenVisited(K key) {
		Boolean result = visited.get(key);
		if(result == null) {
			return false;
		}
		return (boolean)result;
	}

	/**
	* Checks if all vertices of the graph have been visited.
	*
	* @return true if all vertices have been marked as 'visited'.
	*/
	public final boolean allVisited() {
		globalFlags.add("__allVisited", true);
		forEach((key, value) -> {
			if(!hasBeenVisited(key)){
				globalFlags.set("__all_visited", false);
			}
		});
		boolean result = (boolean)globalFlags.get("__allVisited");
		globalFlags.remove("__allVisited");
		return result;
	}

	/**
	* Marks all vertices as unvisited.
	*/
	public final void clearVisited() {
		forEach((key, value) -> {
			markAsUnvisited(key);
		});
	}

	/**
	* Performs a dfs starting with 'current' and having 'previous' as
	* parent.
	*
	* @param current current vertex label.
	* @param previous previous vertex label. Null if in the root.
	* @param pre action to be performed upon entering the vertex.
	* Takes current and previous vertex, respectively, as arguments.
	* @param visited action to be performed if the encountered
	* neighbour had already been visited. Takes current and next
	* vertex, respectively, as arguments.
	* @param notVisitedPre action to be performed before entering
	* the neighbour that had not been visited previously. Takes
	* current and next vertex, respectively, as arguments.
	* @param notVisitedPost action to be performed after leaving
	* the neighbour that had not been visited previously. Takes
	* current and next vertex, respectively, as arguments.
	* @param post action to be performed upon leaving the vertex.
	* Takes current and previous vertex, respectively, as arguments.
	*/
	private void dfs(K current, K previous, BiConsumer<K, K> pre, BiConsumer<K, K> visited, BiConsumer<K, K> notVisitedPre, BiConsumer<K, K> notVisitedPost, BiConsumer<K, K> post) {
		markAsVisited(current);
		if(pre != null)
			pre.accept(current, previous);
		forEachNeighbour(current, (neighbour) -> {
			if(!hasBeenVisited(neighbour)) {
				if(notVisitedPre != null)
					notVisitedPre.accept(current, neighbour);
				dfs(neighbour, current, pre, visited, notVisitedPre, notVisitedPost, post);
				if(notVisitedPost != null)
					notVisitedPost.accept(current, neighbour);
			} else if(visited != null) {
					visited.accept(current, neighbour);
			}
		});
		if(post != null)
			post.accept(current, previous);
	}

	/**
	* Performs a dfs starting with 'current'.
	*
	* @param current current vertex label.
	* @param pre action to be performed upon entering the vertex.
	* Takes current and previous vertex, respectively, as arguments.
	* @param visited action to be performed if the encountered
	* neighbour had already been visited. Takes current and next
	* vertex, respectively, as arguments.
	* @param notVisitedPre action to be performed before entering
	* the neighbour that had not been visited previously. Takes
	* current and next vertex, respectively, as arguments.
	* @param notVisitedPost action to be performed after leaving
	* the neighbour that had not been visited previously. Takes
	* current and next vertex, respectively, as arguments.
	* @param post action to be performed upon leaving the vertex.
	* Takes current and previous vertex, respectively, as arguments.
	*/
	public void dfs(K current, BiConsumer<K, K> pre, BiConsumer<K, K> visited, BiConsumer<K, K> notVisitedPre, BiConsumer<K, K> notVisitedPost, BiConsumer<K, K> post) {
		dfs(current, null, pre, visited, notVisitedPre, notVisitedPost, post);
	}

	/**
	* Performs a dfs for all vertices in a graph. Clears the 
	* 'visited' flag beforehand.
	*
	* @param pre action to be performed upon entering the vertex.
	* Takes current and previous vertex, respectively, as arguments.
	* @param visited action to be performed if the encountered
	* neighbour had already been visited. Takes current and next
	* vertex, respectively, as arguments.
	* @param notVisitedPre action to be performed before entering
	* the neighbour that had not been visited previously. Takes
	* current and next vertex, respectively, as arguments.
	* @param notVisitedPost action to be performed after leaving
	* the neighbour that had not been visited previously. Takes
	* current and next vertex, respectively, as arguments.
	* @param post action to be performed upon leaving the vertex.
	* Takes current and previous vertex, respectively, as arguments.
	*/
	public void dfs(BiConsumer<K, K> pre, BiConsumer<K, K> visited, BiConsumer<K, K> notVisitedPre, BiConsumer<K, K> notVisitedPost, BiConsumer<K, K> post) {
		clearVisited();
		forEach((key, value) -> {
			if(!hasBeenVisited(key))
				dfs(key, null, pre, visited, notVisitedPre, notVisitedPost, post);
		});
	}

	/**
	* Returns any vertex label currently in the graph.
	* @return any vertex label. Null if there are none.
	*/
	public K anyKey() {
		if(isEmpty()) return null;
		return entrySet().iterator().next().getKey();
	}

	/**
	* Performs a bfs starting with 'start'.
	*
	* @param start initial vertex label.
	* @param pre action to be performed upon entering the vertex.
	* takes the current vertex label as an argument.
	* @param notVisited action to be performed upon encountering
	* a neighbour that had not been visited previously. Takes the
	* current and next vertex, respectively, as arguments.
	* @param visited action to be performed upon encountering
	* a neighbour that had been visited previously. Takes the
	* current and next vertex, respectively, as arguments.
	* @param post action to be performed upon having analysed all
	* neighbour of the given the vertex. Takes the current vertex
	* label as as argument.
	*/
	public void bfs(K start, Consumer<K> pre, BiConsumer<K, K> notVisited, BiConsumer<K, K> visited, Consumer<K> post) {
		LinkedList<K> queue = new LinkedList<K>();
		queue.add(start);
		markAsVisited(start);
		while(!queue.isEmpty()) {
			K key = queue.remove();
			if(pre != null)
				pre.accept(key);
			forEachNeighbour(key, (neighbour) -> {
				if(!hasBeenVisited(neighbour)) {
					queue.add(neighbour);
					markAsVisited(neighbour);
					if(notVisited != null)
						notVisited.accept(key, neighbour);
				} else if(visited != null) {
					visited.accept(key, neighbour);
				}
			});
			if(post != null)
				post.accept(key);
		}
	}

	/**
	* Performs a bfs on all vertices of the graph. Clears the
	* 'visited' flag beforehand.
	*
	* @param pre action to be performed upon entering the vertex.
	* takes the current vertex label as an argument.
	* @param notVisited action to be performed upon encountering
	* a neighbour that had not been visited previously. Takes the
	* current and next vertex, respectively, as arguments.
	* @param visited action to be performed upon encountering
	* a neighbour that had been visited previously. Takes the
	* current and next vertex, respectively, as arguments.
	* @param post action to be performed upon having analysed all
	* neighbour of the given the vertex. Takes the current vertex
	* label as as argument.
	*/
	public void bfs(Consumer<K> pre, BiConsumer<K, K> notVisited, BiConsumer<K, K> visited, Consumer<K> post) {
		clearVisited();
		forEach((key, value) -> {
			if(!hasBeenVisited(key))
				bfs(key, pre, notVisited, visited, post);
		});
	}
}
