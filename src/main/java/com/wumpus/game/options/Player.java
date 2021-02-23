package com.wumpus.game.options;

import com.wumpus.game.navigation.Coordinate;

import lombok.Data;

@Data
public class Player {
	private String participantName;
	private Integer arrows;
	private String playerview;
	private Coordinate coordinate;
	private Boolean isDead = Boolean.FALSE;
	private Boolean isRich = Boolean.FALSE;

	public void subtractArrow() {
		this.arrows = Integer.sum(this.arrows, -1);
	}
}
