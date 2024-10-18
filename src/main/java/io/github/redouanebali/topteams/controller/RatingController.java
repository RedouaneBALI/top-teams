package io.github.redouanebali.topteams.controller;

import io.github.redouanebali.topteams.exception.CompositionException;
import io.github.redouanebali.topteams.model.game.Composition;
import io.github.redouanebali.topteams.model.game.Game;
import io.github.redouanebali.topteams.model.game.Score;
import io.github.redouanebali.topteams.service.RatingCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class RatingController {

  private final RatingCalculatorService ratingCalculatorService = new RatingCalculatorService();

  @Operation(summary = "Return the rating modification from a game result",
             description = "Compute the modifications that have to be applied to each players after a played game")
  @ApiResponse(responseCode = "200", description = "Update ratings")
  @ApiResponse(responseCode = "400", description = "Invalid input")
  @PostMapping("/update-player-ratings")
  public Map<String, Double> getUpdatedRatings(
      @RequestBody(description = "Composition", required = true,
                   content = @Content(schema = @Schema(implementation = Composition.class),
                                      examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(
                                          value = """
                                              {
                                                          "teamA": {
                                                              "players": [
                                                                  {
                                                                      "id": "playerG",
                                                                      "rating": 60.0
                                                                  },
                                                                  {
                                                                      "id": "playerC",
                                                                      "rating": 70.0
                                                                  },
                                                                  {
                                                                      "id": "playerD",
                                                                      "rating": 80.0
                                                                  },
                                                                  {
                                                                      "id": "playerA",
                                                                      "rating": 51.0
                                                                  },
                                                                  {
                                                                      "id": "playerJ",
                                                                      "rating": 90.0
                                                                  }
                                                              ],
                                                              "rating": 70.2
                                                          },
                                                          "teamB": {
                                                              "players": [
                                                                  {
                                                                      "id": "playerF",
                                                                      "rating": 50.0
                                                                  },
                                                                  {
                                                                      "id": "playerH",
                                                                      "rating": 70.0
                                                                  },
                                                                  {
                                                                      "id": "playerI",
                                                                      "rating": 80.0
                                                                  },
                                                                  {
                                                                      "id": "playerB",
                                                                      "rating": 60.0
                                                                  },
                                                                  {
                                                                      "id": "playerE",
                                                                      "rating": 90.0
                                                                  }
                                                              ],
                                                              "rating": 70.0
                                                          }\
                                                      }"""
                                      )}))
      @org.springframework.web.bind.annotation.RequestBody Composition composition,
      @Parameter(description = "Number of goals scored by the team A")
      @RequestParam(defaultValue = "0") int scoreA,
      @Parameter(description = "Number of goals scored by the team B")
      @RequestParam(defaultValue = "0") int scoreB

  ) {
    checkArguments(composition, scoreA, scoreB);
    return ratingCalculatorService.getRatingUpdates(new Game(composition, new Score(scoreA, scoreB)), 1);
  }


  private void checkArguments(Composition composition, int scoreA, int scoreB) {
    if (scoreA < 0 || scoreB < 0) {
      throw new CompositionException("the scores have to be equals or superior than 0");
    }
  }
}