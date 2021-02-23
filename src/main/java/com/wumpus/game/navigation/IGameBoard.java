package com.wumpus.game.navigation;

import java.util.List;

import com.wumpus.game.options.BlackHole;
import com.wumpus.game.options.Gold;
import com.wumpus.game.options.Wumpus;

public interface IGameBoard {
	Integer getHeight();

	Integer getWidth();

	Wumpus getWumpus();

	Gold getGold();

	List<BlackHole> getBlackHoles();

	void addBlackHoles(Integer blackHoles);

}
