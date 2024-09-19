package io.github.redouanebali.topteams.model.Player;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PlayerDataLoader {

  public <T extends Player> List<T> getPlayersFromJson(String filePath, Class<T> playerClass) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {
      if (inputStream == null) {
        throw new IOException("Could not find file: " + filePath);
      }

      return objectMapper.readValue(inputStream, objectMapper.getTypeFactory().constructCollectionType(List.class, playerClass));
    }
  }

  public <T extends Player> T getPlayerFromJson(String filePath, Class<T> playerClass) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    try (InputStream inputStream = getClass().getResourceAsStream(filePath)) {
      if (inputStream == null) {
        throw new IOException("Could not find file: " + filePath);
      }

      return objectMapper.readValue(inputStream, playerClass);
    }
  }
}