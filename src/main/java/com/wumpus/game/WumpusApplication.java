package com.wumpus.game;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wumpus.game.controller.GameController;

@SpringBootApplication
public class WumpusApplication {

	public static void main(String[] args) {
		startApp();
	}

	private static void startApp() {
		final GameController gameController = new GameController();
		gameController.startGame();
	}

}