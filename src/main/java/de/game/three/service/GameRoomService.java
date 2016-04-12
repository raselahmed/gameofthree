package de.game.three.service;

import de.game.three.model.GameDTO;
import de.game.three.model.GameRoomStatus;
import de.game.three.model.PlayerDTO;

public interface GameRoomService {

  /**
   * join the game
   * 
   * @param playerName
   * @return
   */
  PlayerDTO joinGame(String playerName);

  /**
   * start the game
   * 
   */
  String startGame();

  /**
   * return all game activity
   * 
   */
  GameRoomStatus gameStatus();

  /**
   * return current game scenerio
   * 
   * @return
   */
  GameDTO getCurrentGameScenerio();

}
