package com.bc2405p.poker_game.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

  @GetMapping("/")
  public String game() {
    return "game";
  }

}
