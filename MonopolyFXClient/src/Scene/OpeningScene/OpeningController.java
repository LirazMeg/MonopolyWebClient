/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene.OpeningScene;

import controllers.GenericController;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Barak
 */
public class OpeningController extends GenericController implements Initializable {

    public static final String EMPTY = "";
    private SimpleBooleanProperty createGame;
    private SimpleBooleanProperty joinGame;
    private File fileToLoad;

    @FXML
    private Button buttonCreateGame;
    @FXML
    private Button buttonJoinGame;
    @FXML
    private Label errorLabel;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createGame = new SimpleBooleanProperty(false);
        joinGame = new SimpleBooleanProperty(false);
    }

    @FXML
    private void OnCreateGame(ActionEvent event) {
        createGame.set(true);
        errorLabel.setText(EMPTY);
    }

    @FXML
    private void OnJoinGame(ActionEvent event) {
        joinGame.set(true);
        errorLabel.setText(EMPTY);
    }

    public SimpleBooleanProperty getCreateGame() {
        return createGame;
    }

    public SimpleBooleanProperty getJoinGame() {
        return joinGame;
    }

    private void guiJoin() {
        errorLabel.setText(EMPTY);
        joinGame.set(true);
    }

    private void guiError(String msg) {
        errorLabel.setText(msg);
    }

    @Override
    public void resetScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
