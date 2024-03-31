package ac.umons.be.firstg.segmentintersection.view.utils;

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
}
