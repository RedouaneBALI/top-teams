package io.github.redouanebali.topteams.service;


import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.game.CompositionGenerator;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CompositionService {

  public Composition getBestCompositionWithStats(List<DetailedPlayer> players) {
    return CompositionGenerator.getBestCompositionFromStats(players);
  }

  public Composition getBestComposition(List<? extends Player> players) {
    return CompositionGenerator.getBestComposition(players);
  }
}