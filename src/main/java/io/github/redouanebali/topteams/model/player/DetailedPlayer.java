package io.github.redouanebali.topteams.model.player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DetailedPlayer extends Player {

  @Getter
  private Map<PlayerStats, Double> stats = new HashMap<>();

  public DetailedPlayer(String id, Map<PlayerStats, Double> stats) {
    super(id, 0.0);
    this.stats = stats;
  }

  public void setStats(Map<PlayerStats, Double> stats) {
    this.stats = stats;
  }

  @Override
  public double getRating() {
    return BigDecimal.valueOf(stats.values().stream().mapToDouble(Double::doubleValue).average().orElse(0))
                     .setScale(1, RoundingMode.HALF_UP)
                     .doubleValue();
  }

}
