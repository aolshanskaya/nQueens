import java.util.*;

public class UserInterface {
	private static UserInterface ui = null;
	private Scanner kb;
	private UserInterface() {
		kb = new Scanner(System.in);
	}
	public static UserInterface getUI(){
		if(ui == null)
			ui = new UserInterface();
		return ui;
	}

	public int welcomeMessage() {
		System.out.println("Welcome to the N-Queens puzzle solver.");
		mainMenu();
		
		String answer = kb.next();
		int decision = Integer.parseInt(answer);
		
		while(decision < 1 || decision > 3) {
			System.out.println("I'm sorry, that was unclear. Please try again. Answer 1,2, or 3.");
			mainMenu();
			answer = kb.next();
			decision = Integer.parseInt(answer);
			System.out.println("You chose: " + decision);
		}
		
		if(decision == 1) {
			return 1;
		}else if(decision == 3){
			return 2;
		}
		return 0;
	}
	
	private void mainMenu() {
		System.out.println("Please choose how you'd like to proceed:");
		System.out.println("1) Print out 3 solutions using each Genetic and Simulated Annealing Search Algorithms.");
		System.out.println("2) Run 500 trials using two versions of each algorithm. (This will take a while)");
		System.out.println("3) Exit.");
	}

	private void goodbyeMessage() {
		kb.close();
		System.out.println("Good-Bye!");
	}
}

