
public class StateAnalyzer {
	private static StateAnalyzer sa = null;
	private StateAnalyzer() {}
	public static StateAnalyzer getStateAnalyzer() {
		if(sa == null)
			sa = new StateAnalyzer();
		return sa;
	}
	
	/**
	 * Returns heuristic based on sum of conflicting queens.
	 */
	public int getNumConflictingQueens(State board) {
		int horizontal_conflicts = getNumHorizontalConflicts(board.getBoardArray());
		int vertical_conflicts = getNumVerticalConflicts(board.getBoardArray());
		int diagonal_conflicts = getNumRightToLeftDiagonalConflicts(board.getBoardArray()) + getNumLeftToRightDiagonalConflicts(board.getBoardArray());
		
		return horizontal_conflicts + vertical_conflicts + diagonal_conflicts;
	}
	
	private int getNumHorizontalConflicts(int[][] board) {
		int conflicts = 0;
		
		for(int row = 0 ; row < board.length ; row++) {
			int queens = 0;
			for(int col = 0 ; col < board.length ; col++) {
				if(board[row][col] == 1)
					queens++;
			}
			if(queens >= 2) {
				for(queens-- ; queens > 0 ; queens--) {
					conflicts += queens;
				}
			}
		}
		return conflicts;
	}
	
	private int getNumVerticalConflicts(int[][] board) {
		int conflicts = 0;
		
		for(int col = 0 ; col < board.length ; col++) {
			int queens = 0;
			for(int row = 0 ; row < board.length ; row++) {
				if(board[row][col] == 1)
					queens++;
			}
			if(queens >= 2) {
				for(queens-- ; queens > 0 ; queens--) {
					conflicts += queens;
				}
			}
		}
		return conflicts;
	}
	
	private int getNumLeftToRightDiagonalConflicts(int[][] board) {
		int conflicts = 0;
		
		//top half
		for(int i = 1 ; i < board.length ; i++) {
			int queens = 0;
			for(int j = 0 ; j+i < board.length ; j++) {
				if(board[j][i+j] == 1) {
					queens++;
				}
			}
			if(queens >= 2) {
				for(queens-- ; queens > 0 ; queens--) {
					conflicts += queens;
				}
			}
		}
		//bottom half & main diagonal
		for(int i = board.length-2 ; i >= 0 ; i--) {
			int queens = 0;
			for(int j = 0 ; i+j < board.length ; j ++) {
				if(board[i+j][j] == 1) {
					queens++;
				}
			}
			if(queens >= 2) {
				for(queens-- ; queens > 0 ; queens--) {
					conflicts += queens;
				}
			}
		}
		return conflicts;
	}
	
	private int getNumRightToLeftDiagonalConflicts(int[][] board) {
		int conflicts = 0;
		
		//top half and main
		for(int col = 1 ; col < board.length ; col++) {
			int queens = 0;
			for(int row = 0 ; row <= col ; row++) {
				if(board[col-row][row] == 1)
					queens++;
			}
			if(queens >= 2) {
				for(queens-- ; queens > 0 ; queens--) {
					conflicts += queens;
				}
			}
		}
		//bottom half
		for(int row = 1 ; row < board.length - 1 ; row++) {
			int queens = 0;
			for(int col = board.length-1 ; col >= row ; col--) {
				if(board[row+(board.length-1-col)][col] == 1) {
					queens++;
				}
			}
			if(queens >= 2) {
				for(queens-- ; queens > 0 ; queens--) {
					conflicts += queens;
				}
			}
		}
		return conflicts;
	}
	
}
