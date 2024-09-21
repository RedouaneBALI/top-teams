package io.github.redouanebali.topteams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.player.PlayerDataLoader;
import io.github.redouanebali.topteams.model.player.PlayerStats;
import io.github.redouanebali.topteams.service.PlayerService;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class DetailedPlayerTests {

  private static final PlayerService PLAYER_SERVICE = new PlayerService(new PlayerDataLoader());

  @Test
  public void testDeserializeDetailedPlayer() throws IOException {
    DetailedPlayer player = PLAYER_SERVICE.loadPlayer("/detailed-player.json", DetailedPlayer.class);
    assertNotNull(player.getId());
    assertNotNull(player.getStats());
    assertFalse(player.getStats().isEmpty());
    for (PlayerStats stats : PlayerStats.values()) {
      assertTrue(player.getStats().get(stats) > 50);
    }
  }

  @Test
  public void testGetDetailedPlayerRating() {
    Player player = new DetailedPlayer("Player1", Map.of(
        PlayerStats.pace, 50.0,
        PlayerStats.defending, 55.0,
        PlayerStats.dribbling, 60.0,
        PlayerStats.passing, 65.0,
        PlayerStats.physical, 70.0,
        PlayerStats.shooting, 60.0
    ));
    assertEquals(60.0, player.getRating());
  }


}
