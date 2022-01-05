package graph;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.BiConsumer;

public class Graph<K, V> extends HashMap<K, V> {
	private HashMap<K, HashMap<K, Object> > edges;
	private HashMap<K, HashMap<K, Object> > edgesInverted;

	private HashMap<K, HashMap<String, Object> > vFlags;
	private HashMap<String, Object> defaultVFlags;
	private HashMap<String, Object> gFlags;

	private void init() {
		edges = new HashMap<K, HashMap<K, Object> >();
		edgesInverted = new HashMap<K, HashMap<K, Object> >();
		vFlags = new HashMap<K, HashMap<String, Object> >();
		defaultVFlags = new HashMap<String, Object>();
		addVFlag("__visited", false);
		gFlags = new HashMap<String, Object>();
	}
	
	public Graph() {
		super();
		init();
	}

	public Graph(K key, V value) {
		super();
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
		// no self-edges
		if(keyA.equals(keyB)) return false;
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

	public final boolean isAdjacent(K keyA, K keyB) {
		if(!containsKey(keyA) || !containsKey(keyB)) {
			return false;
		}
		return edges.get(keyA).containsKey(keyB);
	}

	@Override
	public V put(K key, V value) {
		edges.putIfAbsent(key, new HashMap<K, Object>());
		edgesInverted.putIfAbsent(key, new HashMap<K, Object>());
		vFlags.putIfAbsent(key, new HashMap<String, Object>(defaultVFlags));
		return super.put(key, value);
	}

	@Override
	public V putIfAbsent(K key, V value) {
		edges.putIfAbsent(key, new HashMap<K, Object>());
		edgesInverted.putIfAbsent(key, new HashMap<K, Object>());
		vFlags.putIfAbsent(key, new HashMap<String, Object>(defaultVFlags));
		return super.putIfAbsent(key, value);
	}

	public void detach(Object keyA, Object keyB) throws NoSuchLabelException {
		if(!containsKey(keyA)) {
			throw new NoSuchLabelException(keyA.toString());
		}
		if(!containsKey(keyB)) {
			throw new NoSuchLabelException(keyB.toString());
		}
		edges.get(keyA).remove(keyB);
	}

	private void detachEdgesLeadingFrom(Object key) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		edges.get(key).clear();	
	}

	private void detachEdgesLeadingTo(Object key) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		for(Map.Entry<K, Object> entry : edgesInverted.get(key).entrySet()) {
			K neighbour = entry.getKey();
			detach(neighbour, key);
		}
	}

	private void detachAllEdges(Object key) throws NoSuchLabelException {
		detachEdgesLeadingTo(key);
		detachEdgesLeadingFrom(key);
	}

	@Override
	public V remove(Object key) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		detachAllEdges(key);
		edges.remove(key);
		edgesInverted.remove(key);
		vFlags.remove(key);
		return super.remove(key);
	}

	@Override
	public boolean remove(Object key, Object value) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		if(get(key).equals(value)) {
			remove(key);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void clear() {
		edges.clear();
		edgesInverted.clear();
		clearVFlags();
		clearGFlags();
		super.clear();
	}

	public final void forEachNeighbour(K key, Consumer<? super K> action) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		edges.get(key).forEach((neighbour, value) -> {
			action.accept(neighbour);
		});
	}

	public final Set<K> neighbourSet(K key) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		return edges.get(key).keySet();
	}

	private final void markAsVisited(K key) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		setVFlagRestricted(key, "__visited", true);
	}

	private final void markAsUnvisited(K key) throws NoSuchLabelException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		setVFlagRestricted(key, "__visited", false);
	}

	public final boolean hasBeenVisited(K key) throws NoSuchLabelException {
		return (boolean)getVFlag(key, "__visited");
	}

	public final boolean allVisited() {
		addGFlag("__all_visited", true);
		forEach((key, value) -> {
			if(!hasBeenVisited(key)){
				setGFlag("__all_visited", false);
			}
		});
		return (boolean)removeGFlag("__all_visited");
	}

	public final void clearVisited() {
		forEach((key, value) -> {
			markAsUnvisited(key);
		});
	}

	private void dfs(K key, K previous, BiConsumer<K, K> pre, BiConsumer<K, K> visited, BiConsumer<K, K> preVisit, BiConsumer<K, K> postVisit, BiConsumer<K, K> post) throws NoSuchLabelException {
		markAsVisited(key);
		if(pre != null)
			pre.accept(key, previous);
		forEachNeighbour(key, (neighbour) -> {
			if(!hasBeenVisited(neighbour)) {
				if(preVisit != null)
					preVisit.accept(key, neighbour);
				dfs(neighbour, key, pre, visited, preVisit, postVisit, post);
				if(postVisit != null)
					postVisit.accept(key, neighbour);
			} else if(visited != null) {
					visited.accept(key, neighbour);
			}
		});
		if(post != null)
			post.accept(key, previous);
	}

	public void dfs(K key, BiConsumer<K, K> pre, BiConsumer<K, K> visited, BiConsumer<K, K> preVisit, BiConsumer<K, K> postVisit, BiConsumer<K, K> post) throws NoSuchLabelException {
		dfs(key, null, pre, visited, preVisit, postVisit, post);
	}

	public void dfs(BiConsumer<K, K> pre, BiConsumer<K, K> visited, BiConsumer<K, K> preVisit, BiConsumer<K, K> postVisit, BiConsumer<K, K> post) throws NoSuchLabelException {
		clearVisited();
		forEach((key, value) -> {
			if(!hasBeenVisited(key))
				dfs(key, null, pre, visited, preVisit, postVisit, post);
		});
		clearVisited();
	}

	public K anyKey() {
		return entrySet().iterator().next().getKey();
	}

	// you may want to clearVisited() before using this function
	public void bfs(K start, Consumer<K> pre, BiConsumer<K, K> notVisited, BiConsumer<K, K> visited, Consumer<K> post) throws NoSuchLabelException {
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

	public void bfs(Consumer<K> pre, BiConsumer<K, K> notVisited, BiConsumer<K, K> visited, Consumer<K> post) throws NoSuchLabelException {
		clearVisited();
		forEach((key, value) -> {
			if(!hasBeenVisited(key))
				bfs(key, pre, notVisited, visited, post);
		});
		clearVisited();
	}

// ==========================================================
// FLAGS
// ==========================================================

	// add a new vFlag with default value
	public void addVFlag(String flag, Object def) {
		defaultVFlags.put(flag, def);
		vFlags.forEach((key, value) -> {
			value.put(flag, def);
		});
	}

	public void removeVFlag(String flag) throws IncorrectFlagException {
		if(flag.equals("__visited")) {
			throw new IncorrectFlagException(flag);
		}
		vFlags.forEach((key, value) -> {
			value.remove(flag);	
		});
	}

	private void setVFlagRestricted(K key, String flag, Object value) throws NoSuchLabelException, IncorrectFlagException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		vFlags.get(key).put(flag, value);
	}

	public void setVFlag(K key, String flag, Object value) throws IncorrectFlagException {
		if(!vFlags.get(key).containsKey(flag) || flag.equals("__visited")) {
			throw new IncorrectFlagException(flag);
		}
		setVFlagRestricted(key, flag, value);
	}

	public Object getVFlag(K key, String flag) throws NoSuchLabelException, IncorrectFlagException {
		if(!containsKey(key)) {
			throw new NoSuchLabelException(key.toString());
		}
		if(!vFlags.get(key).containsKey(flag)) {
			throw new IncorrectFlagException(flag);
		}
		return vFlags.get(key).get(flag);
	}

	public void clearVFlags() {
		vFlags.forEach((key, flags) -> {
			flags.forEach((flag, value) -> {
				if(!flag.equals("__visited"))
					flags.remove(flag);
			});
		});
	}

	public void addGFlag(String flag, Object def) {
		gFlags.put(flag, def);
	}

	public Object removeGFlag(String flag) throws IncorrectFlagException {
		if(!gFlags.containsKey(flag)) {
			throw new IncorrectFlagException(flag);
		}
		return gFlags.remove(flag);
	}

	public void setGFlag(String flag, Object value) throws IncorrectFlagException {
		if(!gFlags.containsKey(flag) || flag.equals("__visited")) {
			throw new IncorrectFlagException(flag);
		}
		gFlags.put(flag, value);
	}

	public Object getGFlag(String flag) throws IncorrectFlagException {
		if(!gFlags.containsKey(flag) || flag.equals("__visited")) {
			throw new IncorrectFlagException(flag);
		}
		return gFlags.get(flag);
	}

	public void clearGFlags() {
		gFlags.clear();
	}
}
