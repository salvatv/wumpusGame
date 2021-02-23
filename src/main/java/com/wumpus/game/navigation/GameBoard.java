package com.wumpus.game.navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.wumpus.game.options.BlackHole;
import com.wumpus.game.options.Gold;
import com.wumpus.game.options.Wumpus;

import lombok.Data;

@Data
public class GameBoard implements IGameBoard {

	private Integer height;
	private Integer width;
	private Wumpus wumpus;
	private Gold gold;
	private List<BlackHole> blackHoles = new ArrayList<>();

	@Override
	public Integer getHeight() {
		return height;
	}

	@Override
	public Integer getWidth() {
		return width;
	}

	@Override
	public Wumpus getWumpus() {
		return wumpus;
	}

	@Override
	public Gold getGold() {
		return gold;
	}

	@Override
	public List<BlackHole> getBlackHoles() {
		return blackHoles;
	}

	@Override
	public void addBlackHoles(Integer blackHoles) {
		for (int i = 0; i < blackHoles; ++i) {
			final BlackHole blackHole = new BlackHole();
			final Random random = new Random();
			blackHole.setCoordinate(
					new Coordinate(random.nextInt(this.width - 1) + 1, random.nextInt(this.height - 1) + 1));
			blackHole.generateSmells();
			this.blackHoles.add(blackHole);
		}

	}

}
