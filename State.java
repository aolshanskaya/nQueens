
public class State {
	private int[][] state;
	private int num_queens;
	private int h;
	private int f;
	private StateAnalyzer sa;
	//private State parent;
	
	/**
	 * Constructors
	 */
	public State(int[][] board) {
		sa = StateAnalyzer.getStateAnalyzer();
		
		state = board;
		
		num_queens = board.length;
		h = sa.getNumConflictingQueens(this);
		f = calcFitness();
	}
	
	/**
	 * Getters
	 */
	public int getEval() {
		return h;
	}
	
	public int getFitness() {
		return f;
	}
	
	public int[][] getBoardArray(){
		return state;
	}
	
	public char[][] getPrintableBoard(){
		char[][] printable_board = new char[num_queens][num_queens];
		for(int row = 0 ; row < num_queens ; row++) {
			for(int col = 0 ; col < num_queens ; col++) {
				if(state[row][col] == 1) {
					printable_board[row][col] = 'Q';
				}else {
					printable_board[row][col] = '-';
				}
			}
		}
		return printable_board;
	}
	
	private int calcFitness() {
		int goal = 0;
		for (int i = 0 ; i < state.length ; i++) {
			goal += i;
		}
		return goal-h;
	}
}
