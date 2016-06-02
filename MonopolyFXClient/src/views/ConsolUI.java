/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.util.ArrayList;
import java.util.Scanner;
import models.ComputerPlayer;
import models.Player;

/**
 *
 * @author Liraz
 */
public class ConsolUI {

    final static String HOW_MANY_PLAYERS = "How Many Player Are Playing?";
    final static String HOW_MANY_HUMAN_PLAYERS = "How Many Player Are Humans?";
    final static String NAMES_QUETION = "Please Enter Your Name";
    final static int MAX_OF_PLAYER = 6;

    public static ArrayList<String> getStartInfo() {

        String tempAnswer = "";
        int numberOfPlayers = 0;
        int numberOfHumenPayers = 0;
        ArrayList<String> startInfo = new ArrayList<>();
        //putting the number Of Players at the head of the list
        numberOfPlayers = getNumberFromUser(HOW_MANY_PLAYERS);
        while (numberOfPlayers == 0) {
            ConsolUI.notEnoughPlayers();
            numberOfPlayers = getNumberFromUser(HOW_MANY_PLAYERS);
        }
        String str = Integer.toString(numberOfPlayers);
        startInfo.add(str);

        numberOfHumenPayers = getNumberFromUser(HOW_MANY_HUMAN_PLAYERS);
        while (numberOfHumenPayers > numberOfPlayers) {
            numberOfHumenPayers = getNumberFromUser(HOW_MANY_HUMAN_PLAYERS);
        }
        //putting the number Of Humen Payers at the list
        str = Integer.toString(numberOfHumenPayers);
        startInfo.add(str);
        for (int i = 0; i < numberOfHumenPayers; i++) {
            System.out.print("Player " + (i + 1) + " ");
            tempAnswer = printQuetionReturnAnswer(NAMES_QUETION);
            //if name is allredy exsist, ask again
            if (startInfo.contains(tempAnswer)) {
                ConsolUI.msgNameAllredyExsist();
                tempAnswer = printQuetionReturnAnswer(NAMES_QUETION);
            }
            startInfo.add(tempAnswer);
        }
        return startInfo;
    }

    private static void msgNameAllredyExsist() {
        System.out.println("This Name Is Allredt Exsist! Try Another One Please!");
    }

    public static void showWinner(String winnerName) {
        System.out.println(winnerName + " You Are The Winner!");
    }

    private static int getNumberFromUser(String quation) {

        Scanner sc = new Scanner(System.in);
        int number;
        do {
            System.out.println(quation);
            while (!sc.hasNextInt()) {
                System.out.println("Invalid Input, Try Again Please");
                System.out.println(quation);
                sc.next(); // this is important!
            }
            number = sc.nextInt();
        } while (number < 0 || number > MAX_OF_PLAYER);

        return number;
    }

    public static String loadDest() {
        Scanner input = new Scanner(System.in);
        String res, res2 = System.getProperty("line.separator");
        System.out.println("Please enter a destination to load your Game");
        res = input.next();
        if (!res.contains("\\")) {
            res2 += res + "\\";
            return res2;
        }
        return res;
    }

    public static boolean askAndGetAnswer(String quation) {
        boolean res = false;
        Scanner sc = new Scanner(System.in);
        String answer;
        do {
            System.out.println(quation);
            while (!sc.hasNext()) {
                System.out.println("Invalid Input, Try Again Please");
                System.out.println(quation);
                sc.next(); // this is important!
            }
            answer = sc.next();
        } while (!answer.equals("Y") && !answer.equals("N"));

        if (answer.equals("Y")) {
            res = true;
        }
        return res;
    }

    public static int getNumberOfHumanPlayers() {
        int numOfHumanPlayers = Integer.parseInt(printQuetionReturnAnswer(HOW_MANY_HUMAN_PLAYERS));
        return numOfHumanPlayers;
    }

    public static String printQuetionReturnAnswer(String quation) {
        String answer = "";
        System.out.println(quation);
        Scanner s = new Scanner(System.in);
        answer = s.nextLine();
        return answer;
    }

    public static void printTheDice(int diceNum) {
        System.out.println(diceNum);

    }

    public static boolean askIfWantToPerchasAHouse(long price, String assatName, long houseNumber, long amount) {
        boolean result = false;
        String answer = "";
        answer = "You Are In " + assatName + " Do You Want To Buy House Number " + houseNumber + " (price " + price + ", you have: " + amount + ") ? Y/N";
        return askAndGetAnswer(answer);
    }

    public static void showDiceResult(int[] diecResult) {
        System.out.println("Your Dice : " + diecResult[0] + " , " + diecResult[1]);
    }

    public static void msgPlayerInParking() {
        System.out.println("You Are In Parking Squar! ,wait for your next turn");
    }

    public static void msgPlayerGoOutFromJail() {
        System.out.println("You Can Get Out From Jail In Your Next Turn!");
    }

    public static void msgPlayerWillStyInJailOneMoreTurn() {
        System.out.println("You Can't Get Out From Jail! Wait One More Turn To Get One More Chanse!");
    }

    public static void showCurrentLocationOfPlayer(int squreNum, String currentSquare, String currentPlayerName) throws Exception {
        try {
            StringBuilder toPrint = new StringBuilder();
            toPrint.append(currentPlayerName);
            toPrint.append(", You are on ");
            toPrint.append(currentSquare);
            toPrint.append("(square number ");
            toPrint.append(squreNum + 1);
            toPrint.append(")");
            System.out.println(toPrint.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void msgMonoteryCard(String text, long sum) {
        System.out.printf(text, sum);
        System.out.println();
    }

    public static void msgCard(String text) {
        System.out.println(text);
    }

    public static void tankYouForYourPurchase() {
        System.out.println("Tank You For Your Purchase! Enjoy!");
    }

    public static void msgNextTurnPlayer(String name) {
        StringBuilder toPrint = new StringBuilder();
        toPrint.append(name);
        toPrint.append(", it is your turn now!");
        System.out.println(toPrint);
    }

    public static boolean askIfWantToPerchasAsset(String name, long cost, long amount) {
        boolean result = false;
        String answer = "";
        answer = "Do You Want To Buy " + name + " (price " + cost + ", your amount: " + amount + ")? (Y/N)";
        return askAndGetAnswer(answer);
    }

    public static void msgCantBuy() {
        System.out.println("You can not purchase the property");
    }

    public static void computerPerchasAHouse(long price, String assatName, long houseNumber) {
        System.out.println("Computer Just purchased House Number " + houseNumber + " At " + assatName + " in " + price + " Nis");
    }

    public static void computerPerchas(String playerName, long price, String assatName) {
        System.out.println(playerName + " Just purchased  " + assatName + " in " + price + " Nis");
    }

    private static void notEnoughPlayers() {
        System.out.println("Not Enough Players! Try Call Your Friends To Play With You...  ");
    }

    public static void msgPayment(String payToName, String currentPlayerName, long staycost) {
        System.out.println(currentPlayerName + " Just Paid To " + payToName + " " + staycost + " Nis");
    }

    public static void msgNotEnoughPayment(String currentPlayerName, String payToName, long leftAmount) {
        System.out.println(currentPlayerName + " Dosen't Have Enough Mony! He Will Pay " + payToName + " " + leftAmount + " Nis And Leave The Game.");
    }

    public static void msgStepOnStartSqure() {
        System.out.println("You Get 400 Nis");
    }

    public static void msgPassedByStartSquar() {
        System.out.println("You Just Stepped By Start Squar , You Get 200 Nis , Enjoy!");
    }

    public static void msgPlayerInJail(String nameOfPlayer) {
        System.out.println(nameOfPlayer + " You Are In Jail, You Will Stay Here T'ill You Get Double On Your Dice! Good Luck!");
    }

    public static boolean continueOrQuit(Player currentPlayer) {
        boolean res = false;

        if (currentPlayer.getClass().equals(ComputerPlayer.class)) {
            res = false;
        } else {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();;
            if (input.equals("E")) {
                res = true;
            }
        }
        return res;
    }

    public static void msgPlayerIsOut(String playerName) {
        System.out.println(playerName + " Has Left The Game! Let's Continue...");
    }

    public static void MsgGetOutFromJailByGetOutCard() {
        System.out.println("You Get Out From Jail By Useing  Your - Get Out Frm Jail Card");
    }

    public static void MsgPreseEnterToContinue() {
        System.out.println("Press any key to continue in the game...");
        System.out.println("Press any key to continue...");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
    }

    public static void msgStartGame() {
        System.out.println("Wellcom To The Monopoly Game !!!");
    }

    public static boolean msgAnotherGame() {
        return askAndGetAnswer("Would you like to play again? Y/N");
    }

    public static boolean msgUpLoadFile() {
        return askAndGetAnswer("Do you want to upload your own board? (Y/N)");
    }

    public static void msgGoodBy() {
        System.out.println("GoodBy!!!");
    }

}
