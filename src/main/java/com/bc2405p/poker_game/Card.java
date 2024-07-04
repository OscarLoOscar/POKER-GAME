package com.bc2405p.poker_game;

import lombok.Data;

@Data
public class Card implements Comparable<Card> {

  private Rank rank;
  private Suit suit;

  public Card(Suit suit, Rank rank) {
    this.rank = rank;
    this.suit = suit;
  }

  @Override
  public int compareTo(Card o) {
    if (this.rank != o.rank)
      return Integer.compare(this.rank.getValue(), o.rank.getValue());
    return Integer.compare(this.suit.getValue(), o.suit.getValue());
  }

  public String getCardName() {
    return this.rank.getName() + this.suit.getName();
  }

  
}
