package io.github.redouanebali.topteams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.game.CompositionGenerator;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.player.PlayerDataLoader;
import io.github.redouanebali.topteams.service.PlayerService;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CompositionGeneratorTests {

  private static final PlayerService PLAYER_SERVICE = new PlayerService(new PlayerDataLoader());

  @Test
  public void testGenerateRandomCompoSimple() throws IOException {
    List<Player> allPlayers = PLAYER_SERVICE.loadPlayers("/simple-players.json", Player.class);
    allPlayers.addAll(PLAYER_SERVICE.loadPlayers("/simple-players2.json", Player.class));
    Composition composition = CompositionGenerator.generateRandomComposition(allPlayers, false, false);
    assertNotNull(composition.getTeamA());
    assertNotNull(composition.getTeamB());
    assertFalse(composition.getTeamA().getPlayers().isEmpty());
    assertFalse(composition.getTeamB().getPlayers().isEmpty());
    assertEquals(composition.getTeamA().getPlayers().size(), composition.getTeamB().getPlayers().size());
  }

  @Test
  public void testGetBestCompoSimple() throws IOException {
    List<Player> allPlayers = PLAYER_SERVICE.loadPlayers("/simple-players.json", Player.class);
    allPlayers.addAll(PLAYER_SERVICE.loadPlayers("/simple-players2.json", Player.class));
    List<Composition> compositions = CompositionGenerator.getNBestCompositions(allPlayers, 30, false, false);
    assertTrue(Math.abs(compositions.getFirst().getRatingDifference()) < Math.abs(compositions.getLast().getRatingDifference()));
    Composition composition = compositions.getFirst();
    assertNotNull(composition.getTeamA());
    assertNotNull(composition.getTeamB());
    assertFalse(composition.getTeamA().getPlayers().isEmpty());
    assertFalse(composition.getTeamB().getPlayers().isEmpty());
    assertEquals(composition.getTeamA().getPlayers().size(), composition.getTeamB().getPlayers().size());
    assertEquals(0, composition.getRatingDifference(), 0.2);
  }

  @Test
  public void testGenerateRandomCompoWithSplitBest() throws IOException {
    List<Player> allPlayers = PLAYER_SERVICE.loadPlayers("/simple-players.json", Player.class);
    allPlayers.addAll(PLAYER_SERVICE.loadPlayers("/simple-players2.json", Player.class));
    for (int i = 0; i < 10; i++) {
      Composition composition = CompositionGenerator.generateRandomComposition(allPlayers, true, false);
      allPlayers.sort(Collections.reverseOrder());
      assertTrue((composition.getTeamA().getPlayers().contains(allPlayers.get(0))
                  && composition.getTeamB().getPlayers().contains(allPlayers.get(1)))
                 || (composition.getTeamB().getPlayers().contains(allPlayers.get(0))
                     && composition.getTeamA().getPlayers().contains(allPlayers.get(1))));
    }
  }

  @Test
  public void testGenerateRandomCompoWithSplitWeakest() throws IOException {
    List<Player> allPlayers = PLAYER_SERVICE.loadPlayers("/simple-players.json", Player.class);
    allPlayers.addAll(PLAYER_SERVICE.loadPlayers("/simple-players2.json", Player.class));
    for (int i = 0; i < 10; i++) {
      Composition composition = CompositionGenerator.generateRandomComposition(allPlayers, false, true);
      allPlayers.sort(Collections.reverseOrder());
      assertTrue((composition.getTeamA().getPlayers().contains(allPlayers.getLast())
                  && composition.getTeamB().getPlayers().contains(allPlayers.get(allPlayers.size() - 2)))
                 || (composition.getTeamB().getPlayers().contains(allPlayers.getLast())
                     && composition.getTeamA().getPlayers().contains(allPlayers.get(allPlayers.size() - 2))));
    }
  }

  @Test
  public void testGenerateRandomCompoWithStats() throws IOException {
    List<DetailedPlayer> allPlayers = PLAYER_SERVICE.loadPlayers("/detailed-players.json", DetailedPlayer.class);
    allPlayers.addAll(PLAYER_SERVICE.loadPlayers("/detailed-players2.json", DetailedPlayer.class));
    Composition composition = CompositionGenerator.generateRandomComposition(allPlayers, false, false);
    assertNotNull(composition.getTeamA());
    assertNotNull(composition.getTeamB());
    assertFalse(composition.getTeamA().getPlayers().isEmpty());
    assertFalse(composition.getTeamB().getPlayers().isEmpty());
    assertEquals(composition.getTeamA().getPlayers().size(), composition.getTeamB().getPlayers().size());
  }

  @Test
  public void testGenerateRandomCompoWithStatsSplitBest() throws IOException {
    List<DetailedPlayer> allPlayers = PLAYER_SERVICE.loadPlayers("/detailed-players.json", DetailedPlayer.class);
    allPlayers.addAll(PLAYER_SERVICE.loadPlayers("/detailed-players2.json", DetailedPlayer.class));
    Composition composition = CompositionGenerator.generateRandomComposition(allPlayers, true, false);
    allPlayers.sort(Collections.reverseOrder());
    assertTrue((composition.getTeamA().getPlayers().contains(allPlayers.get(0))
                && composition.getTeamB().getPlayers().contains(allPlayers.get(1)))
               || (composition.getTeamB().getPlayers().contains(allPlayers.get(0))
                   && composition.getTeamA().getPlayers().contains(allPlayers.get(1))));
  }

  @Test
  public void testGenerateRandomCompoWithStatsSplitWeakest() throws IOException {
    List<DetailedPlayer> allPlayers = PLAYER_SERVICE.loadPlayers("/detailed-players.json", DetailedPlayer.class);
    allPlayers.addAll(PLAYER_SERVICE.loadPlayers("/detailed-players2.json", DetailedPlayer.class));
    Composition composition = CompositionGenerator.generateRandomComposition(allPlayers, false, true);
    allPlayers.sort(Collections.reverseOrder());
    assertTrue((composition.getTeamA().getPlayers().contains(allPlayers.getLast())
                && composition.getTeamB().getPlayers().contains(allPlayers.get(allPlayers.size() - 2)))
               || (composition.getTeamB().getPlayers().contains(allPlayers.getLast())
                   && composition.getTeamA().getPlayers().contains(allPlayers.get(allPlayers.size() - 2))));
  }


  @Test
  public void testGetBestCompoDetailedWithStats() throws IOException {
    List<DetailedPlayer> allPlayers = PLAYER_SERVICE.loadPlayers("/detailed-players.json", DetailedPlayer.class);
    allPlayers.addAll(PLAYER_SERVICE.loadPlayers("/detailed-players2.json", DetailedPlayer.class));
    List<Composition> compositions = CompositionGenerator.getNBestCompositionsFromStats(allPlayers, 30, false, false);
    assertTrue(Math.abs(compositions.getFirst().getRatingDifference()) < Math.abs(compositions.getLast().getRatingDifference()));
    Composition composition = compositions.getFirst();
    assertNotNull(composition.getTeamA());
    assertNotNull(composition.getTeamB());
    assertFalse(composition.getTeamA().getPlayers().isEmpty());
    assertFalse(composition.getTeamB().getPlayers().isEmpty());
    assertEquals(composition.getTeamA().getPlayers().size(), composition.getTeamB().getPlayers().size());
    assertTrue(Math.abs(composition.getRatingDifference()) < 5);
  }

}
