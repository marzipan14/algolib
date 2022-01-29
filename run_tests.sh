base="."
bin="$base/bin"
lib="$base/lib"
src="$base/src"
tests="$base/tests"

junit="4.13.2"
hamcrest="1.3"

files=("GraphTest" "GraphDfsTest")

function compile_and_test {
	filename=$1
	
# if the file is not in the directory structure, abort
	path=$(find "$tests/" -name "$filename.java" -type f)
	if [[ -z $path ]]; then
		exit
	fi

# compile
	javac -Xlint -cp $src:$tests:$lib/junit-$junit.jar:$lib/hamcrest-core-$hamcrest.jar -d $bin $path

# change path to java class name
	class="${path#${tests}/}"
	class="${class%.java}"
	class="${class//\//.}"

# run
	java -cp $bin:$lib/junit-$junit.jar:$lib/hamcrest-core-$hamcrest.jar org.junit.runner.JUnitCore $class
}

if [[ -n $1 ]]; then
	compile_and_test $1
else
	for file in ${files[@]}; do
		echo "Testing $file"
		compile_and_test $file
	done
fi
