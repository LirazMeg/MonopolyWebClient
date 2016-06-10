/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene.WaitingScene;

import MonopolyFX.component.PlayerLabel;
import controllers.GenericController;
import game.client.ws.Event;
import game.client.ws.EventType;
import game.client.ws.GameDetails;
import game.client.ws.GameDoesNotExists_Exception;
import game.client.ws.GameStatus;
import game.client.ws.InvalidParameters_Exception;
import game.client.ws.PlayerDetails;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.ComputerPlayer;
import models.Player;

/**
 * FXML Controller class
 *
 * @author Haim
 */
public class WaitingController extends GenericController implements Initializable {

    public static final int ZERO = 0;
    public static final String EMPTY = "";

    @FXML
    private Button backToMenu;
    @FXML
    private Button refresh;

    private SimpleBooleanProperty backToMenuProp;
    private SimpleBooleanProperty refreshProp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.backToMenuProp = new SimpleBooleanProperty(false);
        this.refreshProp = new SimpleBooleanProperty(false);
    }

    @FXML
    private void onRefresh(ActionEvent event) {
        System.out.println("Scene.WaitingScene.WaitingController.onRefresh()");
        this.refreshProp.set(true);
    }

    @FXML
    protected void onBackToMenu(ActionEvent event) {
        super.onReturn(event);
        this.backToMenuProp.set(true);
    }

    public SimpleBooleanProperty getBackToMenuProp() {
        return backToMenuProp;
    }

    public SimpleBooleanProperty getRefreshProp() {
        return refreshProp;
    }

    @Override
    public void resetScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void actionMethod(Timer timer) {
        try {
            this.eventToHandel = this.monopoly.getEvents(this.playerId, this.evntIndex);
            if (this.eventToHandel.size() > 0) {
                timer.cancel();
            }
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(WaitingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkIfEventStartGameExist() {
        boolean res = false;
        for (Event event : this.eventToHandel) {
            if ((event.getType().equals(EventType.GAME_START))) {
                res = true;
                break;
            }
        }
        return res;
    }

    public String getCurentPlayer() {
        try {
            this.eventToHandel = this.monopoly.getEvents(this.playerId, this.evntIndex);
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(WaitingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String name = "";
        for (Event event : this.eventToHandel) {
            if (event.getType().equals(EventType.PLAYER_TURN)) {
                name = event.getPlayerName();
                break;
            }

        }
        return name;
    }

    public List<PlayerLabel> getPlayerLabelList() {
        List<PlayerLabel> playerLabelList = new ArrayList<PlayerLabel>();

        List<Player> playersList = this.gameManager.getPlayers();
        int i = 1;
        PlayerLabel newPlayer;
        String imgPlayer = "";
        for (Player player : playersList) {
            if (player.getClass().equals(ComputerPlayer.class)) {
                imgPlayer = "Computer Player";
                newPlayer = new PlayerLabel(player, imgPlayer);
            } else {
                imgPlayer = "Player";
                newPlayer = new PlayerLabel(player, imgPlayer + i);
                i++;
            }
            playerLabelList.add(newPlayer);
        }
        showNode(playerLabelList.get(0));
        return playerLabelList;
    }
}
