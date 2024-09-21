package io.github.redouanebali.topteams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.player.PlayerDataLoader;
import io.github.redouanebali.topteams.model.player.SimplePlayer;
import io.github.redouanebali.topteams.service.PlayerService;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class SimplePlayerTests {

  private static final PlayerService PLAYER_SERVICE = new PlayerService(new PlayerDataLoader());

  @Test
  public void testDeserializeSimplePlayer() throws IOException {
    SimplePlayer player = PLAYER_SERVICE.loadPlayer("/simple-player.json", SimplePlayer.class);
    assertNotNull(player.getId());
    assertTrue(player.getRating() > 50);
  }

  @Test
  public void testEquals() {
    Player playerA  = new SimplePlayer("PlayerA", 50);
    Player playerA2 = new SimplePlayer("PlayerA", 60);
    Player playerB  = new SimplePlayer("PlayerB", 60);
    assertEquals(playerA, playerA2);
    assertNotEquals(playerA, playerB);
    assertNotEquals(playerA2, playerB);
  }
}
