package io.github.redouanebali.topteams.model.Player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Player {

  private String id;

  public abstract double getRating();

}
