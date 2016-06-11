/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene.PlayerRegistretionScene;

import MonopolyFX.component.PlayerLabel;
import controllers.GenericController;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import models.ComputerPlayer;
import models.HumanPlayer;
import models.Player;

/**
 * FXML Controller class
 *
 * @author efrat
 */
public class PlayersRegistretionController extends GenericController implements Initializable {

    @FXML
    private TextField playerName;
    @FXML
    private Button submitPlayerButton;
    @FXML
    private Button startGameButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Label playerNameLabel;

    private SimpleBooleanProperty submitPlayerButtonProp;
    private SimpleBooleanProperty startGameButtonProp;
    private SimpleBooleanProperty startGameButtonPropAfterUpLoad;
    private int humanPlayersCounter = 0;
    private int numOfPlayers = 0;
    private int playersNameCounter = 1;
    private boolean newUpLoadGame = false;

    public TextField getPlayerName() {
        return playerName;
    }

    public void setErrorLabel(String msg) {
        this.errorLabel.setText(msg);
        showNode(this.errorLabel);

    }

    public void setHumanPlayersCounterAndNumOfPlayers(int humanPlayersCounter, int numOfPlayers) {
        this.humanPlayersCounter = humanPlayersCounter;
        this.numOfPlayers = numOfPlayers;
//        int numOfComputerPlayers = numOfPlayers - humanPlayersCounter;
//        if (numOfComputerPlayers > 0) {
//            for (int i = 1; i <= numOfComputerPlayers; i++) {
//                String name = "Computer Player " + i;
//                this.gameManager.addComputerPlayerToPlayersList(name);
//            }
//        }

    }

    public SimpleBooleanProperty getStartGameButtonProp() {
        return startGameButtonProp;
    }

    public SimpleBooleanProperty getSubmitPlayerButtonProp() {
        return submitPlayerButtonProp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.playerNameLabel.setText("Please Enter Your Name:");
        this.playersNameCounter++;
        this.startGameButton.setDisable(true);
        this.startGameButton.setVisible(false);
        this.errorLabel.setVisible(false);

        submitPlayerButtonProp = new SimpleBooleanProperty(false);
        startGameButtonProp = new SimpleBooleanProperty(false);
        startGameButtonPropAfterUpLoad = new SimpleBooleanProperty(false);
    }

    public void OnSubmitPlayerButton(ActionEvent event) {
        errorLabel.setText("");

        if (isAName(this.playerName, this.errorLabel)) {

            playerNameLabel.setText("Well Done! Now You Can Start Play Monopoly!");
            // this.gameManager.addPlayerToPlayersList(playerName.getText());
            hideNode(playerName);
            hideNode(submitPlayerButton);
            showNode(startGameButton);
            submitPlayerButtonProp.set(true);
        }

    }

    public void OnStartGameButton(ActionEvent event) {
        this.humanPlayersCounter = this.gameManager.getNumOfHumanPlayers();
        //this.gameManager.setCurrentPlayer(this.gameManager.getPlayers().get(this.gameManager.getPleyerIndex()));
        //getPlayerLabelList();
        // setCurrentPlayInLogic();
        startGameButtonProp.set(true);

        //resetValues();
//        if (newUpLoadGame) {
//            startGameButtonPropAfterUpLoad.set(true);
//        } else {
//            startGameButtonProp.set(true);
//        }
    }

    private boolean checkIfAlreadyExists(String name) {
        boolean isExsits = false;
        String toComper = "";
        for (int i = 0; i < this.gameManager.getPlayers().size(); i++) {
            toComper = this.gameManager.getPlayers().get(i).getName();
            if (name.equals(toComper)) {
                isExsits = true;
            }
        }
        return isExsits;
    }

    private boolean checkIfEmpty(String name) {
        return name.isEmpty();
    }

    public List<PlayerLabel> getPlayerLabelList() {
        List<PlayerLabel> playerLabelList = new ArrayList<>();
        List<Player> playersList = this.gameManager.getPlayers();
        int i = 1;
        PlayerLabel newPlayer;
        String imgPlayer = "Player";

        for (Player player : playersList) {
            imgPlayer = "Player";
            newPlayer = new PlayerLabel(player, imgPlayer + i);
            i++;
            playerLabelList.add(newPlayer);
        }
        showNode(playerLabelList.get(0));
        return playerLabelList;
    }

    private void setCurrentPlayInLogic() {
        int currentPlayerIndex = this.gameManager.getPleyerIndex();
        this.gameManager.setCurrentPlayer(this.gameManager.getPlayers().get(currentPlayerIndex));
    }

    /**
     *
     */
    @Override
    public void resetScene() {

        this.playersNameCounter = 1;
        this.newUpLoadGame = true;
        this.playerNameLabel.setText("Human Player " + playersNameCounter + " Please Enter Your Name:");
        this.playersNameCounter++;
        hideNode(startGameButton);
        this.errorLabel.setVisible(false);
        showNode(this.submitPlayerButton);
        showNode(this.playerName);
        //getPlayerLabelList();
        submitPlayerButtonProp.set(false);
        startGameButtonProp.set(false);
        startGameButtonPropAfterUpLoad.set(false);
    }

    public SimpleBooleanProperty getStartGameButtonPropAfterUpLoad() {
        return startGameButtonPropAfterUpLoad;
    }
}
