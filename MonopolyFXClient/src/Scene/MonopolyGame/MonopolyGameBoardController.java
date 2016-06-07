/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene.MonopolyGame;

import MonopolyFX.component.ButtonMonopoly;
import MonopolyFX.component.PlayerLabel;
import controllers.GameController;
import controllers.GenericController;
import game.client.ws.Event;
import game.client.ws.InvalidParameters_Exception;
import generated.ParkingSquareType;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import models.AssetType;
import models.CardBase;
import models.CityType;
import models.ComputerPlayer;
import models.GotoCard;
import models.HumanPlayer;
import models.JailSlashFreeSpaceSquareType;
import models.MonetaryCard;
import models.MonopolyModel;
import models.Player;
import models.SimpleAssetType;
import models.SquareBase;
import models.SquareType;
import models.StartSquare;

/**
 * FXML Controller class
 *
 * @author efrat
 */
public class MonopolyGameBoardController extends GenericController implements Initializable {

    final static int WIDTH = 800;
    final static int HEIGHT = 680;
    private final static int ZERO = 0;
    @FXML
    private Label msgLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView surpriseCard;
    @FXML
    private ImageView warrantCard;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Button yesPerchesButton;
    @FXML
    private Button noPerchesButton;
    // @FXML
    //  private Button nextTurnButton;
    @FXML
    private Button moveButton;

    private SimpleBooleanProperty yesButtonProp;
    private SimpleBooleanProperty noButtonProp;
    private SimpleBooleanProperty endGameProp;
    private SimpleBooleanProperty yesPerchesButtonProp;
    private SimpleBooleanProperty noPerchesButtonProp;
    //  private SimpleBooleanProperty nextTurnButtonProp;
    private SimpleBooleanProperty moveButtonProp;
    private SimpleBooleanProperty playerResingeProp;

    private Map<Integer, Point2D> gridPaneMap = new HashMap<Integer, Point2D>();
    private List<PlayerLabel> playerLabelList = new ArrayList<PlayerLabel>();
    private PlayerLabel currentPlayerLabel;
    private List<ButtonMonopoly> buttonMonopolyList = new ArrayList<>();
    private boolean isOnPertces;
    private models.GotoCard.To to = null;

    public void setCurrentPlayerLabel(PlayerLabel currentPlayerLabel) {
        this.currentPlayerLabel = currentPlayerLabel;
    }

    public SimpleBooleanProperty getEndGameProp() {
        return endGameProp;
    }

    public void setEndGameProp(SimpleBooleanProperty endGameProp) {
        this.endGameProp = endGameProp;
    }

    public SimpleBooleanProperty getYesButtonProp() {
        return this.yesButtonProp;
    }

    public SimpleBooleanProperty getNoButtonProp() {
        return noButtonProp;
    }

    public void setPlayerLabelList(List<PlayerLabel> playerLabelList) {
        this.playerLabelList = playerLabelList;

    }

    public SimpleBooleanProperty getPlayerResingeProp() {
        return playerResingeProp;
    }

    public void setPlayerResingeProp(SimpleBooleanProperty playerResingeProp) {
        this.playerResingeProp = playerResingeProp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.gridPane.setPrefSize(WIDTH, HEIGHT);
        this.yesButtonProp = new SimpleBooleanProperty(false);
        this.noButtonProp = new SimpleBooleanProperty(false);
        this.endGameProp = new SimpleBooleanProperty(false);
        this.yesPerchesButtonProp = new SimpleBooleanProperty(false);
        this.noPerchesButtonProp = new SimpleBooleanProperty(false);
        //      this.nextTurnButtonProp = new SimpleBooleanProperty(false);
        this.isOnPertces = false;
        this.moveButtonProp = new SimpleBooleanProperty(false);
        this.playerResingeProp = new SimpleBooleanProperty(false);

        hideNode(this.noButton);
        hideNode(this.yesButton);
        hideNode(this.yesPerchesButton);
        hideNode(this.noPerchesButton);
        //     hideNode(this.nextTurnButton);
        hideNode(this.moveButton);
    }

//    public Button getNextTurnButton() {
//        return nextTurnButton;
//    }
//    public SimpleBooleanProperty getNextTurnButtonProp() {
//        return nextTurnButtonProp;
//    }
    public void initBoardLogic(MonopolyModel monopolyGame) {
        initGridPaneMap(monopolyGame);
        List<models.SquareBase> monopolyBoard = monopolyGame.getBoard().getContent();

        for (int i = 0; i < monopolyBoard.size(); i++) {
            String text = monopolyBoard.get(i).toString();
            String info = monopolyBoard.get(i).getInfo();
            String img = monopolyBoard.get(i).getImageName();
            ButtonMonopoly squarFx = new ButtonMonopoly(text, img, info);

            Point2D locition = this.gridPaneMap.get(i);
            int x = (int) locition.getX();
            int y = (int) locition.getY();

            this.gridPane.add(squarFx, x, y);
            this.buttonMonopolyList.add(squarFx);
        }
    }

    public SimpleBooleanProperty getMoveButtonProp() {
        return moveButtonProp;
    }

    public void OnMoveButton(ActionEvent event) throws Exception {
        hideNode(this.moveButton);
        actionGoToCard(this.to);
        this.moveButtonProp.set(true);
    }

    public SimpleBooleanProperty getYesPerchesButtonProp() {
        return yesPerchesButtonProp;
    }

    public SimpleBooleanProperty getNoPerchesButtonProp() {
        return noPerchesButtonProp;
    }

    private void initGridPaneMap(MonopolyModel monopolyGame) {
        int size = monopolyGame.getBoard().getContent().size();
        int colume = 0;
        int row = 0;

        for (int i = 0; i < size; i++) {
            this.gridPaneMap.put(i, new Point2D(row, colume));
            if (i >= 0 && i < 9) {
                row++;
            } else if (i >= 9 && i < 18) {

                colume++;
            } else if (i >= 18 && i < 27) {
                row--;
            } else if (i >= 27 && i < 36) {
                colume--;
            }
        }
    }

    public void changeMsgLabelTxt(String text) {
        msgLabel.setText(text);
    }

    public void changeErrorLabelTxt(String text) {
        errorLabel.setText(text);
    }

    public void setPlayerOnBoard(int index, int x, int y) {
        this.gridPane.add(this.playerLabelList.get(index), x, y);
    }

    public void initLabelPlayersOnBoard(PlayerLabel player) {

        for (PlayerLabel playerLabel : this.playerLabelList) {
            this.gridPane.add(playerLabel, ZERO, ZERO);
            hideNode(playerLabel);
        }
        setCurrentPlayerLabel(player);
        //show current player
        showNode(this.currentPlayerLabel);
    }

    public void OnYesButton(ActionEvent event) throws InterruptedException, Exception {
        Player currentPlayer = this.gameManager.getCurrentPlayer();
        hideYesAndNoButton();
        //timing();
        yesButtonProp.set(true);
    }

    public void OnNoButton(ActionEvent event) {
        noButtonProp.set(true);
    }

    public void startPlaying(Player currentPlayer) throws InterruptedException, Exception {
        if (currentPlayer.getClass().equals(ComputerPlayer.class)) {
            addMsgLabel(", It's Your Turn, Roll The Dice");
            showNode(this.currentPlayerLabel);
            //  showNode(this.nextTurnButton);
            hideYesAndNoButton();
            playMove(currentPlayer);
        } else {
            showNode(noButton);
            showNode(yesButton);
            changMsgLabel(currentPlayer.getName() + ", It's Your Turn, Do You Want To Roll The Dice?");
        }
    }

    public void OnYesPerchesButton(ActionEvent event) throws Exception {
        this.yesPerchesButtonProp.set(true);
    }

    public void OnNoPerchesButton(ActionEvent event) throws InterruptedException {
        hidePerchesButton();
        this.isOnPertces = false;
//        showNode(this.nextTurnButton);
    }

//    public void OnNextTurnButton(ActionEvent event) throws Exception {
//       // nextTurn();
//     //   setRollTheDice();
//        hideNode(this.nextTurnButton);
//        //startPlaying(this.gameManager.getCurrentPlayer());
//        this.nextTurnButtonProp.set(true);
//    }
    private void changMsgLabel(String msg) throws InterruptedException {
        this.msgLabel.setText(msg);
        Thread.sleep(1000);
    }

    private void removeFromPlayerLabelList(int pleyerIndex) {
        this.playerLabelList.remove(pleyerIndex);
    }

    private void move(int numOfSteps, Player currentPlayer, boolean isCanPassByStart) throws Exception {
        PlayerLabel currentPlayerLabel = this.playerLabelList.get(this.gameManager.getPleyerIndex());
        showNode(currentPlayerLabel);
        //logic move
        this.gameManager.getCurrentPlayer().move(numOfSteps, isCanPassByStart);
        //FXmove
        currentPlayerLabel.move(this.gridPaneMap.get(currentPlayer.getSqureNum()));
        //actual step on squar
        SquareBase currentSquar = this.gameManager.getMonopolyGame().getBoard().getSqureBaseBySqureNum(currentPlayer.getSqureNum());
        stepOnSquar(currentPlayer, currentSquar);
        if (currentPlayer.getClass().equals(ComputerPlayer.class)) {
            if (currentSquar.getClass().equals(JailSlashFreeSpaceSquareType.class)) {
                if (currentPlayer.isInJail()) {
                    addMsgLabel("\n You are in Jail! Good Luck!");
                } else {
                    addMsgLabel("\n It's a Free Pass!");
                }
                //  showNode(this.nextTurnButton);
            }
        }
    }

    public void nextTurn() throws Exception {
        hideNode(this.currentPlayerLabel);
        setCurrentPlayerLabel(getPlayerLabelByName(this.gameManager.getCurrentPlayer().getName()));
        this.currentPlayerLabel.setToolTipText(this.gameManager.getCurrentPlayer().toString());
        showNode(this.currentPlayerLabel);
    }

    public void setRollTheDice() throws InterruptedException {
        changMsgLabel(", It's Your Turn. Do You WantTo Roll The Dice?");
    }

    private void stepOnSquar(Player currentPlayer, SquareBase currentSquar) throws Exception {
        currentSquar.stepOnMe(currentPlayer);
        if (currentSquar.getClass().equals(SquareType.class)) {
            SquareType currentSquareType = (SquareType) currentSquar;
            if (currentPlayer.isDoesPlayerNeedToPay()) {// current player isn't the owner and the squre has a owner
                //pay to owner
                long stayCost = this.gameManager.getStayCostForAsset(currentSquareType);
                payToPlayer(currentPlayer, currentSquareType.getAsset().getOwner(), stayCost);
            } else if (currentPlayer.isIsPlayerCanBuySquare()) { //has the option to buy 
                if (currentPlayer.isPlayerHaveTheMany(currentSquareType.getAsset().getCost())) {
                    if (currentPlayer.getClass().equals(HumanPlayer.class)) {
                        showPerchesButton();
                    }
                    this.isOnPertces = true;
                    buyingAssetOffer(currentSquareType, currentPlayer.getSqureNum(), currentPlayer);
                } else {
                    changMsgLabel(", You Don't Have Enough Many To Buy  " + currentSquareType.getAsset().getName());
                }
            } //player cnt buy squre
            else if (currentPlayer.isIsNeedToTakeSupriesCard()) {
                actionSurpriseCard(this.gameManager.getMonopolyGame().getSurpries().getCard());
            } else if (currentPlayer.isIsNeedToTakeWarrentCard()) {
                actionWarrantCard(this.gameManager.getMonopolyGame().getWarrant().getCard());
            }
            currentPlayer.setUpFlages();
        } else if (currentSquar.getClass().equals(ParkingSquareType.class)) {
            currentPlayer.setIsInParking(true);
        } else if (currentSquar.getClass().equals(StartSquare.class)) {
            changMsgLabel("You Are at the Start!, You just resived 200 Nis!");
        }
    }

    private void payToPlayer(Player paidPlayer, Player playerToPayTo, long sum) throws InterruptedException {
        long amount = paidPlayer.getAmount();
        boolean haveEnoughMony = paidPlayer.pay(playerToPayTo, sum);
        String msg = "";
        String payToName = playerToPayTo.getName();
        if (haveEnoughMony) {
            msg = "You Just Paid " + sum + " Nis To " + playerToPayTo.getName() + " \n";
        } else {
            msg = "You Don't Have Enough Mony! Pay " + payToName + " \n" + amount + " Nis And Leave The Game.";
        }
        addMsgLabel(msg);
        this.currentPlayerLabel.setToolTipText(paidPlayer.toString());
        this.isOnPertces = false;
    }

    private void buyingAssetOffer(SquareType square, int squreNum, Player currentPlayer) throws InterruptedException {
        switch (square.getType()) {
            case CITY:
                CityType citySquar = (CityType) square.getAsset();
                if (this.gameManager.checkIfPlayerCanBuyHouse(square)) {
                    wantToBuyHouse(currentPlayer, citySquar);
                } else if (this.gameManager.checkIfPlayerCanBuyCity(square)) {
                    wantToBuy(citySquar);
                }
                break;
            case UTILITY:
            case TRANSPORTATION:
                SimpleAssetType assetSquar = (SimpleAssetType) square.getAsset();
                wantToBuy(assetSquar);
                break;
        }
    }

    private void wantToBuyHouse(Player currentPlayer, CityType city) throws InterruptedException {
        String msg = "";
        //if is cmputer player
        if (currentPlayer.getClass().equals(ComputerPlayer.class)) {
            boolean wantToBuy = currentPlayer.doYouWantToPurchaseHouse(city, city.getStayCost());
            if (wantToBuy) {
                this.gameManager.buyHouse(city);
                payToTreasury(currentPlayer, city.getHouseCost());
                this.isOnPertces = false;
                msg = currentPlayer.getName() + ", Just purchased House Number " + (city.getCounterOfHouse() + 1) + " (price: " + city.getHouseCost() + " Nis)";
                addMsgLabel(msg);
            }
        } else {//if is human player
            msg = currentPlayer.getName() + ", Do You Wannt To Buy House Number " + (city.getCounterOfHouse() + 1) + " (price: " + city.getHouseCost() + ") ?";
            addMsgLabel(msg);
        }
    }

    private void wantToBuy(AssetType asset) throws InterruptedException {
        String msg = "";
        //if is copmuter player
        if (this.gameManager.getCurrentPlayer().getClass().equals(ComputerPlayer.class)) {
            boolean wantToBuy = this.gameManager.getCurrentPlayer().doYouWantToPurchase(asset, asset.getCost());
            if (wantToBuy) {
                SquareType square = (SquareType) this.gameManager.getMonopolyGame().getBoard().getSqureBaseBySqureNum(this.gameManager.getCurrentPlayer().getSqureNum());
                switch (square.getType()) {
                    case CITY:
                        CityType city = (CityType) square.getAsset();
                        this.gameManager.buyCity(square);
                        payToTreasury(this.gameManager.getCurrentPlayer(), city.getCost());
                        break;
                    case TRANSPORTATION:
                    case UTILITY:
                        SimpleAssetType assetSquar = (SimpleAssetType) square.getAsset();
                        this.gameManager.buyTrnsportionOrUtility(square, this.gameManager.getCurrentPlayer().getSqureNum());
                        payToTreasury(this.gameManager.getCurrentPlayer(), assetSquar.getCost());
                        break;
                }
                this.isOnPertces = false;
                msg = this.gameManager.getCurrentPlayer().getName() + " Just purchased " + asset.getName() + " (price: " + asset.getCost() + " Nis)";
            }
        } else { // if is human player
            msg = "Do You Want To Buy " + asset.getName() + " (price: " + asset.getCost() + ") ?";
        }
        addMsgLabel(msg);
    }

    private void showPerchesButton() {
        showNode(yesPerchesButton);
        showNode(noPerchesButton);
    }

    private void hidePerchesButton() throws InterruptedException {
        hideNode(yesPerchesButton);
        hideNode(noPerchesButton);
    }

    private void actionSurpriseCard(CardBase card) throws Exception {
        String msg = "";
        String number = "";
        if (card.getClass().equals(models.MonetaryCard.class)) {
            models.MonetaryCard monoteryCard = (models.MonetaryCard) card;
            msg = monoteryCard.getText();
            number = Integer.toString((int) monoteryCard.getSum());
            msg = msg.replace("%s", number);
            addMsgLabel(msg + "\n");
            actionMonoteryCardFromSurpeiseCards(monoteryCard.getSum(), monoteryCard.getType());
            this.currentPlayerLabel.setToolTipText(this.gameManager.getCurrentPlayer().toString());
        } else if (card.getClass().equals(models.GetOutOfJailCard.class)) {
            addMsgLabel(card.getText() + "\n");
            this.gameManager.getCurrentPlayer().setIsHaveGetOutOfJailCard(true, (models.GetOutOfJailCard) card);
        } else if (card.getClass().equals(models.GotoCard.class)) {
            addMsgLabel(card.getText() + "\n");
            models.GotoCard gotoCard = (models.GotoCard) card;
            this.to = gotoCard.getType();
            showNode(this.moveButton);
        }
        if (card.getClass().equals(models.GotoCard.class) || card.getClass().equals(models.MonetaryCard.class)) {
            this.gameManager.getMonopolyGame().getSurpries().addCardToSurpriseList(card);
        }
    }

    public void actionMonoteryCardFromSurpeiseCards(long sum, MonetaryCard.Who type) throws InterruptedException {
        if (type == MonetaryCard.Who.PLAYERS) {
            substractFromAllPlayersAmount(sum);
        } else {
            this.gameManager.getCurrentPlayer().addToAmount(sum);
            addMsgLabel("Tresery Give To " + this.gameManager.getCurrentPlayer().getName() + " " + sum + " Nis");
        }
    }

    private void substractFromAllPlayersAmount(long sum) throws InterruptedException {
        String msg = " ";
        Player currentPlayer = this.gameManager.getCurrentPlayer();
        for (Player player : this.gameManager.getPlayers()) {
            if (!player.equals(currentPlayer)) {
                payToPlayer(player, currentPlayer, sum);
                this.gameManager.handelPlayerPresence(player);
            }
        }
    }

    private void actionGoToCard(GotoCard.To type) throws Exception {
        boolean isCanPasByStart = true;
        int numOfSteps = 0;
        int numNextOfQquare = 0;
        int squareNum = this.gameManager.getCurrentPlayer().getSqureNum();
        if (type.equals(models.GotoCard.To.START)) {
            numOfSteps = this.gameManager.getMonopolyGame().getBoard().getNumberOfStepstToSquareByType(
                    squareNum, new models.StartSquare().toString());
        } else if (type.equals(models.GotoCard.To.NEXT_SURPRISE)) {
            numOfSteps = this.gameManager.getMonopolyGame().getBoard().getNumberOfStepstToSquareByType(
                    squareNum, new models.SquareType("SURPRISE").toString());
        } else if (type.equals(models.GotoCard.To.JAIL)) {
            isCanPasByStart = false;
            numOfSteps = this.gameManager.getMonopolyGame().getBoard().getNumberOfStepstToSquareByType(
                    squareNum, new models.JailSlashFreeSpaceSquareType().toString());
            this.gameManager.getCurrentPlayer().goTOJail();
        } else if (type.equals(models.GotoCard.To.NEXT_WARRANT)) {
            isCanPasByStart = false;
            numOfSteps = this.gameManager.getMonopolyGame().getBoard().getNumberOfStepstToSquareByType(
                    squareNum, new models.SquareType("WARRANT").toString());
            changMsgLabel(", You Are Now Going To the Next Warrant Squar! Good Luck!");
        }
        move(numOfSteps, this.gameManager.getCurrentPlayer(), isCanPasByStart);
        this.to = null;
    }

    private void actionWarrantCard(CardBase card) throws Exception {
        String msg = "";
        String number = "";
        if (card.getClass().equals(models.MonetaryCard.class)) {
            models.MonetaryCard monoteryCard = (models.MonetaryCard) card;
            msg = monoteryCard.getText();
            number = Integer.toString((int) monoteryCard.getSum());
            msg = msg.replace("%s", number);
            changMsgLabel(msg);
            //addMsgLabel(monoteryCard.getText() + " " + monoteryCard.getSum() + "\n");
            actionMonoteryCardFromWarrantCards(monoteryCard.getSum(), monoteryCard.getType());
        } else if (card.getClass().equals(models.GotoCard.class)) {
            models.GotoCard gotoCard = (models.GotoCard) card;
            addMsgLabel(card.getText());
            this.to = gotoCard.getType();
            showNode(this.moveButton);
        }
        this.gameManager.getMonopolyGame().getWarrant().addCardToWarrantCardList(card);
    }

    private void actionMonoteryCardFromWarrantCards(long sum, MonetaryCard.Who type) throws InterruptedException {
        if (type.equals(MonetaryCard.Who.TREASURY)) {
            this.gameManager.getCurrentPlayer().payToTreasury(sum);
            payToTreasury(this.gameManager.getCurrentPlayer(), sum);

        } else if (type.equals(MonetaryCard.Who.PLAYERS)) {
            payToAllPlayers(sum);
        }
    }

    private void payToAllPlayers(long sum) throws InterruptedException {
        Player currentPlayer = this.gameManager.getCurrentPlayer();
        for (Player player : this.gameManager.getPlayers()) {
            if (!player.equals(currentPlayer)) {
                MonopolyGameBoardController.this.payToPlayer(currentPlayer, player, sum);
//                addMsgLabel(currentPlayer.getName() + " Pay to " + player.getName() + " " + sum + "Nis");
            }
        }
    }

    private void payToTreasury(Player currentPlayer, long sum) throws InterruptedException {
        String msg = " ";
        boolean isQuit = this.gameManager.getCurrentPlayer().isQuit();
        if (!isQuit) {
            msg = this.gameManager.getCurrentPlayer().getName() + " Just Paid To Treasury " + sum + " Nis";
        } else {
            msg = this.gameManager.getCurrentPlayer().getName() + " Dosen't Have Enough Mony! He Will Pay Treasury " + sum + "Nis And Leave The Game.";
        }
        addMsgLabel(msg);
        this.currentPlayerLabel.setToolTipText(currentPlayer.toString());
    }

    private void playMove(Player currentPlayer) throws Exception {
        if (!currentPlayer.isQuit()) {
            //player is not in parking
            if (!this.gameManager.isPlayerInParking()) {
                int[] diceRes = this.gameManager.rollTheDice();
                int numOfSteps = diceRes[0] + diceRes[1];
                changMsgLabel(", Your Dice : " + diceRes[0] + ", " + diceRes[1]);
                //player in jail
                if (currentPlayer.isInJail()) {
                    //double dice result
                    if (diceRes[0] == diceRes[1]) {
                        addMsgLabel("You Can Get Out From Jail In Your Next Turn!");
                        currentPlayer.setIsInJail(false);
                    } else if (currentPlayer.isIsHaveGetOutOfJailCard()) {
                        //have get out of jail card
                        this.gameManager.currentPlayerHaveGetOutCard();
                        move(numOfSteps, currentPlayer, true);
                    } else {
                        //in jail + no card + no double
                        addMsgLabel("You Can't Get Out From Jail! Wait One More Turn To Get One More Chanse!");
                    }
                } else {
                    move(numOfSteps, currentPlayer, true);
                }
            } else {
                // in parking
                addMsgLabel(", You Are In Parking Squar! ,wait for your next turn");
                currentPlayer.setIsInParking(false);
            }
        } else {
            removeFromPlayerLabelList(this.gameManager.getPleyerIndex());
            this.gameManager.handelPlayerPresence(currentPlayer);
            changMsgLabel(", Has Left The Game! Let's Continue...");
        }
        // nextTurn();
        if (this.gameManager.checkIfIsGameOver()) {
            this.endGameProp.set(true);
        }
        if (!this.isOnPertces) {
            // showNode(this.nextTurnButton);
        }
    }

    public PlayerLabel getCurrentPlayerLabel() {
        return currentPlayerLabel;
    }

    private void hideYesAndNoButton() {
        hideNode(this.yesButton);
        hideNode(this.noButton);
    }

    private void addMsgLabel(String msg) throws InterruptedException {
        //try to fix quation mark//
        this.msgLabel.setText(this.msgLabel.getText() + "\n" + msg);
        Thread.sleep(1000);
    }

    private void startNewGame(String fileName) throws Exception {
        GameController newGame = new GameController(fileName, false, true);
        this.setGameManager(newGame);
        resetBoardLogic();
        initBoardLogic(newGame.getMonopolyGame());
    }

    public void setErrorLabel(String msg) {
        this.errorLabel.setText(msg);
        showNode(this.errorLabel);
    }

    @Override
    public void resetScene() {
        this.gridPane.setPrefSize(WIDTH, HEIGHT);
        this.yesButtonProp.set(false);
        this.noButtonProp.set(false);
        this.endGameProp.set(false);
        this.yesPerchesButtonProp.set(false);
        this.noPerchesButtonProp.set(false);
        //    this.nextTurnButtonProp.set(false);
        this.isOnPertces = false;
        this.moveButtonProp.set(false);

        this.playerLabelList.clear();
    }

    private void resetBoardLogic() {
        this.gridPane.getChildren().clear();
    }

    @Override
    protected void actionMethod(Timer timer) {
        List<Event> events;
        try {
            events = this.monopoly.getEvents(playerId, evntIndex);
            for (Event event : events) {
                switch (event.getType()) {
                    case GAME_OVER:
                        gameOver(event, timer);
                        break;
                    case GAME_WINNER:
                        gameWinner(event);
                        break;
                    case PLAYER_RESIGNED:
                    case PLAYER_LOST:
                        removePlayerFromGame(event);
                        break;
                    case PLAYER_TURN:
                        playerTurn(event);
                        break;
                    case DICE_ROLL:
                        showDiceResult(event);
                        break;
                    case MOVE:
                        moveEvent(event);
                        break;
                    case PLAYER_USED_GET_OUT_OF_JAIL_CARD:
                    case PASSED_START_SQUARE:
                    case LANDED_ON_START_SQUARE:
                    case GO_TO_JAIL:
                        showMsg(event);
                        break;
                    case PROPMPT_PLAYER_TO_BY_HOUSE:
                    case PROPMT_PLAYER_TO_BY_ASSET:
                        proprmPlayerToBuy(event);
                    case ASSET_BOUGHT:
                        assetBought(event);
                        break;
                    case HOUSE_BOUGHT:
                        houseBought(event);
                        break;
                    case SURPRISE_CARD:
                        break;
                    case WARRANT_CARD:
                        break;
                    case GET_OUT_OF_JAIL_CARD:
                        break;
                    case PAYMENT:
                        payment(event);
                        break;
                    default:
                        evntIndex--;
                        break;
                }
//                playerDetails = this.monopoly.getPlayerDetails(playerId);
//                gameDet = this.monopoly.getGameDetails(gameName);
//                Platform.runLater(() -> SetView());
                evntIndex++;
            }
        } catch (InvalidParameters_Exception ex) {
            errorLabel.setText(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(MonopolyGameBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void gameOver(Event event, Timer timer) {
        if (!isEventNull(event)) {
            timer.cancel();
            this.endGameProp.set(true);
        }
    }

    private boolean isEventNull(Event event) {
        boolean res = event == null;

        if (res) {
            System.out.println("error event sequenceCreated  is null");
        }
        return res;
    }

    private void startGame() throws Exception {
        startPlaying(this.gameManager.getCurrentPlayer());
    }

    private void gameWinner(Event event) {
        if (!isEventNull(event)) {
            this.endGameProp.set(true);
        }
    }

    private void removePlayerFromGame(Event event) throws InterruptedException {
        showMsg(event);
        this.gameManager.playerResinged(playerName);
        if (this.playerName.equals(event.getPlayerName())) {
            this.playerResingeProp.set(true);
        }
    }

    private void playerTurn(Event event) throws InterruptedException, Exception {
        changMsgLabel(event.getPlayerName() + ", It's Your Turne");
        this.gameManager.setCurrentPlayer(this.gameManager.getPlayerByName(event.getPlayerName()));
        nextTurn();

    }

    private void showDiceResult(Event event) throws InterruptedException {
        String playerName = event.getPlayerName();
        int resOne = event.getFirstDiceResult();
        int resTwo = event.getSecondDiceResult();
        changMsgLabel(playerName + ", Your Dice : " + resOne + ", " + resTwo);
    }

    private void moveEvent(Event event) throws Exception {
        Player currentPlayer = this.gameManager.getCurrentPlayer();
        int squareNum = event.getNextBoardSquareID();
        this.currentPlayerLabel.move(this.gridPaneMap.get(squareNum));

    }

    private void proprmPlayerToBuy(Event event) throws InterruptedException {
        showMsg(event);
        if (event.getPlayerName().equals(this.playerName)) {
            showPerchesButton();
        }
    }

    private void showMsg(Event event) throws InterruptedException {
        String name = event.getPlayerName();
        String msg = event.getEventMessage();
        changMsgLabel(name + ", " + msg);
    }

    private void payment(Event event) {
        String currPlayer = event.getPlayerName();
        String payTo = event.getPaymentToPlayerName();
        int amount = event.getPaymentAmount();
//    if(event.getPaymentToPlayerName().)
    }

    public PlayerLabel getPlayerLabelByName(String name) {
        PlayerLabel toReturn = null;
        for (PlayerLabel player : this.playerLabelList) {
            if (player.getPlayerName().equals(name)) {
                toReturn = player;
            }
            break;
        }
        return toReturn;
    }

    private void assetBought(Event event) throws InterruptedException {
        showMsg(event);
        Player owner = this.gameManager.getPlayerByName(event.getPlayerName());

        SquareType square = (SquareType) this.gameManager.getMonopolyGame().getBoard().getContent().get(event.getBoardSquareID());
        square.updateOwner(owner);
        this.buttonMonopolyList.get(event.getBoardSquareID()).setTextForToolTip(square.getInfo());
    }

    private void houseBought(Event event) throws InterruptedException {
        showMsg(event);
        Player owner = this.gameManager.getPlayerByName(event.getPlayerName());
        SquareType square = (SquareType) this.gameManager.getMonopolyGame().getBoard().getContent().get(event.getBoardSquareID());
        CityType city = (CityType) square.getAsset();
        city.addOneToCounter();
        this.buttonMonopolyList.get(event.getBoardSquareID()).setTextForToolTip(square.getInfo());
    }

}
