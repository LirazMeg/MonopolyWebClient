/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonopolyFX.component;

import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Liraz
 */
public class ImageUtils {

    public static final String RESOURCES_FOLDER = "/pics/";

    public static Image getImage(String imageName) {
        InputStream imageInputStream = ImageUtils.class.getResourceAsStream(RESOURCES_FOLDER + imageName + ".png");
       Image image = new Image(imageInputStream,66,65,true,true);
       
       
        return image;
    }

    public static ImageView getImageView(String imageName) {
        ImageView image = new ImageView(getImage(imageName));

//        image.setFitWidth(60);
//        image.setFitWidth(55.0);
        image.setPreserveRatio(true);
        image.setSmooth(true);
        image.setCache(true);
        image.setOpacity(0.5);

        
        return image;
    }

}
