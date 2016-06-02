/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene.WaitingScene;

import controllers.GenericController;
import game.client.ws.GameDetails;
import game.client.ws.GameDoesNotExists_Exception;
import game.client.ws.GameStatus;
import game.client.ws.InvalidParameters_Exception;
import game.client.ws.PlayerDetails;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class WaitingController extends GenericController implements Initializable
{
    public static final int ZERO = 0;
    public static final String EMPTY = "";
    @FXML
    private CheckBox checkBoxPlayer1;
    @FXML
    private CheckBox checkBoxPlayer2;
    @FXML
    private CheckBox checkBoxPlayer3;
    @FXML
    private CheckBox checkBoxPlayer4;
    @FXML
    private CheckBox checkBoxPlayer5;
    @FXML
    private CheckBox checkBoxPlayer6;
    @FXML
    private Label labelP1;
    @FXML
    private Label labelP2;
    @FXML
    private Label labelP3;
    @FXML
    private Label labelP4;
    @FXML
    private Label labelP5;
    @FXML
    private Label labelP6;
    
    private List<CheckBox> checkBoxList;
    private List<Label> labelList;
    private SimpleBooleanProperty allPlayersConnected;
    private GameDetails gameDetails;
    private PlayerDetails playerDetails;
    private List<game.client.ws.PlayerDetails> playersDetails;

    
    public static final int PLAYERSDETAILS  = 1;
    public static final int GAMEDETAILS  = 2;
    public static final int ACTIONMEHT  = 3;
    public static final int RESIGNSERVER  = 4; 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        allPlayersConnected = new SimpleBooleanProperty(false);
        returnToMenu = new SimpleBooleanProperty(false);
        checkBoxList = new ArrayList<>();
        checkBoxList.add(checkBoxPlayer1);
        checkBoxList.add(checkBoxPlayer2);
        checkBoxList.add(checkBoxPlayer3);
        checkBoxList.add(checkBoxPlayer4);
        checkBoxList.add(checkBoxPlayer5);
        checkBoxList.add(checkBoxPlayer6);
        labelList = new ArrayList<>();
        labelList.add(labelP1);
        labelList.add(labelP2);
        labelList.add(labelP3);
        labelList.add(labelP4);
        labelList.add(labelP5);
        labelList.add(labelP6);

       // waitOrProceed();
    }

    private void setCheckBox()
    {
        createNewThread(PLAYERSDETAILS);
    }

    public SimpleBooleanProperty getAllPlayersConnected()
    {
        return allPlayersConnected;
    }

    public void setGameDetails(String name, Integer id)
    {
        gameName = name;
        playerId = id;
        createNewThread(GAMEDETAILS);
    }

    @Override
    protected void actionMethod(Timer timer){
        Platform.runLater(() -> onShow(null));
        if(! (gameDetails.getJoinedHumanPlayers()== gameDetails.getHumanPlayers()))
        {
            createNewThread(ACTIONMEHT);
        }
        else{
            timer.cancel();
            /// invoke event start
            gameDetails.setStatus(GameStatus.ACTIVE);
            eventId = ZERO;
            Platform.runLater(() -> setGameOverSecen());
        }
    }
    
    @FXML
    private void onShow(ActionEvent event) {
        setCheckBox();
    }
    
    @FXML
    @Override
    protected void onReturn(ActionEvent event) {
        super.onReturn(event);
        createNewThread(RESIGNSERVER);
    }

    private void setGameOverSecen() {
     
        labelP1.setText(EMPTY);
        labelP2.setText(EMPTY);
        labelP3.setText(EMPTY);
        labelP4.setText(EMPTY);
        setCheckBox();
        allPlayersConnected.set(gameDetails.getJoinedHumanPlayers()== gameDetails.getHumanPlayers());
    }
    
    @Override
    protected void createNewThread(int selection)
    {
        Thread thread = new Thread();
        switch(selection)
        {
            case PLAYERSDETAILS:
                thread = new Thread(() -> this.getPlayersDetailsFromServer());
                break;
            case GAMEDETAILS:
                thread = new Thread(() -> this.getGameDetailsFromServer());
                break;
            case ACTIONMEHT:
                thread = new Thread(() -> this.checkStartServer());
                break;
            case RESIGNSERVER:
                thread = new Thread(this::sendPlayerResign);
                break;
            default:
                break;
        }
        thread.setDaemon(true);
        thread.start();
    }
    @Override
    protected void guiSet(int selection)
    {
        switch(selection)
        {
            case PLAYERSDETAILS:
                guiSetCheckBox();
                break;
            default:
                break;
        }
    }
    
    private void getPlayersDetailsFromServer() 
    {
        try
        {
            playersDetails = monopoly.getPlayersDetails(gameName);
            Platform.runLater(() -> guiSet(PLAYERSDETAILS));
        }
        catch(GameDoesNotExists_Exception ex)
        {
            System.out.print(ex.getMessage());
        }
    }

    private void guiSetCheckBox() {
        int index = 0;
        addPayersNames(labelList, playersDetails,false);
        for (CheckBox checkBox : checkBoxList) {
            checkBox.setDisable(false);
            checkBox.setSelected(!(labelList.get(index).getText().equals(EMPTY)));
            checkBox.setVisible(!(labelList.get(index).getText().equals(EMPTY)));
            checkBox.setDisable(true);
            index++;
        }
    }
    
    private void getGameDetailsFromServer() 
    {
        try
        {
            gameDetails = monopoly.getGameDetails(gameName);
            timing();
        }
        catch (GameDoesNotExists_Exception ex)
        {
            System.out.print(ex.getMessage());
        }
    }
    
    private void checkStartServer() 
    {
        try {
                gameDetails = monopoly.getGameDetails(gameName);
                playerDetails= monopoly.getPlayerDetails(playerId);
            }
        catch (GameDoesNotExists_Exception | InvalidParameters_Exception ex) 
        {
            System.out.print(ex.getMessage());
        }
    }
    
    private void sendPlayerResign() 
    {
        try
        {
            monopoly.resign(playerId);
        }
        catch (InvalidParameters_Exception ex)
        {
            System.out.print(ex.getMessage());
        }
    }

    @Override
    public void resetScene() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
