package io.github.redouanebali.topteams.model.team;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.player.PlayerStats;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Team {

  @Getter
  private Set<Player> players = new HashSet<>();

  public Team(Player... players) {
    this.players = new HashSet<>(Set.of(players));
  }

  public <T extends Player> Team(List<T> players) {
    this.players = new HashSet<>(Set.of(players.toArray(Player[]::new)));
  }

  public double getRating() {
    return BigDecimal.valueOf(players.stream().mapToDouble(Player::getRating).average().orElse(0.0))
                     .setScale(1, RoundingMode.HALF_UP)
                     .doubleValue();
  }

  public double getRating(PlayerStats stats) {
    if (this.isDetailedPlayersTeam()) {
      return players.stream()
                    .map(p -> (DetailedPlayer) p)
                    .mapToDouble(p -> p.getStats().getOrDefault(stats, 0.0))
                    .average()
                    .orElse(0.0);
    }
    return 0.0;
  }

  @JsonIgnore
  public boolean isDetailedPlayersTeam() {
    return players.stream().allMatch(DetailedPlayer.class::isInstance);
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
    StringBuilder bld = new StringBuilder();
    for (Player p : players) {
      bld.append(p);
      bld.append(" ");
    }
    return bld.toString();
  }

  public void addPlayer(Player player) {
    this.players.add(player);
  }
}
