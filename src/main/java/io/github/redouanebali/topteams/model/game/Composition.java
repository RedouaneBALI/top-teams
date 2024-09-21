package io.github.redouanebali.topteams.model.game;

import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.team.Team;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Composition implements Comparable<Composition> {

  private final Team teamA;
  private final Team teamB;

  public double getRatingDifference() {
    return (teamA.getRating() - teamB.getRating());
  }

  @Override
  public int compareTo(Composition o) {
    return Double.compare(Math.abs(this.getRatingDifference()), Math.abs(o.getRatingDifference()));
  }

  @Override
  public String toString() {
    StringBuilder s        = new StringBuilder();
    List<Player>  playersA = new ArrayList<>(this.getTeamA().getPlayers());
    playersA.sort(Collections.reverseOrder());
    s.append("TEAM A [").append(new DecimalFormat("##.#").format(this.getTeamA().getRating())).append("]\n");
    for (Player p : playersA) {
      s.append("- ").append(p).append("\n");
    }
    s.append("[VS]\n");
    s.append("TEAM B [").append(new DecimalFormat("##.#").format(this.getTeamB().getRating())).append("]\n");
    List<Player> playersB = new ArrayList<>(this.getTeamB().getPlayers());
    playersB.sort(Collections.reverseOrder());
    for (Player p : playersB) {
      s.append("- ").append(p).append("\n");
    }
    return s.toString();
  }
}
