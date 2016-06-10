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

    private GameController spesificGame;
    private int currentPlayerIndex;
    private final static int ZERO = 0;

    public GameControllerFX() {
        this.currentPlayerIndex = ZERO;
    }

    public GameController getSpesificGame() {
        return this.spesificGame;
    }

    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }

    public void setLogicGame(GameController logicGame) {
        this.spesificGame = logicGame;
    }

    public void setNewGameFromFile(String xmlFileName, boolean uploadFile, boolean uploadFileFX) throws Exception {
        this.spesificGame = new GameController(xmlFileName, uploadFile, uploadFileFX);
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }

    public void setCurrentPlayer(int playerIndex) {
        this.spesificGame.setCurrentPlayerAccordingToIndex(playerIndex);
    }

    public void playGame() {

    }
    
        


}
