package com.wumpus.game.services.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.wumpus.game.navigation.Coordinate;
import com.wumpus.game.navigation.GameBoard;
import com.wumpus.game.options.Gold;
import com.wumpus.game.options.Player;
import com.wumpus.game.options.Wumpus;

//TODO: Continue with tests
public class MovementServiceImplTest {

	private MovementServiceImpl movementServiceImpl;

	@Mock
	private Player mockedPlayer;

	@BeforeEach
	public void setUp() throws Exception {
		movementServiceImpl = new MovementServiceImpl();
		mockedPlayer = Mockito.mock(Player.class);
	}

	@Test
	@DisplayName("Check look test")
	public void testValidCheckLook() {
		assertEquals(Boolean.TRUE, movementServiceImpl.checkLook("W"), "Should return true");
	}

	@Test
	@DisplayName("Check invalid look test")
	public void testInvalidCheckLook() {
		assertEquals(Boolean.FALSE, movementServiceImpl.checkLook("Z"), "Should return false");
	}

	@Test
	@DisplayName("Check player look test")
	public void testValidLook() {
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
		this.movementServiceImpl.look(mockedPlayer, "W");
		Mockito.verify(mockedPlayer).setPlayerview(arg.capture());
	}

	@Test
	@DisplayName("Move up method test")
	public void testMoveUp() {
		Mockito.when(this.mockedPlayer.getCoordinate()).thenReturn(new Coordinate(0, 0));
		Mockito.when(this.mockedPlayer.getPlayerview()).thenReturn("W");
		this.movementServiceImpl.move(getGameBoard(), mockedPlayer);
		verify(this.mockedPlayer, times(4)).getCoordinate();
	}

	private GameBoard getGameBoard() {
		final Gold gold = new Gold();
		gold.setCoordinate(new Coordinate(0, 1));
		final Wumpus wumpus = new Wumpus();
		wumpus.setCoordinate(new Coordinate(1, 1));
		final GameBoard gameBoard = new GameBoard();
		gameBoard.setGold(gold);
		gameBoard.setWumpus(wumpus);
		gameBoard.setHeight(5);
		gameBoard.setWidth(5);
		return gameBoard;
	}

}
