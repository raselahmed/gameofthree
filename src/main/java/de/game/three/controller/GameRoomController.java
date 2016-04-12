package de.game.three.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.game.three.model.PlayerDTO;
import de.game.three.service.GameRoomService;

@RestController
public class GameRoomController {

  private static final Logger LOG = LoggerFactory.getLogger(GameRoomController.class);

  @Autowired
  private GameRoomService     gameRoomService;

  /**
   * api for join the game for a player
   * 
   * @param playerName
   * @return
   */
  @RequestMapping(value = "/game/join/{playerName}", method = RequestMethod.GET)
  public ResponseEntity<PlayerDTO> joinGame(@PathVariable("playerName") final String playerName) {
    PlayerDTO playerDTO = null;
    try {
      playerDTO = gameRoomService.joinGame(playerName);
    } catch (Exception e) {
      LOG.error(e.getMessage(), e);
      return new ResponseEntity<PlayerDTO>(playerDTO, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<PlayerDTO>(playerDTO, HttpStatus.OK);
  }

  /**
   * api for start the game
   * 
   * @param 
   * @return
   */
  @RequestMapping(value = "/game/start", method = RequestMethod.PUT)
  public void startGame() {
    gameRoomService.startGame();
  }

}
