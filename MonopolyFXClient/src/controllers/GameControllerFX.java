/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.List;

/**
 *
 * @author efrat
 */
public class GameControllerFX {

    private GameController spesificLogicGame;
    private int currentPlayerIndex;
    private final static int ZERO = 0;

    public GameControllerFX() {
        this.currentPlayerIndex = ZERO;
    }

    public GameController getLogicGame() {
        return spesificLogicGame;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setLogicGame(GameController logicGame) {
        this.spesificLogicGame = logicGame;
    }

    public void setNewGameFromFile(String xmlFileName, boolean uploadFile, boolean uploadFileFX) throws Exception {
        this.spesificLogicGame = new GameController(xmlFileName, uploadFile, uploadFileFX);
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void setCurrentPlayer(int playerIndex) {
        this.spesificLogicGame.setCurrentPlayerAccordingToIndex(playerIndex);
    }

    public void playGame() {

    }
    
        


}
