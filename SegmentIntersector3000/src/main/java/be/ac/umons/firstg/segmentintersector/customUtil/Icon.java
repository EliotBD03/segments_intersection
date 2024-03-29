package be.ac.umons.firstg.segmentintersector.customUtil;

import javafx.scene.image.ImageView;

public class Icon
{
    public static ImageView getIcon(Class<?> callingClass, String iconName, float size)
    {
        System.out.println(callingClass.getResource("icons/" + iconName));
        System.out.println("size: " + size);
        ImageView imageView = new ImageView(String.valueOf(callingClass.getResource("icons/" + iconName)));
        imageView.setScaleX(size);
        imageView.setScaleY(size);

        return imageView;
    }
}
