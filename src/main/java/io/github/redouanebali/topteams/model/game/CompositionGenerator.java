package io.github.redouanebali.topteams.model.game;

import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.team.Team;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CompositionGenerator {

  public static Composition generateRandomComposition(List<? extends Player> players) {
    if (players.size() % 2 != 0) {
      throw new IllegalArgumentException("number of players should be even, not odd");
    }
    Collections.shuffle(players, new Random());

    Team teamA = new Team(players.subList(0, players.size() / 2));
    Team teamB = new Team(players.subList(players.size() / 2, players.size()));
    return new Composition(teamA, teamB);
  }

}
