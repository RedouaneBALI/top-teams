package io.github.redouanebali.topteams;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.player.PlayerDataLoader;
import io.github.redouanebali.topteams.model.player.PlayerStats;
import io.github.redouanebali.topteams.model.team.Team;
import io.github.redouanebali.topteams.service.PlayerService;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TeamTests {

  private static final PlayerService PLAYER_SERVICE = new PlayerService(new PlayerDataLoader());

  @Test
  public void getSimpleTeamRatingTest() throws IOException {
    List<Player> players = PLAYER_SERVICE.loadPlayers("/simple-players.json", Player.class);
    Team         team    = new Team(players);
    assertEquals(5, team.getPlayers().size());
    assertEquals(70, team.getRating());
  }

  @Test
  public void getDetailedTeamRatingTest() throws IOException {
    List<DetailedPlayer> players = PLAYER_SERVICE.loadPlayers("/detailed-players.json", DetailedPlayer.class);
    Team                 team    = new Team(players);
    assertEquals(5, team.getPlayers().size());
    assertEquals(65, team.getRating());
  }

  @Test
  public void getDetailedTeamRatingByCharacteristicTest() throws IOException {
    Team team = new Team(PLAYER_SERVICE.loadPlayers("/detailed-players.json", DetailedPlayer.class));
    assertEquals(63, team.getRating(PlayerStats.pace));
    assertEquals(67, team.getRating(PlayerStats.dribbling));
    assertEquals(63, team.getRating(PlayerStats.physical));
    assertEquals(67, team.getRating(PlayerStats.passing));
    assertEquals(65, team.getRating(PlayerStats.shooting));
    assertEquals(67, team.getRating(PlayerStats.dribbling));
  }

  @Test
  public void testEquals() {
    Player playerA1 = new Player("PlayerA", 50);
    Player playerA2 = new Player("PlayerA2", 60);
    Player playerA3 = new Player("PlayerA3", 60);
    Player playerB1 = new Player("PlayerB1", 60);

    Team team1 = new Team(playerA1, playerA2, playerA3);
    Team team2 = new Team(playerA1, playerA2, playerA3);
    Team team3 = new Team(playerA2, playerA3, playerA1);
    Team team4 = new Team(playerA3, playerA2, playerA1);
    Team team5 = new Team(playerA1, playerA2, playerB1);

    assertEquals(team1, team2);
    assertEquals(team2, team3);
    assertEquals(team3, team4);
    assertEquals(team1, team4);
    assertNotEquals(team1, team5);
  }

}
