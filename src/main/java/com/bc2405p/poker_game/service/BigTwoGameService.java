package com.bc2405p.poker_game.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.bc2405p.poker_game.BigTwoRules;
import com.bc2405p.poker_game.Card;
import com.bc2405p.poker_game.Deck;
import com.bc2405p.poker_game.GameState;
import com.bc2405p.poker_game.Player;
import com.bc2405p.poker_game.Rank;
import com.bc2405p.poker_game.Suit;
import lombok.Getter;

@Service
@Getter
public class BigTwoGameService {

  private GameState gameState;
  private Deck deck;

  public BigTwoGameService() {
    this.gameState = new GameState();
    this.gameState.setPlayers(new ArrayList<>());
    this.gameState.setCurrentPlay(new ArrayList<>());
    this.gameState.setGameStatus("Waiting for players");
    this.deck = new Deck();
  }

  public void addPlayer(String playerId, String playerName) {
    Player player = new Player();
    player.setId(playerId);
    player.setName(playerName);
    player.setHand(new ArrayList<>());
    gameState.getPlayers().add(player);

    if (gameState.getPlayers().size() == 4) {
      startGame();
    }
  }

  private void startGame() {
    deck.shuffle();
    dealCards();
    gameState.setCurrentPlayerIndex(findStartingPlayerIndex());
    gameState.setGameStatus("In progress");
    gameState.setRoundsPlayed(0);
  }

  private void dealCards() {
    for (int i = 0; i < 13; i++) {
      for (Player player : gameState.getPlayers()) {
        player.getHand().add(deck.drawCard());
      }
    }
  }

  private int findStartingPlayerIndex() {
    for (int i = 0; i < gameState.getPlayers().size(); i++) {
      if (gameState.getPlayers().get(i).getHand()
          .contains(new Card(Suit.DIAMONDS, Rank.THREE))) {
        return i;
      }
    }
    return 0; // This should never happen if the deck is complete
  }

  public boolean playCards(String playerId, List<Card> cards) {
    Player currentPlayer =
        gameState.getPlayers().get(gameState.getCurrentPlayerIndex());
    if (!currentPlayer.getId().equals(playerId)) {
      return false; // Not this player's turn
    }

    boolean isFirstPlay = gameState.getCurrentPlay().isEmpty()
        && gameState.getRoundsPlayed() == 0;
    if (!BigTwoRules.isValidPlay(gameState.getCurrentPlay(), cards,
        isFirstPlay)) {
      return false; // Invalid play
    }

    // Remove played cards from player's hand
    currentPlayer.getHand().removeAll(cards);
    gameState.setCurrentPlay(cards);

    // Check if the player has won
    if (currentPlayer.getHand().isEmpty()) {
      gameState.setGameStatus("Won");
      gameState.setWinnerId(playerId);
      return true;
    }

    // Move to next player
    gameState.setCurrentPlayerIndex((gameState.getCurrentPlayerIndex() + 1)
        % gameState.getPlayers().size());
    gameState.setRoundsPlayed(gameState.getRoundsPlayed() + 1);

    return true;
  }

  private boolean isValidPlay(List<Card> cards) {
    // Implement Big Two rules here
    // For simplicity, let's just check if it's the first play and contains 3 of Diamonds
    if (gameState.getCurrentPlay().isEmpty()) {
      return cards.contains(new Card(Suit.DIAMONDS, Rank.THREE));
    }
    // Add more rules as needed
    return true;
  }

  public GameState getGameState() {
    return this.gameState;
  }

  public void processPlayerPass(Player player) {
    // Check if the current player is the one passing
    if (!gameState.getPlayers().get(gameState.getCurrentPlayerIndex())
        .equals(player)) {
      throw new IllegalStateException("It is not the passing player's turn");
    }

    gameState.incrementPasses();

    if (gameState.getConsecutivePasses() == gameState.getPlayers().size() - 1) {
      gameState.resetPasses();
      // gameState.setCurrentPlay(player.getHand());
    }
    // Update the game state to the next player
    int nextPlayerIndex =
        (gameState.getCurrentPlayerIndex() + 1) % gameState.getPlayers().size();
    gameState.setCurrentPlayerIndex(nextPlayerIndex);
  }

  public void sortByRank(String playerId) {
    Player player = gameState.getPlayers().stream()
        .filter(p -> p.getId().equals(playerId)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Player not found"));
    player.sortByRank();
  }

  public void sortBySuit(String playerId) {
    Player player = gameState.getPlayers().stream()
        .filter(p -> p.getId().equals(playerId)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Player not found"));
    player.sortBySuit();
  }

  public void processPlayerMove(Player player, List<Card> cardsToPlay) {
    // Check if the current player is the one making the move
    if (!gameState.getPlayers().get(gameState.getCurrentPlayerIndex())
        .equals(player)) {
      throw new IllegalStateException("It is not the player's turn");
    }

    // Check if the move is valid
    if (!isValidPlay(cardsToPlay)) {
      throw new IllegalArgumentException("Invalid move");
    }

    // Remove played cards from player's hand
    player.getHand().removeAll(cardsToPlay);

    // Update the current play
    gameState.setCurrentPlay(cardsToPlay);

    // Check if the player has won
    if (player.getHand().isEmpty()) {
      gameState.setGameStatus("Game Over - Winner: " + player.getName());
    }

    // Move to the next player
    int nextPlayerIndex =
        (gameState.getCurrentPlayerIndex() + 1) % gameState.getPlayers().size();
    gameState.setCurrentPlayerIndex(nextPlayerIndex);
    gameState.setRoundsPlayed(gameState.getRoundsPlayed() + 1);
  }
}

