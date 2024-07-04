package com.bc2405p.poker_game;

import java.util.List;
import lombok.Data;

@Data
public class GameState {
  private List<Player> players;
  private List<Card> currentPlay;
  private int currentPlayerIndex;
  private String gameStatus;
  private int roundsPlayed;
  private int consecutivePasses;
  private String winnerId;

  public GameState() {
    this.roundsPlayed = 0;
  }

  public void incrementRoundsPlayed() {
    this.roundsPlayed++;
  }

  public void incrementPasses() {
    this.consecutivePasses++;
  }

  public void resetPasses() {
    consecutivePasses = 0;
    this.roundsPlayed = 0;
  }
}
