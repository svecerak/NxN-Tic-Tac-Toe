/**
 * This class implements all the methods needed by algorithm computerPlay
 * 
 * @author Sasa Vecerak (#250470100)
 *
 */

public class nk_TicTacToe {

	private char[][] gameBoard;
	private int inline;
	private int boardsize;

	/**
	 * Constructs an empty game board of size board_size by board_size.
	 *
	 * @param board_size the length and width of the board.
	 * @param inline     number of consecutive symbols needed in one line to win the
	 *                   game.
	 * @param max_levels maximum level of the game tree that will be explored by the
	 *                   program.
	 */
	public nk_TicTacToe(int board_size, int inline, int max_levels) {
		this.boardsize = board_size;
		this.inline = inline;

		gameBoard = new char[board_size][board_size];
		for (int i = 0; i < board_size; i++) {
			for (int j = 0; j < board_size; j++) {
				gameBoard[i][j] = ' ';
			}
		}
	}

	/**
	 * Creates a new Dictionary object.
	 *
	 * @return an empty Dictionary object of size 6473.
	 */
	public Dictionary createDictionary() {
		return new Dictionary(6473);
	}

	/**
	 * Creates string representation of the game board. X represents human, O
	 * represents computer, and empty squares are represented by a space.
	 *
	 * @return string representation of the game board.
	 */
	public String boardToString() {
		String output = "";
		for (int i = 0; i < boardsize; i++) {
			for (int j = 0; j < boardsize; j++) {
				output += gameBoard[i][j];
			}
		}
		return output;
	}

	/**
	 * Checks whether the string representing the gameBoard is in the configurations
	 * dictionary: If it is in the dictionary this method returns its associated
	 * score, otherwise returns value -1
	 *
	 * @param configurations configuration
	 * @return score if found; -1 otherwise
	 */
	public int repeatedConfig(Dictionary configurations) {
		return configurations.get(boardToString());
	}

	/**
	 * Inserts string representation of the board and score in the configurations
	 * dictionary; throws exception if configuration is already in dictionary
	 * 
	 *
	 * @param configurations configuration
	 * @param score          score
	 */
	public void insertConfig(Dictionary configurations, int score) {
		String boardString = boardToString();
		Record newPair = new Record(boardString, score);

		try {
			configurations.insert(newPair);
		} catch (DictionaryException e) {
			e.getMessage();
		}
	}

	/**
	 * Stores symbol in gameBoard[row][col]
	 *
	 * @param row    row of the board
	 * @param col    column of the board
	 * @param symbol symbol representing the player
	 */
	public void storePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
	}

	/**
	 * Determines if the specified square is empty.
	 *
	 * @param row row of the board
	 * @param col column of the board
	 * @return true if square is empty; false otherwise
	 */
	public boolean squareIsEmpty(int row, int col) {
		return gameBoard[row][col] == ' ';
	}

	/**
	 * Iterates horizontally, checking every row to determine if the number of
	 * consecutive squares of type symbol is enough to constitute a win
	 *
	 * @param symbol the character representing the player
	 * @return boolean; true if winning count of consecutive squares attained; false
	 *         otherwise.
	 */
	public boolean checkRows(char symbol) {

		for (int i = 0; i < boardsize; i++) {
			int consecutiveSquares = 0; // Set counter
			for (int j = 0; j < boardsize; j++) {
				if (gameBoard[i][j] == symbol) { // If match found, increment counter
					consecutiveSquares++;

					if (consecutiveSquares >= inline) // Checks for a win
						return true;
				} else {
					consecutiveSquares = 0; // Reset count; to win, it has to be consecutive
				}
			}
		}
		return false;
	}

	/**
	 * Iterates vertically, checking every column to determine if the number of
	 * consecutive squares of type symbol is enough to constitute a win
	 *
	 * @param symbol the character representing the player
	 * @return boolean; true if winning count of consecutive squares attained; false
	 *         otherwise.
	 */
	public boolean checkColumns(char symbol) {

		for (int i = 0; i < boardsize; i++) {
			int consecutiveSquares = 0; // Set counter
			for (int j = 0; j < boardsize; j++) {
				if (gameBoard[j][i] == symbol) { // If match found, increment counter
					consecutiveSquares++;

					if (consecutiveSquares >= inline) // Checks for a win
						return true;
				} else {
					consecutiveSquares = 0; // Resets count; to win, it has to be consecutive
				}
			}
		}
		return false;
	}

	/**
	 * Checks if there is a winning combination of squares diagonally from top-left
	 * to bottom-right. By establishing a boundary, we find which squares can serve 
	 * as the starting point. Those that meet the first if condition, are capable
	 * of having a sufficient number of squares diagonally to constitute a win.
	 * Once we identify these squares, we iterate diagonally, down and to the right 
	 * with the while loop. 
	 * 
	 * @param symbol the character representing the player
	 * @return boolean; true if winning count of consecutive squares attained; false
	 *         otherwise
	 */
	public boolean mainDiagonal(char symbol) {
		
		int boundary = inline - 1;

		for (int i = 0; i < boardsize; i++) { // Rows
			for (int j = 0; j < boardsize; j++) { // Columns

				// Establishes vertical bounds &&  horizontal bounds
				if ((i + boundary < boardsize) && (j + boundary < boardsize)) { 
					int countMain = 0;
					int k = 0;
					// Now we have a valid starting square, we scan diagonally starting from this square
					while (k < inline) {
						if (gameBoard[i + k][j + k] == symbol) { // Checks from top-left to bottom-right
							countMain++;
							k++;
						} else {
							break;
						}
					}
					if (countMain == inline) // Checks for a win
						return true;
				}
			}
		}
		return false;
	}
		
 
	/**
	 * Checks if there is a winning combination of squares diagonally from top-right
	 * to bottom-left. By establishing a boundary, we find which squares can serve 
	 * as the starting point. Those that meet the first if condition, are capable
	 * of having a sufficient number of squares diagonally to constitute a win.
	 * Once we identify these squares, we iterate diagonally, down and to the left 
	 * with the while loop. 
	 * 
	 * @param symbol the character representing the player
	 * @return boolean; true if winning count of consecutive squares attained; false
	 *         otherwise
	 */
	public boolean antiDiagonal(char symbol) {
		
		int boundary = inline - 1;

		for (int i = 0; i < boardsize; i++) { // Rows
			for (int j = 0; j < boardsize; j++) { // Columns
				
				// Establishes vertical bounds &&  horizontal bounds
				if ((i + boundary < boardsize) && (j - boundary >= 0)) {  
					int countAnti = 0;
					int k = 0;
					// Now we have a valid starting square, we scan diagonally starting from this square
					while (k < inline) {
						if (gameBoard[i + k][j - k] == symbol) { // Checks top-right to bottom-left. 																	
							countAnti++; 
							k++; 
						} else {
							break;
						}
					}
					if (countAnti == inline) // Checks for a win
						return true;
				}
			}
		}
		return false;

	}

	/**
	 * Checks if there are 'inline' adjacent occurrences of symbol in the same row,
	 * column, or diagonal of gameBoard
	 * 
	 * @param symbol the character representing the player
	 * @return boolean; true if any helper method found winning row/column/diagonal;
	 *         false otherwise
	 */
	public boolean wins(char symbol) {
		return (checkRows(symbol) || checkColumns(symbol) || mainDiagonal(symbol) || antiDiagonal(symbol));
	}

	/**
	 * Iterates through the game board, and returns false if an empty square is
	 * encountered. If, after checking all squares, no empty squares detected, exits
	 * nested loop and returns true.
	 * 
	 * @return true if no empty spaces left on game board; false otherwise
	 */
	public boolean noEmptySpaces() {
		for (int i = 0; i < boardsize; i++) {
			for (int j = 0; j < boardsize; j++) {
				if (squareIsEmpty(i, j)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the board has no positions, and neither the computer nor the player
	 * has won
	 * 
	 * @return true if gameBoard has no empty positions left and no player has won
	 *         the game; false otherwise
	 */
	public boolean isDraw() {
		return (noEmptySpaces() && wins('O') == false && wins('X') == false);
	}

	/**
	 * Returns value associated with the game board
	 * 
	 * @return integer value associated with current state of the game board.
	 */
	public int evalBoard() {
		if (wins('O')) { // O is computer
			return 3;
		} else if (wins('X')) { // X is human
			return 0;
		} else if (isDraw()) {
			return 2;
		} else {
			return 1;
		}
	}
}