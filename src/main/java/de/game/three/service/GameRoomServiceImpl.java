package de.game.three.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import de.game.three.model.GameActionDTO;
import de.game.three.model.GameDTO;
import de.game.three.model.GameRoomStatus;
import de.game.three.model.PlayerDTO;

@Service
public class GameRoomServiceImpl implements GameRoomService {

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  private GameDTO                      gameDTO         = new GameDTO();

  private final static int[]           parameterValues = { 0, -1, 1 };

  /**
   * service for joining the game for a player
   * 
   * @see de.game.three.service.GameRoomService#joinGameRoom(java.lang.String)
   */
  @Override
  public PlayerDTO joinGame(String playerName) {
    final PlayerDTO playerDTO = new PlayerDTO();
    if (StringUtils.hasText(playerName)) {
      if (GameRoomStatus.GAME_FINISH.equals(gameDTO.getGameRoomStatus())) {
        gameDTO = new GameDTO();
        gameDTO.setValue(getRandomNumber());
        addPlayer(playerName, playerDTO, GameRoomStatus.GAME_WAITING);
      } else if (GameRoomStatus.GAME_WAITING.equals(gameDTO.getGameRoomStatus())) {
        if (!gameDTO.getPalyers().contains(new PlayerDTO(playerName))) {
          addPlayer(playerName, playerDTO, GameRoomStatus.GAME_READY);
        }
      } else {
        addPlayer("Can't Join", playerDTO, gameDTO.getGameRoomStatus());
      }
    }
    return playerDTO;
  }

  /**
   * 
   * service for starting the game
   * 
   * @see de.game.three.service.GameRoomService#startGame()
   */
  @Override
  public String startGame() {
    String winningPlayer = null;
    if (gameDTO.getPalyers().size() >= 2) {
      final String firstPlayer = gameDTO.getPalyers().get(0).getName();
      final String secondPlayer = gameDTO.getPalyers().get(1).getName();
      gameDTO.setGameRoomStatus(GameRoomStatus.GAME_RUNNING);
      while (gameDTO.getValue() > 1) {
        final Integer currentValue = gameDTO.getValue();
        final GameActionDTO gameActionDTO = new GameActionDTO(gameDTO.getCurrentAction(), -1, -1);
        checkForWin(gameActionDTO, currentValue);
        if (gameActionDTO.getValue() == -1) {
          checkForDivisibleByThree(gameActionDTO, currentValue);
        }
        winningPlayer = gameDTO.getCurrentAction();
        gameDTO.setValue(gameActionDTO.getValue());
        gameDTO.setCurrentAction(secondPlayer.equals(gameDTO.getCurrentAction()) ? firstPlayer : secondPlayer);
        sendGameActionStatus(gameActionDTO);
      }
      gameDTO = new GameDTO();
    }
    return winningPlayer;
  }

  /**
   * return current game scenerio
   * 
   * @see de.game.three.service.GameRoomService#getCurrentGameScenerio()
   */
  @Override
  public GameDTO getCurrentGameScenerio() {
    return gameDTO;
  }

  /**
   * send game current action details
   * 
   * @param gameActionDTO
   */
  private void sendGameActionStatus(final GameActionDTO gameActionDTO) {
    messagingTemplate.convertAndSend("/topic/gamestatus", gameActionDTO);
    try {
      Thread.sleep(500);// 1000 milliseconds is one second.
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * check for winning condition
   * 
   * @param gameActionDTO
   * @param currentValue
   */
  private void checkForWin(GameActionDTO gameActionDTO, Integer currentValue) {
    if (currentValue < 3) {
      return;
    }
    for (final int parameter : parameterValues) {
      if (isWinner(currentValue, parameter)) {
        gameActionDTO.setValue(getActionValue(currentValue, parameter));
        gameActionDTO.setAdded(parameter);
        break;
      }
    }
  }

  /**
   * check for divisible by three or not
   * 
   * @param gameActionDTO
   * @param currentValue
   */
  private void checkForDivisibleByThree(GameActionDTO gameActionDTO, Integer currentValue) {
    if (currentValue < 3) {
      gameActionDTO.setValue(1);
      gameActionDTO.setAdded(1);
      return;
    }
    for (final int parameter : parameterValues) {
      if (isDivisibleByThree(currentValue, parameter)) {
        gameActionDTO.setValue(getActionValue(currentValue, parameter));
        gameActionDTO.setAdded(parameter);
        break;
      }
    }
  }

  /**
   * add plaer to gameroom
   * 
   * @param playerName
   * @param playerDTO
   * @param gameRoomStatus
   */
  private void addPlayer(final String playerName, PlayerDTO playerDTO, GameRoomStatus gameRoomStatus) {
    playerDTO.setName(playerName);
    playerDTO.setGameRoomStatus(gameRoomStatus);
    playerDTO.setValue(gameDTO.getValue());
    gameDTO.setGameRoomStatus(gameRoomStatus);
    gameDTO.setCurrentAction(playerName);
    gameDTO.getPalyers().add(playerDTO);
    playerDTO.setTotalPlayers(gameDTO.getPalyers().size());
  }

  /**
   * 
   * @param value
   * @param parameter
   * @return
   */
  private boolean isWinner(int value, int parameter) {
    return getActionValue(value, parameter) == 1;
  }

  /**
   * 
   * @param parameter
   * @return
   */
  private int getActionValue(int value, int parameter) {
    return (int) Math.ceil((double) (value + parameter) / 3);
  }

  /**
   * 
   * @param value
   * @param parameter
   * @return
   */
  private boolean isDivisibleByThree(int value, int parameter) {
    return (value + parameter) % 3 == 0;
  }

  /**
   * return all game activity
   * 
   * @see de.game.three.service.GameRoomService#gameActivity()
   */
  @Override
  public GameRoomStatus gameStatus() {
    return gameDTO.getGameRoomStatus();
  }

  /**
   * return a random number between 3 and 500
   * 
   * @return
   */
  private int getRandomNumber() {
    return new Random().nextInt(500) + 3;
  }

}
