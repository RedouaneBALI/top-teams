package io.github.redouanebali.topteams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.game.CompositionGenerator;
import io.github.redouanebali.topteams.model.player.PlayerDataLoader;
import io.github.redouanebali.topteams.model.player.SimplePlayer;
import io.github.redouanebali.topteams.service.PlayerService;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CompositionGeneratorTests {

  private static final PlayerService PLAYER_SERVICE = new PlayerService(new PlayerDataLoader());

  @Test
  public void testGenerateRandomCompo() throws IOException {
    List<SimplePlayer> allPlayers = PLAYER_SERVICE.loadPlayers("/simple-players.json", SimplePlayer.class);
    allPlayers.addAll(PLAYER_SERVICE.loadPlayers("/simple-players2.json", SimplePlayer.class));
    Composition composition = CompositionGenerator.generateRandomComposition(allPlayers);
    assertNotNull(composition.getTeamA());
    assertNotNull(composition.getTeamB());
    assertFalse(composition.getTeamA().getPlayers().isEmpty());
    assertFalse(composition.getTeamB().getPlayers().isEmpty());
    assertEquals(composition.getTeamA().getPlayers().size(), composition.getTeamB().getPlayers().size());
    System.out.println(composition);
  }
}
