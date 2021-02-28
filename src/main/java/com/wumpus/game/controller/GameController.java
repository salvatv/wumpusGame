package com.wumpus.game.controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.wumpus.game.navigation.Coordinate;
import com.wumpus.game.navigation.GameBoard;
import com.wumpus.game.options.Player;
import com.wumpus.game.services.movement.MovementServiceImpl;

public class GameController {

	private static final int MIN_HEGITH = 5;
	private static final int MIN_WIDTH = 5;

	public void startGame() {
		System.out.println("********************************************************************");
		System.out.println("Welcome to Hunt thw Wumpus Game, please choose a name:");

		final MovementServiceImpl movementServiceImpl = new MovementServiceImpl();

		final Scanner in = new Scanner(System.in);
		final String name = in.next();

		final Player player = new Player();
		player.setParticipantName(name);
		player.setCoordinate(new Coordinate(0, 0));

		System.out.println("Now, choose how many arrows you want: ");
		final Integer arrows = getInputInt();
		player.setArrows(arrows);

		System.out.println("************");
		final GameBoard gameBoard = new GameBoard();
		System.out.println("Game board height: ");
		final Integer height = getInputIntMin(MIN_HEGITH);
		gameBoard.setHeight(height);

		System.out.println("Game board width: ");
		final Integer width = getInputIntMin(MIN_WIDTH);
		gameBoard.setWidth(width);

		System.out.println("How many black holes you want? ");
		final Integer balckHoles = getInputIntMax(gameBoard.getHeight());
		gameBoard.addBlackHoles(balckHoles);

		gameBoard.addWumpus(1);

		gameBoard.addGold(1);

		checkSmells(player, gameBoard);

		System.out.println("************");
		while (!Boolean.TRUE.equals(player.getIsDead()) && checkEnd(player)) {
			move(movementServiceImpl, in, player, gameBoard);
			checkPlayerDeath(player, gameBoard);
			checkSmells(player, gameBoard);
		}
		System.out.println("GAME OVER");
		in.close();
	}

	private Integer getInputInt() {
		final Scanner in = new Scanner(System.in);
		Integer value = 0;
		try {
			value = in.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Invalid number");
			this.getInputInt();
		}
		return value;
	}

	private Integer getInputIntMin(final Integer minimum) {
		final Integer number = this.getInputInt();
		if (number < minimum) {
			System.out.println("The minimum board size is " + minimum);
			this.getInputIntMin(minimum);
		}
		return number;
	}

	private Integer getInputIntMax(final Integer max) {
		final Integer number = this.getInputInt();
		if (number > max) {
			System.out.println("Too much! Enter another value please ");
			this.getInputIntMax(max);
		}
		return number;
	}

	private void checkSmells(Player player, GameBoard gameBoard) {
		gameBoard.getWumpusList().forEach(wumpus -> {
			wumpus.getSmells().forEach(wumpusSmell -> {
				if (player.getCoordinate().equals(wumpusSmell)) {
					System.out.println("Smells something funny...");
				}
			});
		});
		gameBoard.getBlackHoles().forEach(bH -> {
			bH.getSmells().forEach(bHSmell -> {
				if (player.getCoordinate().equals(bHSmell)) {
					System.out.println("The wind blows strong...");
				}
			});
		});

	}

	private void checkPlayerDeath(Player player, GameBoard gameBoard) {
		gameBoard.getWumpusList().forEach(wumpus -> {
			if (player.getCoordinate().equals(wumpus.getCoordinate())) {
				System.out.println("Wumpus killed you.");
				player.setIsDead(Boolean.TRUE);
			}
		});
		gameBoard.getBlackHoles().forEach(bh -> {
			if (player.getCoordinate().equals(bh.getCoordinate())) {
				System.out.println("You fall into a black hole.");
				player.setIsDead(Boolean.TRUE);
			}
		});

	}

	private void move(final MovementServiceImpl movementServiceImpl, final Scanner in, final Player player,
			final GameBoard gameBoard) {
		System.out.println("Where you want yo go? Press X to shoot an arrow.");
		final String movement = in.next();
		if (movement.equalsIgnoreCase("X")) {
			if (player.getArrows() > 0) {
				movementServiceImpl.shoot(player, gameBoard.getWumpusList());
				System.out.println(player.getArrows() + " arrows left.");
			} else {
				System.out.println("0 arrows left.");
			}
		} else if (movement.equalsIgnoreCase("motherlode")) {
			gameBoard.getWumpusList().forEach(wumpus -> wumpus.setIsDead(Boolean.TRUE));
			System.out.println("Cheats activated");
		} else {
			if (movementServiceImpl.checkLook(movement)) {
				player.setPlayerview(movement);
				System.out.println("Move?");
				final String yesNo = in.next();
				if (yesNo.equalsIgnoreCase("Y")) {
					movementServiceImpl.move(gameBoard, player);
				} else {
					System.out.println("Not a valid movement");
				}
			} else {
				System.out.println("Not a valid movement");
			}

		}
	}

	private boolean checkEnd(final Player player) {
		if (player.getCoordinate().getX() == 0 && player.getCoordinate().getY() == 0
				&& Boolean.TRUE.equals(player.getIsRich())) {
			System.out.println("CONGRATULATIONS, YOU WON!!!");
			return false;
		}
		return true;
	}

}
