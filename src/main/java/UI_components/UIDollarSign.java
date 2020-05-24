package UI_components;

import Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UIDollarSign {
    private float counter;
    private Image dollarImage;
    private int   width;
    private int   height;
    private int   x;
    private int   y;

    public UIDollarSign(int initialCounter, ImageIcon imageIcon, int width, int height, int x, int y) {
        this.counter = initialCounter;
        this.dollarImage = imageIcon.getImage();
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void modifyCashAmount(float amount) {
        counter += amount;
    }

    public void render(Graphics g) {
        g.drawImage(
                this.dollarImage,
                this.x,
                this.y,
                this.width,
                this.height,
                null
        );

        g.setColor( Constants.dollarSign );

        /*
        try (FileInputStream fileInputStream = new FileInputStream( new File( Constants.KENVECTOR_FUTURE_THIN_URL ) )) {
            g.setFont(
                    Font.createFont(
                            Font.TRUETYPE_FONT,
                            fileInputStream ).deriveFont( 60.0f )
            );
        } catch ( FontFormatException | IOException e ) {
            e.printStackTrace();
        }
         */
        g.setFont( Constants.KENVECTOR_60 );

        g.drawString(
                "[CASH]: " + Integer.toString( ( int ) counter ),
                x + Constants.DOLLAR_SIGN_X_PADDING,
                y + Constants.DOLLAR_SIGN_Y_PADDING
        );
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
