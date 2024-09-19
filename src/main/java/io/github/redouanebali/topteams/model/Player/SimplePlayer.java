package io.github.redouanebali.topteams.model.Player;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SimplePlayer extends Player {

  @Getter
  private double rating = 0.0;

  public SimplePlayer(String id, double rating) {
    super(id);
    this.rating = rating;
  }
}
