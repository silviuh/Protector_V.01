package UI_components;

import Constants.Constants;
import ecs_container.Actors.Player;
import factories.ClockFactory;
import game_managers.logicManagers.ClockManager;
import game_managers.menus.HelpMenu;
import utilities.Clock;

import javax.swing.*;
import java.awt.*;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UIConsole {
    private static ArrayList< Image >  buyHistory;
    private static Image               consoleImage;
    private static int                 width;
    private static Clock               updateClock;
    private static int                 height;
    private static int                 x;
    private static int                 y;
    private static ArrayList< String > consoleMessages;

    static {
        consoleImage = new ImageIcon( Constants.UI_CONSOLE_BG ).getImage();
        width = Constants.CONSOLE_WIDTH;
        height = Constants.CONSOLE_HEIGHT;
        x = Constants.CONSOLE_X;
        y = Constants.CONSOLE_Y;
        updateClock = ClockFactory.createInstance(
                Constants.clockType.UI_CONSOLE
        );
        buyHistory = new ArrayList< Image >( Constants.BUY_HISTORY_CAPACITY );
        consoleMessages = new ArrayList< String >( Constants.UI_CONSOLE_CAPACITY );

        consoleMessages.add( "Welcome to PROTECTOR V.01" );
        consoleMessages.add( "Try your best to kill all invaders." );
        consoleMessages.add( "You may upgrade your towers with the upgrade button" );
        consoleMessages.add( "Try not to waste all your money and place towers strategically." );
        consoleMessages.add( "\t\t\tGood luck! <^^>" );
    }

    public static void printHelpMenu() {
        consoleMessages.add( "Welcome to PROTECTOR V.01" );
        consoleMessages.add( "[Life: " + Player.getNrOfLives() + "\\" + Player.getInitialNumberOfLives() + "]" );
        consoleMessages.add( "\t\t\tGood luck! <^^>" );
    }

    public static boolean addMessage(String Message) {
        if (consoleMessages.size() < Constants.UI_CONSOLE_CAPACITY) {
            consoleMessages.add( Message );
            return true;
        }
        return false;
    }

    public static boolean addItemIntoBuyHistory(Image item) {
        if (buyHistory.size() < Constants.BUY_HISTORY_CAPACITY) {
            buyHistory.add( item );
            return true;
        }
        return false;
    }

    public static void update() {
        updateBuyHistory();
        if (consoleMessages.size() == Constants.UI_CONSOLE_CAPACITY - 1) {
            consoleMessages.clear();
            UIConsole.printHelpMenu();
        }
    }

    public static void updateBuyHistory() {
        if (buyHistory.size() == Constants.BUY_HISTORY_CAPACITY - 1) {
            buyHistory.clear();
        }
    }

    public static void render(Graphics g) {
        try {
            g.setFont(
                    Font.createFont(
                            Font.TRUETYPE_FONT,
                            new FileInputStream( new File( Constants.UBUNTU_FONT_BOLD ) ) ).deriveFont( 18.0f )
            );
        } catch ( FontFormatException | IOException e ) {
            e.printStackTrace();
        }

        g.drawRect(
                x,
                y,
                width,
                height
        );
        g.setColor( Constants.UIConsole_bg_color );
        g.fillRect(
                x,
                y,
                width,
                height
        );

        g.setColor( Constants.dollarSign);
        for (int counter = 0; counter < consoleMessages.size(); counter++) {
            g.drawString(
                    "[ PROTECTOR ]:~$ " + consoleMessages.get( counter ),
                    x + Constants.CONSOLE_MESSAGES_X_PADDING,
                    y + counter * Constants.CONSOLE_MESSAGES_Y_PADDING + Constants.CONSOLE_FIRST_MESSAGE_BORDER
            );
        }

        g.setColor( Color.green );
        g.drawString(
                "[BUY HISTORY]: ",
                Constants.BUY_HISTORY_X,
                Constants.BUY_HISTORY_Y - Constants.BUY_HISTORY_MESSAGE_Y_PADDING
        );

        g.setColor( Constants.dollarSign );
        for (int counter = 0; counter < buyHistory.size(); counter++) {
            g.drawLine( // |
                    Constants.BUY_HISTORY_X + Constants.BUY_HISTORY_X_PADDING * counter,
                    Constants.BUY_HISTORY_Y,
                    Constants.BUY_HISTORY_X + Constants.BUY_HISTORY_X_PADDING * counter,
                    Constants.BUY_HISTORY_Y + Constants.BUY_HISTORY_IMAGE_HEIGHT
            );
            g.drawLine(// -
                    Constants.BUY_HISTORY_X + Constants.BUY_HISTORY_X_PADDING * counter,
                    Constants.BUY_HISTORY_Y,
                    Constants.BUY_HISTORY_X + Constants.BUY_HISTORY_X_PADDING * counter + Constants.BUY_HISTORY_X_PADDING,
                    Constants.BUY_HISTORY_Y
            );
            g.drawLine( //
                    Constants.BUY_HISTORY_X + Constants.BUY_HISTORY_X_PADDING * counter + Constants.BUY_HISTORY_X_PADDING,
                    Constants.BUY_HISTORY_Y,
                    Constants.BUY_HISTORY_X + Constants.BUY_HISTORY_X_PADDING * counter + Constants.BUY_HISTORY_X_PADDING,
                    Constants.BUY_HISTORY_Y + Constants.BUY_HISTORY_IMAGE_HEIGHT

            );
            g.drawLine( // __
                    Constants.BUY_HISTORY_X + Constants.BUY_HISTORY_X_PADDING * counter,
                    Constants.BUY_HISTORY_Y - 2 + Constants.BUY_HISTORY_IMAGE_HEIGHT,
                    Constants.BUY_HISTORY_X + Constants.BUY_HISTORY_X_PADDING * counter + Constants.BUY_HISTORY_X_PADDING,
                    Constants.BUY_HISTORY_Y - 2 + Constants.BUY_HISTORY_IMAGE_HEIGHT

            );
            g.drawImage(
                    buyHistory.get( counter ),
                    Constants.BUY_HISTORY_X + Constants.BUY_HISTORY_X_PADDING * counter,
                    Constants.BUY_HISTORY_Y,
                    Constants.BUY_HISTORY_IMAGE_WIDTH,
                    Constants.BUY_HISTORY_IMAGE_HEIGHT,
                    null
            );
        }
    }

    public static Clock getUpdateClock() {
        return updateClock;
    }
}
