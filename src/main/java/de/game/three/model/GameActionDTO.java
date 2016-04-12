package de.game.three.model;

import java.io.Serializable;

/**
 * Rasel AHmed
 */
public class GameActionDTO implements Serializable {
  private static final long serialVersionUID = -7327033702830459605L;

  private String            moveBy;

  private Integer           added;

  private Integer           value;

  public GameActionDTO() {
  }

  public GameActionDTO(String moveBy, int added, int value) {
    this.moveBy = moveBy;
    this.added = added;
    this.value = value;
  }

  /**
   * @return Returns the moveBy.
   */
  public String getMoveBy() {
    return moveBy;
  }

  /**
   * @param moveBy
   *          The moveBy to set.
   */
  public void setMoveBy(String moveBy) {
    this.moveBy = moveBy;
  }

  /**
   * @return Returns the added.
   */
  public Integer getAdded() {
    return added;
  }

  /**
   * @param added
   *          The added to set.
   */
  public void setAdded(Integer added) {
    this.added = added;
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

}
