/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scene.StartWindowScene;

import controllers.GenericController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author efrat
 */
public class StartWindowController extends GenericController implements Initializable {

    @FXML
    private ComboBox comboBoxNumPlayers;
    @FXML
    private ComboBox comboBoxNumHumenPlayers;
    @FXML
    private Button buttonSubmit;

    private SimpleBooleanProperty buttonSubmitedProp;
    private SimpleBooleanProperty humanPlayerSelectedProp;

    public SimpleBooleanProperty getSubmitButtonPror() {
        return this.buttonSubmitedProp;
    }

    public SimpleBooleanProperty getHumanPlayerSelectedProp() {
        return humanPlayerSelectedProp;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.comboBoxNumPlayers.getItems().addAll(2, 3, 4, 5, 6);
//        comboBoxNumPlayers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
//            @Override
//            // when preesed on first combo
//            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
//                setComboBoxNumHumenPlayers((int) newValue);
//            }
//        });

////ttttrrryyyyyy
        comboBoxNumPlayers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            // when preesed on first combo
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                setComboBoxNumHumenPlayers((int) newValue);
            }
        });

        comboBoxNumHumenPlayers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            // when preesed on first combo
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                int selectedItem = (int) comboBoxNumHumenPlayers.getSelectionModel().getSelectedItem();
                gameManager.setNumOfHumanPlayers(selectedItem);
                showNode(buttonSubmit);
                
//                humanPlayerSelectedProp.set(true);
            }
        });

        // this.comboBoxNumHumenPlayers.setEditable(true);
        hideNode(comboBoxNumHumenPlayers);
        hideNode(buttonSubmit);
        this.buttonSubmitedProp = new SimpleBooleanProperty(false);
        this.humanPlayerSelectedProp = new SimpleBooleanProperty(false);
    }

    private void resetCombo(int size) {
        for (int i = 0; i < size; i++) {
            this.comboBoxNumHumenPlayers.getItems().remove(i);
        }
    }

    public ComboBox getComboBoxNumPlayers() {
        return comboBoxNumPlayers;
    }

    public void setComboBoxNumHumenPlayers(int newNumOfPlayer) {
        this.comboBoxNumHumenPlayers.getSelectionModel().selectLast();
        this.comboBoxNumHumenPlayers.getItems().clear();
        for (int i = 1; i <= newNumOfPlayer; i++) {
            this.comboBoxNumHumenPlayers.getItems().add(i);
        }
        comboBoxNumHumenPlayers.setDisable(false);
        comboBoxNumHumenPlayers.setVisible(true);
    }

    //when preesed on second combo
//    @FXML
//    private void onNumberOfHumenPlayers(ActionEvent event) {
//        showNode(this.buttonSubmit);
//        int selectedItem = (int) this.comboBoxNumHumenPlayers.getSelectionModel().getSelectedItem();
//        this.gameManager.setNumOfHumanPlayers(selectedItem);
//        humanPlayerSelectedProp.set(true);
//    }
    @FXML
    private void onSubmitButten(ActionEvent event) {
        buttonSubmitedProp.set(true);
    }

    public ComboBox getComboBoxNumHumenPlayers() {
        return comboBoxNumHumenPlayers;
    }

    private void setHumanPlayerComboBox(int numOfPlayer) {
        this.gameManager.setNumOfPlayers(numOfPlayer);
        setComboBoxNumHumenPlayers(numOfPlayer);
    }

    @Override
    public void resetScene() {
//        this.comboBoxNumPlayers.getItems().clear();
//        this.comboBoxNumPlayers.getItems().addAll(2, 3, 4, 5, 6);
        hideNode(comboBoxNumHumenPlayers);
        hideNode(buttonSubmit);
        this.buttonSubmitedProp.set(false);
        this.humanPlayerSelectedProp.set(false);
    }

}