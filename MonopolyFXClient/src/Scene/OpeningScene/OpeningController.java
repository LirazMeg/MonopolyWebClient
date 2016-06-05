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
    private SimpleBooleanProperty createGameProp;
    private SimpleBooleanProperty joinGameProp;
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
        createGameProp = new SimpleBooleanProperty(false);
        joinGameProp = new SimpleBooleanProperty(false);
    }

    @FXML
    private void OnCreateGame(ActionEvent event) {
        createGameProp.set(true);
        errorLabel.setText(EMPTY);
    }

    @FXML
    private void OnJoinGame(ActionEvent event) {
        this.joinGameProp.set(true);
       this.errorLabel.setText(EMPTY);
    }

    public SimpleBooleanProperty getCreateGameProp() {
        return this.createGameProp;
    }

    public SimpleBooleanProperty getJoinGameProp() {
        return this.joinGameProp;
    }

    private void guiJoin() {
        this.errorLabel.setText(EMPTY);
        this.joinGameProp.set(true);
    }

    private void guiError(String msg) {
        this.errorLabel.setText(msg);
    }

    @Override
    public void resetScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
