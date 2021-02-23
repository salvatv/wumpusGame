package com.wumpus.game;

import java.util.Random;
import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wumpus.game.navigation.Coordinate;
import com.wumpus.game.navigation.GameBoard;
import com.wumpus.game.options.Gold;
import com.wumpus.game.options.Player;
import com.wumpus.game.options.Wumpus;
import com.wumpus.game.services.movement.MovementServiceImpl;

@SpringBootApplication
public class WumpusApplication {

	public static void main(String[] args) {
		startApp();
	}

	private static void startApp() {
		System.out.println("********************************************************************");
		System.out.println("Welcome to Hunt thw Wumpus Game, please choose a name:");

		final MovementServiceImpl movementServiceImpl = new MovementServiceImpl();

		final Scanner in = new Scanner(System.in);
		final String name = in.next();

		final Player player = new Player();
		player.setParticipantName(name);
		player.setCoordinate(new Coordinate(0, 0));

		System.out.println("Now, choose how many arrows you want: ");
		final Integer arrows = in.nextInt();
		player.setArrows(arrows);

		System.out.println("************");
		final GameBoard gameBoard = new GameBoard();
		System.out.println("Game board height: ");
		final Integer height = in.nextInt();
		gameBoard.setHeight(height);

		System.out.println("Game board width: ");
		final Integer width = in.nextInt();
		gameBoard.setWidth(width);

		System.out.println("How many black holes you want? ");
		final Integer balckHoles = in.nextInt();
		// TODO: Check wumpus and gold not be the same position as the black holes
		gameBoard.addBlackHoles(balckHoles);

		generateWumpus(gameBoard);

		generateGold(gameBoard);

		checkSmells(player, gameBoard);

		System.out.println("************");
		while (!Boolean.TRUE.equals(player.getIsDead()) && checkEnd(player)) {
			move(movementServiceImpl, in, player, gameBoard);
			checkPlayerDeath(player, gameBoard);
			checkSmells(player, gameBoard);
		}
		System.out.println("GAME OVER");
	}

	private static void checkSmells(Player player, GameBoard gameBoard) {
		gameBoard.getWumpus().getSmells().forEach(wumpusSmell -> {
			if (player.getCoordinate().equals(wumpusSmell)) {
				// TODO: Create class for constants
				System.out.println("Smells something funny...");
			}
		});
		gameBoard.getBlackHoles().forEach(bH -> {
			bH.getSmells().forEach(bHSmell -> {
				if (player.getCoordinate().equals(bHSmell)) {
					System.out.println("The wind blows strong...");
				}
			});
		});

	}

	private static void checkPlayerDeath(Player player, GameBoard gameBoard) {
		if (player.getCoordinate().equals(gameBoard.getWumpus().getCoordinate())) {
			System.out.println("Wumpus killed you.");
			player.setIsDead(Boolean.TRUE);
		}
		gameBoard.getBlackHoles().forEach(bh -> {
			if (player.getCoordinate().equals(bh.getCoordinate())) {
				System.out.println("You fall into a black hole.");
				player.setIsDead(Boolean.TRUE);
			}
		});

	}

	// TODO: Check wumpus and gold not be the same position
	private static void generateWumpus(final GameBoard gameBoard) {
		final Wumpus wumpus = new Wumpus();
		final Random random = new Random();
		wumpus.setCoordinate(new Coordinate(random.nextInt(gameBoard.getWidth() - 1) + 1,
				random.nextInt(gameBoard.getHeight() - 1) + 1));
		wumpus.generateSmells();
		gameBoard.setWumpus(wumpus);
	}

	// TODO: Check wumpus and gold not be the same position
	private static void generateGold(final GameBoard gameBoard) {
		final Gold gold = new Gold();
		final Random random = new Random();
		gold.setCoordinate(new Coordinate(random.nextInt(gameBoard.getWidth() - 1) + 1,
				random.nextInt(gameBoard.getHeight() - 1) + 1));
		gameBoard.setGold(gold);
	}

	private static void move(final MovementServiceImpl movementServiceImpl, final Scanner in, final Player player,
			final GameBoard gameBoard) {
		System.out.println("Where you want yo go? Press X to shoot an arrow.");
		final String movement = in.next();
		if (movement.equalsIgnoreCase("X")) {
			if (player.getArrows() > 0) {
				movementServiceImpl.shoot(player, gameBoard.getWumpus());
				System.out.println(player.getArrows() + " arrows left.");
			} else {
				System.out.println("0 arrows left.");
			}
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

	private static boolean checkEnd(final Player player) {
		if (player.getCoordinate().getX() == 0 && player.getCoordinate().getY() == 0
				&& Boolean.TRUE.equals(player.getIsRich())) {
			System.out.println("CONGRATULATIONS, YOU WON!!!");
			return false;
		}
		return true;
	}

}