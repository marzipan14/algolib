#ifndef NODE_H
#define NODE_H

#include <vector>

template <typename T>
class node {
private:
	T value;
	std::vector<node<T>*> neighbours;
public:
	node();
	node(const T&);
	T getValue() const;
};

template <typename T>
node<T>::node () {
}

template <typename T>
node<T>::node (const T &new_value) {
	this->value = new_value;
}

template <typename T>
T node<T>::getValue() const {
	return value;
}

#endif /* NODE_H */
