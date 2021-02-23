package com.wumpus.game.options;

import java.util.ArrayList;
import java.util.List;

import com.wumpus.game.navigation.Coordinate;

import lombok.Data;

@Data
public class Wumpus {
	private Coordinate coordinate;
	private List<Coordinate> smells = new ArrayList<>();
	private Boolean isDead = Boolean.FALSE;

	public void generateSmells() {
		final Coordinate leftSmell = new Coordinate(this.coordinate.getX(), this.coordinate.getY());
		leftSmell.moveLeft();
		this.smells.add(leftSmell);
		final Coordinate rightSmell = new Coordinate(this.coordinate.getX(), this.coordinate.getY());
		rightSmell.moveRight();
		this.smells.add(leftSmell);
		final Coordinate upSmell = new Coordinate(this.coordinate.getX(), this.coordinate.getY());
		upSmell.moveUp();
		this.smells.add(upSmell);
		final Coordinate downSmell = new Coordinate(this.coordinate.getX(), this.coordinate.getY());
		downSmell.moveDown();
		this.smells.add(downSmell);
	}

}
