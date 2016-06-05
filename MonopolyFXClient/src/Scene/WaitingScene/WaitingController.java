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
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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

    public boolean checkIfEventStartGameExist() {
        boolean res = false;
        for (Event event : this.eventToHandel) {
            if ((event.getType().equals(EventType.GAME_START))) {
                this.eventToHandel.remove(event);
                res = true;
                break;
            }
        }
        return res;
    }
}
