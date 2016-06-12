/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import MonopolyFX.MonopolyGameApp;
import controllers.GameController;
import game.client.ws.Event;
import game.client.ws.EventType;
import game.client.ws.GameDetails;
import game.client.ws.GameDoesNotExists_Exception;
import game.client.ws.GameStatus;
import game.client.ws.InvalidParameters_Exception;
import game.client.ws.MonopolyWebService;
import game.client.ws.MonopolyWebServiceService;
import game.client.ws.PlayerDetails;
import game.client.ws.PlayerStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author efrat
 */
public abstract class GenericController {

    public static final int ZERO = 0;
    protected GameController gameManager;
    protected Stage mainStage;
    protected String fileName = "";
    protected MonopolyWebService monopoly;
    protected MonopolyWebServiceService monopolyService;
    protected Integer playerId;
    protected String playerName;
    protected String gameName;
    protected int evntIndex = 0;
    protected SimpleBooleanProperty returnToMenuProp;
    protected List<Event> eventToHandel = new ArrayList<>();
    protected boolean isActive = false;
    //   protected int evntIndex = 0;

    public void setEventToHandel(List<Event> eventToHandel) {
        this.eventToHandel = eventToHandel;
    }

    public void addToEventId(int i) {
        this.evntIndex += i;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setMonopoly(MonopolyWebService monopoly) {
        this.monopoly = monopoly;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public String getFileName() {
        return fileName;
    }

    public MonopolyWebService getMonopoly() {
        return monopoly;
    }

    public MonopolyWebServiceService getMonopolyService() {
        return monopolyService;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public String getGameName() {
        return gameName;
    }

    public int getEventId() {
        return evntIndex;
    }

    public SimpleBooleanProperty getReturnToMenuProp() {
        return returnToMenuProp;
    }

    public GameController getGameManager() {
        return gameManager;
    }

    public void setGameManager(GameController gameManagerToSet) {
        this.gameManager = gameManagerToSet;
    }

    public void setStage(Stage stage) {
        this.mainStage = stage;
    }

    public void setFileName(String fileNameToSet) {
        this.fileName = fileNameToSet;
    }

    public void showNode(Node node) {
        node.setDisable(false);
        node.setVisible(true);

        FadeTransition animation = new FadeTransition();
        animation.setNode(node);
        animation.setDuration(Duration.seconds(0.9));
        animation.setFromValue(0.0);
        animation.setToValue(1.0);

        animation.play();
    }

    public void showFadeTransition(Node node) {
        FadeTransition ft = new FadeTransition(Duration.millis(5000), node);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }

    public void setMonopolyServic(MonopolyWebService monopoly) {
        this.monopoly = monopoly;
    }

    abstract public void resetScene();

    public void hideNode(Node node) {
        node.setDisable(true);
        node.setVisible(false);
    }

    public void timing() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                actionMethod(timer);
            }
        }, 1000, 1000);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void addPayersNames(List<Label> labelsPlayers, List<game.client.ws.PlayerDetails> playersDetails, boolean isSecenJoin) {
        int maxJoinPlayers = playersDetails.size();
        int index = 0;
        game.client.ws.PlayerDetails player;
        for (Label labelPlayer : labelsPlayers) {
            if (maxJoinPlayers > ZERO) {
                showNode(labelPlayer);
                player = playersDetails.get(index);
                if (player.getStatus().equals(PlayerStatus.ACTIVE) || isSecenJoin) {
                    labelPlayer.setText(player.getName());
                }
                index++;
            } else {
                labelPlayer.setText("");
                hideNode(labelPlayer);
            }

            maxJoinPlayers--;
        }
    }

    public void setGame(String name, Integer clientId, int eventID) {
        gameName = name;
        this.playerId = clientId;
        this.evntIndex = eventID;
    }

    protected void actionMethod(Timer timer) {
    }

    ;
    protected void createNewThread(int selection) {
    }

    ;
    protected void guiSet(int selection) {
    }

    ;

    protected void onReturn(ActionEvent event) {
        Platform.runLater(() -> returnToMenuProp.set(true));
    }

    protected String getText(TextField box) {
        return box.getText();
    }

    protected boolean isAName(TextField box, Label lable) {
        String name;
        boolean res = false;
        if (box != null) {
            name = getText(box);
            res = !name.isEmpty() && name.matches("[a-zA-Z]+");// if is letters
            if (!res) {
                lable.setText("This name (" + name + ")is illigel, please try name witch leters only, Try again");
                lable.setVisible(!res);
                box.clear();
            }
        }
        return res;
    }

    protected void showError(Label lable, String message) {
        lable.setText(message);
        lable.setVisible(true);
        FadeTransition animation = new FadeTransition();
        animation.setNode(lable);
        animation.setDuration(Duration.seconds(0.3));
        animation.setFromValue(0.0);
        animation.setToValue(1.0);
        animation.play();
    }

    public void getEventsAndFiltr(MonopolyWebService monopoly) throws InvalidParameters_Exception, GameDoesNotExists_Exception {

        List<Event> events = monopoly.getEvents(this.evntIndex, this.playerId);
        this.evntIndex += events.size();
        PlayerDetails playerDetails = monopoly.getPlayerDetails(this.playerId);
        String name = playerDetails.getName();

        for (Event event : events) {
            if (event.getPlayerName().equals(name)) {
                this.eventToHandel.add(event);
            }
        }
        //   return eventsFilter;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public boolean isGameActive() {
        return this.isActive;
    }

    public void setGameStatus() {
        boolean res = false;
        try {
            GameDetails gameDetails = this.monopoly.getGameDetails(gameName);
            if (gameDetails.getStatus().equals(GameStatus.ACTIVE)) {
                res = true;
            }
        } catch (GameDoesNotExists_Exception ex) {
            Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.isActive = res;
    }
}
