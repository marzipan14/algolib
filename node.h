#ifndef NODE_H
#define NODE_H

#include <vector>

template <typename T>
class node {
private:
	T value;
	std::vector<node*> neighbours;
public:
	node(T);
};

template <typename T>
node<T>::node (T new_value) {
	this->value = new_value;
}

#endif /* NODE_H */
