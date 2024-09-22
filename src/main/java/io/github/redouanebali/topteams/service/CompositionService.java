package io.github.redouanebali.topteams.service;


import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.game.CompositionGenerator;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompositionService {

  public List<Composition> getNBestCompositions(List<? extends Player> players, int count) {
    return CompositionGenerator.getNBestCompositions(players, count);
  }

  public Composition getBestComposition(List<? extends Player> players) {
    return CompositionGenerator.getNBestCompositions(players, 1).getFirst();
  }

  public List<Composition> getNBestCompositionsWithStats(List<DetailedPlayer> players, int count) {
    return CompositionGenerator.getNBestCompositionsFromStats(players, count);
  }

  public Composition getBestCompositionWithStats(List<DetailedPlayer> players) {
    return getNBestCompositionsWithStats(players, 1).getFirst();
  }
}