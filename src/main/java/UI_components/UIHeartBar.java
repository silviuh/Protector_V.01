package UI_components;

import Constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UIHeartBar {
    private int   currentNrOfLifes;
    private int   initialNumberOfLifes;
    private Image heartForeground;
    private Image heartBackground;
    private int   width;
    private int   height;
    private int   x;
    private int   y;

    public UIHeartBar(int initialCounter, ImageIcon foregroundIcon, ImageIcon backgroundIcon, int width,
                      int height, int x, int y, int currentNrOfLifes) {
        this.initialNumberOfLifes = initialCounter;
        this.currentNrOfLifes = currentNrOfLifes;
        this.heartForeground = foregroundIcon.getImage();
        this.heartBackground = backgroundIcon.getImage();
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void updateNrOfLifes(int amount) {
        this.currentNrOfLifes += amount;
    }

    public void render(Graphics g) {
        float percentage = this.currentNrOfLifes / ( float ) this.initialNumberOfLifes;

        g.drawImage(
                this.heartBackground,
                this.x,
                this.y,
                this.width,
                this.height,
                null
        );

        g.drawImage(
                this.heartForeground,
                this.x + Constants.HEART_FOREGROUND_PADDING,
                this.y + 1,
                ( int ) ( ( this.width - Constants.HEART_FOREGROUND_PADDING ) * percentage ),
                this.height - Constants.HEART_FOREGROUND_HEIGHT_PADDING,
                null
        );

    }

    public int getCurrentNrOfLifes() {
        return currentNrOfLifes;
    }

    public void setInitialNumberOfLifes(int initialNumberOfLifes) {
        this.initialNumberOfLifes = initialNumberOfLifes;
    }
}
