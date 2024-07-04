package com.bc2405p.poker_game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BigTwoRules {
  public static boolean isValidPlay(List<Card> currentPlay, List<Card> newPlay,
      boolean isFirstPlay) {
    if (newPlay.isEmpty()) {
      return false;
    }

    if (isFirstPlay && !containsThreeOfDiamonds(newPlay)) {
      return false;
    }

    if (currentPlay.isEmpty()) {
      return isValidCombination(newPlay);
    }

    if (newPlay.size() != currentPlay.size()) {
      return false;
    }

    return isValidCombination(newPlay) && comparePlay(currentPlay, newPlay) < 0;
  }

  private static boolean containsThreeOfDiamonds(List<Card> play) {
    return play.stream().anyMatch(card -> card.getRank() == Rank.THREE
        && card.getSuit() == Suit.DIAMONDS);
  }

  public static boolean isValidCombination(List<Card> play) {
    int size = play.size();
    if (size == 1)
      return true; // Single
    if (size == 2)
      return isPair(play);
    if (size == 3)
      return isThreeOfAKind(play);
    if (size == 5)
      return isStraight(play) || isFlush(play) || isFullHouse(play)
          || isFourOfAKind(play) || isStraightFlush(play);
    return false;
  }

  private static boolean isPair(List<Card> play) {
    return play.size() == 2 && play.get(0).getRank() == play.get(1).getRank();
  }

  private static boolean isThreeOfAKind(List<Card> play) {
    return play.size() == 3 && play.get(0).getRank() == play.get(1).getRank()
        && play.get(1).getRank() == play.get(2).getRank();
  }

  private static boolean isStraight(List<Card> play) {
    if (play.size() != 5)
      return false;
    List<Card> sortedPlay = new ArrayList<>(play);
    Collections.sort(sortedPlay);
    for (int i = 0; i < 4; i++) {
      if (sortedPlay.get(i + 1).getRank().getValue()
          - sortedPlay.get(i).getRank().getValue() != 1) {
        return false;
      }
    }
    return true;
  }

  private static boolean isFlush(List<Card> play) {
    if (play.size() != 5)
      return false;
    Suit suit = play.get(0).getSuit();
    return play.stream().allMatch(card -> card.getSuit() == suit);
  }

  private static boolean isFullHouse(List<Card> play) {
    if (play.size() != 5)
      return false;
    Map<Rank, Integer> rankCount = new HashMap<>();
    for (Card card : play) {
      rankCount.put(card.getRank(),
          rankCount.getOrDefault(card.getRank(), 0) + 1);
    }
    return rankCount.containsValue(3) && rankCount.containsValue(2);
  }

  private static boolean isFourOfAKind(List<Card> play) {
    if (play.size() != 5)
      return false;
    Map<Rank, Integer> rankCount = new HashMap<>();
    for (Card card : play) {
      rankCount.put(card.getRank(),
          rankCount.getOrDefault(card.getRank(), 0) + 1);
    }
    return rankCount.containsValue(4);
  }

  private static boolean isStraightFlush(List<Card> play) {
    return isStraight(play) && isFlush(play);
  }

  public static int comparePlay(List<Card> play1, List<Card> play2) {
    if (play1.size() != play2.size()) {
      throw new IllegalArgumentException(
          "Plays must be of the same size to compare");
    }

    int type1 = getCombinationType(play1);
    int type2 = getCombinationType(play2);

    if (type1 != type2) {
      return Integer.compare(type1, type2);
    }

    // For pairs and three of a kind, compare the rank of the matching cards
    if (type1 == 1 || type1 == 2) {
      return play1.get(0).compareTo(play2.get(0));
    }

    // For other combinations, compare the highest card
    List<Card> sortedPlay1 = new ArrayList<>(play1);
    List<Card> sortedPlay2 = new ArrayList<>(play2);
    Collections.sort(sortedPlay1, Collections.reverseOrder());
    Collections.sort(sortedPlay2, Collections.reverseOrder());

    return sortedPlay1.get(0).compareTo(sortedPlay2.get(0));
  }

  private static int getCombinationType(List<Card> play) {
    if (isStraightFlush(play))
      return 5;
    if (isFourOfAKind(play))
      return 4;
    if (isFullHouse(play))
      return 3;
    if (isFlush(play))
      return 2;
    if (isStraight(play))
      return 1;
    if (isThreeOfAKind(play))
      return 2;
    if (isPair(play))
      return 1;
    return 0; // Single
  }
}
