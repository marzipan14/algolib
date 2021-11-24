#include "node.h"
#include <string>

typedef struct t_str{
	int x;
	int y;
} str;

int main(){
	node<std::string> n1("hello");
	node<double> n2(2.4);
	node<int> n(5);
	str new_str;
	new_str.x = 1;
	new_str.y = 2;
	node<str> n3(new_str);
	return 0;
}
