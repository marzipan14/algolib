#ifndef DIRECTED_GRAPH_H
#define DIRECTED_GRAPH_H

#include <map>
#include <iostream>
#include <typeinfo>
#include "node.h"

template <typename T, typename U>
class directed_graph {
private:
	std::map<T, node<U> > map;
public:
	directed_graph(T, U);
	U operator [] (const T&);
};

template <typename T, typename U>
directed_graph<T, U>::directed_graph (T key, U value) {
	node<U> root(value);
	map[key] = root;
}

template <typename T, typename U>
U directed_graph<T, U>::operator [] (const T &key) {
	return map[key].getValue();
}

#endif
