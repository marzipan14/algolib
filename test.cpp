#include "directed_graph.h"
#include <string>

typedef struct t_str{
	int x;
	int y;
} str;

int main(){
	directed_graph<std::string, int> graph("root node", 5);
	graph["root node"];
	return 0;
}
