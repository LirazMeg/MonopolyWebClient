/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import game.client.ws.Event;
import game.client.ws.GameDoesNotExists_Exception;
import game.client.ws.InvalidParameters_Exception;
import game.client.ws.MonopolyWebService;
import game.client.ws.PlayerDetails;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liraz
 */
public class PullingMsgAndFilterController {

    private int index = 0;
    private int playerId;

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public List<Event> getEventsAndFiltr(MonopolyWebService monopoly) throws InvalidParameters_Exception, GameDoesNotExists_Exception {
        List<Event> eventsFilter = new ArrayList<>();
        List<Event> events = monopoly.getEvents(this.index, this.playerId);
        this.index += events.size();
        PlayerDetails playerDetails = monopoly.getPlayerDetails(this.playerId);
        String name = playerDetails.getName();

        for (Event event : events) {
            if (event.getPlayerName().equals(name)) {
                eventsFilter.add(event);
            }
        }
        return eventsFilter;
    }

    public void activeEvent(Event event) {

        switch (event.getType()) {
            case GAME_START:

                break;
            case GAME_OVER:
                break;
            case GAME_WINNER:
                break;
            case PLAYER_LOST:
                break;
            case DICE_ROLL:
                break;
            case MOVE:
                break;
            case PASSED_START_SQUARE:
                break;
            case LANDED_ON_START_SQUARE:
                break;
            case GO_TO_JAIL:
                break;
            case PROPMT_PLAYER_TO_BY_ASSET:
                break;
            case PROPMPT_PLAYER_TO_BY_HOUSE:
                break;
            case ASSET_BOUGHT:
                break;
            case HOUSE_BOUGHT:
                break;
            case SURPRISE_CARD:
                break;
            case WARRANT_CARD:
                break;
            case GET_OUT_OF_JAIL_CARD:
                break;
            case PAYMENT:
                break;
            case PLAYER_USED_GET_OUT_OF_JAIL_CARD:
                break;
            case PLAYER_TURN:
                break;
            case PLAYER_RESIGNED:
                break;
            default:
                throw new AssertionError(event.getType().name());
        }
    }
}
