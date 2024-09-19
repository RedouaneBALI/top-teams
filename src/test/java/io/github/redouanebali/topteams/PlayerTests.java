package io.github.redouanebali.topteams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.redouanebali.topteams.model.Player.DetailedPlayer;
import io.github.redouanebali.topteams.model.Player.Player;
import io.github.redouanebali.topteams.model.Player.PlayerCharacteristics;
import io.github.redouanebali.topteams.model.Player.PlayerDataLoader;
import io.github.redouanebali.topteams.model.Player.SimplePlayer;
import io.github.redouanebali.topteams.service.PlayerService;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class PlayerTests {

  private static final PlayerService PLAYER_SERVICE = new PlayerService(new PlayerDataLoader());

  @Test
  public void testDeserializeSimplePlayer() throws IOException {
    SimplePlayer player = PLAYER_SERVICE.loadPlayer("/simple-player.json", SimplePlayer.class);
    assertNotNull(player.getId());
    assertTrue(player.getRating() > 0);
  }

  @Test
  public void testDeserializeDetailedPlayer() throws IOException {
    DetailedPlayer player = PLAYER_SERVICE.loadPlayer("/detailed-player.json", DetailedPlayer.class);
    assertNotNull(player.getId());
    assertNotNull(player.getCharacteristics());
    assertFalse(player.getCharacteristics().isEmpty());
    for (PlayerCharacteristics characteristics : PlayerCharacteristics.values()) {
      assertTrue(player.getCharacteristics().get(characteristics) > 0);
    }

  }

  @Test
  public void testGetRating() {
    Player player = new DetailedPlayer("Player1", Map.of(
        PlayerCharacteristics.pace, 50.0,
        PlayerCharacteristics.defending, 55.0,
        PlayerCharacteristics.dribbling, 60.0,
        PlayerCharacteristics.passing, 65.0,
        PlayerCharacteristics.physical, 70.0,
        PlayerCharacteristics.shooting, 60.0
    ));
    assertEquals(60.0, player.getRating());
  }
}
