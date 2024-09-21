package io.github.redouanebali.topteams.controller;

import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.SimplePlayer;
import io.github.redouanebali.topteams.service.CompositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

  @Operation(summary = "Return the best found composition",
             description = "Generate randomly a high number of compositions and return the best one found. If all players have characteristics, it "
                           + "will return the composition with the lowest standard deviation.")
  @ApiResponse(responseCode = "200", description = "Best composition returned")
  @PostMapping("/best")
  public Composition getBestComposition(@RequestBody List<DetailedPlayer> players) {
    if (players == null || players.isEmpty()) {
      throw new IllegalArgumentException("player list cannot be empty or null");
    } else if (players.size() % 2 != 0) {
      throw new IllegalArgumentException("player count should be even");
    }
    return compositionService.getBestCompositionWithCharacteristics(players);
  }

  @PostMapping("/best-simple")
  public Composition getBestSimpleComposition(@RequestBody List<SimplePlayer> players) {
    return compositionService.getBestComposition(players);
  }
}