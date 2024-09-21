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
  private Map<PlayerCharacteristics, Double> characteristics = new HashMap<>();

  public DetailedPlayer(String id, Map<PlayerCharacteristics, Double> characteristics) {
    super(id);
    this.characteristics = characteristics;
  }

  public void setCharacteristics(Map<PlayerCharacteristics, Double> characteristics) {
    this.characteristics = characteristics;
  }

  @Override
  public double getRating() {
    return BigDecimal.valueOf(characteristics.values().stream().mapToDouble(Double::doubleValue).average().orElse(0))
                     .setScale(1, RoundingMode.HALF_UP)
                     .doubleValue();
  }

}
