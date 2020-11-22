package ticTacToe;

public class TicTacToe {
	private String game[][] = new String[3][3];
	// I defined player 1 == X, player 2 == O
	private int playerThisRound = 1;
	private int aI = 2;

	public boolean isAPossiblePlay(int i) {
		i--;
		return game[i / 3][i % 3] == null;
	}

	public void play(int position) throws TicTacToeException {
		play(position, playerThisRound);
		playerThisRound = playerThisRound == 1 ? 2 : 1;
	}

	private void play(int position, int player) throws TicTacToeException {
		if (!isAPossiblePlay(position))
			throw new TicTacToeException("The number " + position
					+ " is not available.\nPlease, play one of the following numbers: " + possiblePlays());
		if (player != 1 && player != 2)
			throw new TicTacToeException("Sorry, this is a 2 players game. Player " + player + " is not acceptable");

		position--;
		// I defined player 1 as X and 2 as O
		game[position / 3][position % 3] = player == 1 ? "X" : "O";
	}

	public String possiblePlays() {
		StringBuilder plays = new StringBuilder();

		for (int i = 0; i < 9; i++) {
			if (isAPossiblePlay(i + 1))
				plays.append((i + 1) + " ");
		}

		return plays.toString();
	}

	public void clearBoard() {
		for (int i = 1; i < 10; i++)
			clearPlay(i);
		playerThisRound = 1;
	}

	private void clearPlay(Integer position) {
		position--;
		game[position / 3][position % 3] = null;
	}

	public void aIsTurn() throws TicTacToeException {
		Integer bestScore = Integer.MIN_VALUE;
		Integer position = 0;
		Integer depth = 10;

		// Check all the available options
		for (int i = 1; i < 10; i++) {
			if (isAPossiblePlay(i)) {
				play(i, aI);
				Integer score = miniMax(depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
				clearPlay(i);
				if (score != null && bestScore < score) {
					bestScore = score;
					position = i;
				}
			}
		}

		play(position);
	}

	public boolean isATieGame() {
		// Check if all the spaces are completed
		boolean complete = true;
		for (int i = 0; i < 9; i++)
			if (game[i / 3][i % 3] == null) {
				complete = false;
				break;
			}

		return complete && getWinner() == null;
	}

	public Integer getWinner() {
		// Check if anyone won in the vertical or horizontal
		for (int i = 0; i < 3; i++) {

			if (equals(game[i][0], game[i][1], game[i][2]))
				return game[i][0].equals("X") ? 1 : 2;

			if (equals(game[0][i], game[1][i], game[2][i]))
				return game[0][i].equals("X") ? 1 : 2;
		}

		// Check for the diagonals
		if (equals(game[0][0], game[1][1], game[2][2]))
			return game[0][0].equals("X") ? 1 : 2;

		if (equals(game[2][0], game[1][1], game[0][2]))
			return game[2][0].equals("X") ? 1 : 2;

		return null;

	}

	// Score = +100 (win) + depth (minimum plays to win); - 100 (lose); 0 (Tie);
	// Note: Maximize = AI (Player 2)
	// Alpha = BestValue until now. Beta = Lower value
	private Integer miniMax(Integer depth, Integer alpha, Integer beta, boolean maximize) throws TicTacToeException {
		if (depth == 0)
			return 0;

		if (isGameOver()) {
			if (isATieGame())
				return 0;
			else if (getWinner() == aI)
				return 100 + depth;
			else
				return -100 - depth;
		}

		// Initializing bestValue, as the opposite of its best
		Integer bestValue = maximize ? Integer.MIN_VALUE : Integer.MAX_VALUE;

		// Change to opposite turn
		for (int i = 1; i < 10; i++) {
			if (isAPossiblePlay(i)) {

				int human = aI == 1 ? 2 : 1;
				play(i, maximize ? aI : human);
				Integer value = miniMax(depth - 1, alpha, beta, !maximize);
				clearPlay(i);

				// AI's turn
				if (maximize) {
					bestValue = Math.max(value, bestValue);
					alpha = Integer.max(alpha, value);
					if (alpha >= beta)
						break;
				}
				// Human's turn
				else {
					bestValue = Math.min(value, bestValue);
					beta = Integer.min(beta, value);
					if (alpha >= beta)
						break;
				}
			}
		}
		return bestValue;

	}

	private boolean equals(String one, String two, String three) {
		// Not null
		if (one != null && two != null && three != null)
			// Equals
			if (one.equals(two) && one.equals(three))
				return true;
		return false;
	}

	public boolean isGameOver() {
		return isATieGame() || getWinner() != null;
	}

	public int getPlayerThisRound() {
		return playerThisRound;
	}

	public String[][] getGame() {
		return game;
	}

	public int getaI() {
		return aI;
	}

	public void setaI(Integer ai) {
		if (ai != 1 && ai != 2)
			throw new RuntimeException("The only possible values for AI's turn are 1 and 2");
		this.aI = ai;
	}

}
