package io.github.redouanebali.topteams.model.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Score {

  private final int scoreA;
  private final int scoreB;

  public int getGoalDifference() {
    return scoreA - scoreB;
  }

  @Override
  public String toString() {
    return this.scoreA + "-" + this.scoreB;
  }
}