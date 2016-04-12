package de.game.three.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameDTO implements Serializable {

  private static final long serialVersionUID = -7081738348971992350L;

  private List<PlayerDTO>   players;

  private Integer           value            = 0;

  private String            currentAction;

  private String            winner;

  private GameRoomStatus    gameRoomStatus   = GameRoomStatus.GAME_FINISH;

  /**
   * @return the gameName
   */
  public List<PlayerDTO> getPalyers() {
    if (players == null) {
      players = new ArrayList<>();
    }
    return players;
  }

  /**
   * @return the value
   */
  public Integer getValue() {
    return value;
  }

  /**
   * @param value
   *          the value to set
   */
  public void setValue(Integer value) {
    this.value = value;
  }

  /**
   * @return Returns the currentAction.
   */
  public String getCurrentAction() {
    return currentAction;
  }

  /**
   * @param currentAction
   *          The currentAction to set.
   */
  public void setCurrentAction(String currentAction) {
    this.currentAction = currentAction;
  }

  /**
   * @return the winner
   */
  public String getWinner() {
    return winner;
  }

  /**
   * @return the gameRoomStatus
   */
  public GameRoomStatus getGameRoomStatus() {
    return gameRoomStatus;
  }

  /**
   * @param gameRoomStatus
   *          the gameRoomStatus to set
   */
  public void setGameRoomStatus(GameRoomStatus gameRoomStatus) {
    this.gameRoomStatus = gameRoomStatus;
  }

  /**
   * @param winner
   *          the winner to set
   */
  public void setWinner(String winner) {
    this.winner = winner;
  }

}
