package io.github.redouanebali.topteams.model.player;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PlayerCharacteristics {

  pace("PAC"),
  shooting("SHO"),
  passing("PAS"),
  dribbling("DRI"),
  defending("DEF"),
  physical("PHY");

  private String name;
}
