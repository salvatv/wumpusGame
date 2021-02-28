package com.wumpus.game.services.movement;

import java.util.List;

import com.wumpus.game.constants.MovementConst;
import com.wumpus.game.navigation.GameBoard;
import com.wumpus.game.options.Player;
import com.wumpus.game.options.Wumpus;

public class MovementServiceImpl {

	private static final String YOU_KILLED_THE_WUMPUS = "YOU KILLED THE WUMPUS!";
	private static final String NOT_A_VALID_MOVEMENT = "Not a valid movement";

	public boolean checkLook(final String movement) {
		return movement.toUpperCase().contains(MovementConst.W) || movement.toUpperCase().contains(MovementConst.S)
				|| movement.toUpperCase().contains(MovementConst.A) || movement.toUpperCase().contains(MovementConst.D);
	}

	public void look(final Player player, final String movement) {
		if (movement.toUpperCase().contains(MovementConst.W) || movement.toUpperCase().contains(MovementConst.S)
				|| movement.toUpperCase().contains(MovementConst.A)
				|| movement.toUpperCase().contains(MovementConst.D)) {
			player.setPlayerview(movement.toUpperCase());
		} else {
			System.out.print(NOT_A_VALID_MOVEMENT);
		}
	}

	public void move(final GameBoard gameBoard, final Player player) {
		if (this.movementValid(gameBoard, player)) {
			final String view = player.getPlayerview();
			switch (view.toUpperCase()) {
			case MovementConst.W:
				player.getCoordinate().moveUp();
				break;
			case MovementConst.S:
				player.getCoordinate().moveDown();
				break;
			case MovementConst.A:
				player.getCoordinate().moveLeft();
				break;
			case MovementConst.D:
				player.getCoordinate().moveRight();
				break;
			default:
				System.out.print(NOT_A_VALID_MOVEMENT);
				break;
			}
			this.checkGold(gameBoard, player);

		} else {
			System.out.print(NOT_A_VALID_MOVEMENT);
		}

	}

	private void checkGold(GameBoard gameBoard, Player player) {
		gameBoard.getGoldList().forEach(gold -> {
			if (gold.getCoordinate().equals(player.getCoordinate())) {
				player.setIsRich(gold.getCoordinate().equals(player.getCoordinate()));
				System.out.print("You got the gold, now run to the start position!");
			}
		});
	}

	public boolean movementValid(GameBoard gameBoard, Player player) {
		return !player.getCoordinate().getY().equals(gameBoard.getHeight())
				&& player.getPlayerview().equalsIgnoreCase(MovementConst.W)
				|| player.getCoordinate().getY() == 0 && player.getPlayerview().equalsIgnoreCase(MovementConst.S)
				|| player.getCoordinate().getX() == gameBoard.getWidth()
						&& player.getPlayerview().equalsIgnoreCase(MovementConst.D)
				|| player.getCoordinate().getX() == 0 && player.getPlayerview().equalsIgnoreCase(MovementConst.A);
	}

	public void shoot(Player player, List<Wumpus> wumpusList) {
		final String view = player.getPlayerview();

		switch (view.toUpperCase()) {
		case MovementConst.W:
			wumpusList.forEach(wumpus -> {
				if (player.getCoordinate().getX().equals(wumpus.getCoordinate().getX())
						&& player.getCoordinate().getY() < wumpus.getCoordinate().getY()) {
					wumpus.setIsDead(Boolean.TRUE);
					System.out.print(YOU_KILLED_THE_WUMPUS);
				}
			});
			break;
		case MovementConst.S:
			wumpusList.forEach(wumpus -> {
				if (player.getCoordinate().getX().equals(wumpus.getCoordinate().getX())
						&& player.getCoordinate().getY() > wumpus.getCoordinate().getY()) {
					wumpus.setIsDead(Boolean.TRUE);
					System.out.print(YOU_KILLED_THE_WUMPUS);
				}
			});
			break;
		case MovementConst.A:
			wumpusList.forEach(wumpus -> {
				if (player.getCoordinate().getY().equals(wumpus.getCoordinate().getY())
						&& player.getCoordinate().getX() > wumpus.getCoordinate().getX()) {
					wumpus.setIsDead(Boolean.TRUE);
					System.out.print(YOU_KILLED_THE_WUMPUS);
				}
			});
			break;
		case MovementConst.D:
			wumpusList.forEach(wumpus -> {
				if (player.getCoordinate().getY().equals(wumpus.getCoordinate().getY())
						&& player.getCoordinate().getX() < wumpus.getCoordinate().getX()) {
					wumpus.setIsDead(Boolean.TRUE);
					System.out.print(YOU_KILLED_THE_WUMPUS);
				}
			});
			break;
		default:
			System.out.print(NOT_A_VALID_MOVEMENT);
			break;
		}
		player.subtractArrow();
	}
}
