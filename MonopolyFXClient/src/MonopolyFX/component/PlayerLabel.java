/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonopolyFX.component;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

/**
 *
 * @author Liraz
 */
public class PlayerLabel extends Label {

    private final TranslateTransition transition;

    public PlayerLabel(String playerName, String imgStr) {

        setHeight(33);
        setWidth(44);

        this.setTextForToolTip(playerName);
        setAlignment(Pos.TOP_LEFT);
        
//        Image image = ImageUtils.getImage(imgStr);
//        BackgroundImage bckImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//        setBackground(new Background(bckImg));
        
        setGraphic(ImageUtils.getImageView(imgStr));
        transition = createTransition();
    }

    public void setToolTipText(String txt) {
        this.setTooltip(new Tooltip(txt));
    }

    private TranslateTransition createTransition() {
        TranslateTransition translateTransition
                = new TranslateTransition(Duration.seconds(0.5), this);
        //translateTransition.setOnFinished((actionEvent) -> registerEventHandlers());
        return translateTransition;
    }

//    private void registerEventHandlers() {
//    }
    private void MovePlayetToNextSquar(int x, int y) {
        this.relocate(x, y);
    }

    private void runInSlowMotion(int newX, int newY) {
        transition.setFromX(boundsInParentProperty().getValue().getMinX());
        transition.setFromY(boundsInParentProperty().getValue().getMinY());
        transition.setToX(newX);
        transition.setToY(newY);

        transition.play();
    }

    private void setTextForToolTip(String playerName) {
        Tooltip tooltip = new Tooltip(playerName);
        this.setTooltip(tooltip);
    }

    public void move(Point2D point) {
        double x = getNewX(point.getX());
        double y = getNewY(point.getY());

        //this.relocate(point.getX(), point.getY());
        transition.setFromX(boundsInParentProperty().getValue().getMinX());
        transition.setFromY(boundsInParentProperty().getValue().getMinY());
        transition.setToX(x);
        transition.setToY(y);
        transition.play();
    }

    private double getNewX(double x) {
        //first time= 0 
        double res = x * (this.getScene().getWidth()) / 10;
        if (x == 0) {
            res = 0;
        }
        return res;
    }

    private double getNewY(double y) {
        double res = y * (this.getScene().getHeight()) / 10;
        if (y == 0) {
            res = 0;
        }
        return res;
    }
}
