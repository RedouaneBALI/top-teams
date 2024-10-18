package io.github.redouanebali.topteams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.game.Game;
import io.github.redouanebali.topteams.model.game.RatingCalculator;
import io.github.redouanebali.topteams.model.game.Score;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.team.Team;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.jupiter.api.Test;

public class RatingCalculatorTests {


  public Composition getBalancedComposition() {
    Team teamA = new Team(new Player("PlayerA", 50), new Player("PlayerB", 60), new Player("PlayerC", 70));
    Team teamB = new Team(new Player("PlayerD", 50), new Player("PlayerE", 60), new Player("PlayerF", 70));
    return new Composition(teamA, teamB);
  }

  public Composition getUnalancedComposition() {
    Team teamA = new Team(new Player("PlayerA", 50), new Player("PlayerB", 60), new Player("PlayerC", 70));
    Team teamB = new Team(new Player("PlayerD", 60), new Player("PlayerE", 70), new Player("PlayerF", 80));
    return new Composition(teamA, teamB);
  }

  @Test
  public void testPredictionDraw() {
    Composition composition = getBalancedComposition();
    assertEquals(0, composition.getPrediction(1));
    assertEquals(0, composition.getPrediction(10));
  }

  @Test
  public void testPrediction() {
    Composition composition = getUnalancedComposition();
    assertEquals(-10, composition.getPrediction(1));
    assertTrue(composition.getPrediction(1) < composition.getPrediction(10));
  }

  @Test
  public void testRatingUpdatesSameLevelTeamsAndDraw() {
    Composition         composition = getBalancedComposition();
    Score               score       = new Score(10, 10);
    Game                game        = new Game(composition, score);
    Map<String, Double> result      = RatingCalculator.getRatingUpdates(game, 1);
    for (Entry<String, Double> x : result.entrySet()) {
      assertEquals(0, x.getValue(), 0.01);
    }
  }

  @Test
  public void testRatingUpdatesDifferentLevelTeamsAndDraw() {
    Composition         composition = getUnalancedComposition();
    Score               score       = new Score(10, 10);
    Game                game        = new Game(composition, score);
    Map<String, Double> result      = RatingCalculator.getRatingUpdates(game, 1);
    for (Entry<String, Double> x : result.entrySet()) {
      assertEquals(10 / (double) 6, Math.abs(x.getValue()), 0.01);
    }
  }
}
