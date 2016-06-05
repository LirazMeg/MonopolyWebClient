/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene.GameOverScene;

import controllers.GenericController;
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
 * @author efrat
 */
public class GameOverController extends GenericController implements Initializable {

    @FXML
    private Button playAnotherGameButton;
    @FXML
    private Button exitButton;
    @FXML
    private Label winPlayerName;

    private SimpleBooleanProperty playAnotherGameButtonProp;
    private SimpleBooleanProperty exitButtonProp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.exitButtonProp = new SimpleBooleanProperty(false);
        this.playAnotherGameButtonProp = new SimpleBooleanProperty(false);
        showNode(winPlayerName);
    }

    public void setWinnerName(String name) {
        this.winPlayerName.setText(name);
    }

    public SimpleBooleanProperty getPlayAnotherGameButtonProp() {
        return playAnotherGameButtonProp;
    }

    public SimpleBooleanProperty getExitButtonProp() {
        return exitButtonProp;
    }

    public void OnplayAnotherGameButton(ActionEvent event) {
        this.playAnotherGameButtonProp.set(true);
    }

    public void OnexitButton(ActionEvent event) {

        this.exitButtonProp.set(true);
    }

    @Override
    public void resetScene() {
        this.exitButtonProp.set(false);
        this.playAnotherGameButtonProp = new SimpleBooleanProperty(false);
        this.winPlayerName.setText(this.gameManager.getCurrentPlayer().getName());
    }

  
}
