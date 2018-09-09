
public class Main {
	private static PuzzleGenerator pg;
	private static Solver sas;
	private static Solver gs;
	private static UserInterface ui;
	
	public static void main(String[] a) {
		pg = PuzzleGenerator.getPuzzleGenerator();
		sas = SimulatedAnnealingSolver.getSolver();
		gs = GeneticSolver.getSolver();
		ui = UserInterface.getUI();
		
		int todo = ui.welcomeMessage();
		
		if(todo == 1) {
			printOutThreeTrials();
		}else {
			runTrials();
		}
	}
	
	private static void printOutThreeTrials() {
		for(int trial = 1 ; trial <= 3 ; trial++) {
			System.out.println("Trial #" + trial);
			
			System.out.println("Initial State: "); 
			State problem = pg.getScrambledPuzzle();
			printPuzzle(problem);
			
			System.out.println("Genetic Algorithm with 10000 trials solution: ");
			State genetic_solution = gs.solvePuzzle(problem , 10000);
			printPuzzle(genetic_solution);
			
			System.out.println("Simulated Annealing solution: ");
			State simulated_solution = sas.solvePuzzle(problem);
			printPuzzle(simulated_solution);
			
			System.out.println();
		}
	}
	
	private static void runTrials() {
		int genetic_solved = 0;
		int genetic_long_solved = 0;
		int simulated_solved = 0;
		int simulated_in_parallel_solved = 0;
		
		System.out.println("Running 500 random n-queens trials...\n");
		for(int trial = 0 ; trial < 500 ; trial++) {
			State problem = pg.getScrambledPuzzle();
			
			State genetic_solution = gs.solvePuzzle(problem);
			State genetic_long_solution = gs.solvePuzzle(problem , 10000);
			State simulated_solution = sas.solvePuzzle(problem);
			State simulated_in_parallel_solution = sas.solvePuzzle(problem , 10);
			
			if(genetic_solution.getEval() == 0)
				genetic_solved++;
			if(genetic_long_solution.getEval() == 0)
				genetic_long_solved++;
			if(simulated_solution.getEval() == 0)
				simulated_solved++;
			if(simulated_in_parallel_solution.getEval() == 0)
				simulated_in_parallel_solved++;
		}
		System.out.println("Solved " + genetic_solved + " out of 500 using genetic algorithm with 1000 generations.");
		System.out.println("Solved " + genetic_long_solved + " out of 500 using genetic algorithm with 10000 generations.");
		System.out.println("Solved " + simulated_solved + " out of 500 using simulated annealing algorithm.");
		System.out.println("Solved " + simulated_in_parallel_solved + " out of 500 using 10 simulated annealing searches in parallel.");
	}
	
	private static void printPuzzle(State board) {
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
