import java.util.*;

public class SimulatedAnnealingSolver extends Solver{
	private static Solver sas = null;
	private SimulatedAnnealingSolver() {}
	public static Solver getSolver() {
		if(sas == null)
			sas = new SimulatedAnnealingSolver();
		return sas;
	}

	private final int POPULATION_SIZE = 1;
	private final int MAX_NUM_OF_GENERATIONS = 1000;
	
	public State solvePuzzle(State initial_state) {
		return this.solvePuzzle(initial_state , POPULATION_SIZE);
	}
	
	public State solvePuzzle(State initial_state , int population_size) {
		
		List<State> population = super.getInitialPopulation(initial_state, population_size);
		State solution = null;
		
		for(int gen = 0 ; gen < MAX_NUM_OF_GENERATIONS ; gen++) {
			int chance_of_decrease = (int)((MAX_NUM_OF_GENERATIONS - gen) / (MAX_NUM_OF_GENERATIONS));
			
			PriorityQueue<State> sorted_population = new PriorityQueue<>(
					new Comparator<State>() {
						public int compare(State arg0, State arg1) {
							return arg0.getEval() - arg1.getEval();
						}
					});
			sorted_population.addAll(population);
			
			if(solution == null || solution.getEval() > sorted_population.peek().getEval()) {
				solution = sorted_population.poll();
			}
			if(solution.getEval() == 0) {
				return solution;
			}
			
			population = getNextPopulation(population , chance_of_decrease);
		}
		
		return solution;
	}
	
	private List<State> getNextPopulation(List<State> parents , int chance_of_decrease){
		List<State> children = new Stack<>();
		
		for(int i = 0 ; i < parents.size() ; i++) {
			State child = getRandomChild(parents.get(i));
			int attempts_to_move = 100;
			while(child.getEval() > parents.get(i).getEval() && attempts_to_move > 0) {
				if((int)(Math.random()*100) < chance_of_decrease) {
					break;
				}
				child = getRandomChild(parents.get(i));
				attempts_to_move--;
			}
			children.add(child);
		}
		
		return children;
	}
	
	private State getRandomChild(State p) {
		int[][] parent = p.getBoardArray();
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
		
		return new State(child);
	}

}
