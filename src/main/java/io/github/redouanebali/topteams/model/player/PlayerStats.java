package io.github.redouanebali.topteams.model.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PlayerStats {

  PAC("PAC"),
  SHO("SHO"),
  PAS("PAS"),
  DRI("DRI"),
  DEF("DEF"),
  PHY("PHY");

  @Getter
  private String name;
}
