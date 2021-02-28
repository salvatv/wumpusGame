package com.wumpus.game.services.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.wumpus.game.constants.MovementConst;
import com.wumpus.game.navigation.Coordinate;
import com.wumpus.game.navigation.GameBoard;
import com.wumpus.game.options.Gold;
import com.wumpus.game.options.Player;
import com.wumpus.game.options.Wumpus;

class MovementServiceImplTest {

	private MovementServiceImpl movementServiceImpl;

	@Mock
	private Player mockedPlayer;

	@BeforeEach
	void setUp() throws Exception {
		movementServiceImpl = new MovementServiceImpl();
		mockedPlayer = Mockito.mock(Player.class);
	}

	@Test
	@DisplayName("Check look test")
	void testValidCheckLook() {
		assertEquals(Boolean.TRUE, movementServiceImpl.checkLook(MovementConst.W), "Should return true");
	}

	@Test
	@DisplayName("Check invalid look test")
	void testInvalidCheckLook() {
		assertEquals(Boolean.FALSE, movementServiceImpl.checkLook("Z"), "Should return false");
	}

	@Test
	@DisplayName("Check player look test")
	void testValidLook() {
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		this.movementServiceImpl.look(mockedPlayer, MovementConst.W);
		Mockito.verify(mockedPlayer).setPlayerview(arg.capture());
	}

	@Test
	@DisplayName("Move up method test")
	void testMoveUp() {
		Mockito.when(this.mockedPlayer.getCoordinate()).thenReturn(new Coordinate(0, 0));
		Mockito.when(this.mockedPlayer.getPlayerview()).thenReturn(MovementConst.W);
		this.movementServiceImpl.move(getGameBoard(), mockedPlayer);
		verify(this.mockedPlayer, times(4)).getCoordinate();
	}

	@Test
	@DisplayName("Shoot test")
	void testShoot() {
		Mockito.when(this.mockedPlayer.getCoordinate()).thenReturn(new Coordinate(1, 0));
		Mockito.when(this.mockedPlayer.getPlayerview()).thenReturn(MovementConst.W);
		this.movementServiceImpl.shoot(mockedPlayer, getGameBoard().getWumpusList());
		verify(this.mockedPlayer, times(1)).subtractArrow();
	}

	private GameBoard getGameBoard() {
		final List<Gold> goldList = new ArrayList<>();
		final List<Wumpus> wumpusList = new ArrayList<>();
		final Gold gold = new Gold();
		gold.setCoordinate(new Coordinate(0, 1));
		goldList.add(gold);
		final Wumpus wumpus = new Wumpus();
		wumpus.setCoordinate(new Coordinate(1, 1));
		wumpusList.add(wumpus);
		final GameBoard gameBoard = new GameBoard();
		gameBoard.setGoldList(goldList);
		gameBoard.setWumpusList(wumpusList);
		gameBoard.setHeight(5);
		gameBoard.setWidth(5);
		return gameBoard;
	}

}
