package graph;

public class NoSuchLabelException extends RuntimeException {
	public NoSuchLabelException(String label) {
		super("Label " + label + " has not been defined in the graph.");
	}
}
