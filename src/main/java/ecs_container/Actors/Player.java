package ecs_container.Actors;

import Constants.Constants;
import UI_components.UIConsole;
import UI_components.UIDollarSign;
import UI_components.UIHeartBar;
import factories.ClockFactory;
import game_managers.logicManagers.ClockManager;

import javax.swing.*;
import java.util.concurrent.locks.ReentrantLock;

public class Player {
    public static  UIHeartBar    uiHeartBar;
    public static  UIDollarSign  uiDollarSign;
    public static  UIConsole     uiConsole;
    private static Player        player;
    private static ReentrantLock singletonLock;
    private static int           lives;
    private static int           initialNumberOfLives;
    private static int           deposit;
    private static int           fortressX;
    private static int           fortressY;

    private Player() {
    }

    public static Player getInstance(int nrOfLives, int initialNumberOfLives, int initialDeposit, ClockManager clockManager) {
        singletonLock = new ReentrantLock();
        if (player == null) {
            try {
                singletonLock.lock();
                player = new Player();
                setUp(
                        nrOfLives,
                        initialNumberOfLives,
                        initialDeposit,
                        clockManager
                );
            } finally {
                singletonLock.unlock();
            }
        }
        return player;
    }

    public static void setUp(int nrOfLives, int initialNumberOfLives, int initialDeposit, ClockManager clockManager) {
        setDeposit( initialDeposit );
        setInitialNumberOfLives( initialNumberOfLives );
        setLives( initialNumberOfLives );

        uiHeartBar = new UIHeartBar(
                initialNumberOfLives,
                new ImageIcon( Constants.HEARTH_BAR_FOREGROUND_URL ),
                new ImageIcon( Constants.HEARTH_BAR_BACKGROUND_URL ),
                Constants.HEART_BAR_WIDTH,
                Constants.HEART_BAR_HEIGHT,
                Constants.HEART_BAR_X,
                Constants.HEART_BAR_Y
        );

        uiDollarSign = new UIDollarSign(
                initialDeposit,
                new ImageIcon( Constants.DOLLAR_SIGN_URL ),
                Constants.DOLLAR_SIGN_WIDTH,
                Constants.DOLLAR_SIGN_HEIGHT,
                Constants.DOLLAR_SIGN_X,
                Constants.DOLLAR_SIGN_Y
        );

        /*
        uiConsole = new UIConsole(
                new ImageIcon( Constants.UI_CONSOLE_BG ),
                Constants.CONSOLE_WIDTH,
                Constants.CONSOLE_HEIGHT,
                Constants.CONSOLE_X,
                Constants.CONSOLE_Y,
                ClockFactory.createInstance(
                        Constants.clockType.UI_CONSOLE
                )
        );
        */
    }

    public static boolean modifyLives(int amount) {
        if (lives + amount <= Constants.MAX_LIVES_AMOUNT_LEVEL_1) {
            lives += amount;
            uiHeartBar.updateNrOfLifes( amount );
            return true;
        }
        return false;
    }

    public static boolean tryToBuyTower(Constants.towerType towerType) {
        int cost = 0;
        switch (towerType) {
            case IN_GAME_ARCANE_TOWER: {
                cost = Constants.ARCANE_TOWER_COST;
                break;
            }
            case IN_GAME_CRANE_TOWER: {
                cost = Constants.CRANE_TOWER_COST;
                break;
            }
            case IN_GAME_ZOMBIE_TOWER: {
                cost = Constants.ZOMBIE_TOWER_COST;
                break;
            }
            case IN_GAME_CANNON_TOWER: {
                cost = Constants.CANNON_TOWER_COST;
                break;
            }
        }


        if (deposit - cost >= 0) {
            deposit -= cost;
            uiDollarSign.modifyCashAmount( -cost );
            return true;
        }

        return false;
    }

    public static boolean tryToBuyUpgrade(float upgradeCost) {
        if (deposit - upgradeCost >= 0) {
            deposit -= upgradeCost;
            uiDollarSign.modifyCashAmount( -upgradeCost );
            return true;
        }
        return false;
    }


    public static void addMoney(int amount) {
        if (amount > 0) {
            deposit += amount;
            uiDollarSign.modifyCashAmount( amount );
        }
    }

    public static int getDeposit() {
        return deposit;
    }

    public static void setDeposit(int deposit) {
        Player.deposit = deposit;
    }

    public static int getInitialNumberOfLives() {
        return initialNumberOfLives;
    }

    public static void setInitialNumberOfLives(int initialNumberOfLives) {
        Player.initialNumberOfLives = initialNumberOfLives;
    }

    public static int getLives() {
        return lives;
    }

    public static void setLives(int lives) {
        Player.lives = lives;
    }

    public Player(int lives, int initialDeposit) {
        this.lives = lives;
        this.deposit = initialDeposit;
    }

    public static void setFortressX(int fortressX) {
        Player.fortressX = fortressX;
    }

    public static void setFortressY(int fortressY) {
        Player.fortressY = fortressY;
    }

    public static int getFortressX() {
        return fortressX;
    }

    public static int getFortressY() {
        return fortressY;
    }

    public static int getNrOfLives(){
        return Player.uiHeartBar.getCurrentNrOfLifes();
    }
}
