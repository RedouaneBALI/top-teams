package io.github.redouanebali.topteams.model.player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Comparable<Player> {

  private String id;
  private double rating = 0.0;

  public double getRating() {
    return BigDecimal.valueOf(rating)
                     .setScale(1, RoundingMode.HALF_UP)
                     .doubleValue();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Player player = (Player) o;
    return Objects.equals(id, player.id);

  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public int compareTo(Player other) {
    return Double.compare(this.getRating(), other.getRating());
  }

  @Override
  public String toString() {
    return this.id + " (" + new DecimalFormat("##.#").format(this.getRating()) + ")";
  }

}
