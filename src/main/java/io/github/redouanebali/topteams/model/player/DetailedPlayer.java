package io.github.redouanebali.topteams.model.player;

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
    return characteristics.values().stream().mapToDouble(Double::doubleValue).average().orElse(0);
  }

}
