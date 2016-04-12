package de.game.three.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import de.game.three.model.GameRoomStatus;
import de.game.three.model.PlayerDTO;

@Component
@ManagedBean
@ViewScoped
public class GameRoomBean implements Serializable {

  private static final long  serialVersionUID = 8935229426664405741L;

  public static final String SERVER_URI       = "http://localhost:9090/gameofthree/";

  private String             playerName;

  private RestTemplate       restTemplate     = new RestTemplate();

  private PlayerDTO          playerDTO        = null;

  private boolean            playerStatus     = false;

  public boolean getPlayerStatus() {
    return playerStatus;
  }

  public void joinGame() {
    if (!StringUtils.hasText(getPlayerName())) {
      try {
        setPlayerName("Player" + getRandomNumber());
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("playerName", getPlayerName());
        playerDTO = restTemplate.getForObject(SERVER_URI + "/game/join/{playerName}", PlayerDTO.class, vars);
        if (GameRoomStatus.GAME_WAITING.equals(playerDTO.getGameRoomStatus()) || GameRoomStatus.GAME_READY.equals(playerDTO.getGameRoomStatus())) {
          playerStatus = true;
        }
      } catch (Exception e) {

      }
    }
  }

  public void startGame() {
    try {
      Map<String, String> vars = new HashMap<String, String>();
      vars.put("playerName", getPlayerName());
      restTemplate.put(SERVER_URI + "/game/start", null);
    } catch (Exception e) {
    }
    refresh();
  }

  /**
   * @return Returns the playerName.
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * @param playerName
   *          The playerName to set.
   */
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public void refresh() {
    playerName = null;
    playerDTO = null;
    playerStatus = false;
  }

  public String getStatus() {
    String status = null;
    status =
        playerDTO != null ? ("Action Result: " + playerDTO.getName() + " | Game Status : " + playerDTO.getGameRoomStatus() + " | Value : "
            + playerDTO.getValue() + " | Palyer(s) in GameRoom : " + playerDTO.getTotalPlayers()) : "";
    playerName = null;
    playerDTO = null;
    playerStatus = false;
    return status;
  }

  private int getRandomNumber() {
    return new Random().nextInt(500) + 3;
  }
}
