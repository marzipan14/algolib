package graph;

import java.util.HashMap;

abstract class Flags {
	abstract boolean add(String flag, Object val);
	abstract boolean remove(Object flag);
	abstract void clear();
}
