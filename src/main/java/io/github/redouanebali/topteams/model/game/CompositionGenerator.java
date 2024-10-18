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

  public static Composition generateRandomComposition(List<? extends Player> players, boolean splitBestPlayers, boolean splitWeakesterPlayers) {
    Team         teamA            = new Team();
    Team         teamB            = new Team();
    List<Player> remainingPlayers = new ArrayList<>(players);
    if (splitBestPlayers) {
      players.sort(Collections.reverseOrder());
      teamA.addPlayer(players.get(0));
      teamB.addPlayer(players.get(1));
      remainingPlayers.removeFirst();
      remainingPlayers.removeFirst();
    }
    if (splitWeakesterPlayers) {
      Collections.sort(remainingPlayers);
      teamA.addPlayer(remainingPlayers.get(0));
      teamB.addPlayer(remainingPlayers.get(1));
      remainingPlayers.removeFirst();
      remainingPlayers.removeFirst();
    }
    Collections.shuffle(remainingPlayers, new Random());

    teamA.getPlayers().addAll(remainingPlayers.subList(0, remainingPlayers.size() / 2));
    teamB.getPlayers().addAll(remainingPlayers.subList(remainingPlayers.size() / 2, remainingPlayers.size()));
    return new Composition(teamA, teamB);
  }

  public static List<Composition> generatAllPossibleCompositions(List<? extends Player> players,
                                                                 boolean splitBestPlayers,
                                                                 boolean splitWeakestPlayers) {
    List<Composition> compositions           = new ArrayList<>();
    long              nbPossibleCombinations = getNbPossibleCombinations(players.size(), splitBestPlayers, splitWeakestPlayers);
    int               i                      = 0;
    while (i < nbPossibleCombinations * PERCENTAGE_OF_POSSIBLE_COMPO_TO_GENERATE) {
      Composition randomComposition = generateRandomComposition(players, splitBestPlayers, splitWeakestPlayers);
      if (!compositions.contains(randomComposition)) {
        compositions.add(randomComposition);
        i++;
      }
    }
    return compositions;
  }

  public static List<Composition> getNBestCompositions(List<? extends Player> players,
                                                       int count,
                                                       boolean splitBestPlayers,
                                                       boolean splitWeakestPlayers) {
    List<Composition> compositions = generatAllPossibleCompositions(players, splitBestPlayers, splitWeakestPlayers);
    Collections.sort(compositions);
    if (count > compositions.size()) {
      count = compositions.size() - 1;
    }
    return compositions.subList(0, count);
  }

  public static List<Composition> getNBestCompositionsFromStats(List<DetailedPlayer> players,
                                                                int count,
                                                                boolean splitBestPlayers,
                                                                boolean splitWeakestPlayers) {
    List<Composition> compositions = generatAllPossibleCompositions(players, splitBestPlayers, splitWeakestPlayers);
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

  public static long getNbPossibleCombinations(int n, boolean splitBestPlayers, boolean splitWeakestPlayers) {
    if (n % 2 != 0) {
      throw new IllegalArgumentException("player count should be even.");
    }
    long binomialCoefficient = CombinatoricsUtils.binomialCoefficient(n, n / 2);
    if (splitBestPlayers) {
      binomialCoefficient /= 2;
    }
    if (splitWeakestPlayers) {
      binomialCoefficient /= 2;
    }
    return binomialCoefficient / 2;
  }

}