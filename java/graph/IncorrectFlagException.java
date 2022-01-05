package graph;

public class IncorrectFlagException extends RuntimeException {
	public IncorrectFlagException(String flag) {
		super("Flag '" + flag + "' cannot be used");
	}
}
