package io.github.redouanebali.topteams.controller;

import io.github.redouanebali.topteams.exception.CompositionException;
import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.model.player.SimplePlayer;
import io.github.redouanebali.topteams.service.CompositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/composition")
public class CompositionController {

  @Autowired
  private CompositionService compositionService;

  @Operation(summary = "Return the best found composition based on player characteristics and general rating",
             description =
                 "Generate randomly a high number of team compositions and return the best one foundbased on the standard deviation of player "
                 + "characteristics differences and rating average difference")
  @ApiResponse(responseCode = "200", description = "Best composition returned")
  @PostMapping("/best")
  public Composition getBestComposition(
      @RequestBody(description = "List of detailed players", required = true,
                   content = @Content(schema = @Schema(implementation = DetailedPlayer.class),
                                      examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(
                                          value = "[\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerA\",\n"
                                                  + "    \"characteristics\": {\n"
                                                  + "      \"pace\": 50,\n"
                                                  + "      \"shooting\": 60,\n"
                                                  + "      \"passing\": 70,\n"
                                                  + "      \"dribbling\": 70,\n"
                                                  + "      \"defending\": 60,\n"
                                                  + "      \"physical\": 50\n"
                                                  + "    }\n"
                                                  + "  },\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerB\",\n"
                                                  + "    \"characteristics\": {\n"
                                                  + "      \"pace\": 60,\n"
                                                  + "      \"shooting\": 60,\n"
                                                  + "      \"passing\": 60,\n"
                                                  + "      \"dribbling\": 60,\n"
                                                  + "      \"defending\": 60,\n"
                                                  + "      \"physical\": 60\n"
                                                  + "    }\n"
                                                  + "  },\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerC\",\n"
                                                  + "    \"characteristics\": {\n"
                                                  + "      \"pace\": 70,\n"
                                                  + "      \"shooting\": 70,\n"
                                                  + "      \"passing\": 70,\n"
                                                  + "      \"dribbling\": 70,\n"
                                                  + "      \"defending\": 70,\n"
                                                  + "      \"physical\": 70\n"
                                                  + "    }\n"
                                                  + "  },\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerD\",\n"
                                                  + "    \"characteristics\": {\n"
                                                  + "      \"pace\": 70,\n"
                                                  + "      \"shooting\": 70,\n"
                                                  + "      \"passing\": 70,\n"
                                                  + "      \"dribbling\": 70,\n"
                                                  + "      \"defending\": 70,\n"
                                                  + "      \"physical\": 70\n"
                                                  + "    }\n"
                                                  + "  }\n"
                                                  + "]"
                                      )}))
      @org.springframework.web.bind.annotation.RequestBody List<DetailedPlayer> players) {
    checkPlayersBody(players);
    return compositionService.getBestCompositionWithCharacteristics(players);
  }

  @Operation(summary = "Return the best composition based on general rating",
             description = "Generate randomly a high number of compositions and return the best one found based rating average difference.")
  @ApiResponse(responseCode = "200", description = "Best simple composition returned")
  @PostMapping("/best-simple")
  public Composition getBestSimpleComposition(
      @RequestBody(description = "List of simple players", required = true,
                   content = @Content(schema = @Schema(implementation = SimplePlayer.class),
                                      examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(
                                          value = "[\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerA\",\n"
                                                  + "    \"rating\": 50\n"
                                                  + "  },\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerB\",\n"
                                                  + "    \"rating\": 60\n"
                                                  + "  },\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerC\",\n"
                                                  + "    \"rating\": 70\n"
                                                  + "  },\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerD\",\n"
                                                  + "    \"rating\": 80\n"
                                                  + "  }\n"
                                                  + "]"
                                      )}))
      @org.springframework.web.bind.annotation.RequestBody List<SimplePlayer> players) {
    checkPlayersBody(players);
    return compositionService.getBestComposition(players);
  }

  private void checkPlayersBody(List<? extends Player> players) {
    if (players == null || players.isEmpty()) {
      throw new CompositionException("Player list cannot be empty or null");
    } else if (players.size() % 2 != 0) {
      throw new CompositionException("Player count should be even");
    } else if (players.stream().anyMatch(p -> p.getRating() == 0)) {
      throw new CompositionException("All players must have a rating greater than 0");
    }
  }
}