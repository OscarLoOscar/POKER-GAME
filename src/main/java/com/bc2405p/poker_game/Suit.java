package com.bc2405p.poker_game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Suit {
  HEARTS("H", 2), //
  DIAMONDS("D", 0), //
  CLUBS("C", 1), //
  SPADES("S", 3);//

  private final String name;
  private final int value;

  Suit(String name, int value) {
    this.name = name;
    this.value = value;
  }

  @JsonCreator
  public static Suit fromCode(String code) {
    for (Suit suit : Suit.values()) {
      if (suit.name.equals(code)) {
        return suit;
      }
    }
    throw new IllegalArgumentException("Unknown suit code: " + code);
  }

  @Override
  public String toString() {
    return this.name;
  }

  @JsonValue
  public String getName() {
    return this.name;
  }
}
