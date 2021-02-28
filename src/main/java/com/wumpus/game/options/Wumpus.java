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
		final Coordinate leftSmell = new Coordinate(Integer.sum(this.coordinate.getX(), -1), this.coordinate.getY());
		this.smells.add(leftSmell);
		final Coordinate rightSmell = new Coordinate(Integer.sum(this.coordinate.getX(), 1), this.coordinate.getY());
		this.smells.add(rightSmell);
		final Coordinate upSmell = new Coordinate(this.coordinate.getX(), Integer.sum(this.coordinate.getY(), 1));
		this.smells.add(upSmell);
		final Coordinate downSmell = new Coordinate(this.coordinate.getX(), Integer.sum(this.coordinate.getY(), -1));
		this.smells.add(downSmell);
	}

}
