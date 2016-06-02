/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import controllers.GameController;
import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author efrat
 */
public abstract class GenericController {

    protected GameController gameManager;
    protected Stage mainStage;
    protected String fileName = "";

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

    abstract public void resetScene();

    public void hideNode(Node node) {
        node.setDisable(true);
        node.setVisible(false);
    }
}
