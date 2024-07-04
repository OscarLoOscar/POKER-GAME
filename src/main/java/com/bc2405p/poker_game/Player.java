package com.bc2405p.poker_game;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.Data;

@Data
public class Player {
  private String id;
  private String name;
  private List<Card> hand;


  public void sortByRank() {
    // Collections.sort(this.hand, new Comparator<Card>() {
    // @Override
    // public int compare(Card c1, Card c2) {
    // return c1.getRank().getValue() - c2.getRank().getValue();
    // }
    // });
    Collections.sort(this.hand,
        Comparator.comparingInt(c -> c.getRank().getValue()));
  }

  public void sortBySuit() {
    Collections.sort(this.hand, (c1, c2) -> {
      int suitComparison = c1.getSuit().getValue() - c2.getSuit().getValue();
      if (suitComparison != 0) {
        return suitComparison;
      } else {
        return c1.getRank().getValue() - c2.getRank().getValue();
      }
    });
    // Collections.sort(this.hand, new Comparator<Card>() {
    // @Override
    // public int compare(Card c1, Card c2) {
    // int rankComparison = c1.getRank().getValue() - c2.getRank().getValue();
    // if (rankComparison != 0) {
    // return rankComparison;
    // } else {
    // return c1.getSuit().getValue() - c2.getSuit().getValue();
    // }
    // }
    // });

  }
}
