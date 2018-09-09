import java.util.List;
import java.util.Stack;

public class Solver {
	private static PuzzleGenerator pg = PuzzleGenerator.getPuzzleGenerator();
	
	public State solvePuzzle(State initial_state) {
		return null;
	}
	
	public State solvePuzzle(State initial_state , int population_size) {
		return null;
	}
	
	public List<State> getInitialPopulation(int board_dimension , int population_size){
		List<State> population = new Stack<>();
		for(int i = 0 ; i < population_size ; i++) {
			population.add(pg.getScrambledPuzzle(board_dimension));
		}
		return population;
	}
	
	public List<State> getInitialPopulation(State initial_state , int population_size) {
		List<State> initial_population = new Stack<>();
		
		int[][] parent = initial_state.getBoardArray();
		
		for(int i = 0 ; i < population_size ; i++) {
			int[][] child = new int[parent.length][parent.length];
			int row = (int)(Math.random() * child.length);
			int col = (int)(Math.random() * child.length);
			
			for(int r = 0 ; r < child.length ; r++) {
				for(int c = 0 ; c < child.length ; c++) {
					child[r][c] = parent[r][c];
				}
			}
			
			if(child[row][col] == 1) {
				child[row][col] = 0;
				col++;
				if(col >= child.length)
					col = 0;
			}else {
				for(int c = 0 ; c < child.length ; c++) {
					child[row][c] = 0;
				}
			}
			child[row][col] = 1;
			
			initial_population.add(new State(child));
		}
		return initial_population;
	}
	
	public static void printPuzzle(State board) {
		char[][] printable_board = board.getPrintableBoard();
		for(char[] row : printable_board) {
			for(char col : row) {
				System.out.print(col + " ");
			}
			System.out.println();
		}
		System.out.println("eval: " + board.getEval());
	}
}
