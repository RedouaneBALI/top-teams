package io.github.redouanebali.topteams.model.game;

import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.team.Team;
import io.github.redouanebali.topteams.model.team.TeamSide;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RatingCalculator {


  public static List<Player> getUpdatedPlayers(Game game, double kf) {
    List<Player> result  = new ArrayList<>();
    List<Player> players = new ArrayList<>(game.getComposition().getTeamA().getPlayers());
    players.addAll(new ArrayList<>(game.getComposition().getTeamB().getPlayers()));
    Map<String, Double> ratingUpdates = getRatingUpdates(game, kf);
    for (Player p : players) {
      result.add(new Player(p.getId(), p.getRating() + ratingUpdates.get(p.getId())));
    }
    return result;
  }

  public static Map<String, Double> getRatingUpdates(Game game, double kf) {
    Team teamA = game.getComposition().getTeamA();
    Team teamB = game.getComposition().getTeamB();

    double              modifA                    = getModif(game, TeamSide.A, kf);
    double              modifB                    = getModif(game, TeamSide.B, kf);
    Map<String, Double> playerRatingModifications = new HashMap<>();
    teamA.getPlayers().forEach(p -> playerRatingModifications.put(p.getId(), calculatePlayerRatingUpdate(teamA, modifA, p)));
    teamB.getPlayers().forEach(p -> playerRatingModifications.put(p.getId(), calculatePlayerRatingUpdate(teamB, modifB, p)));

    return playerRatingModifications;
  }

  private static double getModif(Game game, TeamSide teamSide, double kf) {
    double modif;
    double globalModif = game.getPredictionError(kf); // @todo to be checked
    if (teamSide == TeamSide.A) {
      modif = globalModif / 2;
    } else {
      modif = -globalModif / 2;
    }
    return modif * kf;
  }

  private static double calculatePlayerRatingUpdate(Team team, double teamModif, Player p) {
    return BigDecimal.valueOf(teamModif * (1 / (double) team.getPlayers().size()))
                     .setScale(2, RoundingMode.HALF_UP)
                     .doubleValue();
  }

}