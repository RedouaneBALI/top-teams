package io.github.redouanebali.topteams.model.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PlayerStats {

  pace("PAC"),
  shooting("SHO"),
  passing("PAS"),
  dribbling("DRI"),
  defending("DEF"),
  physical("PHY");

  @Getter
  private String name;
}
