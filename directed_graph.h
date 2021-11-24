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
};

template <typename T, typename U>
directed_graph<T, U>::directed_graph (T key, U value) {
	node<U> root(value);
	map.insert({key, root});
}

#endif
