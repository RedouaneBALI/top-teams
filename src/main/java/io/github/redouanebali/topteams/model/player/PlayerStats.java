package io.github.redouanebali.topteams.model.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PlayerStats {

  PAC("PAC", "pace"),
  SHO("SHO", "shooting"),
  PAS("PAS", "passing"),
  DRI("DRI", "dribbling"),
  DEF("DEF", "defending"),
  PHY("PHY", "physical");

  @Getter
  private String name;
  @Getter
  private String description;
}