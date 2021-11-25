#ifndef NODE_H
#define NODE_H

#include <vector>

template <typename T>
class node {
private:
	T value;
	std::vector<node*> neighbours;
public:
	node();
	node(T);
	T getValue();
};

template <typename T>
node<T>::node () {
}

template <typename T>
node<T>::node (T new_value) {
	this->value = new_value;
}

template <typename T>
T node<T>::getValue() {
	return value;
}

#endif /* NODE_H */
