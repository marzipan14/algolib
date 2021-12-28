#include "graph.h"
#include <string>
#include <iostream>

using namespace std;

int main(){
	graph<int, string> mygraph(1, "new");
	mygraph.add_node(3, "another");
	mygraph.add_node(5, "ok");
	//cout << mygraph.is_adjacent(1, 3) << "\n";
	//cout << mygraph.is_adjacent(3, 1) << "\n";
	//cout << mygraph.is_adjacent(1, 2) << "\n";
	mygraph.attach(1, 3);
	mygraph.attach(3, 1);
	//cout << mygraph.is_adjacent(1, 3) << "\n";
	//cout << mygraph.is_adjacent(3, 1) << "\n";
	//cout << mygraph.is_adjacent(1, 2) << "\n";
	//cout << mygraph.is_adjacent(6, 2) << "\n";
	cout << mygraph[1] << "\n";
	cout << mygraph.get_value(1) << "\n";
	cout << mygraph.get_value(2) << "\n";
	return 0;
}
