#ifndef DIRECTED_GRAPH_H
#define DIRECTED_GRAPH_H

#include <map>
#include <iostream>
#include "node.h"

template <typename T, typename U>
class directed_graph {
private:
	std::map<T, node<U> > map;
public:
	directed_graph(const T&, U);
	U operator [] (const T&);
	void add_node(const T&, U);
	void attach(const T&, const T&);
	void attach(const T&, const T&, U);
	void detach(const T&, const T&);
};

template <typename T, typename U>
directed_graph<T, U>::directed_graph (const T &key, U value) {
	add_node(key, value);
}

template <typename T, typename U>
U directed_graph<T, U>::operator [] (const T &key) {
	return map[key].getValue();
}

template <typename T, typename U>
void directed_graph<T, U>::add_node (const T &key, U value) {
	node<U> root(value);
	map[key] = root;
}

template <typename T, typename U>
void directed_graph<T, U>::attach (const T &key_1, const T &key_2) {
	map[key_1].neighbours.push_back(&(map[key_2]));
}

template <typename T, typename U>
void directed_graph<T, U>::attach (const T &key_1, const T &key_2, U value) {
	add_node(key_2, value);
	attach(key_1, key_2);
}

#endif
