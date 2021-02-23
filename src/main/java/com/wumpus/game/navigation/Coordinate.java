package com.wumpus.game.navigation;

import lombok.Data;

@Data
public class Coordinate {
	private Integer x;
	private Integer y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void moveUp() {
		this.y = Integer.sum(y, 1);
	}

	public void moveDown() {
		this.y = Integer.sum(y, -1);
	}

	public void moveLeft() {
		this.x = Integer.sum(x, -1);
	}

	public void moveRight() {
		this.x = Integer.sum(x, 1);
	}

}