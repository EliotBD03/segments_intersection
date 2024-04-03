package ac.umons.be.firstg.segmentintersection.view.utils;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Icon
{
    public static ImageView getIcon(Class<?> callingClass, String iconName, float size)
    {
        ImageView imageView = new ImageView(String.valueOf(callingClass.getResource("icons/" + iconName)));
        imageView.setScaleX(size);
        imageView.setScaleY(size);

        return imageView;
    }

    public static void setButtonIcon(Class<?> callingClass, String iconName, Button button)
    {
        ImageView buttonIcon = Icon.getIcon(callingClass, iconName, 1);
        buttonIcon.fitWidthProperty().bind(button.widthProperty());
        buttonIcon.fitHeightProperty().bind(button.heightProperty());
        button.setGraphic(buttonIcon);
    }
}
