package io.github.redouanebali.topteams.model.game;

import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.team.Team;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.apache.commons.math3.util.CombinatoricsUtils;

public class CompositionGenerator {

  public static Composition generateRandomComposition(List<? extends Player> players) {
    Collections.shuffle(players, new Random());

    Team teamA = new Team(players.subList(0, players.size() / 2));
    Team teamB = new Team(players.subList(players.size() / 2, players.size()));
    return new Composition(teamA, teamB);
  }

  public static Composition getBestComposition(List<? extends Player> players) {
    List<Composition> compositions = new ArrayList<>();
    long              nbTry        = getNbPossibleCombinations(players.size());
    for (int i = 0; i < nbTry; i++) {
      Composition randomComposition = generateRandomComposition(players);
      if (!compositions.contains(randomComposition)) {
        compositions.add(randomComposition);
      }
    }
    Collections.sort(compositions);
    return compositions.getFirst();
  }

  public static Composition getBestCompositionFromStats(List<DetailedPlayer> players) {
    List<Composition> compositions           = new ArrayList<>();
    long              nbPossibleCombinations = getNbPossibleCombinations(players.size());
    int               i                      = 0;
    while (i < nbPossibleCombinations / 2) {
      Composition randomComposition = generateRandomComposition(players);
      if (!compositions.contains(randomComposition)) {
        compositions.add(randomComposition);
        i++;
      }
    }
    // priority by standard deviation + rating difference
    return compositions.stream()
                       .min(Comparator.comparingDouble(c -> Math.abs(c.getStatsStandardDeviation()) + Math.abs(c.getRatingDifference())))
                       .orElseThrow(() -> new IllegalStateException("No compositions generated"));
  }

  public static long getNbPossibleCombinations(int N) {
    if (N % 2 != 0) {
      throw new IllegalArgumentException("player count should be even.");
    }
    long binomialCoefficient = CombinatoricsUtils.binomialCoefficient(N, N / 2);
    return binomialCoefficient / 2;
  }

}
