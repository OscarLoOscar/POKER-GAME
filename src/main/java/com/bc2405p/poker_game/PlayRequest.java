package com.bc2405p.poker_game;

import java.util.List;
import lombok.Data;

@Data
public class PlayRequest {
  private String playerId;
  private List<Card> cards;
}
