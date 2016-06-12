/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonopolyFX;

import MonopolyFX.component.PlayerLabel;
import Scene.GameOverScene.GameOverController;
import Scene.JoinScene.JoinGameController;
import Scene.MonopolyGame.MonopolyGameBoardController;
import Scene.OpeningScene.OpeningController;
import Scene.PlayerRegistretionScene.PlayersRegistretionController;
import Scene.StartWindowScene.StartNewGameController;
import Scene.WaitingScene.WaitingController;
import controllers.FileController;
import controllers.GameController;
import controllers.GameControllerFX;
import controllers.GenericController;
import game.client.ws.DuplicateGameName_Exception;
import game.client.ws.GameDetails;
import game.client.ws.GameDoesNotExists_Exception;
import game.client.ws.GameStatus;
import game.client.ws.InvalidParameters_Exception;
import game.client.ws.MonopolyWebService;
import game.client.ws.MonopolyWebServiceService;
import game.client.ws.PlayerDetails;
import game.client.ws.PlayerType;
import generated.Monopoly;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.text.Document;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import jdk.nashorn.internal.runtime.Timing;
import models.ComputerPlayer;
import models.HumanPlayer;
import models.MonopolyModel;
import models.Player;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import sun.misc.IOUtils;

/**
 *
 * @author efrat
 */
public class MonopolyGameApp extends Application {

    private static final String START_WINDOW_FXML_PATH = "/Scene/StartWindowScene/StartWindow.fxml";
    private static final String PLAYERS_REGISTRETION_FXML_PATH = "/Scene/PlayerRegistretionScene/PlayerRegistration.fxml";
    private static final String MONOPOLY_BOARD_FXML_PATH = "/Scene/MonopolyGame/MonopolyGameBoard.fxml";
    private static final String GAME_OVER_FXML_PATH = "/Scene/GameOverScene/GameOver.fxml";
    private static final String WAITING_FXML_PATH = "/Scene/WaitingScene/Waiting.fxml";
    private static final String OPENING_FXML_PATH = "/Scene/OpeningScene/Opening.fxml";
    private static final String JOIN_GAME_FXML_PATH = "/Scene/JoinScene/JoinGame.fxml";

    private Scene startWindowScene;
    private StartNewGameController startWindowController;
    private Scene playerRsisterationScene;
    private PlayersRegistretionController playerRegisterController;
    private Scene monopolyBoardScene;
    private MonopolyGameBoardController monopolyGameBoardController;
    private Scene gameOverScene;
    private GameOverController gameOverController;
    private Scene waitingScene;
    private WaitingController waitingController;
    private Scene openingScene;
    private OpeningController openingController;
    private Scene joinGameScene;
    private JoinGameController joinGameController;

    private GameControllerFX gameManager;
    private MonopolyWebService monopoly;
    private MonopolyWebServiceService service;

    @Override
    public void start(Stage stage) throws Exception {

        service = new MonopolyWebServiceService();
        monopoly = service.getMonopolyWebServicePort();

        setLogicGame(false, null);
        makeScene(stage);
        List<String> waitingGames = this.monopoly.getWaitingGames();
        if (waitingGames.size() > 0) {
            stage.setScene(this.openingScene);
            this.joinGameController.setListGamesName(waitingGames);
            this.joinGameController.setListViewGames();
        } else {
            stage.setScene(this.startWindowScene);
        }
        stage.setTitle("Monopoly Game");
        stage.setResizable(false);

        stage.show();
    }

    private void setLogicGame(boolean isUplaodGame, GameController logic) throws Exception {
        //filechocher
        if (!isUplaodGame) {
            String boardXml = this.monopoly.getBoardXML();
            gameManager = new GameControllerFX();
            GameController gameLogic = new GameController(boardXml, false, true);
            //GameController gameLogic = new GameController("monopoly_config", false, false);
            gameManager.setLogicGame(gameLogic);

        } else {
            gameManager = new GameControllerFX();
            gameManager.setLogicGame(logic);
        }
    }

    private void makeScene(Stage primaryStage) throws IOException {
        FXMLLoader startWindowSceneLoader = getLoader(START_WINDOW_FXML_PATH);
        Parent startWindowParent = startWindowSceneLoader.load(startWindowSceneLoader.getLocation().openStream());
        this.startWindowScene = new Scene(startWindowParent);

        FXMLLoader playerRsisterationLoader = getLoader(PLAYERS_REGISTRETION_FXML_PATH);
        Parent playerRsisterationParent = playerRsisterationLoader.load(playerRsisterationLoader.getLocation().openStream());
        this.playerRsisterationScene = new Scene(playerRsisterationParent);

        FXMLLoader monopolyBoardLoader = getLoader(MONOPOLY_BOARD_FXML_PATH);
        Parent monopolyBoardParent = monopolyBoardLoader.load(monopolyBoardLoader.getLocation().openStream());
        this.monopolyBoardScene = new Scene(monopolyBoardParent);

        FXMLLoader gameOverLoader = getLoader(GAME_OVER_FXML_PATH);
        Parent gameOverParent = gameOverLoader.load(gameOverLoader.getLocation().openStream());
        this.gameOverScene = new Scene(gameOverParent);

        FXMLLoader waitingLoader = getLoader(WAITING_FXML_PATH);
        Parent waitingParent = waitingLoader.load(waitingLoader.getLocation().openStream());
        this.waitingScene = new Scene(waitingParent);

        FXMLLoader openingLoader = getLoader(OPENING_FXML_PATH);
        Parent openingParent = openingLoader.load(openingLoader.getLocation().openStream());
        this.openingScene = new Scene(openingParent);

        FXMLLoader joinGameLoader = getLoader(JOIN_GAME_FXML_PATH);
        Parent joinGameParent = joinGameLoader.load(joinGameLoader.getLocation().openStream());
        this.joinGameScene = new Scene(joinGameParent);

        this.openingController = getOpeningController(openingLoader, primaryStage);
        this.openingController.setGameManager(gameManager.getSpesificGame());

        this.startWindowController = getStartNewGameController(startWindowSceneLoader, primaryStage);
        this.startWindowController.setGameManager(gameManager.getSpesificGame());

        if (getSizeOfWaitingGamesList() > 0) {
            this.openingController.setStage(primaryStage);
        } else {
            this.startWindowController.setStage(primaryStage);
        }

        this.playerRegisterController = getPlayerRegistrationController(playerRsisterationLoader, primaryStage);
        this.playerRegisterController.setGameManager(gameManager.getSpesificGame());
        this.monopolyGameBoardController = getGameBoardController(monopolyBoardLoader, primaryStage);
        this.monopolyGameBoardController.setGameManager(gameManager.getSpesificGame());
        this.gameOverController = getGameOverController(gameOverLoader, primaryStage);
        this.gameOverController.setGameManager(gameManager.getSpesificGame());
        this.waitingController = getWaitingController(waitingLoader, primaryStage);
        this.waitingController.setGameManager(gameManager.getSpesificGame());

        this.joinGameController = getJoinGameController(joinGameLoader, primaryStage);
        this.joinGameController.setGameManager(gameManager.getSpesificGame());
        initAllControoler();
    }

    private FXMLLoader getLoader(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource(path);
        fxmlLoader.setLocation(url);
        return fxmlLoader;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private StartNewGameController getStartNewGameController(FXMLLoader fxmlLoader, Stage primaryStage) {
        StartNewGameController fxmlDocumentController = (StartNewGameController) fxmlLoader.getController();
        SimpleBooleanProperty btn = fxmlDocumentController.getSubmitButtonPror();
        btn.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    try {
                        fxmlDocumentController.getSubmitButtonPror().set(false);
                        int numOfPlayers = MonopolyGameApp.this.gameManager.getSpesificGame().getNumOfPlayers();
                        int numOfHumenPlayers = MonopolyGameApp.this.gameManager.getSpesificGame().getNumOfHumanPlayers();
                        int munOfComputerPlayers = numOfPlayers - numOfHumenPlayers;
                        String gameName = fxmlDocumentController.getGameName();
                        primaryStage.setTitle("Monopoly Game: " + gameName);
                        monopoly.createGame(munOfComputerPlayers, numOfHumenPlayers, gameName);
                        initGameName(gameName);
                        playerRegisterController.setHumanPlayersCounterAndNumOfPlayers(numOfHumenPlayers, numOfPlayers);
                        setSceneAndCenter(primaryStage, MonopolyGameApp.this.playerRsisterationScene);

                    } catch (DuplicateGameName_Exception | InvalidParameters_Exception ex) {
                        MonopolyGameApp.this.startWindowController.setErrorLabel(ex.getMessage());
                        Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        return fxmlDocumentController;
    }

    private PlayersRegistretionController getPlayerRegistrationController(FXMLLoader fxmlLoader, Stage primaryStage) {
        PlayersRegistretionController fxmlDocumentController = (PlayersRegistretionController) fxmlLoader.getController();
        fxmlDocumentController.getSubmitPlayerButtonProp().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                try {
                    fxmlDocumentController.getSubmitPlayerButtonProp().set(false);
                    String playerName = fxmlDocumentController.getPlayerNameTextField().getText();
                    String gameName = fxmlDocumentController.getGameName();
                    int playerId = this.monopoly.joinGame(gameName, playerName);
                    initPlayerIdAndName(playerId, playerName);
                    primaryStage.setTitle("Monopoly Game: " + gameName + "(" + playerName + ")");
                } catch (GameDoesNotExists_Exception | InvalidParameters_Exception ex) {
                    Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
                    fxmlDocumentController.setErrorLabel(ex.getMessage());
                }
            }
        });
        return fxmlDocumentController;
    }

    private MonopolyGameBoardController getGameBoardController(FXMLLoader fxmlLoader, Stage primaryStage) {
        MonopolyGameBoardController fxmlDocumentController = (MonopolyGameBoardController) fxmlLoader.getController();
        fxmlDocumentController.initBoardLogic(this.gameManager.getSpesificGame().getMonopolyGame());

        SimpleBooleanProperty btn = this.playerRegisterController.getStartGameButtonProp();
        btn.addListener((source, oldValue, newValue) -> {
            if (newValue) {
                try {
                    this.playerRegisterController.getStartGameButtonProp().set(false);
                    setSceneAndCenter(primaryStage, this.waitingScene);
                } catch (Exception ex) {
                    Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        SimpleBooleanProperty yesBtnProperty = fxmlDocumentController.getYesButtonProp();
        yesBtnProperty.addListener((source, oldValue, newValue) -> {
            if (newValue) {

                fxmlDocumentController.getYesButtonProp().set(false);

            }
        });

        SimpleBooleanProperty yesPerchesBtnProperty = fxmlDocumentController.getYesPerchesButtonProp();
        yesPerchesBtnProperty.addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getYesPerchesButtonProp().set(false);
                int playerId = fxmlDocumentController.getPlayerId();
                try {
                    this.monopoly.buy(playerId, fxmlDocumentController.getEventId(), true);
                } catch (InvalidParameters_Exception ex) {
                    Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
                }          
                fxmlDocumentController.timing();
            }
        });

        SimpleBooleanProperty noPerchesBtnProperty = fxmlDocumentController.getNoPerchesButtonProp();
        noPerchesBtnProperty.addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getNoPerchesButtonProp().set(false);
                try {
                    this.monopoly.buy(fxmlDocumentController.getPlayerId(), fxmlDocumentController.getEventId(), false);
                    fxmlDocumentController.timing();
                } catch (InvalidParameters_Exception ex) {
                    Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
                    fxmlDocumentController.setErrorLabel(ex.getMessage());
                }
            }
        });

        SimpleBooleanProperty noBtnProperty = fxmlDocumentController.getNoButtonProp();
        noBtnProperty.addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getNoButtonProp().set(false);
                this.gameOverController.setWinnerName(this.gameManager.getSpesificGame().getCurrentPlayer().getName());
                setSceneAndCenter(primaryStage, this.gameOverScene);
            }
        });

        SimpleBooleanProperty playerResingeProp = fxmlDocumentController.getPlayerResingeProp();
        playerResingeProp.addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getPlayerResingeProp().set(false);
                List<String> waitingGames = this.monopoly.getWaitingGames();
                if (waitingGames.size() > 0) {
                    setSceneAndCenter(primaryStage, this.openingScene);
                    this.joinGameController.setListGamesName(waitingGames);
                    this.joinGameController.setListViewGames();
                } else {
                    this.startWindowController.resetScene();
                    setSceneAndCenter(primaryStage, this.startWindowScene);
                }
            }
        });

        SimpleBooleanProperty endGameProperty = fxmlDocumentController.getEndGameProp();
        endGameProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> source, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    fxmlDocumentController.getEndGameProp().set(false);
                    MonopolyGameApp.this.gameOverController.setWinnerName(MonopolyGameApp.this.gameManager.getSpesificGame().getWinnerName());
                    setSceneAndCenter(primaryStage, gameOverScene);
                }
            }
        });
        return fxmlDocumentController;
    }

    //TODO
    private GameOverController getGameOverController(FXMLLoader fxmlLoader, Stage primaryStage) {
        GameOverController fxmlDocumentController = (GameOverController) fxmlLoader.getController();

        //another Game
        SimpleBooleanProperty anotherBtn = fxmlDocumentController.getPlayAnotherGameButtonProp();
        anotherBtn.addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getPlayAnotherGameButtonProp().set(false);
                try {
                    this.setLogicGame(false, null);
                    makeScene(primaryStage);
                } catch (IOException ex) {
                    Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                setSceneAndCenter(primaryStage, startWindowScene);

            }
        });
        //exit
        SimpleBooleanProperty exitBtn = fxmlDocumentController.getExitButtonProp();
        exitBtn.addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getExitButtonProp().set(false);
                //primaryStage.setOnCloseRequest(e -> Platform.exit());
                primaryStage.close();
            }
        });
        return fxmlDocumentController;
    }

    private void excuteReturn(GenericController controller, final Stage primaryStage) {
        controller.getReturnToMenuProp().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                controller.getReturnToMenuProp().set(false);
                try {
                    this.setLogicGame(false, null);
                    makeScene(primaryStage);
                } catch (Exception ex) {
                    Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                setSceneAndCenter(primaryStage, this.startWindowScene);
            }
        });
    }

    private WaitingController getWaitingController(FXMLLoader fxmlLoader, Stage primaryStage) {
        WaitingController fxmlDocumentController = (WaitingController) fxmlLoader.getController();

        fxmlDocumentController.getRefreshProp().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getRefreshProp().set(false);
                String gameName = fxmlDocumentController.getGameName();
                //  fxmlDocumentController.setGameStatus();
                if (isGameActive(gameName)) {
                    try {
                        //set players list in gameManager
                        this.monopolyGameBoardController.updateGamePlayersDetails(this.monopoly.getPlayersDetails(gameName));
                        Player currPlayer = getCurrentPlayer();
                        //set players label list 
                        List<PlayerLabel> playersLabel = MonopolyGameApp.this.playerRegisterController.getPlayerLabelList();
                        this.gameManager.getSpesificGame().setCurrentPlayer(currPlayer);
                        MonopolyGameApp.this.monopolyGameBoardController.setPlayerLabelList(playersLabel);
                        PlayerLabel playerLabel = MonopolyGameApp.this.monopolyGameBoardController.getPlayerLabelByName(currPlayer.getName());
                        MonopolyGameApp.this.monopolyGameBoardController.initLabelPlayersOnBoard(playerLabel);

                        MonopolyGameApp.this.monopolyGameBoardController.startPlaying(currPlayer);
                        setSceneAndCenter(primaryStage, this.monopolyBoardScene);
                    } catch (GameDoesNotExists_Exception ex) {
                        Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
                        fxmlDocumentController.setLabelError(ex.getMessage());
                    } catch (Exception ex) {
                        Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
                        fxmlDocumentController.setLabelError(ex.getMessage());
                    }
                }
            }
        });
        return fxmlDocumentController;
    }

    private OpeningController getOpeningController(FXMLLoader fxmlLoader, Stage primaryStage) {
        OpeningController fxmlDocumentController = (OpeningController) fxmlLoader.getController();

        fxmlDocumentController.getCreateGameProp().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getCreateGameProp().set(false);
                setSceneAndCenter(primaryStage, this.startWindowScene);
                setSceneAndCenter(primaryStage, this.startWindowScene);
            }
        });
        fxmlDocumentController.getJoinGameProp().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getJoinGameProp().set(false);
                this.joinGameController.setListViewGames();
                setSceneAndCenter(primaryStage, this.joinGameScene);
            }
        });
        return fxmlDocumentController;
    }

    private JoinGameController getJoinGameController(FXMLLoader fxmlLoader, Stage primaryStage) {
        JoinGameController fxmlDocumentController = (JoinGameController) fxmlLoader.getController();
        excuteReturn(fxmlDocumentController, primaryStage);
        fxmlDocumentController.getJoinGameProp().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getJoinGameProp().set(false);
                initGameName(fxmlDocumentController.getGameName());
                initPlayerIdAndName(fxmlDocumentController.getPlayerId(), fxmlDocumentController.getPlayerName());
                primaryStage.setTitle("Monopoly Game: " + fxmlDocumentController.getGameName() + "(" + fxmlDocumentController.getPlayerName() + ")");
                setSceneAndCenter(primaryStage, this.waitingScene);

            }
        });

        fxmlDocumentController.getReturnToMenuProp().addListener((source, oldValue, newValue) -> {
            if (newValue) {
                fxmlDocumentController.getReturnToMenuProp().set(false);
                setSceneAndCenter(primaryStage, this.openingScene);
            }
        });

        return fxmlDocumentController;
    }

    private int getSizeOfWaitingGamesList() {
        List<String> waitingGames = this.monopoly.getWaitingGames();
        return waitingGames.size();
    }

    private boolean eventStartGameExist() {
        this.waitingController.timing();
        boolean res = this.waitingController.checkIfEventStartGameExist();
        if (res) {
            this.monopolyGameBoardController.addToEventId(1);
        }
        return res;
    }

    private void initAllControoler() {
        this.gameOverController.setMonopolyServic(this.monopoly);
        this.joinGameController.setMonopolyServic(this.monopoly);
        this.monopolyGameBoardController.setMonopolyServic(this.monopoly);
        this.openingController.setMonopolyServic(this.monopoly);
        this.startWindowController.setMonopolyServic(this.monopoly);
        this.playerRegisterController.setMonopolyServic(this.monopoly);
        this.waitingController.setMonopolyServic(this.monopoly);
    }

    private void initPlayerIdAndName(int playerId, String playerName) {
        this.gameOverController.setPlayerId(playerId);
        this.joinGameController.setPlayerId(playerId);
        this.monopolyGameBoardController.setPlayerId(playerId);
        this.openingController.setPlayerId(playerId);
        this.startWindowController.setPlayerId(playerId);
        this.playerRegisterController.setPlayerId(playerId);
        this.waitingController.setPlayerId(playerId);

        this.gameOverController.setPlayerName(playerName);
        this.joinGameController.setPlayerName(playerName);
        this.monopolyGameBoardController.setPlayerName(playerName);
        this.openingController.setPlayerName(playerName);
        this.startWindowController.setPlayerName(playerName);
        this.playerRegisterController.setPlayerName(playerName);
        this.waitingController.setPlayerName(playerName);

    }

    public void initGameName(String gameName) {
        this.gameOverController.setGameName(gameName);
        this.joinGameController.setGameName(gameName);
        this.monopolyGameBoardController.setGameName(gameName);
        this.openingController.setGameName(gameName);
        this.startWindowController.setGameName(gameName);
        this.playerRegisterController.setGameName(gameName);
        this.waitingController.setGameName(gameName);
    }

    private Player getCurrentPlayer() {

        Player currPlayer = null;
        String playerName = this.waitingController.getCurentPlayer();
        if (!(playerName.isEmpty())) {
            currPlayer = this.gameManager.getSpesificGame().getPlayerByName(playerName);
        }

        return currPlayer;
    }

    private void convertStringToXmlFile(String gameXml) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document doc = null;
        try {
            doc = (Document) builder.parse(new InputSource(new StringReader(gameXml)));
        } catch (SAXException ex) {
            Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Write the parsed document to an xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        DOMSource source = new DOMSource((Node) doc);

        StreamResult result = new StreamResult(new File("/resources/monopoloy_config_server.xml"));
        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void checkIfEventStartGameExist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean isGameActive(String gameName) {
        boolean res = false;
        GameDetails gameDetails;
        try {
            gameDetails = this.monopoly.getGameDetails(gameName);
            if (gameDetails.getStatus().equals(GameStatus.ACTIVE)) {
                res = true;
            }
        } catch (GameDoesNotExists_Exception ex) {
            Logger.getLogger(MonopolyGameApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return res;
    }

    private void setSceneAndCenter(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
    }

}
