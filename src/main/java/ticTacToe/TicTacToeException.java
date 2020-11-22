package ticTacToe;

public class TicTacToeException extends Exception {
	private static final long serialVersionUID = -529220456223163857L;
	private String exception;

	public TicTacToeException(String exception) {
		this.exception = exception;
	}

	public String getException() {
		return exception;
	}

}
