package com.wumpus.game.navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.wumpus.game.options.BlackHole;
import com.wumpus.game.options.Gold;
import com.wumpus.game.options.Wumpus;

import lombok.Data;

@Data
public class GameBoard {

	private Integer height;
	private Integer width;
	private List<Wumpus> wumpusList = new ArrayList<>();
	private List<Gold> goldList = new ArrayList<>();
	private List<BlackHole> blackHoles = new ArrayList<>();
	private final Random random = new Random();

	public void addWumpus(final Integer units) {
		for (int i = 0; i < units; ++i) {
			final Wumpus wumpus = new Wumpus();
			wumpus.setCoordinate(
					getValidCoord(this.blackHoles.stream().map(BlackHole::getCoordinate).collect(Collectors.toList())));
			wumpus.generateSmells();
			this.wumpusList.add(wumpus);
		}
	}

	public void addGold(final Integer units) {
		for (int i = 0; i < units; ++i) {
			final Gold gold = new Gold();
			final List<Coordinate> wumpusCoords = this.wumpusList.stream().map(Wumpus::getCoordinate)
					.collect(Collectors.toList());
			final List<Coordinate> blackHolesCoords = this.blackHoles.stream().map(BlackHole::getCoordinate)
					.collect(Collectors.toList());
			gold.setCoordinate(getValidCoord(
					Stream.of(wumpusCoords, blackHolesCoords).flatMap(x -> x.stream()).collect(Collectors.toList())));
			this.goldList.add(gold);
		}
	}

	public void addBlackHoles(final Integer blackHoles) {
		for (int i = 0; i < blackHoles; ++i) {
			final BlackHole blackHole = new BlackHole();
			blackHole.setCoordinate(
					new Coordinate(this.random.nextInt(this.width - 1) + 1, random.nextInt(this.height - 1) + 1));
			blackHole.generateSmells();
			this.blackHoles.add(blackHole);
		}
	}

	private Coordinate getCoordinate() {
		return new Coordinate(this.random.nextInt(this.width - 1) + 1, random.nextInt(this.height - 1) + 1);
	}

	private Coordinate getValidCoord(List<Coordinate> coords) {
		Coordinate coordinate = getCoordinate();
		while (coords.contains(coordinate)) {
			coordinate = this.getCoordinate();
		}
		return coordinate;

	}

}
