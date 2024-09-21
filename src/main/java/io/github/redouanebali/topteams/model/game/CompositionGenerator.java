package io.github.redouanebali.topteams.model.game;

import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.player.PlayerCharacteristics;
import io.github.redouanebali.topteams.model.team.Team;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class CompositionGenerator {

  private static final int NB_TRY = 1000;

  public static Composition generateRandomComposition(List<? extends Player> players) {
    if (players.size() % 2 != 0) {
      throw new IllegalArgumentException("number of players should be even, not odd");
    }
    Collections.shuffle(players, new Random());

    Team teamA = new Team(players.subList(0, players.size() / 2));
    Team teamB = new Team(players.subList(players.size() / 2, players.size()));
    return new Composition(teamA, teamB);
  }

  public static Composition getBestComposition(List<? extends Player> players) {
    List<Composition> compositions = new ArrayList<>();
    for (int i = 0; i < NB_TRY; i++) {
      compositions.add(generateRandomComposition(players));
    }
    Collections.sort(compositions);
    return compositions.getFirst();
  }

  public static Composition getBestCompositionFromCharacteristics(List<DetailedPlayer> players) {
    List<Composition> compositions = new ArrayList<>();

    for (int i = 0; i < NB_TRY; i++) {
      Composition composition = generateRandomComposition(players);
      compositions.add(composition);
    }

    return compositions.stream()
                       .min(Comparator.comparingDouble(CompositionGenerator::getCharacteristicStandardDeviation))
                       .orElseThrow(() -> new IllegalStateException("No compositions generated"));
  }

  public static double getCharacteristicStandardDeviation(Composition composition) {
    List<Double> differences = Arrays.stream(PlayerCharacteristics.values())
                                     .map(playerCharacteristics ->
                                              composition.getTeamA().getRating(playerCharacteristics) -
                                              composition.getTeamB().getRating(playerCharacteristics))
                                     .toList();

    double mean = differences.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

    double variance = differences.stream()
                                 .mapToDouble(diff -> Math.pow(diff - mean, 2))
                                 .average().orElse(0.0);

    // Retourner l'écart type
    return Math.sqrt(variance);
  }

}
