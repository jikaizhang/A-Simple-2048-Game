import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;
public class Main {
	static int[][] board = new int[4][4];	// TODO: make 0's null
	// a list of null (zero) entries in the board
	static ArrayList<Integer> zeroList = new ArrayList<Integer>();
	static int totalScore = 0;
	
	// start the game
	public static void start() {
		board = new int[4][4];
		zeroList = new ArrayList<Integer>();
		totalScore = 0;
		for (int i = 0; i < 16; i++)
			zeroList.add(i);
		// the game starts with two random 2 or 4
		int updateIndex = getRandomZeroElement(zeroList);
		int updateNumber = getRandomNumber();
		int row = updateIndex / 4;
		int col = updateIndex % 4;
		board[row][col] = updateNumber;
		zeroList.remove(Integer.valueOf(updateIndex));
		System.out.println();
		autoUpdate();
	}
	
	// randomly choose a zero index to update it to a 2 or a 4
	public static int getRandomZeroElement(ArrayList<Integer> list) {
		Random rand = new Random();
		return list.get(rand.nextInt(list.size()));
	}
	
	// return 2 with probability 3/4, and return 4 with probability 1/4
	public static int getRandomNumber() {
		if (Math.random() <= 0.25)
			return 4;
		return 2;
	}
	
	// auto-update the board for each iteration by randomly add a 2 or a 4
	public static void autoUpdate(){
		int updateIndex = getRandomZeroElement(zeroList);
		int updateNumber = getRandomNumber();
		int row = updateIndex / 4;
		int col = updateIndex % 4;
		board[row][col] = updateNumber;
		zeroList.remove(Integer.valueOf(updateIndex));
		visualizeBoard();
	}
	
	// visualize the board
	public static void visualizeBoard() {
		System.out.println("Total Score: " + totalScore);
		for (int i = 0; i < board.length; i++) {
			System.out.println(" -----------------");
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0)
					System.out.print(" |  ");
				else
					System.out.print(" | " + board[i][j]);
			}
			System.out.println(" | ");
		}
		System.out.println(" -----------------");
	}
	
	// after each iteration, update the zeroList
	public static void updateZeroList() {
		zeroList = new ArrayList<Integer>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0)
					zeroList.add(board.length * i + j);
			}
		}
	}
	
	// clone the board
	public static int[][] cloneBoard(){
		int[][] clone = new int[board.length][board[0].length];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++)
				clone[i][j] = board[i][j];
		}
		return clone;
	}
	
	public static void moveUp() {
		int[][] oldBoard = cloneBoard();
		// First collapse all zero entries
		for (int i = board.length - 2; i >= 0; i--) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					for (int k = i; k < board.length - 1; k++) 
						board[k][j] = board[k + 1][j];
					board[board.length - 1][j] = 0;
				}
			}
		}
		// Then collapse those (vertically) adjacent entries with same value
		for (int i = 0; i < board.length - 1; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != 0 && board[i][j] == board[i + 1][j]) {
					totalScore += board[i][j] * 2;
					board[i][j] = board[i][j] * 2;
					board[i + 1][j] = 0;
				}
			}
		}
		// Collapse all zero entries again
		for (int i = board.length - 2; i >= 0; i--) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					for (int k = i; k < board.length - 1; k++) 
						board[k][j] = board[k + 1][j];
					board[board.length - 1][j] = 0;
				}
			}
		}
		updateZeroList();
		if (Arrays.deepEquals(board, oldBoard)) {
			visualizeBoard();
			return;
		}
		autoUpdate();
	}
	
	public static void moveDown() {
		int[][] oldBoard = cloneBoard();
		// First collapse all zero entries
		for (int i = 1; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					for (int k = i; k >= 1; k--) 
						board[k][j] = board[k - 1][j];
					board[0][j] = 0;
				}
			}
		}
		// Then collapse those (vertically) adjacent entries with same value
		for (int i = board.length - 2; i >= 0; i--) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] != 0 && board[i][j] == board[i + 1][j]) {
					totalScore += board[i][j] * 2;
					board[i + 1][j] = board[i + 1][j] * 2;
					board[i][j] = 0;
				}
			}
		}
		// Collapse all zero entries again
		for (int i = 1; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					for (int k = i; k >= 1; k--) 
						board[k][j] = board[k - 1][j];
					board[0][j] = 0;
				}
			}
		}
		updateZeroList();
		if (Arrays.deepEquals(board, oldBoard)) {
			visualizeBoard();
			return;
		}
		autoUpdate();
	}
	
	public static void moveLeft() {
		int[][] oldBoard = cloneBoard();
		// First collapse all zero entries
		for (int i = 0; i < board.length; i++) {
			for (int j = board[i].length - 2; j >= 0; j--) {
				if (board[i][j] == 0) {
					for (int k = j; k < board[i].length - 1; k++) 
						board[i][k] = board[i][k + 1];
					board[i][board[i].length - 1] = 0;
				}
			}
		}
		// Then collapse those (horizontally) adjacent entries with same value
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length - 1; j++) {
				if (board[i][j] != 0 && board[i][j] == board[i][j + 1]) {
					totalScore += board[i][j] * 2;
					board[i][j] = board[i][j] * 2;
					board[i][j + 1] = 0;
				}
			}
		}
		// Collapse all zero entries again
		for (int i = 0; i < board.length; i++) {
			for (int j = board[i].length - 2; j >= 0; j--) {
				if (board[i][j] == 0) {
					for (int k = j; k < board[i].length - 1; k++) 
						board[i][k] = board[i][k + 1];
					board[i][board[i].length - 1] = 0;
				}
			}
		}
		updateZeroList();
		if (Arrays.deepEquals(board, oldBoard)) {
			visualizeBoard();
			return;
		}
		autoUpdate();
	}
	
	public static void moveRight() {
		int[][] oldBoard = cloneBoard();
		// First collapse all zero entries
		for (int i = 0; i < board.length; i++) {
			for (int j = 1; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					for (int k = j; k >= 1; k--) 
						board[i][k] = board[i][k - 1];
					board[i][0] = 0;
				}
			}
		}
		// Then collapse those (horizontally) adjacent entries with same value
		for (int i = 0; i < board.length; i++) {
			for (int j = board[i].length - 2; j >= 0; j--) {
				if (board[i][j] != 0 && board[i][j] == board[i][j + 1]) {
					totalScore += board[i][j] * 2;
					board[i][j + 1] = board[i][j + 1] * 2;
					board[i][j] = 0;
				}
			}
		}
		// Collapse all zero entries again
		for (int i = 0; i < board.length; i++) {
			for (int j = 1; j < board[i].length; j++) {
				if (board[i][j] == 0) {
					for (int k = j; k >= 1; k--) 
						board[i][k] = board[i][k - 1];
					board[i][0] = 0;
				}
			}
		}
		updateZeroList();
		if (Arrays.deepEquals(board, oldBoard)) {
			visualizeBoard();
			return;
		}
		autoUpdate();
	}
	
	public static boolean win() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == 2048)
					return true;
			}
		}
		return false;
	}
	
	// if the board is full, we use this method to determine
	// if the game ends
	public static boolean noNextMove() {
		// check horizontally adjacent entries
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length - 1; j++) {
				if (board[i][j] == board[i][j + 1])
					return false;
			}
		}
		// check vertically adjacent entries
		for (int i = 0; i < board.length - 1; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == board[i + 1][j])
					return false;
			}
		}
		return true;
	}
	public static boolean lose() {
		if (zeroList.isEmpty() && noNextMove())
			return true;
		return false;
	}
	
	public static void rules() {
		System.out.println("\"W\" or \"w\" to move Up");
		System.out.println("\"S\" or \"s\" to move Down");
		System.out.println("\"A\" or \"a\" to move Left");
		System.out.println("\"D\" or \"d\" to move Right");
		System.out.println("\"Q\" or \"q\" to Quit");
	}
	
	public static void main(String args[]) {
		rules();
		System.out.println();
		start();
		while (true) {
			Scanner scr = new Scanner(System.in);
			String input = scr.nextLine();
			if (input.charAt(0) == 'Q' || input.charAt(0) == 'q') {
				System.out.println("Do you want to exit? (Y/N)");
				input = scr.nextLine();
				if (input.charAt(0) == 'Y' || input.charAt(0) == 'y') {
					System.out.println("Thank you for playing my game! Hope "
							+ "you enjoy it!");
					break;
				}
				else
					visualizeBoard();
			}
			else if (input.charAt(0) == 'W' || input.charAt(0) == 'w') 
				moveUp();
			else if (input.charAt(0) == 'S' || input.charAt(0) == 's') 
				moveDown();
			else if (input.charAt(0) == 'A' || input.charAt(0) == 'a') 
				moveLeft();
			else if (input.charAt(0) == 'D' || input.charAt(0) == 'd') 
				moveRight();
			else {
				rules();
				continue;
			}
			if (win()) {
				System.out.println("Congratulations! You win!");
				System.out.println("Total Score: " + totalScore);
				System.out.println("One more game? (Y/N)");
				input = scr.nextLine();
				if (input.charAt(0) == 'N' || input.charAt(0) == 'n') {
					System.out.println("Thank you for playing my game! Hope "
							+ "you enjoy it!");
					System.out.println("Total Score: " + totalScore);
					break;
				}
				else if (input.charAt(0) == 'Y' || input.charAt(0) == 'y')
					start();
			}
			if (lose()) {
				System.out.println("Sorry, you lose.");
				System.out.println("Total Score: " + totalScore);
				System.out.println("One more game? (Y/N)");
				input = scr.nextLine();
				if (input.charAt(0) == 'N' || input.charAt(0) == 'n') {
					System.out.println("Thank you for playing my game! Hope "
							+ "you enjoy it!");
					break;
				}
				else if (input.charAt(0) == 'Y' || input.charAt(0) == 'y')
					start();
			}
		}
	}
}
