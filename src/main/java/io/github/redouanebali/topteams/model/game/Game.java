package io.github.redouanebali.topteams.model.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Game {

  private Composition composition;
  private Score       score;

  /**
   * @param kf the factor
   * @return the différence between the prediction and the observed result using a factor
   */
  public double getPredictionError(double kf) {
    return this.score.getGoalDifference() - this.composition.getPrediction(kf);
  }

  @Override
  public String toString() {
    return this.score + "\n" + this.composition;
  }

}