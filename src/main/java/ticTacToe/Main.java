package ticTacToe;

import java.util.NoSuchElementException;
import java.util.Scanner;

// Playing on console
public class Main {
	private static Scanner scanner = new Scanner(System.in);

	// Console version
	public static void main(String[] args) {

		TicTacToe ticTacToe = new TicTacToe();

		// Greetings
		System.out.println("Welcome to Murilo's tic tac toe.");
		System.out.println("It is you playing against my AI. I'll let you start first, but we'll take turns.");
		System.out.println("In case you want to surrender, just type \"exit\"");
		System.out.println();
		System.out.println("Look at the board and where you'll play");

		while (true) {
			// What's your next play?
			try {

				// AI's turn
				try {
					if (ticTacToe.getPlayerThisRound() == ticTacToe.getaI() && !ticTacToe.isGameOver())
						ticTacToe.aIsTurn();
				} catch (TicTacToeException e) {
					System.err.println(e.getException());
					continue;
				}

				// Showing the game
				drawGame(ticTacToe);

				// Check if the game is over
				if (ticTacToe.isGameOver()) {
					// Congratulations player/AI won
					if (ticTacToe.getWinner() != null) {
						if (ticTacToe.getWinner() == ticTacToe.getaI())
							System.out.println("Sorry Player! the AI won!!");
						else
							System.out.println("Congratulations Player, you won!!");
						// It's a tie!
					} else
						System.out.println("Congratulations! we tied!!");

					// Change current player
					ticTacToe.setaI(ticTacToe.getaI() == 1 ? 2 : 1);
					// Start a new game
					String firstPlayer = ticTacToe.getaI() == 1 ? "the AI" : "you";
					System.out.println("Let's play another game! This time " + firstPlayer + " will start");
					ticTacToe.clearBoard();
					continue;
				}

				System.out.println("Player, insert the number you choosed:");
				Integer number;
				String text = scanner.nextLine();

				// In case I want to start over
				if ("clear".equals(text)) {
					ticTacToe.clearBoard();
					drawGame(ticTacToe);
					continue;
				}

				// Exit
				if ("exit".equalsIgnoreCase(text))
					break;

				try {
					number = Integer.valueOf(text);
				} catch (RuntimeException e) {
					System.err.println("Sorry, I couldn't get the number from: " + text);
					continue;
				}

				// Validating and playing
				try {
					if (number > 0 && number < 10)
						ticTacToe.play(number);
					else {
						System.err.println("Sorry, only numbers between 1-9 are available");
						continue;
					}

				} catch (TicTacToeException e) {
					System.err.println(e.getException());
					continue;
				}

				// System.in has been closed or something wrong happened
			} catch (IllegalStateException | NoSuchElementException e) {
				e.printStackTrace();
				System.out.println("System.in was closed; exiting");
				break;
			}
		}
	}

	private static void drawGame(TicTacToe ticTacToe) {
		String game[][] = ticTacToe.getGame();
		int counting = 1;

		StringBuilder board = new StringBuilder();
		for (int i = 0; i < game.length; i++) {
			board.append("\n-------------\n| ");
			for (int j = 0; j < game.length; j++) {
				if (game[i][j] == null)
					board.append(counting);
				else
					board.append(game[i][j]);
				board.append(" | ");
				counting++;
			}
		}
		board.append("\n-------------\n");
		System.out.println(board.toString());
	}

}
