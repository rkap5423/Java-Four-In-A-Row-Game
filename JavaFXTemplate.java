import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Stack;

public class JavaFXTemplate extends Application {
	final GameLogic gameLogic = new GameLogic(this);
	EventHandler<ActionEvent> btnClick;
	int infoBoxMode = 0;
	public ArrayList<Integer> winningRow = new ArrayList<Integer>();
	public ArrayList<Integer> winningCol = new ArrayList<Integer>();
	public Label lblPlayerMove, lblExtraInfo, endName, lblScore;
	public boolean gameOver, tieGame;
	String[] chips = new String[5];
	public Scene startScene, gameScene, endScene;
	public BorderPane startBorder, gameBorder, endBorder;
	public GridPane gamePane;
	public int player;
	public ArrayList<Player> people = new ArrayList<>();
	public TextField infoBox;
	public Menu options, gamePlay, themes;
	public MenuItem howToPlay, newGame, exit, reverseMove, lightMode, darkMode, christmasMode;
	public MenuBar menuBar;
	public Button startBtn, endExit, endRestart;
	public Stack<Pair<Integer, Integer>> gameStack = new Stack<>();
	GameButton[][] gameBtns = new GameButton[6][7];
	PauseTransition pause = new PauseTransition(Duration.seconds(5));

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	public void setGameBtns(GameButton[][] gameBtns) {
		this.gameBtns = gameBtns;
	}

	public void setPeople(ArrayList<Player> people){
		this.people=people;
	}
	public void setPlayer(int person){
		player=person;
	}

	private void pTurn() {
		player++;
		if (player > 1) {
			player = 0;
		}
		// Used to change the label on whose turn it is now
		lblPlayerMove.setText("It is " + people.get(player).getName() + "'s (" + people.get(player).getColorString() + ") turn");
	}

	private void reversePTurn() {
		player--;
		if (player < 0) {
			player = 1;
		}
		// Used to set the label on whose turn it is now
		lblPlayerMove.setText("Move reversed! " + people.get(player).getName() + "'s (" + people.get(player).getColorString() + ") turn");
	}


	public ArrayList<Integer> getWinningCol() {
		return winningCol;
	}

	public ArrayList<Integer> getWinningRow() {
		return winningRow;
	}

	public void menuBarSetup(){ //set up menu bar with all the various options, and style it to default colors
		options = new Menu("Options");
		howToPlay = new MenuItem("How To Play");
		newGame = new MenuItem("New Game");
		exit = new MenuItem("Exit");
		options.getItems().add(howToPlay);
		options.getItems().add(newGame);
		options.getItems().add(exit);

		gamePlay = new Menu("Game Play");
		reverseMove = new MenuItem("Reverse Move");
		gamePlay.getItems().add(reverseMove);

		themes = new Menu("Themes");

		lightMode = new MenuItem("Light Mode");
		darkMode = new MenuItem("Dark Mode");
		christmasMode = new MenuItem("Christmas Mode");
		themes.getItems().add(lightMode);
		themes.getItems().add(darkMode);
		themes.getItems().add(christmasMode);

		menuBar = new MenuBar();
		menuBar.getMenus().add(options);
		menuBar.getMenus().add(gamePlay);
		menuBar.getMenus().add(themes);

		menuBar.setStyle("-fx-background-color:#4da6ff");
	}

	/*
	Test method to print out the data of the game buttons and the data of which player is where
	 */
	private void printData() {

		for (int i = 0; i < gameBtns.length; i++) {
			for (int j = 0; j < gameBtns[0].length; j++) {
				System.out.print(gameBtns[i][j].getPlayerOnBtn() + " ");
			}
			System.out.println();

		}
		System.out.println("");
	}

	public void  gameOverSetup(){ //Game is over, someone won. Disable all buttons, and update label
		gameOver = true;
		lblPlayerMove.setText("Congrats " + people.get(player).getName() + " (" + people.get(player).getColorString()  + ") has won!");

		people.get(player).updateScore();

		for (int i = 0; i < gameBtns.length; i++){
			for(int j = 0; j < gameBtns[0].length; j++){

				if(gameBtns[i][j].getPlayerOnBtn() == -1){
					double temp = gameBtns[i][j].getOpacity();
					gameBtns[i][j].setDisable(true);
					gameBtns[i][j].setOpacity(temp);
					gameBtns[i][j].setStyle("-fx-background-radius: 5em; " +
							"-fx-min-width: 50px; " +
							"-fx-min-height: 50px; " +
							"-fx-max-width: 50px; " +
							"-fx-max-height: 50px; " +
							"-fx-background-color: -fx-body-color;" +
							"-fx-background-insets: 0px; " +
							"-fx-padding: 0px;"
					);
				}
			}
		}

		int numCounter = winningRow.size() - 1;

		while(numCounter > -1){ //make the winning chips glow to highlight them

			Glow glow = new Glow();
			glow.setLevel(0.9);

			gameBtns[winningRow.get(numCounter)][winningCol.get(numCounter)].setEffect(glow);
			gameBtns[winningRow.get(numCounter)][winningCol.get(numCounter)].setOpacity(1);

			numCounter--;
		}

		menuBar.setDisable(true);
		pause.play();
	}

	public void setupChips(){
		chips[0] = "Blank.PNG";
		chips[1] = "BlueChip.png";
		chips[2] = "YellowChip.png";
		chips[3] = "GreenChip.png";
		chips[4] = "RedChip.png";
	}

	public void MenuButtons(){ //various menu bar options

		exit.setOnAction(exit-> //exit application
		{
			Platform.exit();

		});

		reverseMove.setOnAction(e-> //reverse move
		{
			lblExtraInfo.setVisible(false);
			if (gameStack.size() > 0){


				reversePTurn();
				Pair<Integer, Integer> pair = gameStack.pop(); //Grab the element at the top to use for current Game Buttons

				gameBtns[pair.getKey()][pair.getValue()].setPlayerOnBtn(-1);


				gameBtns[pair.getKey()][pair.getValue()].setDisable(false);
				gameBtns[pair.getKey()][pair.getValue()].setGraphic(null);

				printData();

			}

			else{
				lblPlayerMove.setText("No move to reverse, it is " + people.get(player).getName() + "'s (" + people.get(player).getColorString() + ") turn");
			}



		});

		howToPlay.setOnAction(e-> //how to play label
		{
			lblExtraInfo.setVisible(true);
			lblExtraInfo.setText("Connect Four is played on a grid of 7 columns " +
					"and 6 rows, as displayed. It is a two player game " +
					"where each player takes a \nturn dropping a checker into " +
					"a slot (one of the columns) on the game board. That " +
					"checker will fall down the column and \neither land on " +
					"top of another checker or land on the bottom row. To " +
					"win the game, a player needs to get 4 of their checkers \n" +
					"in a vertical, horizontal or diagonal row before the " +
					"other player. A player may \"drop\" their checker by clicking on any \n" +
					"available button which is right above a currently occupied space and " +
					"is within the board confines. You may change various \n" +
					"                                                      options by using " +
					"the menu bar at the top of the screen. ");
		});

		newGame.setOnAction(e-> //start new game
		{
			lblExtraInfo.setVisible(false);

			while(gameStack.size() > 0){

				Pair<Integer, Integer> pair = gameStack.pop(); //Grab the element at the top to use for current Game Buttons

				gameBtns[pair.getKey()][pair.getValue()].setPlayerOnBtn(-1);
				gameBtns[pair.getKey()][pair.getValue()].setDisable(false);
				gameBtns[pair.getKey()][pair.getValue()].setGraphic(null);

			}

			printData();
			player = (int) Math.round(Math.random()); //randomly select who goes first
			lblPlayerMove.setText("Game has been reset, it's " + people.get(player).getName() + "'s (" + people.get(player).getColorString() + ") turn");

		});

		lightMode.setOnAction(e-> //various lightmode (default mode) styling and coloring
		{
			lblExtraInfo.setVisible(false);
			lblPlayerMove.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			lblExtraInfo.setFont(Font.font("Helvetica", FontPosture.ITALIC, 12));


			lblPlayerMove.setTextFill(Color.web("#4da6ff"));
			lblExtraInfo.setTextFill(Color.web("#0059b3"));
			gameBorder.setBackground(new Background(new BackgroundFill(Color.CORNSILK, CornerRadii.EMPTY, Insets.EMPTY)));

			people.get(1).updateColor("Yellow");
			lblPlayerMove.setText("It is " + people.get(player).getName() + "'s (" + people.get(player).getColorString() + ") turn");

			for(int i = 0; i < gameBtns.length; i++){
				for(int j = 0; j < gameBtns[0].length; j++){
					if(gameBtns[i][j].getPlayerOnBtn() == 1){


						Image image = new Image(chips[people.get(1).getColor()]);
						ImageView img = new ImageView(image);
						img.fitWidthProperty().bind(gameBtns[i][j].widthProperty());
						img.fitHeightProperty().bind(gameBtns[i][j].heightProperty());
						img.setPreserveRatio(true);
						gameBtns[i][j].setGraphic(img);

					}
				}
			}
			menuBar.setStyle("-fx-background-color:#4da6ff");

		});

		darkMode.setOnAction(e-> //various darkmode styling and coloring
		{
			lblExtraInfo.setVisible(false);
			lblPlayerMove.setTextFill(Color.web("#ffffff"));
			lblExtraInfo.setTextFill(Color.web("#ffffff"));
			gameBorder.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
			menuBar.setStyle("-fx-background-color:#ffffff");

		});

		christmasMode.setOnAction(e->{ //various christmas mode styling and coloring
			people.get(1).updateColor("Green");
			lblPlayerMove.setText("It is " + people.get(player).getName() + "'s (" + people.get(player).getColorString() + ") turn");
			for(int i = 0; i < gameBtns.length; i++){
				for(int j = 0; j < gameBtns[0].length; j++){
					if(gameBtns[i][j].getPlayerOnBtn() == 1){


						Image image = new Image(chips[people.get(1).getColor()]);
						ImageView img = new ImageView(image);
						img.fitWidthProperty().bind(gameBtns[i][j].widthProperty());
						img.fitHeightProperty().bind(gameBtns[i][j].heightProperty());
						img.setPreserveRatio(true);
						gameBtns[i][j].setGraphic(img);

					}
				}
			}

			lblExtraInfo.setVisible(false);
			menuBar.setStyle("-fx-background-color:#197419");

			lblPlayerMove.setTextFill(Color.web("#197419"));
			lblExtraInfo.setTextFill(Color.web("#197419"));

			gameBorder.setBackground(new Background(new BackgroundFill(Color.INDIANRED, CornerRadii.EMPTY, Insets.EMPTY)));
		});

	}

	public void buttonClick(int row, int column){ //handles what happens when you put down a game piece
		if(row == 5 || gameBtns[row+1][column].getPlayerOnBtn() != -1 ){
			gameBtns[row][column].setDisable(true);

			System.out.println(people.get(player).getName() + " placed chip on row: " + (row + 1) + " and column: " + (column + 1));

			gameBtns[row][column].setPlayerOnBtn(player); //Change the data member in generic button to the player that went
			gameStack.push(new Pair<Integer, Integer>(row, column)); //Row and column of last added element pushed onto stack
			printData();

					/*
					Code that adds an image to the button, of the player that just went
					 */
			Image image = new Image(chips[people.get(player).getColor()]);
			ImageView img = new ImageView(image);
			img.fitWidthProperty().bind(gameBtns[row][column].widthProperty());
			img.fitHeightProperty().bind(gameBtns[row][column].heightProperty());
			img.setPreserveRatio(true);
			gameBtns[row][column].setGraphic(img);

			gameBtns[row][column].setOpacity(1);

			gameLogic.checkWin(row, column);
			if (gameOver){
				gameOverSetup();
			}

			if(gameOver == false){
				gameLogic.checkTie();
			}

			if(gameOver == false){
				pTurn();
			}

		}
		else{
			lblPlayerMove.setText("Invalid move " + people.get(player).getName() + " (" + people.get(player).getColorString() + ")" + ", select valid spot!");
		}
	}

	public void genericBtnClick(){
		btnClick = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){

				lblExtraInfo.setVisible(false);
				//If there is something under then we can disable it
				GameButton b = (GameButton) event.getSource();
				int column = GridPane.getColumnIndex((GameButton) event.getSource());
				int row = GridPane.getRowIndex((GameButton) event.getSource());

				buttonClick(row, column);
			}
		};
	}

	public void establishGridPane(){ //set up game board
		for (int i = 0; i < gameBtns.length; i++){
			for(int j = 0; j < gameBtns[0].length; j++){
				gameBtns[i][j] = new GameButton(-1);
				gameBtns[i][j].setOnAction(btnClick);

				gameBtns[i][j].setStyle("-fx-background-radius: 5em; " +
						"-fx-min-width: 50px; " +
						"-fx-min-height: 50px; " +
						"-fx-max-width: 50px; " +
						"-fx-max-height: 50px; " +
						"-fx-background-color: -fx-body-color;" +
						"-fx-background-insets: 0px; " +
						"-fx-padding: 0px;"
				);


				gamePane.add(gameBtns[i][j], j, i);
			}
		}
	}


	public void infoBoxNameEntered(){ //handles start scene code for entering player names
		infoBox.setOnKeyPressed(key ->
		{
			if(key.getCode().equals(KeyCode.ENTER) && infoBoxMode == 0){
				infoBoxMode++;
				people.add(new Player(infoBox.getText(), "Red", 0));
				infoBox.clear();
				infoBox.setText("Enter Player Two's Name");
				infoBox.setAlignment(Pos.BASELINE_CENTER);
			}
			else if(key.getCode().equals(KeyCode.ENTER) && infoBoxMode == 1){
				infoBoxMode++;
				people.add(new Player(infoBox.getText(), "Yellow", 0));
				infoBox.clear();

				infoBox.setText("Welcome to Connect 4 " + people.get(0).getName() + " and " + people.get(1).getName() + "!");

				player = (int) Math.round(Math.random());

				infoBox.setDisable(true);

				infoBox.setText("Press start button to proceed!");
				startBtn.setVisible(true);
				startBtn.setDisable(false);

			}

		});
	}

	public void setupStartScene() {
		startBtn = new Button("Start Game");
		startBtn.setVisible(false);
		startBtn.setDisable(true);

		infoBox = new TextField();
		infoBox.setStyle("-fx-font-size: 18;" + "-fx-border-color: purple;");
		infoBox.setText("Enter Player One's Name");
		infoBox.setAlignment(Pos.BASELINE_CENTER);

		Image image1 = new Image("c4.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);

		startBtn.setTextFill(Color.BLUE);
		startBorder = new BorderPane();

		startBorder.setBackground(new Background(new BackgroundImage(image1,
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				bSize)));

		startBorder.setCenter(startBtn);
		BorderPane.setMargin(startBtn, new Insets(250, 12, 12, 350));
		startBtn.setStyle("-fx-background-radius: 5em; " +
				"-fx-min-width: 100px; " +
				"-fx-min-height: 100px; " +
				"-fx-max-width: 100px; " +
				"-fx-max-height: 100px; " +
				"-fx-background-color: yellow;" +
				"-fx-background-insets: 0px; " +
				"-fx-padding: 0px;"
		);

		startBtn.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
		startBtn.setTextFill(Color.RED);

		startBorder.setTop(infoBox);
		startScene = new Scene(startBorder, 700, 700);
	}

	public void setupEndScene(Stage primaryStage) { //endgame scene, displays who won as well as options to exit or restart
		endBorder = new BorderPane();

		endName = new Label();
		if(tieGame == false){
			endName.setText("Congratulations! " + people.get(player).getName() + " has won!");
		}
		else{
			endName.setText("The game has ended in a tie!");
			people.get(0).updateScore();
			people.get(1).updateScore();

		}

		endName.setFont(Font.font("Helvetica", FontWeight.BOLD,FontPosture.ITALIC, 24));
		endName.setTextFill(Color.web("#cc3399"));

		endExit = new Button("Exit Game");
		endExit.setOnAction(e -> {
			Platform.exit();
		});
		endExit.setStyle("-fx-background-radius: 5em; " +
				"-fx-min-width: 100px; " +
				"-fx-min-height: 100px; " +
				"-fx-max-width: 100px; " +
				"-fx-max-height: 100px; " +
				"-fx-background-color: pink;" +
				"-fx-background-insets: 0px; " +
				"-fx-padding: 0px;"
		);
		endExit.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
		endExit.setTextFill(Color.web("#cc3399"));


		endRestart = new Button("Restart Game");

		endRestart.setOnAction(e -> {

			while (gameStack.size() > 0) {
				Pair<Integer, Integer> pair = gameStack.pop(); //Grab the element at the top to use for current Game Buttons
				gameBtns[pair.getKey()][pair.getValue()].setPlayerOnBtn(-1);
				gameBtns[pair.getKey()][pair.getValue()].setDisable(false);
				gameBtns[pair.getKey()][pair.getValue()].setGraphic(null);
			}

			for(int i = 0; i < gameBtns.length; i++){
				for(int j = 0; j < gameBtns[i].length; j++){
					gameBtns[i][j].setEffect(null);
					gameBtns[i][j].setDisable(false);
				}
			}

			printData();
			player = (int) Math.round(Math.random());
			lblPlayerMove.setText("New game, it's " + people.get(player).getName() + "'s (" + people.get(player).getColorString() + ") turn");
			menuBar.setDisable(false);
			primaryStage.setScene(gameScene);

		});

		endRestart.setStyle("-fx-background-radius: 5em; " +
				"-fx-min-width: 100px; " +
				"-fx-min-height: 100px; " +
				"-fx-max-width: 100px; " +
				"-fx-max-height: 100px; " +
				"-fx-background-color: pink;" +
				"-fx-background-insets: 0px; " +
				"-fx-padding: 0px;"
		);
		endRestart.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
		endRestart.setTextFill(Color.web("#cc3399"));

		Image image1 = new Image("confetti.png");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);

		endBorder.setBackground(new Background(new BackgroundImage(image1,
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				bSize)));
		BorderPane.setMargin(endExit, new Insets(550, 12, 12, 12));
		BorderPane.setMargin(endRestart, new Insets(550, 12, 12, 12));

		endBorder.setCenter(endName);
		endBorder.setLeft(endExit);
		endBorder.setRight(endRestart);
		endScene = new Scene(endBorder, 700, 700);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to Connect 4!");
		primaryStage.getIcons().add(new Image("RedChip.png"));

		primaryStage.setResizable(false);
		menuBarSetup();

		setupStartScene();

		primaryStage.setScene(startScene);
		primaryStage.show();

		startBtn.setOnAction(e -> { //Handle function which does tasks that button 1 should do on click
			setupChips();

			gameBorder = new BorderPane();
			gamePane = new GridPane();

			lblExtraInfo = new Label();
			lblExtraInfo.setTextAlignment(TextAlignment.JUSTIFY);

			lblPlayerMove = new Label();
			lblPlayerMove.setText("It is " + people.get(player).getName() + "'s (" + people.get(player).getColorString() + ") turn");

			gameBorder.setBottom(lblExtraInfo);

			Image c4path = new Image("ConnectBoard.jpg");
			ImageView c4View = new ImageView(c4path);
			c4View.setX(100);
			c4View.setY(200);
			c4View.setFitHeight(455);
			c4View.setFitWidth(495);
			c4View.setPreserveRatio(true);

			establishGridPane();

			gamePane.setHgap(24);
			gamePane.setVgap(5);

			gameBorder.setTop(menuBar);
			gameBorder.setCenter(gamePane);
			gameBorder.getCenter().toBack();

			gameBorder.setCenter(c4View);
			c4View.toBack();

			BorderPane.setAlignment(lblExtraInfo, Pos.CENTER);
			gameBorder.setBottom(lblExtraInfo);

			//player move info text coloring and positioning
			BorderPane.setAlignment(lblPlayerMove, Pos.TOP_CENTER);
			BorderPane.setMargin(lblPlayerMove, new Insets(110,12,12,12));
			gameBorder.setCenter(lblPlayerMove);
			lblPlayerMove.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
			lblPlayerMove.setTextFill(Color.web("#4da6ff"));

			//instructions text coloring
			lblExtraInfo.setFont(Font.font("Helvetica", FontPosture.ITALIC, 12));
			lblExtraInfo.setTextFill(Color.web("#0059b3"));

			lblPlayerMove.setTextFill(Color.web("#4da6ff"));
			lblExtraInfo.setTextFill(Color.web("#0059b3"));
			gameBorder.setBackground(new Background(new BackgroundFill(Color.CORNSILK, CornerRadii.EMPTY, Insets.EMPTY)));

			gamePane.relocate(100,200);
			gameScene = new Scene(gameBorder, 700, 700);

			primaryStage.setScene(gameScene);
			primaryStage.setTitle("Game Board");

		});

		infoBoxNameEntered();

		pause.setOnFinished(pause -> //briefly pauses game
		{
			if(gameOver == true){
				people.get(player).updateScore();
				gameOver = false;
				setupEndScene(primaryStage);
				primaryStage.setScene(endScene);
				primaryStage.setTitle("Game Finished");
			}
		});

		MenuButtons();
		genericBtnClick();

	}

	public GameButton[][] getGameBtns() {
		return gameBtns;
	}

	public int getPlayer() {
		return player;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean b) {
		gameOver=b;
	}

	public void setTieGame(boolean b) {
		tieGame=b;
	}

	public Label getLblPlayerMove() {
		return lblPlayerMove;
	}

	public PauseTransition getPause() {
		return pause;
	}
}
