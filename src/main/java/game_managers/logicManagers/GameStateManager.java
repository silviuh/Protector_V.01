package game_managers.logicManagers;

import Constants.Constants;

import java.util.concurrent.locks.ReentrantLock;

/**
 * change game States depending on the events in the game: PAUSED, PLAY, DESTROYED etc.
 */
public class GameStateManager {
    private                 Constants.StateID currentState = Constants.StateID.IDLE;
    private static volatile GameStateManager  gameStateManager;
    private static          ReentrantLock     singletonLock;

    private GameStateManager() {

    }

    public Constants.StateID getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Constants.StateID currentState) {
        this.currentState = currentState;
    }

    public static GameStateManager getInstance() {
        singletonLock = new ReentrantLock();
        if (gameStateManager == null) {
            try {
                singletonLock.lock();
                gameStateManager = new GameStateManager();
            } finally {
                singletonLock.unlock();
            }
        }
        return gameStateManager;
    }
}
