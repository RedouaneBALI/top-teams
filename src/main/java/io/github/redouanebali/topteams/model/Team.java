package io.github.redouanebali.topteams.model;

import io.github.redouanebali.topteams.model.Player.Player;
import java.util.List;

public class Team {

  List<Player> players;

  public Team(Player... players) {
    this.players = List.of(players);
  }

  public <T extends Player> Team(List<T> players) {
    this.players = List.of(players.toArray(Player[]::new));
  }

  public double getRating() {
    return players.stream().mapToDouble(Player::getRating).average().orElse(0.0);
  }
}
