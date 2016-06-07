/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene.WaitingScene;

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
        } catch (InvalidParameters_Exception ex) {
            Logger.getLogger(WaitingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 public boolean checkIfEventStartGameExist() {
        boolean res = false;
        for (Event event : this.eventToHandel) {
            if ((event.getType().equals(EventType.GAME_START))) {
           //     this.eventToHandel.remove(event);
                res = true;
                break;
            }
        }
        return res;
    }
    public String getCurentPlayer() {
        String name = "";
        for (Event event : this.eventToHandel) {
            if (event.getType().equals(EventType.PLAYER_TURN)) {
                name = event.getPlayerName();
            }

        }
        return name;
    }
}
