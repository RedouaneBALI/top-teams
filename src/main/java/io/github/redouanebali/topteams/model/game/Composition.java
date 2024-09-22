package io.github.redouanebali.topteams.model.game;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.player.PlayerStats;
import io.github.redouanebali.topteams.model.team.Team;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

@AllArgsConstructor
@Getter
public class Composition implements Comparable<Composition> {

  private final Team teamA;
  private final Team teamB;

  @JsonProperty("rating_difference")
  public double getRatingDifference() {
    return BigDecimal.valueOf(teamA.getRating() - teamB.getRating())
                     .setScale(1, RoundingMode.HALF_UP)
                     .doubleValue();
  }


  @JsonInclude(Include.NON_NULL)
  @JsonProperty("characteristic_rating_differences")
  public Map<PlayerStats, Double> getCharacteristicRatingDifferences() {
    if (isDetailedPlayersCompo()) {
      return Arrays.stream(PlayerStats.values())
                   .filter(ps -> this.getTeamA().getRating(ps) > 0.0 && this.getTeamB().getRating(ps) > 0.0)
                   .flatMap(p -> teamB.getPlayers().stream()
                                      .map(q -> Pair.of(p, q)))
                   .map(pair -> Pair.of(
                       pair.getLeft(),
                       this.getTeamA().getRating(pair.getLeft()) - this.getTeamB()
                                                                       .getRating(pair.getLeft())))
                   .collect(Collectors.groupingBy(Pair::getLeft,
                                                  Collectors.averagingDouble(Pair::getRight)));
    }
    return null;
  }

  @JsonInclude(Include.NON_DEFAULT)
  @JsonProperty("characteristic_standard_deviation")
  public double getStatsStandardDeviation() {
    if (isDetailedPlayersCompo()) {

      DescriptiveStatistics stats = new DescriptiveStatistics();

      Arrays.stream(PlayerStats.values())
            .filter(ps -> this.getTeamA().getRating(ps) > 0 && this.getTeamB().getRating(ps) > 0)
            .map(ps -> this.getTeamA().getRating(ps) - this.getTeamB().getRating(ps))
            .forEach(stats::addValue);
      if (!Double.isNaN(stats.getStandardDeviation())) {
        return BigDecimal.valueOf(stats.getStandardDeviation())
                         .setScale(1, RoundingMode.HALF_UP)
                         .doubleValue();
      }
    }
    return 0.0;
  }

  public boolean isDetailedPlayersCompo() {
    return (teamA.getPlayers().stream().allMatch(DetailedPlayer.class::isInstance)
            && teamB.getPlayers().stream().allMatch(DetailedPlayer.class::isInstance));
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Composition composition = (Composition) o;
    return (composition.getTeamA().equals(this.getTeamA()) && composition.getTeamB().equals(this.getTeamB())
            || composition.getTeamA().equals(this.getTeamB()) && composition.getTeamB().equals(this.getTeamA()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.teamA) + Objects.hash(this.teamB);
  }
}
