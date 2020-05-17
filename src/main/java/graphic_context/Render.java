package graphic_context;

import Constants.Constants;

import javax.swing.*;
import java.awt.*;

public class Render {
    public static boolean render(Graphics graphicContext, Image image, int x, int y, int width, int height) {
        return graphicContext.drawImage(
                image,
                x,
                y,
                width,
                height,
                null
        );
    }
}
