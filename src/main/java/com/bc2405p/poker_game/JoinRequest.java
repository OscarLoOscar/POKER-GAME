package com.bc2405p.poker_game;

import lombok.Data;

@Data
public class JoinRequest {
  private String playerId;
  private String playerName;
}
