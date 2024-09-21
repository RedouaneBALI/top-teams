package io.github.redouanebali.topteams.model.player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SimplePlayer extends Player {

  private double rating = 0.0;

  public SimplePlayer(String id, double rating) {
    super(id);
    this.rating = rating;
  }

  @Override
  public double getRating() {
    return BigDecimal.valueOf(rating)
                     .setScale(1, RoundingMode.HALF_UP)
                     .doubleValue();
  }
}
