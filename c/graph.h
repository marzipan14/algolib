#ifndef GRAPH
#define GRAPH

#include <unordered_map>

template <typename _Label, typename _Value>
class graph {
public:
	typedef _Label label_type;
	typedef _Value value_type;
	typedef std::unordered_map<label_type, value_type> vertex_map;
	typedef std::unordered_map<label_type, nullptr_t> half_edge_map;
	typedef std::unordered_map<label_type, half_edge_map> edge_map;
private:
	vertex_map V;
	edge_map E;
public:
	graph(void) {
	}

	graph(const label_type &key, value_type value) {
		add_node(key, value);
	}

	// referencing non-existent node
	value_type operator[] (const label_type &key) const {
		return get_value(key);
	}

	// referencing non-existent node
	value_type get_value(const label_type &key) const {
		try{
			typename vertex_map::const_iterator it = V.find(key);
			return it->second;
		}
		catch(std::exception &ex){
			printf("%s\n", ex.what());
		}
	}

	// referencing existing node
	void add_node(const label_type &key, value_type value) {
		V[key] = value;
	}	

	// edge already exists
	// no such node
	void attach(const label_type &key_1, const label_type &key_2) {
		E[key_1].insert({key_2, nullptr});
	}
	
	void attach(const label_type &key_1, const label_type &key_2, value_type value) {
		add_node(key_2, value);
		attach(key_1, key_2);
	}

	bool is_adjacent(const label_type &key_1, const label_type &key_2){
		typename edge_map::const_iterator v1 = E.find(key_1);
		if(v1 == E.end()) return false;
		typename half_edge_map::const_iterator v2 = v1->second.find(key_2);
		return v2 != v1->second.end();
	}
};

#endif /* GRAPH */
