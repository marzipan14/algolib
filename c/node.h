#ifndef NODE
#define NODE

#include <vector>

template <typename value_type>
class node {
private:
	value_type value;
	std::vector<node<value_type>*> adj_list;
public:
	node ();
	node (value_type);
	value_type get_value () const;
	int get_adj_size () const;
	void add_neighbour (node<value_type>*);
	bool is_neighbour(node<value_type>*);
	std::vector<node<value_type>*> get_adj_list () const;
};

template <typename value_type>
node<value_type>::node () {
}

template <typename value_type>
node<value_type>::node (value_type new_value) {
	this->value = new_value;
}

template <typename value_type>
value_type node<value_type>::get_value () const {
	return value;
}

template <typename value_type>
int node<value_type>::get_adj_size () const {
	return adj_list.size();
}

template <typename value_type>
void node<value_type>::add_neighbour (node<value_type> *neighbour) {
	adj_list.push_back(neighbour);
}

template <typename value_type>
std::vector<node<value_type>*> node<value_type>::get_adj_list () const {
	return adj_list;
}

template <typename value_type>
bool node<value_type>::is_neighbour(const node<value_type> *nd){
	
}
#endif /* NODE */
