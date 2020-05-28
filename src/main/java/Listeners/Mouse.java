package Listeners;

import Constants.Constants;
import UI_components.UIConsole;
import UI_components.UItower;
import UI_components.UItowersManager;
import ecs_container.Actors.Player;
import ecs_container.towers.Tower;
import factories.GameTowerFactory;
import game_managers.logicManagers.GamePanel;
import game_managers.logicManagers.TowerManager;
import graphic_context.MapManager;
import graphic_context.MouseIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.function.Consumer;

/**
 * Mouse Manager Class, used for handling the mouse events.
 */
public class Mouse implements MouseMotionListener, MouseListener {
    boolean         towerIsInHand    = false;
    boolean         upgradeIsInHand  = false;
    boolean         sellIsInHand     = false;
    UItower         currentTower     = null;
    UItowersManager uItowersManager  = null;
    TowerManager    towerManager     = null;
    int             currentMouseButt = 0;


    MapManager mapManager = null;
    MouseIcon  mouseIcon  = null;
    private static int       mouseX     = -1;
    private static int       mouseY     = -1;
    private static int       mouseB     = -1;
    private static int       clickCount = 0;
    private        GamePanel gamePanel;

    public Mouse(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        mouseIcon = gamePanel.getMouseIcon();
        this.towerIsInHand = false;
        uItowersManager = gamePanel.getuItowersManager();
        mapManager = gamePanel.getMapManager();
        towerManager = gamePanel.getTowerManager();
    }

    public static int getX() {
        return mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static void setMouseY(int mouseY) {
        Mouse.mouseY = mouseY;
    }

    public static int getMouseB() {
        return mouseB;
    }

    public static void setMouseB(int mouseB) {
        Mouse.mouseB = mouseB;
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static void setMouseX(int mouseX) {
        Mouse.mouseX = mouseX;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    /**
     * mouse click events handling function
     * <p>if the mouse is right-clicked 2 times, then we might have to display a selected tower specifications.</p>
     * <p>if the mouse is right-clicked 1 time, then the player may have bought a tower.</p>
     * <p>if the mouse is left-clicked 1 time, then the player may have bought an upgrade or tried to sell a tower.</p>
     * <p>all the events are displayed on the UI Console</p>
     *
     * @param e awt mouseEvent
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        clickCount = e.getClickCount();

        if (clickCount == 2) {
            UIConsole.addMessage(
                    "Mouse entered: " +
                            "[" + e.getX() + "]" +
                            "[" + e.getY() + "]"
            );
            Constants.PairOfCoordinates towerCoordinates = towerManager.returnTowerCoordinates( e.getX(), e.getY() );

            if (towerCoordinates != null) {
                if (!towerManager.towerHasRangeVisible( towerCoordinates.getxCoord(), towerCoordinates.getyCoord() )) {
                    towerManager.setVisibleRange(
                            towerCoordinates.getxCoord(),
                            towerCoordinates.getyCoord()
                    );
                } else {
                    towerManager.hideVisibleRange(
                            towerCoordinates.getxCoord(),
                            towerCoordinates.getyCoord()
                    );
                }
            }
            // ! display an image
        } else {
            currentMouseButt = e.getButton();
            if (currentMouseButt == MouseEvent.BUTTON1) {
                if (!towerIsInHand) { // we try to buy a tower
                    currentTower = uItowersManager.getTower(
                            e.getX(),
                            e.getY() );
                    if (currentTower != null && Player.tryToBuyTower( currentTower.getType() )) {
                        UIConsole.addItemIntoBuyHistory( currentTower.getSprite().getImage() );
                        UIConsole.addMessage( "You have bought a tower: [" + currentTower.getType() + "]" );
                        towerIsInHand = true;

                    } else if (currentTower != null && Player.tryToBuyTower( currentTower.getType() )) {
                        UIConsole.addMessage( "Not enough money to buy: " + currentTower.getType() );
                    }
                    // ! display an image
                } else {
                    if (currentTower != null) { // we have a bought tower but not yet placed
                        MapManager.Pair placeablePosition = mapManager.placeTower(
                                e.getX(),
                                e.getY(),
                                currentTower.getSprite().getImage()
                        );
                        if (placeablePosition != null &&
                                mapManager.getTileProperty( placeablePosition.getxCoord(), placeablePosition.getyCoord() ) == Constants.tileProperty.PLACEABLE) {
                            towerManager.addTower(
                                    GameTowerFactory.createInstance(
                                            placeablePosition.getxCoord(),
                                            placeablePosition.getyCoord(),
                                            currentTower.getSprite().getResizedVersion( Constants.TILE_SIZE, Constants.TILE_SIZE ),
                                            currentTower.getType()
                                    ),
                                    currentTower.getTowerName()
                            );
                            UIConsole.addMessage(
                                    "Tower set on: "
                                            + "[" + placeablePosition.getxCoord() + "]"
                                            + "[" + placeablePosition.getyCoord() + "]"
                            );
                            towerIsInHand = false;
                        }
                    }
                }
            } else if (currentMouseButt == MouseEvent.BUTTON3) {
                int mouseX = e.getX();
                int mouseY = e.getY();

                if (!upgradeIsInHand) {
                    if (
                            mouseX >= Constants.IN_GAME_UPGRADE_BUTTON_X && mouseX <= Constants.IN_GAME_UPGRADE_BUTTON_X + Constants.IN_GAME_UPGRADE_BUTTON_WIDTH &&
                                    mouseY >= Constants.IN_GAME_UPGRADE_BUTTON_Y && mouseY <= Constants.IN_GAME_UPGRADE_BUTTON_Y + Constants.IN_GAME_UPGRADE_BUTTON_HEIGHT
                    ) {
                        UIConsole.addMessage( "Mouse placed on UPGRADE button..." );
                        mouseIcon.setMouseSprite( new ImageIcon( Constants.UPGRADE_HAMMER_URL ).getImage() );
                        upgradeIsInHand = true;
                    }
                } else {
                    Tower tower = towerManager.getTowerByCoordinates( mouseX, mouseY );
                    if (tower != null && tower.mayUpgrade() && Player.tryToBuyUpgrade( tower.getUpgradeCost() )) {
                        tower.upgrade();
                        UIConsole.addMessage( "Upgrade bought for: [" + currentTower.getType() + "]" );
                        UIConsole.addItemIntoBuyHistory(
                                new ImageIcon( Constants.UPGRADE_HAMMER_URL ).getImage()
                        );
                        // ! change upgrade icon and upgrade the tower literarly
                    } else if (tower != null) {
                        UIConsole.addMessage( "Not enough money for upgrade or MAX_LEVEL" );
                    } else {
                        UIConsole.addMessage(
                                "Mouse is not set on any tower to upgrade: " +
                                        "[" + mouseX + "]" +
                                        "[" + mouseY + "]"
                        );
                    }
                    upgradeIsInHand = false;
                }

                if (!sellIsInHand) {
                    if (
                            mouseX >= Constants.IN_GAME_SELL_BUTTON_X && mouseX <= Constants.IN_GAME_SELL_BUTTON_X + Constants.IN_GAME_SELL_BUTTON_WIDTH &&
                                    mouseY >= Constants.IN_GAME_SELL_BUTTON_Y && mouseY <= Constants.IN_GAME_SELL_BUTTON_Y + Constants.IN_GAME_SELL_BUTTON_HEIGHT
                    ) {
                        UIConsole.addMessage( "Mouse placed on SELL button..." );
                        mouseIcon.setMouseSprite( new ImageIcon( Constants.SELL_ICON_URL ).getImage() );
                        sellIsInHand = true;
                    }
                } else {
                    Tower tower = towerManager.getTowerByCoordinates( mouseX, mouseY );
                    if (tower != null) {
                        Player.addMoney( ( int ) tower.refund() );
                        UIConsole.addMessage( "Refund for: [" + currentTower.getType() + "]" );
                        UIConsole.addItemIntoBuyHistory(
                                new ImageIcon( Constants.SELL_ICON_URL ).getImage()
                        );
                    } else {
                        UIConsole.addMessage(
                                "Mouse is not set on any tower to refund: " +
                                        "[" + mouseX + "]" +
                                        "[" + mouseY + "]"
                        );
                    }
                    sellIsInHand = false;
                }
            }
        }
    }


    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseB = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseB = -1;
    }

}
