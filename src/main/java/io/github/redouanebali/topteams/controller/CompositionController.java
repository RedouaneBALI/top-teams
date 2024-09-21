package io.github.redouanebali.topteams.controller;

import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.service.CompositionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/composition")
public class CompositionController {

  @Autowired
  private CompositionService compositionService;

  @PostMapping("/best")
  public Composition getBestComposition(@RequestBody List<? extends Player> players) {
    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("player list cannot be empty or null");
    } else if (players.size() % 2 != 0) {
      throw new IllegalArgumentException("player count should be even");
    }

    if (players.stream().allMatch(p -> p instanceof DetailedPlayer)) {
      @SuppressWarnings("unchecked")
      List<DetailedPlayer> detailedPlayers = (List<DetailedPlayer>) players;
      return compositionService.getBestCompositionWithCharacteristics(detailedPlayers);
    } else {
      return compositionService.getBestComposition(players);
    }
  }
}