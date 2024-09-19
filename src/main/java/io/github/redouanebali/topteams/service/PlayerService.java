package io.github.redouanebali.topteams.service;

import io.github.redouanebali.topteams.model.Player.Player;
import io.github.redouanebali.topteams.model.Player.PlayerDataLoader;
import java.io.IOException;
import java.util.List;

public class PlayerService {

  private final PlayerDataLoader dataLoader;

  public PlayerService(PlayerDataLoader dataLoader) {
    this.dataLoader = dataLoader;
  }

  public <T extends Player> List<T> loadPlayers(String filePath, Class<T> playerClass) throws IOException {
    return dataLoader.getPlayersFromJson(filePath, playerClass);
  }

  public <T extends Player> T loadPlayer(String filePath, Class<T> playerClass) throws IOException {
    return dataLoader.getPlayerFromJson(filePath, playerClass);
  }

}