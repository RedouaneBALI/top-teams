package io.github.redouanebali.topteams.model.game;

public class Game {

  private Composition composition;
  private Score       score;

  @Override
  public String toString() {
    return score + "\n" + composition;
  }
}