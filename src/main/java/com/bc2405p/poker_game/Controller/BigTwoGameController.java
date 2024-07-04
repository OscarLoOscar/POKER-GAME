package com.bc2405p.poker_game.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import com.bc2405p.poker_game.Card;
import com.bc2405p.poker_game.GameState;
import com.bc2405p.poker_game.JoinRequest;
import com.bc2405p.poker_game.PlayRequest;
import com.bc2405p.poker_game.Player;
import com.bc2405p.poker_game.service.BigTwoGameService;

@RestController
public class BigTwoGameController {

  @Autowired
  private BigTwoGameService bigTwoGameService;

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/join")
  @SendTo("/topic/game-state")
  public GameState joinGame(JoinRequest joinRequest) {
    bigTwoGameService.addPlayer(joinRequest.getPlayerId(),
        joinRequest.getPlayerName());
    return bigTwoGameService.getGameState();
  }

  @MessageMapping("/play")
  @SendTo("/topic/game-state")
  public GameState playCards(PlayRequest playRequest) {
    // boolean validPlay = bigTwoGameService.playCards(playRequest.getPlayerId(),
    // playRequest.getCards());
    // if (validPlay) {
    // return bigTwoGameService.getGameState();
    // } else {
    // // Handle invalid play
    // return null;
    // }
    // Extract playerId and selected cards
    String playerId = playRequest.getPlayerId();
    List<Card> cardsToPlay = playRequest.getCards();
    GameState gameState = bigTwoGameService.getGameState();

    // Retrieve the player from game state
    Player player = gameState.getPlayers().stream()
        .filter(p -> p.getId().equals(playRequest.getPlayerId())).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Player not found"));

    // Validate the player and cards
    if (player != null && cardsToPlay != null && !cardsToPlay.isEmpty()) {
      // Process the player's move
      bigTwoGameService.processPlayerMove(player, cardsToPlay);

      // Update and return the new game state
      return bigTwoGameService.getGameState();
    } else {
      // Handle invalid play request
      throw new IllegalArgumentException("Invalid play request");
    }
  }

  @MessageMapping("/pass")
  @SendTo("/topic/game-state")
  public GameState handlePass(@Payload PlayRequest passRequest) {
    // Fetch the current game state
    GameState gameState = bigTwoGameService.getGameState();

    // Find the player who is passing
    Player player = gameState.getPlayers().stream()
        .filter(p -> p.getId().equals(passRequest.getPlayerId())).findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Player not found"));

    // Mark the player as having passed this turn
    bigTwoGameService.processPlayerPass(player);

    // Update and return the new game state
    return bigTwoGameService.getGameState();
  }

  @MessageMapping("/sort-by-rank")
  // @SendTo("/topic/game-state")
  public void handleSortByRank(@Payload PlayRequest passRequest) {
    bigTwoGameService.sortByRank(passRequest.getPlayerId());
    // return bigTwoGameService.getGameState();
    simpMessagingTemplate.convertAndSend("/topic/game-state",
        bigTwoGameService.getGameState());

  }

  @MessageMapping("/sort-by-suit")
  // @SendTo("/topic/game-state")
  public void handleSortBySuit(@Payload PlayRequest passRequest) {
    bigTwoGameService.sortBySuit(passRequest.getPlayerId());
    // return bigTwoGameService.getGameState();
    simpMessagingTemplate.convertAndSend("/topic/game-state",
        bigTwoGameService.getGameState());

  }

  @Scheduled(fixedRate = 1000)
  public void broadcastGameState() {
    simpMessagingTemplate.convertAndSend("/topic/game-state",
        bigTwoGameService.getGameState());
  }
}
