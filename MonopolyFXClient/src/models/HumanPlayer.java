/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import models.Player;
import views.ConsolUI;

/**
 *
 * @author Liraz
 */
public class HumanPlayer extends Player {

    public HumanPlayer(String name, long amount) {
        super(name, amount);
    }

    public HumanPlayer(String name) {
        super(name);
    }

    @Override
    public boolean doYouWantToPurchaseHouse(CityType square, long price) {
        return ConsolUI.askIfWantToPerchasAHouse(price, square.getName(), square.getCounterOfHouse() + 1, this.amount);
    }

    @Override
    public boolean doYouWantToPurchase(AssetType square, long price) {
     String name = square.getName();
        
        return ConsolUI.askIfWantToPerchasAsset(name, square.getCost(),this.getAmount());
    }

}
