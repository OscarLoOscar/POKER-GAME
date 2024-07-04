package com.bc2405p.poker_game;

import java.util.Collections;
import java.util.Comparator;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Rank {
    THREE("3", 0), //
    FOUR("4", 1), //
    FIVE("5", 2), //
    SIX("6", 3), //
    SEVEN("7", 4), //
    EIGHT("8", 5), //
    NINE("9", 6), //
    TEN("10", 7), //
    JACK("J", 8), //
    QUEEN("Q", 9), //
    KING("K", 10), //
    ACE("A", 11), //
    TWO("2", 12),//
    ;//

    private final String name;
    private final int value;

    Rank(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @JsonCreator
    public static Rank fromCode(String code) {
        for (Rank rank : Rank.values()) {
            if (rank.name.equals(code)) {
                return rank;
            }
        }
        throw new IllegalArgumentException("Unknown rank code: " + code);
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
