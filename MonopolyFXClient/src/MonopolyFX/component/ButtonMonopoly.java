/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonopolyFX.component;

import java.awt.Color;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 *
 * @author Liraz
 */
public class ButtonMonopoly extends Button {

    private final static String DEFULT_IMAGE = "defult";

    public ButtonMonopoly(String text, String imgStr, String infoTxt) {
        setText(text);
        Image image;
        try {
            image = ImageUtils.getImage(imgStr);
        }catch(Exception ex){
            image = ImageUtils.getImage(DEFULT_IMAGE);
        }
        BackgroundImage bckImg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        setBackground(new Background(bckImg));

        this.setTextForToolTip(infoTxt);

    }

    public void setTextForToolTip(String newInfo) {
        Tooltip tooltip = new Tooltip(newInfo);
        this.setTooltip(tooltip);

    }
}
