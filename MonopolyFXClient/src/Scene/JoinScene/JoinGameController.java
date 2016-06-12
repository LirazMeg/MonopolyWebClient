/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene.JoinScene;

import controllers.GenericController;
import game.client.ws.Event;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import game.client.ws.GameDetails;
import game.client.ws.GameDoesNotExists_Exception;
import game.client.ws.InvalidParameters_Exception;
import game.client.ws.MonopolyWebService;
import game.client.ws.PlayerDetails;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Player;

/**
 * FXML Controller class
 *
 * @author Barak
 */
public class JoinGameController extends GenericController implements Initializable {

    private static final String EMPTY = "";
    @FXML
    private ListView<?> listViewOfGames;
    @FXML
    private Label labelNamePlayer;
    @FXML
    private TextField textBoxPlayerName;
    @FXML
    private Label labelErrorMessage;
    @FXML
    private Label labelGameName;
    @FXML
    private Label labelErrorMessage1;
    @FXML
    private Label labelNumOfPlayer;
    @FXML
    private Label labelNumOfPcPlayer;
    @FXML
    private Label labelNumOfJoinPlayers;
    @FXML
    private HBox panePlayer1;
    @FXML
    private Label labelPlayer1;
    @FXML
    private HBox panePlayer2;
    @FXML
    private Label labelPlayer2;
    @FXML
    private HBox panePlayer3;
    @FXML
    private Label labelPlayer3;
    @FXML
    private HBox panePlayer4;
    @FXML
    private Label labelPlayer4;
    @FXML
    private HBox panePlayer5;
    @FXML
    private Label labelPlayer5;
    @FXML
    private HBox panePlayer6;
    @FXML
    private Label labelPlayer6;
    @FXML
    private Button buttonJoinGame;
    @FXML
    private Button buttonReturn;
    @FXML
    private RadioButton radioButtonP1;
    @FXML
    private RadioButton radioButtonP2;
    @FXML
    private RadioButton radioButtonP3;
    @FXML
    private RadioButton radioButtonP4;
    @FXML
    private RadioButton radioButtonP5;
    @FXML
    private RadioButton radioButtonP6;

    private SimpleBooleanProperty joinGame;
    private String namePlayer;
    private List<String> gameNamesList = new ArrayList<>();
    private ToggleGroup players;
    private List<game.client.ws.PlayerDetails> playersDetails;
    private List<Label> labelsPlayers;
    private GameDetails gameDetails;

    public static final int SETDETAILS = 1;
    public static final int JOINGAME = 2;
    public static final int WAITINGAMES = 3;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.returnToMenuProp = new SimpleBooleanProperty(false);
        this.joinGame = new SimpleBooleanProperty(false);
        this.players = new ToggleGroup();
        this.radioButtonP1.setToggleGroup(players);
        this.radioButtonP2.setToggleGroup(players);
        this.radioButtonP3.setToggleGroup(players);
        this.radioButtonP4.setToggleGroup(players);
        this.radioButtonP5.setToggleGroup(players);
        this.radioButtonP6.setToggleGroup(players);

        hideNode(buttonJoinGame);

    }

    public SimpleBooleanProperty getJoinGame() {
        return this.joinGame;
    }

    @FXML
    private void onInitDetails(MouseEvent event) {

    }

    @FXML
    private void onChooseGame(MouseEvent event) {
        this.gameName = (String) listViewOfGames.getSelectionModel().getSelectedItem();
        updatelabelPlayers(this.gameManager.getPlayers());
        if (this.gameName != null && !this.gameName.equals(EMPTY)) {
            getDetailsFromServer();
        }
    }

    @FXML
    private void onJoinGame(ActionEvent event) {
        if (isAName(textBoxPlayerName, labelErrorMessage)) {
            namePlayer = getText(textBoxPlayerName);
            joinGame();
        }
    }

    private List<Label> addLabels() {
        List<Label> labelsPlayers = new ArrayList<>();
        int i;
        for (i = 1; i <= this.gameDetails.getHumanPlayers(); i++) {
            switch (i) {
                case 1:
                    labelsPlayers.add(this.labelPlayer1);
                    break;
                case 2:
                    labelsPlayers.add(this.labelPlayer2);
                    break;
                case 3:
                    labelsPlayers.add(this.labelPlayer3);
                    break;
                case 4:
                    labelsPlayers.add(this.labelPlayer4);
                    break;
                case 5:
                    labelsPlayers.add(this.labelPlayer5);
                    break;
                case 6:
                    labelsPlayers.add(this.labelPlayer6);
                    break;
            }

        }
        for (int j = i; j <= 6; j++) {
            switch (j) {
                case 1:
                    hideNode(this.panePlayer1);
                    break;
                case 2:
                    hideNode(this.panePlayer2);
                    break;
                case 3:
                    hideNode(this.panePlayer3);
                    break;
                case 4:
                    hideNode(this.panePlayer4);
                    break;
                case 5:
                    hideNode(this.panePlayer5);
                    break;
                case 6:
                    hideNode(this.panePlayer6);
                    break;
            }

        }

        return labelsPlayers;
    }

    @Override
    protected void actionMethod(Timer timer) {
        ObservableList items = FXCollections.observableArrayList();
        createNewThread(WAITINGAMES);
        Platform.runLater(() -> onShow(items));
    }

    private void onShow(ObservableList items) {
        items.addAll(gameNamesList);
        Platform.runLater(() -> listViewOfGames.setItems(items));
    }

    private void hideRadioButtons() {
        if (radioButtonP1.isSelected()) {
            hideNode(radioButtonP1);
        } else if (radioButtonP2.isSelected()) {
            hideNode(radioButtonP2);

        } else if (radioButtonP3.isSelected()) {
            hideNode(radioButtonP3);

        } else if (radioButtonP4.isSelected()) {
            hideNode(radioButtonP4);
        }

    }

    @FXML
    private void onJoinPlayerName(ActionEvent event) {
        String name = EMPTY;

        if (radioButtonP1.isSelected()) {
            name = labelPlayer1.getText();

        } else if (radioButtonP2.isSelected()) {
            name = labelPlayer2.getText();

        } else if (radioButtonP3.isSelected()) {
            name = labelPlayer3.getText();

        } else if (radioButtonP4.isSelected()) {
            name = labelPlayer4.getText();
        }

        textBoxPlayerName.setText(name);
    }

    @Override
    protected void createNewThread(int selection) {
        Thread thread = new Thread();
        switch (selection) {
            case SETDETAILS:
                thread = new Thread(this::getDetailsFromServer);
                break;
            case JOINGAME:
                thread = new Thread(this::joinGame);
                break;
            case WAITINGAMES:
                thread = new Thread(this::getWaitingGamesFromServer);
                break;
            default:
                break;
        }

        thread.setDaemon(true);
        thread.start();
    }

    @Override
    protected void guiSet(int selection) {
        switch (selection) {
            case SETDETAILS:
                guiSetDetails();
                break;
            case JOINGAME:
                guiJoinGame();
                break;
            default:
                break;
        }
    }

    private void getDetailsFromServer() {
        try {
            this.gameDetails = this.monopoly.getGameDetails(this.gameName);
            this.playersDetails = this.monopoly.getPlayersDetails(this.gameName);
            this.labelsPlayers = addLabels();
        } catch (GameDoesNotExists_Exception ex) {
            Logger.getLogger(JoinGameController.class.getName()).log(Level.SEVERE, null, ex);

        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                guiSetDetails();
            }

        });
    }

    private void guiSetDetails() {
        if (gameDetails != null) {
            labelGameName.setText(gameDetails.getName());
            labelNumOfJoinPlayers.setText(((Integer) gameDetails.getJoinedHumanPlayers()).toString());
            labelNumOfPlayer.setText(((Integer) gameDetails.getHumanPlayers()).toString());
            labelNumOfPcPlayer.setText(((Integer) gameDetails.getComputerizedPlayers()).toString());
            addPayersNames(this.labelsPlayers, this.playersDetails, true);
            //setXMLGame(gameDetails, playersDetails);
            showNode(this.textBoxPlayerName);
            showNode(this.buttonJoinGame);
        }
    }

    private void joinGame() {
        try {
            this.playerId = this.monopoly.joinGame(gameName, namePlayer);

        } catch (GameDoesNotExists_Exception gdne) {
            System.out.print(gdne.getMessage());
        } catch (InvalidParameters_Exception ipx) {
            showError(labelErrorMessage, ipx.getMessage());
        }
    }

    private void guiJoinGame() {
        hideRadioButtons();
        this.joinGame.set(true);
    }

    private void getWaitingGamesFromServer() {
        this.gameNamesList = this.monopoly.getWaitingGames();
    }

    @Override
    public void resetScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    public void onReturn(ActionEvent event) {
    }

    public void setListGamesName(List<String> waitingGames) {
        for (String name : waitingGames) {
            this.gameNamesList.add(name);
        }
    }

    public void setListViewGames() {
        ObservableList items = FXCollections.observableArrayList();
        items.addAll(this.gameNamesList);
        this.listViewOfGames.setItems(items);
    }

    private void updatelabelPlayers(List<Player> players) {
        int i = 0;
        for (Player player : players) {
            this.labelsPlayers.get(i).setText(player.getName());
            i++;
        }
    }

    private void swich(int humanPlayers) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
