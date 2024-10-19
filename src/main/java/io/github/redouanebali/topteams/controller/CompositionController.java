package io.github.redouanebali.topteams.controller;

import io.github.redouanebali.topteams.dto.CompositionResponse;
import io.github.redouanebali.topteams.exception.CompositionException;
import io.github.redouanebali.topteams.model.player.DetailedPlayer;
import io.github.redouanebali.topteams.model.player.Player;
import io.github.redouanebali.topteams.service.CompositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Composition", description = "Generate the best team composition(s) from a list of players")
@RequestMapping("/composition")
public class CompositionController {

  private final CompositionService compositionService = new CompositionService();

  @Operation(summary = "Return the best composition based on general rating",
             description = "Generate randomly a high number of compositions and return the best one found based rating average difference.")
  @ApiResponse(responseCode = "200", description = "Best composition returned")
  @ApiResponse(responseCode = "400", description = "Invalid input")
  @PostMapping("/optimize-with-overall")
  public CompositionResponse getBestSimpleComposition(
      @RequestBody(description = "List of simple players", required = true,
                   content = @Content(schema = @Schema(implementation = Player.class),
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
      @org.springframework.web.bind.annotation.RequestBody List<Player> players,
      @Parameter(description = "Number of compositions to return. Default (and minimum) is 1. Maximum is 10.")
      @RequestParam(defaultValue = "1") int count,
      @Parameter(description = "Split of the best players in each team")
      @RequestParam(defaultValue = "false") boolean splitBestPlayers,
      @Parameter(description = "Split of the weakest players in each team")
      @RequestParam(defaultValue = "false") boolean splitWeakestPlayers
  ) {
    checkArguments(players, count);
    return new CompositionResponse(compositionService.getNBestCompositions(players, count, splitBestPlayers, splitWeakestPlayers));
  }


  @Operation(summary = "Return the best found composition based on player stats and general rating",
             description =
                 "Generate randomly a high number of team compositions and return the best one foundbased on the standard deviation of player "
                 + "stats differences and rating average difference")
  @ApiResponse(responseCode = "200", description = "Best composition returned")
  @ApiResponse(responseCode = "400", description = "Invalid input")
  @PostMapping("/optimize-with-stats")
  public CompositionResponse getBestComposition(
      @RequestBody(description = "List of detailed players", required = true,
                   content = @Content(schema = @Schema(implementation = DetailedPlayer.class),
                                      examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(
                                          value = "[\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerA\",\n"
                                                  + "    \"stats\": {\n"
                                                  + "      \"PAC\": 50,\n"
                                                  + "      \"SHO\": 60,\n"
                                                  + "      \"PAS\": 70,\n"
                                                  + "      \"DRI\": 70,\n"
                                                  + "      \"DEF\": 60,\n"
                                                  + "      \"PHY\": 50\n"
                                                  + "    }\n"
                                                  + "  },\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerB\",\n"
                                                  + "    \"stats\": {\n"
                                                  + "      \"PAC\": 60,\n"
                                                  + "      \"SHO\": 70,\n"
                                                  + "      \"PAS\": 80,\n"
                                                  + "      \"DRI\": 90,\n"
                                                  + "      \"DEF\": 80,\n"
                                                  + "      \"PHY\": 60\n"
                                                  + "    }\n"
                                                  + "  },\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerC\",\n"
                                                  + "    \"stats\": {\n"
                                                  + "      \"PAC\": 70,\n"
                                                  + "      \"SHO\": 65,\n"
                                                  + "      \"PAS\": 70,\n"
                                                  + "      \"DRI\": 75,\n"
                                                  + "      \"DEF\": 80,\n"
                                                  + "      \"PHY\": 70\n"
                                                  + "    }\n"
                                                  + "  },\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerD\",\n"
                                                  + "    \"stats\": {\n"
                                                  + "      \"PAC\": 70,\n"
                                                  + "      \"SHO\": 60,\n"
                                                  + "      \"PAS\": 50,\n"
                                                  + "      \"DRI\": 60,\n"
                                                  + "      \"DEF\": 70,\n"
                                                  + "      \"PHY\": 90\n"
                                                  + "    }\n"
                                                  + "  },"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerE\",\n"
                                                  + "    \"stats\": {\n"
                                                  + "      \"PAC\": 80,\n"
                                                  + "      \"SHO\": 75,\n"
                                                  + "      \"PAS\": 70,\n"
                                                  + "      \"DRI\": 70,\n"
                                                  + "      \"DEF\": 60,\n"
                                                  + "      \"PHY\": 55\n"
                                                  + "    }\n"
                                                  + "  },\n"
                                                  + "  {\n"
                                                  + "    \"id\": \"playerF\",\n"
                                                  + "    \"stats\": {\n"
                                                  + "      \"PAC\": 70,\n"
                                                  + "      \"SHO\": 50,\n"
                                                  + "      \"PAS\": 60,\n"
                                                  + "      \"DRI\": 65,\n"
                                                  + "      \"DEF\": 75,\n"
                                                  + "      \"PHY\": 60\n"
                                                  + "    }\n"
                                                  + "  }"
                                                  + "]"
                                      )}))
      @org.springframework.web.bind.annotation.RequestBody List<DetailedPlayer> players,
      @Parameter(description = "Number of compositions to return. Default (and minimum) is 1. Maximum is 10.")
      @RequestParam(defaultValue = "1") int count,
      @Parameter(description = "Split of the best players in each team")
      @RequestParam(defaultValue = "false") boolean splitBestPlayers,
      @Parameter(description = "Split of the weakest players in each team")
      @RequestParam(defaultValue = "false") boolean splitWeakestPlayers) {
    checkArguments(players, count);
    return new CompositionResponse(compositionService.getNBestCompositionsWithStats(players, count, splitBestPlayers, splitWeakestPlayers));
  }

  private void checkArguments(List<? extends Player> players, int count) {
    if (players == null || players.isEmpty()) {
      throw new CompositionException("Player list cannot be empty or null");
    } else if (players.size() % 2 != 0) {
      throw new CompositionException("Player count should be even");
    } else if (players.stream().anyMatch(p -> p.getRating() == 0)) {
      if (players.getFirst() instanceof DetailedPlayer) {
        throw new CompositionException("All players must have statistic ratings greater than 0");
      } else {
        throw new CompositionException("All players must have a general rating greater than 0");
      }
    } else if (count < 0 || count > 10) {
      throw new CompositionException("count should be between 1 and 10");
    }
  }
}