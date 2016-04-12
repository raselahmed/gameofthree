package de.game.three.model;

import java.io.Serializable;

public class PlayerDTO implements Serializable {

  private static final long serialVersionUID = -7081738348971992350L;

  private String            name;

  private GameRoomStatus    gameRoomStatus   = GameRoomStatus.GAME_FINISH;

  private Integer           value            = 0;

  private Integer           totalPlayers;

  public PlayerDTO() {
  }

  public PlayerDTO(String name) {
    this.name = name;
  }

  public PlayerDTO(String name, GameRoomStatus gameRoomStatus) {
    this.name = name;
    this.gameRoomStatus = gameRoomStatus;
  }

  /**
   * @return Returns the name.
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          The name to set.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return Returns the gameRoomStatus.
   */
  public GameRoomStatus getGameRoomStatus() {
    return gameRoomStatus;
  }

  /**
   * @param gameRoomStatus
   *          The gameRoomStatus to set.
   */
  public void setGameRoomStatus(GameRoomStatus gameRoomStatus) {
    this.gameRoomStatus = gameRoomStatus;
  }

  /**
   * @return Returns the value.
   */
  public Integer getValue() {
    return value;
  }

  /**
   * @param value
   *          The value to set.
   */
  public void setValue(Integer value) {
    this.value = value;
  }

  /**
   * @return Returns the totalPlayers.
   */
  public Integer getTotalPlayers() {
    return totalPlayers;
  }

  /**
   * @param totalPlayers
   *          The totalPlayers to set.
   */
  public void setTotalPlayers(Integer totalPlayers) {
    this.totalPlayers = totalPlayers;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PlayerDTO other = (PlayerDTO) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
}
