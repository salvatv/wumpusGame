package com.wumpus.game.services.movement;

import com.wumpus.game.navigation.GameBoard;
import com.wumpus.game.options.Player;
import com.wumpus.game.options.Wumpus;

public class MovementServiceImpl {

	private static final String YOU_KILLED_THE_WUMPUS = "YOU KILLED THE WUMPUS!";
	private static final String NOT_A_VALID_MOVEMENT = "Not a valid movement";

	public boolean checkLook(final String movement) {
		return movement.toUpperCase().contains("W") || movement.toUpperCase().contains("S")
				|| movement.toUpperCase().contains("A") || movement.toUpperCase().contains("D");
	}

	public void look(final Player player, final String movement) {
		if (movement.toUpperCase().contains("W") || movement.toUpperCase().contains("S")
				|| movement.toUpperCase().contains("A") || movement.toUpperCase().contains("D")) {
			player.setPlayerview(movement.toUpperCase());
		} else {
			System.out.print(NOT_A_VALID_MOVEMENT);
		}
	}

	public void move(final GameBoard gameBoard, final Player player) {
		if (this.movementValid(gameBoard, player)) {
			final String view = player.getPlayerview();
			switch (view.toUpperCase()) {
			case "W":
				player.getCoordinate().moveUp();
				break;
			case "S":
				player.getCoordinate().moveDown();
				break;
			case "A":
				player.getCoordinate().moveLeft();
				break;
			case "D":
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
		if (gameBoard.getGold().getCoordinate().equals(player.getCoordinate())) {
			player.setIsRich(gameBoard.getGold().equals(player.getCoordinate()));
			System.out.print("You got the gold, now run to the start position!");
		}
	}

	public boolean movementValid(GameBoard gameBoard, Player player) {
		return !player.getCoordinate().getY().equals(gameBoard.getHeight())
				&& player.getPlayerview().equalsIgnoreCase("W")
				|| player.getCoordinate().getY() == 0 && player.getPlayerview().equalsIgnoreCase("S")
				|| player.getCoordinate().getX() == gameBoard.getWidth() && player.getPlayerview().equalsIgnoreCase("D")
				|| player.getCoordinate().getX() == 0 && player.getPlayerview().equalsIgnoreCase("A");
	}

	public void shoot(Player player, Wumpus wumpus) {
		final String view = player.getPlayerview();

		switch (view.toUpperCase()) {
		case "W":
			if (player.getCoordinate().getX().equals(wumpus.getCoordinate().getX())
					&& player.getCoordinate().getY() < wumpus.getCoordinate().getY()) {
				wumpus.setIsDead(Boolean.TRUE);
				System.out.print(YOU_KILLED_THE_WUMPUS);
			}
			break;
		case "S":
			if (player.getCoordinate().getX().equals(wumpus.getCoordinate().getX())
					&& player.getCoordinate().getY() > wumpus.getCoordinate().getY()) {
				wumpus.setIsDead(Boolean.TRUE);
				System.out.print(YOU_KILLED_THE_WUMPUS);
			}
			break;
		case "A":
			if (player.getCoordinate().getY().equals(wumpus.getCoordinate().getY())
					&& player.getCoordinate().getX() > wumpus.getCoordinate().getX()) {
				wumpus.setIsDead(Boolean.TRUE);
				System.out.print(YOU_KILLED_THE_WUMPUS);
			}
			break;
		case "D":
			if (player.getCoordinate().getY().equals(wumpus.getCoordinate().getY())
					&& player.getCoordinate().getX() < wumpus.getCoordinate().getX()) {
				wumpus.setIsDead(Boolean.TRUE);
				System.out.print(YOU_KILLED_THE_WUMPUS);
			}
			break;
		default:
			System.out.print(NOT_A_VALID_MOVEMENT);
			break;
		}
		player.subtractArrow();
	}
}
