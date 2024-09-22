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

  // X% of all the possible compositions will be generated
  private final static double PERCENTAGE_OF_POSSIBLE_COMPO_TO_GENERATE = 0.9;

  public static Composition generateRandomComposition(List<? extends Player> players) {
    Collections.shuffle(players, new Random());

    Team teamA = new Team(players.subList(0, players.size() / 2));
    Team teamB = new Team(players.subList(players.size() / 2, players.size()));
    return new Composition(teamA, teamB);
  }

  public static List<Composition> generatAllPossibleCompositions(List<? extends Player> players) {
    List<Composition> compositions           = new ArrayList<>();
    long              nbPossibleCombinations = getNbPossibleCombinations(players.size());
    int               i                      = 0;
    while (i < nbPossibleCombinations * PERCENTAGE_OF_POSSIBLE_COMPO_TO_GENERATE) {
      Composition randomComposition = generateRandomComposition(players);
      if (!compositions.contains(randomComposition)) {
        compositions.add(randomComposition);
        i++;
      }
    }
    return compositions;
  }

  public static List<Composition> getNBestCompositions(List<? extends Player> players, int count) {
    List<Composition> compositions = generatAllPossibleCompositions(players);
    Collections.sort(compositions);
    if (count >= compositions.size()) {
      count = compositions.size() - 1;
    }
    return compositions.subList(0, count);
  }

  public static List<Composition> getNBestCompositionsFromStats(List<DetailedPlayer> players, int count) {
    List<Composition> compositions = generatAllPossibleCompositions(players);
    // priority by standard deviation + rating difference
    List<Composition>
        sortedCompositions =
        compositions.stream().sorted(Comparator.comparingDouble(c -> Math.abs(c.getStatsStandardDeviation()) + Math.abs(c.getRatingDifference())))
                    .toList();
    if (count >= sortedCompositions.size()) {
      count = sortedCompositions.size() - 1;
    }
    return sortedCompositions.subList(0, count);
  }

  public static long getNbPossibleCombinations(int n) {
    if (n % 2 != 0) {
      throw new IllegalArgumentException("player count should be even.");
    }
    long binomialCoefficient = CombinatoricsUtils.binomialCoefficient(n, n / 2);
    return binomialCoefficient / 2;
  }

}
