import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller implements Initializable {
	
	@FXML
	private BorderPane root;
	
	@FXML
	private MenuItem H1Item;
	
	@FXML
	private MenuItem H2Item;
	
	@FXML
	private MenuItem seeSolItem;
	
	@FXML
	private MenuItem P1Item;
	
	@FXML
	private MenuItem P2Item;
	
	@FXML
	private MenuItem P3Item;
	
	@FXML
	private MenuItem P4Item;
	
	@FXML
	private MenuItem P5Item;
	
	@FXML
	private MenuItem P6Item;
	
	@FXML
	private MenuItem P7Item;
	
	@FXML
	private MenuItem P8Item;
	
	@FXML
	private MenuItem P9Item;
	
	@FXML
	private MenuItem P10Item;
	
	// Keeps track of number of moved the player has done
	private static Integer moves = 0;
	// Only needs to be initialized once
	private static Stage htpStage = getHTP_Stage();
	private static Stage congratsStage = getCongratsStage();
	// The puzzles
	private final int puzzle1[][] = {{0, 10, 2, 6}, {7, 1, 9, 5}, {3, 4, 15, 8}, {12, 11, 13, 14}};
	private final int puzzle2[][] = {{0, 1, 5, 7}, {9, 13, 3, 11}, {2, 10, 14, 6}, {8, 12, 15, 4}};
	private final int puzzle3[][] = {{0, 9, 3, 6}, {4, 5, 1, 12}, {8, 13, 2, 7}, {15, 11, 14, 10}};
	private final int puzzle4[][] = {{0, 8, 10, 4}, {2, 5, 11, 6}, {1, 9, 12, 15}, {13, 14, 3, 7}};
	private final int puzzle5[][] = {{0, 6, 11, 10}, {5, 2, 8, 4}, {13, 1, 3, 15}, {14, 12, 9, 7}};
	private final int puzzle6[][] = {{0, 10, 11, 15}, {9, 12, 13, 14}, {4, 8, 1, 2}, {5, 6, 7, 3}};
	private final int puzzle7[][] = {{0, 5, 6, 2}, {6, 4, 11, 10}, {1, 15, 13, 14}, {9, 8, 12, 3}};
	private final int puzzle8[][] = {{0, 13, 4, 2}, {1, 15, 6, 3}, {14, 9, 11, 7}, {5, 12, 10, 8}};
	private final int puzzle9[][] = {{0, 9, 5, 7}, {1, 10, 12, 6}, {4, 2, 3, 11}, {8, 13, 14, 15}};
	private final int puzzle10[][] = {{0, 7, 5, 6}, {9, 1, 12, 10}, {8, 14, 13, 3}, {15, 11, 4, 2}};
	
	private final int goal[][] = {{0, 1, 2, 3}, {4, 5, 6, 7}, {8, 9, 10, 11}, {12, 13, 14, 15}};
	// This will be used to keep track of how the board is
	private int gameBoard[][];
	// This will be used to unlock new ones
	private static int maxPuzzleAvailable = 1;

	ArrayList<Node> path;
	private GridPane gridPane = new GridPane();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		gameBoard = puzzle1;
		makeGrid(0);
	}
	
	public void makeGrid(int puzzleNum) {
		int puzzle[][];
		
		if (puzzleNum == -1) {
			puzzle = gameBoard;
		}
		else if (puzzleNum == 0) {
			puzzle = puzzle1;
		}
		else if (puzzleNum == 1) {
			puzzle = puzzle2;
		}
		else if (puzzleNum == 2) {
			puzzle = puzzle3;
		}
		else if (puzzleNum == 3) {
			puzzle = puzzle4;
		}
		else if (puzzleNum == 4) {
			puzzle = puzzle5;
		}
		else if (puzzleNum == 5) {
			puzzle = puzzle6;
		}
		else if (puzzleNum == 6) {
			puzzle = puzzle7;
		}
		else if (puzzleNum == 7) {
			puzzle = puzzle8;
		}
		else if (puzzleNum == 8) {
			puzzle = puzzle9;
		}
		else if (puzzleNum == 9) {
			puzzle = puzzle10;
		}
		else {	
			puzzle = puzzle1;
		}
		gridPane = new GridPane();
			
		// Create the first puzzle and display it in the game scene	
		String t;
		for (int i = 0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				if (puzzle[i][j] == 0) {
					GameButton blank = new GameButton(2, 0, i, j);
					gridPane.add(blank, j, i);
				}
				else {
					t = Integer.toString(puzzle[i][j]);
					GameButton tile = new GameButton(1, puzzle[i][j], i, j);
					tile.setText(t);
					tile.setOnAction(e->{
						 if (gameBoard[1][1] == tile.getNum() || gameBoard[1][2] == tile.getNum() || gameBoard[2][2] == tile.getNum() || gameBoard[2][1] == tile.getNum()) {
							 if (gameBoard[tile.getX() - 1][tile.getY()] == 0) { // up
								 gameBoard[tile.getX() - 1][tile.getY()] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX() + 1][tile.getY()] == 0) { // down
								 gameBoard[tile.getX() + 1][tile.getY()] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX()][tile.getY() - 1] == 0) { // left
								 gameBoard[tile.getX()][tile.getY() - 1] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX()][tile.getY() + 1] == 0) { // right
								 gameBoard[tile.getX()][tile.getY() + 1] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else { // nothing
							 }
						 }
						 else if (gameBoard[0][1] == tile.getNum() || gameBoard[0][2] == tile.getNum()) {
							 if (gameBoard[tile.getX() + 1][tile.getY()] == 0) { // down
								 gameBoard[tile.getX() + 1][tile.getY()] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX()][tile.getY() - 1] == 0) { // left
								 gameBoard[tile.getX()][tile.getY() - 1] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX()][tile.getY() + 1] == 0) { // right
								 gameBoard[tile.getX()][tile.getY() + 1] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else { // nothing
							 }
						 }
						 else if (gameBoard[1][3] == tile.getNum() || gameBoard[2][3] == tile.getNum()) {
							 if (gameBoard[tile.getX() - 1][tile.getY()] == 0) { // up
								 gameBoard[tile.getX() - 1][tile.getY()] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX() + 1][tile.getY()] == 0) { // down
								 gameBoard[tile.getX() + 1][tile.getY()] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX()][tile.getY() - 1] == 0) { // left
								 gameBoard[tile.getX()][tile.getY() - 1] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else {
								 // nothing
							 }
						 }
						 else if (gameBoard[1][0] == tile.getNum() || gameBoard[2][0] == tile.getNum()) {
							 if (gameBoard[tile.getX() - 1][tile.getY()] == 0) { // up
								 gameBoard[tile.getX() - 1][tile.getY()] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX() + 1][tile.getY()] == 0) { // down
								 gameBoard[tile.getX() + 1][tile.getY()] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX()][tile.getY() + 1] == 0) { // right
								 gameBoard[tile.getX()][tile.getY() + 1] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else {
								 // nothing
							 }
						 }
						 else if (gameBoard[3][1] == tile.getNum() || gameBoard[3][2] == tile.getNum()) {
							 if (gameBoard[tile.getX() - 1][tile.getY()] == 0) { // up
								 gameBoard[tile.getX() - 1][tile.getY()] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX()][tile.getY() - 1] == 0) { // left
								 gameBoard[tile.getX()][tile.getY() - 1] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else if (gameBoard[tile.getX()][tile.getY() + 1] == 0) { // right
								 gameBoard[tile.getX()][tile.getY() + 1] = tile.getNum();
								 gameBoard[tile.getX()][tile.getY()] = 0;
								 ++moves; // Add one to the count "moves"
							 }
							 else {
								 // nothing
							 }
						 }
						 else { // Then it's a corner button
							 if (gameBoard[0][0] == tile.getNum()) { // top left
								 if (gameBoard[tile.getX() + 1][tile.getY()] == 0) { // down
									 gameBoard[tile.getX() + 1][tile.getY()] = tile.getNum();
									 gameBoard[tile.getX()][tile.getY()] = 0;
									 ++moves; // Add one to the count "moves"
								 }
								 else if (gameBoard[tile.getX()][tile.getY() + 1] == 0) { // right
									 gameBoard[tile.getX()][tile.getY() + 1] = tile.getNum();
									 gameBoard[tile.getX()][tile.getY()] = 0;
									 ++moves; // Add one to the count "moves"
								 }
								 else { // nothing
								 }
							 }
							 else if (gameBoard[0][3] == tile.getNum()) { // top right
								 if (gameBoard[tile.getX() + 1][tile.getY()] == 0) { // down
									 gameBoard[tile.getX() + 1][tile.getY()] = tile.getNum();
									 gameBoard[tile.getX()][tile.getY()] = 0;
									 ++moves; // Add one to the count "moves"
								 }
								 else if (gameBoard[tile.getX()][tile.getY() - 1] == 0) { // left
									 gameBoard[tile.getX()][tile.getY() - 1] = tile.getNum();
									 gameBoard[tile.getX()][tile.getY()] = 0;
									 ++moves; // Add one to the count "moves"
								 }
								 else { // nothing
								 }
							 }
							 else if (gameBoard[3][0] == tile.getNum()) { // bottom left
								 if (gameBoard[tile.getX() - 1][tile.getY()] == 0) { // up
									 gameBoard[tile.getX() - 1][tile.getY()] = tile.getNum();
									 gameBoard[tile.getX()][tile.getY()] = 0;
									 ++moves; // Add one to the count "moves"
								 }
								 else if (gameBoard[tile.getX()][tile.getY() + 1] == 0) { // right
									 gameBoard[tile.getX()][tile.getY() + 1] = tile.getNum();
									 gameBoard[tile.getX()][tile.getY()] = 0;
									 ++moves; // Add one to the count "moves"
								 }
								 else { // nothing
								 }
							 }
							 else if (gameBoard[3][3] == tile.getNum()) { // bottom right
								 if (gameBoard[tile.getX() - 1][tile.getY()] == 0) { // up
									 gameBoard[tile.getX() - 1][tile.getY()] = tile.getNum();
									 gameBoard[tile.getX()][tile.getY()] = 0;
									 ++moves; // Add one to the count "moves"
								 }
								 else if (gameBoard[tile.getX()][tile.getY() - 1] == 0) { // left
									 gameBoard[tile.getX()][tile.getY() - 1] = tile.getNum();
									 gameBoard[tile.getX()][tile.getY()] = 0;
									 ++moves; // Add one to the count "moves"
								 }
								 else { // nothing
								 }
							 } else { }
						 }
						 // Update the grid
						 makeGrid(-1);
						 // See if it's a win
						 if (java.util.Arrays.deepEquals(gameBoard, goal) == true) {
							 congratsStage.show();
							 getNewPuzzle(); // unlock new puzzle
							 ++maxPuzzleAvailable;
							 // disable the H1, H2, and See solution
							 H1Item.setDisable(true);
							 H2Item.setDisable(true);
							 seeSolItem.setDisable(true);
						 }
					});
					gridPane.add(tile, j, i);
				}
			}
		}
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(70));
		gridPane.setHgap(10.0);
		gridPane.setVgap(10.0);
		root.setCenter(gridPane);
	}
	
	public void getNewPuzzle() {
		switch(maxPuzzleAvailable) {
		case 1:
			P2Item.setDisable(false);
			break;
		case 2:
			P3Item.setDisable(false);
			break;
		case 3:
			P4Item.setDisable(false);
			break;
		case 4:
			P5Item.setDisable(false);
			break;
		case 5:
			P6Item.setDisable(false);
			break;
		case 6:
			P7Item.setDisable(false);
			break;
		case 7:
			P8Item.setDisable(false);
			break;
		case 8:
			P9Item.setDisable(false);
			break;
		case 9:
			P10Item.setDisable(false);
			break;
		default:
				// Nothing
		}
		
	}

	public Integer[] boardToArray() {
		int index = 0;
		Integer array[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		for (int i =  0; i < 4; ++i) {
			for (int j = 0; j < 4; ++j) {
				array[index] = gameBoard[i][j];
				++index;
			}
		}
		return array;
	}
	
	//	Event Handlers
	public void H1Method(ActionEvent e) throws IOException {
		// disable the H1, H2, and See solution
		 H1Item.setDisable(true);
		 H2Item.setDisable(true);
		 
		ExecutorService ex = Executors.newFixedThreadPool(5);
		int[] boardArray = Arrays.stream(boardToArray()).mapToInt(Integer::intValue).toArray(); // Convert Integer[] to int[]
		A_IDS_A_15solver ids = new A_IDS_A_15solver(boardArray, 1);
		
		Future<ArrayList<Node>> future = ex.submit(new MyCall(ids));
			
		try {
			path = future.get();
		} catch(Exception ex1) {
			System.out.println(ex1.getMessage());
		}
		ex.shutdown();
		 
		seeSolItem.setDisable(false);
	}
	
	public void H2Method(ActionEvent e) throws IOException {
		// disable the H1, H2, and See solution
		H1Item.setDisable(true);
		H2Item.setDisable(true);
				 
		ExecutorService ex = Executors.newFixedThreadPool(5);
		int[] boardArray = Arrays.stream(boardToArray()).mapToInt(Integer::intValue).toArray(); // Convert Integer[] to int[]
		A_IDS_A_15solver ids = new A_IDS_A_15solver(boardArray, 2);
				
		Future<ArrayList<Node>> future = ex.submit(new MyCall(ids));
					
		try {
			path = future.get();
		} catch(Exception ex1) {
		System.out.println(ex1.getMessage());
		}
		ex.shutdown();
				 
		seeSolItem.setDisable(false);
	}
	
	public void seeSolution(ActionEvent e) throws IOException {
		// Display move by move
		recursivePause(0);
		seeSolItem.setDisable(true);
	}
	
	public void recursivePause(int value){
			int[] puzzleArray = path.get(value).getKey();
			int index = 0;
			for (int i = 0; i < 4; ++i) {
				for (int j = 0; j < 4; ++j) {
					gameBoard[i][j] = puzzleArray[index];
					++index;
				}
			}
			
			PauseTransition s = new PauseTransition(Duration.seconds(2));

			s.setOnFinished(ev->{ 
				makeGrid(-1);
				if (value < 9) {
					recursivePause(value + 1);
				}
				});
				
		  s.play();
		}
	
	public void P1Method(ActionEvent e) throws IOException {
		moves = 0;
		makeGrid(0);
		H1Item.setDisable(false);
		H2Item.setDisable(false);
		seeSolItem.setDisable(true);
	}
	
	public void P2Method(ActionEvent e) throws IOException {
		moves = 0;
		makeGrid(1);
		H1Item.setDisable(false);
		H2Item.setDisable(false);
		seeSolItem.setDisable(true);
	}

	public void P3Method(ActionEvent e) throws IOException {
		moves = 0;
		makeGrid(2);
		H1Item.setDisable(false);
		H2Item.setDisable(false);
		seeSolItem.setDisable(true);
	}

	public void P4Method(ActionEvent e) throws IOException {
		moves = 0;
		makeGrid(3);
		H1Item.setDisable(false);
		H2Item.setDisable(false);
		seeSolItem.setDisable(true);
	}

	public void P5Method(ActionEvent e) throws IOException {
		moves = 0;
		makeGrid(4);
		H1Item.setDisable(false);
		H2Item.setDisable(false);
		seeSolItem.setDisable(true);
	}

	public void P6Method(ActionEvent e) throws IOException {
		moves = 0;
		makeGrid(5);
		H1Item.setDisable(false);
		H2Item.setDisable(false);
		seeSolItem.setDisable(true);
	}

	public void P7Method(ActionEvent e) throws IOException {
		moves = 0;
		makeGrid(6);
		H1Item.setDisable(false);
		H2Item.setDisable(false);
		seeSolItem.setDisable(true);
	}

	public void P8Method(ActionEvent e) throws IOException {
		moves = 0;
		makeGrid(7);
		H1Item.setDisable(false);
		H2Item.setDisable(false);
		seeSolItem.setDisable(true);
	}
	

	public void P9Method(ActionEvent e) throws IOException {
		moves = 0;
		makeGrid(8);
		H1Item.setDisable(false);
		H2Item.setDisable(false);
		seeSolItem.setDisable(true);
	}
	
	
	public void P10Method(ActionEvent e) throws IOException {
		moves = 0;
		makeGrid(9);
		H1Item.setDisable(false);
		H2Item.setDisable(false);
		seeSolItem.setDisable(true);
	}
	
	public void howPlayMethod(ActionEvent e) throws IOException {
		htpStage.show();
	}
	
	
	private static Stage getHTP_Stage() {
		Text htp = new Text(
				"\tHow to Play:\n" +
				"1. Your goal is to get the numbers in this exact order:\n" +
						"\t      1   2   3\n" +
						"\t 4   5   6   7\n" +
						"\t 8   9  10 11\n" +
						"\t12 13 14 15\n" + 
				"2. To move a number, click on the box that contains that number. If it is a valid\n" +
				"    move, then the number will move spots. Otherwise, nothing will occur.\n\n"
				+ "\tHelp:\n"
				+ "1. If you need help, the puzzle can be solved for you:\n"
				+ "\tSolve -> AI H1\tor\tSolve-> AI H2\n"
				+ "2. When the solution is available, go to: Solve -> See Solution\n"
				+ "    which will be enabled, click on it. You will see the solution.");
		VBox text = new VBox(htp);
		text.setAlignment(Pos.CENTER);
		Stage stage = new Stage();
        stage.setTitle("15 Puzzle - How to Play");
        stage.setScene(new Scene(text, 550, 300));
        return stage;
	}
	
	
	private static Stage getCongratsStage() {
		Text congrats = new Text("Congratulations! You have completed the puzzle!\n" + "See if you unlocked a new puzzle or exit.");
		congrats.setTextAlignment(TextAlignment.CENTER);
		congrats.setStyle("-fx-font: 20 arial;");
		VBox text = new VBox(congrats);
		text.setAlignment(Pos.CENTER);
		Stage stage = new Stage();
        stage.setTitle("15 Puzzle - Congratulations!");
        stage.setScene(new Scene(text, 550, 300));
        return stage;
	}

	public void exitMethod(ActionEvent e) throws IOException {
		System.exit(0);
	}
	
	// Class
	class MyCall implements Callable<ArrayList<Node>>{
		A_IDS_A_15solver ids;
		
		MyCall(A_IDS_A_15solver ids){
			this.ids = ids;
		}
		
		@Override
		public ArrayList<Node> call() throws Exception {
			return ids.Solve();
		}
		
	}
	
}
