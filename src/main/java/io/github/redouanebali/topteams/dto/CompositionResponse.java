package io.github.redouanebali.topteams.dto;

import io.github.redouanebali.topteams.model.game.Composition;
import java.util.List;
import lombok.Getter;

@Getter
public class CompositionResponse {

  private final List<Composition> result;
  private final int               count;

  public CompositionResponse(List<Composition> result) {
    this.result = result;
    this.count  = result.size();
  }
}
