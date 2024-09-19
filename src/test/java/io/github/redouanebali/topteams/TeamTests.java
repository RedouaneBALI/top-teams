package io.github.redouanebali.topteams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.redouanebali.topteams.model.Player.DetailedPlayer;
import io.github.redouanebali.topteams.model.Player.PlayerDataLoader;
import io.github.redouanebali.topteams.model.Player.SimplePlayer;
import io.github.redouanebali.topteams.model.Team;
import io.github.redouanebali.topteams.service.PlayerService;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TeamTests {

  private static final PlayerService PLAYER_SERVICE = new PlayerService(new PlayerDataLoader());

  @Test
  public void getSimpleTeamRatingTest() throws IOException {
    List<SimplePlayer> players = PLAYER_SERVICE.loadPlayers("/simple-players.json", SimplePlayer.class);
    Team               team    = new Team(players);
    assertEquals(70, team.getRating());
  }

  @Test
  public void getDetailedTeamRatingTest() throws IOException {
    List<DetailedPlayer> players = PLAYER_SERVICE.loadPlayers("/detailed-players.json", DetailedPlayer.class);
    Team                 team    = new Team(players);
    assertEquals(60, team.getRating());
  }

}
