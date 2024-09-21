package io.github.redouanebali.topteams.model.team;

import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.player.PlayerCharacteristics;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;

public class Team {

  @Getter
  private final Set<Player> players;

  public Team(Player... players) {
    this.players = new HashSet<>(Set.of(players));
  }

  public <T extends Player> Team(List<T> players) {
    this.players = new HashSet<>(Set.of(players.toArray(Player[]::new)));
  }

  public double getRating() {
    return players.stream().mapToDouble(Player::getRating).average().orElse(0.0);
  }

  public double getRating(PlayerCharacteristics characteristics) {
    if (this.isDetailedPlayersTeam()) {
      return players.stream()
                    .map(p -> (DetailedPlayer) p)
                    .mapToDouble(p -> p.getCharacteristics().getOrDefault(characteristics, 0.0))
                    .average()
                    .orElse(0.0);
    }
    return 0.0;
  }

  public boolean isDetailedPlayersTeam() {
    return players.stream().allMatch(p -> p instanceof DetailedPlayer);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Team        team         = (Team) o;
    Set<Player> thisPlayers  = new HashSet<>(this.players);
    Set<Player> otherPlayers = new HashSet<>(team.players);
    return Objects.equals(thisPlayers, otherPlayers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(players);
  }

  @Override
  public String toString() {
    String result = "";
    for (Player p : players) {
      result += p;
      result += " ";
    }
    return result;
  }

  public void addPlayer(Player player) {
    this.players.add(player);
  }
}
