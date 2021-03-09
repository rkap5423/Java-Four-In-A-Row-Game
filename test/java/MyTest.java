import static org.junit.jupiter.api.Assertions.*;

import com.sun.glass.ui.Application;
import com.sun.javafx.application.PlatformImpl;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

class MyTest{
	private static GameButton[][] btns = new GameButton[6][7];
	private JavaFXTemplate temp=new JavaFXTemplate();
	private GameLogic gamelogic = new GameLogic(temp);

	@BeforeAll
	static void setup() {
		PlatformImpl.startup(() -> {});
		for (int i = 0; i < btns.length; i++){
			for(int j = 0; j < btns[0].length; j++){
				btns[i][j] = new GameButton(-1);
			}
		}


	}
	@Test
	void testInitialization() { //test if board was initialized properly as specificed in setup
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		assertEquals(-1,btns[5][5].getPlayerOnBtn());
	}

	@Test
	void testSet() { //test if chip's player can be changed
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		btns[1][1].setPlayerOnBtn(1);
		btns[1][2].setPlayerOnBtn(1);
		btns[1][3].setPlayerOnBtn(1);
		btns[1][4].setPlayerOnBtn(1);
		btns[1][5].setPlayerOnBtn(1);
		assertEquals(1,btns[1][3].getPlayerOnBtn());
	}

	@Test
	void testHorizontal() {//check if horizontal win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[5][0].setPlayerOnBtn(1);
		btns[5][1].setPlayerOnBtn(1);
		btns[5][2].setPlayerOnBtn(1);
		btns[5][3].setPlayerOnBtn(1);
		btns[5][4].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(4,gamelogic.checkHorizontal(5,2));
	}

	@Test
	void testRight() {//check if right win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[5][0].setPlayerOnBtn(1);
		btns[5][1].setPlayerOnBtn(1);
		btns[5][2].setPlayerOnBtn(1);
		btns[5][3].setPlayerOnBtn(1);
		btns[5][4].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(3,gamelogic.checkRight(5,2));
	}
	@Test
	void testLeft() {//check if left win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[5][0].setPlayerOnBtn(1);
		btns[5][1].setPlayerOnBtn(1);
		btns[5][2].setPlayerOnBtn(1);
		btns[5][3].setPlayerOnBtn(1);
		btns[5][4].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(5,gamelogic.checkLeft(5,4));
	}
	@Test
	void testVertical() {//check if vertical win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[1][0].setPlayerOnBtn(1);
		btns[2][0].setPlayerOnBtn(1);
		btns[3][0].setPlayerOnBtn(1);
		btns[4][0].setPlayerOnBtn(1);
		btns[5][0].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(5,gamelogic.checkVertical(3,0));
	}
	@Test
	void testUp() {//check if up win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[1][0].setPlayerOnBtn(1);
		btns[2][0].setPlayerOnBtn(1);
		btns[3][0].setPlayerOnBtn(1);
		btns[4][0].setPlayerOnBtn(1);
		btns[5][0].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(4,gamelogic.checkUp(3,0));
	}
	@Test
	void testDown() {//check if down win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[1][0].setPlayerOnBtn(1);
		btns[2][0].setPlayerOnBtn(1);
		btns[3][0].setPlayerOnBtn(1);
		btns[4][0].setPlayerOnBtn(1);
		btns[5][0].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(4,gamelogic.checkDown(2,0));
	}
	@Test
	void testDiagonalPositive() {//check if diagonal-positive win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[5][0].setPlayerOnBtn(1);
		btns[4][1].setPlayerOnBtn(1);
		btns[3][2].setPlayerOnBtn(1);
		btns[2][3].setPlayerOnBtn(1);
		btns[1][4].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(4,gamelogic.checkDiagonalPositive(4,1));
	}
	@Test
	void testUpRight() {//check if up-right win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[5][0].setPlayerOnBtn(1);
		btns[4][1].setPlayerOnBtn(1);
		btns[3][2].setPlayerOnBtn(1);
		btns[2][3].setPlayerOnBtn(1);
		btns[1][4].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(4,gamelogic.checkUpRight(4,1));
	}
	@Test
	void testDownLeft() {//check if down-left win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[5][0].setPlayerOnBtn(1);
		btns[4][1].setPlayerOnBtn(1);
		btns[3][2].setPlayerOnBtn(1);
		btns[2][3].setPlayerOnBtn(1);
		btns[1][4].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(2,gamelogic.checkDownLeft(4,1));
	}
	@Test
	void testDiagonalNegative() {//check if diagonal-negative win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[0][0].setPlayerOnBtn(1);
		btns[1][1].setPlayerOnBtn(1);
		btns[2][2].setPlayerOnBtn(1);
		btns[3][3].setPlayerOnBtn(1);
		btns[4][4].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(4,gamelogic.checkDiagonalNegative(3,3));
	}
	@Test
	void testUpLeft() {//check if up-left win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[0][0].setPlayerOnBtn(1);
		btns[1][1].setPlayerOnBtn(1);
		btns[2][2].setPlayerOnBtn(1);
		btns[3][3].setPlayerOnBtn(1);
		btns[4][4].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(4,gamelogic.checkUpLeft(3,3));
	}
	@Test
	void testDownRight() {//check if down-right win check works
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[0][0].setPlayerOnBtn(1);
		btns[1][1].setPlayerOnBtn(1);
		btns[2][2].setPlayerOnBtn(1);
		btns[3][3].setPlayerOnBtn(1);
		btns[4][4].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		assertEquals(3,gamelogic.checkDownRight(2,2));
	}

	@Test
	void testWin() {//check if overarching win check works, and if game becomes over
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
		temp.setGameBtns(btns);
		btns[0][0].setPlayerOnBtn(1);
		btns[1][1].setPlayerOnBtn(1);
		btns[2][2].setPlayerOnBtn(1);
		btns[3][3].setPlayerOnBtn(1);
		btns[4][4].setPlayerOnBtn(1);
		ArrayList<Player> tempPeople = new ArrayList<>();
		tempPeople.add(new Player("Hi","Green", 0));
		tempPeople.add(new Player("There","Red", 1));
		temp.setPeople(tempPeople);
		int person=1;
		temp.setPlayer(1);
		gamelogic.checkWin(2,2);
		assertTrue(temp.isGameOver());
	}


}
