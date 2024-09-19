package io.github.redouanebali.topteams.model.Player;

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
