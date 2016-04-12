package de.game.three;

import main.Main;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.game.three.model.GameRoomStatus;
import de.game.three.model.PlayerDTO;
import de.game.three.service.GameRoomService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Main.class)
@WebIntegrationTest
public class TestGameRoomService {

  @Autowired
  private GameRoomService gameRoomService;

  @Test
  public void tesJoinGameRoom() {
    final PlayerDTO playerDTO = gameRoomService.joinGame("Player1");
    Assert.assertEquals(playerDTO.getGameRoomStatus(), GameRoomStatus.GAME_WAITING);
    final PlayerDTO playerDTO1 = gameRoomService.joinGame("Player2");
    Assert.assertEquals(playerDTO1.getGameRoomStatus(), GameRoomStatus.GAME_READY);
  }

  @Test
  public void tesStartGame() {
    PlayerDTO playerDTO1;
    PlayerDTO playerDTO2;
    
    playerDTO1 = gameRoomService.joinGame("Player1");
    Assert.assertEquals(playerDTO1.getGameRoomStatus(), GameRoomStatus.GAME_WAITING);
    playerDTO2 = gameRoomService.joinGame("Player2");
    Assert.assertEquals(playerDTO2.getGameRoomStatus(), GameRoomStatus.GAME_READY);
    /* we assume that this 56 is setup by player1, service internal logic is like that value is setup by first player */
    gameRoomService.getCurrentGameScenerio().setValue(56);
    final String winningPlayer1 = gameRoomService.startGame();
    Assert.assertEquals(winningPlayer1, "Player1");

    playerDTO1 = gameRoomService.joinGame("Player1");
    Assert.assertEquals(playerDTO1.getGameRoomStatus(), GameRoomStatus.GAME_WAITING);
    playerDTO2 = gameRoomService.joinGame("Player2");
    Assert.assertEquals(playerDTO2.getGameRoomStatus(), GameRoomStatus.GAME_READY);
    /* we assume that this 56 is setup by player1, service internal logic is like that value is setup by first player */
    gameRoomService.getCurrentGameScenerio().setValue(15);
    final String winningPlayer2 = gameRoomService.startGame();
    Assert.assertEquals(winningPlayer2, "Player2");

  }
}
