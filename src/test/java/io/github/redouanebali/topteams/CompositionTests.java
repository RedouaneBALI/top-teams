package io.github.redouanebali.topteams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.player.PlayerStats;
import io.github.redouanebali.topteams.model.team.Team;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CompositionTests {

  private Composition composition;
  private Team        teamA;
  private Team        teamB;

  @BeforeEach
  public void setUp() {
    teamA       = new Team();
    teamB       = new Team();
    composition = new Composition(teamA, teamB);
  }

  @Test
  public void testStandardDeviation_NoDifference_ShouldReturnZero() {
    DetailedPlayer playerA1 = createDetailedPlayer("playerA1", 80.0, 85.0, 90.0);
    DetailedPlayer playerA2 = createDetailedPlayer("playerA2", 80.0, 85.0, 90.0);
    DetailedPlayer playerB1 = createDetailedPlayer("playerB1", 80.0, 85.0, 90.0);
    DetailedPlayer playerB2 = createDetailedPlayer("playerA2", 80.0, 85.0, 90.0);

    teamA.addPlayer(playerA1);
    teamA.addPlayer(playerA2);
    teamB.addPlayer(playerB1);
    teamB.addPlayer(playerB2);

    assertEquals(0.0, composition.getStatsStandardDeviation(), 0.01);
  }

  @Test
  public void testStandardDeviation_WithDifferences_ShouldReturnCorrectValue() {
    DetailedPlayer playerA1 = createDetailedPlayer("playerA1", 90.0, 80.0, 85.0);
    DetailedPlayer playerA2 = createDetailedPlayer("playerA2", 85.0, 88.0, 90.0);
    DetailedPlayer playerB1 = createDetailedPlayer("playerB1", 80.0, 75.0, 78.0);
    DetailedPlayer playerB2 = createDetailedPlayer("playerB2", 82.0, 80.0, 81.0);

    teamA.addPlayer(playerA1);
    teamA.addPlayer(playerA2);
    teamB.addPlayer(playerB1);
    teamB.addPlayer(playerB2);

    double expectedStandardDeviation = calculateExpectedStandardDeviation();
    assertEquals(expectedStandardDeviation, composition.getStatsStandardDeviation(), 0.1);
  }

  @Test
  public void testComparable() {
    Composition       composition1 = new Composition(new Team(new Player("A", 60)), new Team(new Player("B", 60)));
    Composition       composition2 = new Composition(new Team(new Player("A", 60)), new Team(new Player("B", 65)));
    Composition       composition3 = new Composition(new Team(new Player("A", 60)), new Team(new Player("B", 75)));
    Composition       composition4 = new Composition(new Team(new Player("A", 59)), new Team(new Player("B", 60)));
    Composition       composition5 = new Composition(new Team(new Player("A", 49)), new Team(new Player("B", 60)));
    List<Composition> compositions = Arrays.asList(composition1, composition2, composition3, composition4, composition5);
    Collections.shuffle(compositions);
    Collections.sort(compositions);
    assertTrue(Math.abs(compositions.get(0).getRatingDifference()) < Math.abs(compositions.get(1).getRatingDifference())
               && Math.abs(compositions.get(1).getRatingDifference()) < Math.abs(compositions.get(2).getRatingDifference())
               && Math.abs(compositions.get(2).getRatingDifference()) < Math.abs(compositions.get(3).getRatingDifference())
               && Math.abs(compositions.get(3).getRatingDifference()) < Math.abs(compositions.get(4).getRatingDifference()));
  }

  private DetailedPlayer createDetailedPlayer(String id, Double... stats) {
    Map<PlayerStats, Double> playerStats = new HashMap<>();
    PlayerStats[]            statsKeys   = PlayerStats.values();

    for (int i = 0; i < stats.length; i++) {
      playerStats.put(statsKeys[i], stats[i]);
    }

    return new DetailedPlayer(id, playerStats);
  }


  private double calculateExpectedStandardDeviation() {
    double[]
        differences =
        {(90.0 + 85.0) / 2 - (80.0 + 82.0) / 2, (80.0 + 88.0) / 2 - (75.0 + 80.0) / 2, (85.0 + 90.0) / 2 - (78.0 + 81.0) / 2};
    DescriptiveStatistics stats = new DescriptiveStatistics();
    for (double diff : differences) {
      stats.addValue(diff);
    }
    return stats.getStandardDeviation();
  }

}
