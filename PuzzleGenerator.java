
public class PuzzleGenerator {
	private static PuzzleGenerator pg = null;
	private PuzzleGenerator() {}
	public static PuzzleGenerator getPuzzleGenerator() {
		if(pg == null)
			pg = new PuzzleGenerator();
		return pg;
	}

	public State getScrambledPuzzle() {
		return this.getScrambledPuzzle(21);
	}
	
	public State getScrambledPuzzle(int size) {
		return new State(populateRelaxedBoard(size));
	}
	
	/**
	 * Empty spaces are represented with 0's. Spaces with queens are represented with 1's.
	 * One queen is randomly placed in each row .
	 */
	private int[][] populateRelaxedBoard(int num_queens){
		int[][] board = new int[num_queens][num_queens];
		
		for(int row = 0 ; row < board.length ; row++) {
			int col = (int)(Math.random() * board.length);
			board[row][col] = 1;
		}
		
		return board;
	}
	
	/**
	 * Empty spaces are represented with 0's. Spaces with queens are represented with 1's.
	 * Queens are placed randomly across the board.
	 */
	private int[][] populateRandomBoard(int num_queens){
		int[][] board = new int[num_queens][num_queens];
		
		for(int queens = 0 ; queens < num_queens ; queens++) {
			int row = (int)(Math.random() * num_queens);
			int col = (int)(Math.random() * num_queens);

			while(board[row][col] != 0) {
				col++;
				if(col >= num_queens) {
					col = 0;
					row++;
					if(row >= num_queens) {
						row = 0;
					}
				}
			}
			board[row][col] = 1;
		}
		return board;
	}
}
