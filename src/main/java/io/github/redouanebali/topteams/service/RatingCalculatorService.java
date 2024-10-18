package io.github.redouanebali.topteams.service;

import io.github.redouanebali.topteams.model.game.Game;
import io.github.redouanebali.topteams.model.game.RatingCalculator;
import java.util.Map;

public class RatingCalculatorService {

  public Map<String, Double> getRatingUpdates(Game game, double kf) {
    return RatingCalculator.getRatingUpdates(game, kf);
  }
}
