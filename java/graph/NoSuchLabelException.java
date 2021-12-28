package graph;

public class NoSuchLabelException extends RuntimeException {
	public NoSuchLabelException() {
		super("No such label has been defined in the graph.");
	}
}
