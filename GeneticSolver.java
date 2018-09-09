import java.util.*;

public class GeneticSolver extends Solver{
	private static Solver gs = null;
	private static PuzzleGenerator pg = null;
	private GeneticSolver() {
		pg = PuzzleGenerator.getPuzzleGenerator();
	}
	public static Solver getSolver() {
		if(gs == null)
			gs = new GeneticSolver();
		return gs;
	}
	
	/**
	 * Every population has 100 members. 
	 * Only the top 30% of a given population reproduces.  
	 * Each child has a 20% chance of mutation.
	 * The maximum number of generations before returning the best found solution is 1000.
	 */
	private final int POPULATION_SIZE = 100;
	private final int CHANCE_OF_MUTATION = 30;
	private final int PERCENTAGE_OF_PARENTS_TO_REPRODUCE = 30;
	private final int MAX_NUM_OF_GENERATIONS = 1000;
	
	public State solvePuzzle(State initial_state) {
		return this.solvePuzzle(initial_state , MAX_NUM_OF_GENERATIONS);
	}
	
	public State solvePuzzle(State initial_state , int num_generations) {
		PriorityQueue<State> population = new PriorityQueue<>(
				new Comparator<State>() {
					public int compare(State arg0, State arg1) {
						return arg1.getFitness() - arg0.getFitness();
					}
				});
		population.addAll(super.getInitialPopulation(initial_state , POPULATION_SIZE));
		
		State solution = null;
		
		for(int generation = 0 ; generation < num_generations ; generation++) {
			if(generation > 0) {
				population = getNextGeneration(population);
			}
			
			PriorityQueue<State> sorted_population = new PriorityQueue<>(
					new Comparator<State>() {
						public int compare(State arg0, State arg1) {
							return arg1.getFitness() - arg0.getFitness();
						}
					});
			sorted_population.addAll(population);
			
			if(sorted_population.peek().getEval() == 0) {
				return sorted_population.poll();
			}
			if(solution == null || sorted_population.peek().getEval() < solution.getEval())
				solution = sorted_population.peek();
		}	
		
		return solution;
	}
	
	private PriorityQueue<State> getNextGeneration(PriorityQueue<State> parents) {
		List<State> children = new Stack<>();
		
		List<State> fit_parents = new Stack<>();
		int parents_to_spawn = (int)(parents.size() * PERCENTAGE_OF_PARENTS_TO_REPRODUCE / 100);
		for(int i = parents_to_spawn ; i > 0 ; i--) {
			fit_parents.add(parents.poll());
		}
		
		int total_of_heuristics = 0;
		for(State parent : fit_parents) {
			total_of_heuristics += parent.getFitness();
		}
		
		
		for(int i = 0 ; i < POPULATION_SIZE/2 ; i++) {
			int p1 = (int)(Math.random()*total_of_heuristics);
			int p2 = (int)(Math.random()*total_of_heuristics);
			
			State parent1 = null , parent2 = null;
			
			for(State parent: fit_parents) {
				p1 -= parent.getFitness();
				p2 -= parent.getFitness();
				
				if(parent1 == null && p1 <= 0) {
					if(parent2 != parent) {
						parent1 = parent;
					}
				}
				
				if(parent2 == null && p2 <= 0) {
					if(parent1 != parent) {
						parent2 = parent;
					}
				}
				
				if(parent1 != null && parent2 != null) {
					break;
				}
			}
			
			if(parent1 == null) {
				parent1 = fit_parents.get(0);
			}else if(parent2 == null) {
				parent2 = fit_parents.get(0);
			}
			
			children.add(mateStates(parent1 , parent2));
			children.add(mateStates(parent2 , parent1));
		}
		
		PriorityQueue<State> sorted_children = new PriorityQueue<>(
				new Comparator<State>() {
					public int compare(State arg0, State arg1) {
						return arg1.getFitness() - arg0.getFitness();
					}
				});
		sorted_children.addAll(children);
		
		return sorted_children;
	}
	
	private State mateStates(State parent1 , State parent2) {
		int[][] p1 = parent1.getBoardArray();
		int[][] p2 = parent2.getBoardArray();
		int[][] child = new int[p1.length][p1.length];
		
		int queens_from_p1 = (int)(Math.random() * (child.length-2)) + 1;
		
		for(int row = 0 ; row < child.length ; row++) {
			for(int col = 0 ; col < child.length ; col++) {
				if(row < queens_from_p1) {
					child[row][col] = p1[row][col];
				}else {
					child[row][col] = p2[row][col];
				}
			}
		}
		
		if((int)(Math.random() * 100) < CHANCE_OF_MUTATION) {
			child = mutateChild(child);
		}
		
		return new State(child);
	}
	
	private int[][] mutateChild(int[][] child){
		int row = (int)(Math.random() * child.length);
		int col = (int)(Math.random() * child.length);
		
		if(child[row][col] == 1) {
			child[row][col] = 0;
			col++;
			if(col >= child.length) {
				col = 0;
			}
		}
		else {
			for(int c = 0 ; c < child.length ; c++) {
				child[row][c] = 0;
			}
		}
		
		child[row][col] = 1;
		return child;
	}
}
