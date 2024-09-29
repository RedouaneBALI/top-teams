package io.github.redouanebali.topteams.service;


import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.game.CompositionGenerator;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompositionService {

  public List<Composition> getNBestCompositions(List<? extends Player> players, int count, boolean splitBestPlayers, boolean splitWeakestPlayers) {
    return CompositionGenerator.getNBestCompositions(players, count, splitBestPlayers, splitWeakestPlayers);
  }

  public Composition getBestComposition(List<? extends Player> players, boolean splitBestPlayers, boolean splitWeakestPlayers) {
    return CompositionGenerator.getNBestCompositions(players, 1, splitBestPlayers, splitWeakestPlayers).getFirst();
  }

  public List<Composition> getNBestCompositionsWithStats(List<DetailedPlayer> players,
                                                         int count,
                                                         boolean splitBestPlayers,
                                                         boolean splitWeakestPlayers) {
    return CompositionGenerator.getNBestCompositionsFromStats(players, count, splitBestPlayers, splitWeakestPlayers);
  }

  public Composition getBestCompositionWithStats(List<DetailedPlayer> players, boolean splitBestPlayers, boolean splitWeakestPlayers) {
    return getNBestCompositionsWithStats(players, 1, splitBestPlayers, splitWeakestPlayers).getFirst();
  }
}