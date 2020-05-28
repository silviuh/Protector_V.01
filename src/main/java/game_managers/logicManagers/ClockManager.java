package game_managers.logicManagers;

import utilities.Clock;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Used to manage clocks
 */
public class ClockManager {
    private static ClockManager       clockManager;
    private static ReentrantLock      singletonLock;
    private        ArrayList< Clock > clocks;

    private ClockManager() {
        clocks = new ArrayList< Clock >();
    }

    public static ClockManager getInstance() {
        singletonLock = new ReentrantLock();
        if (clockManager == null) {
            try {
                singletonLock.lock();
                clockManager = new ClockManager();
            } finally {
                singletonLock.unlock();
            }
        }
        return clockManager;
    }

    public void addClock(Clock clock) {
        clocks.add( clock );
    }

    public void update() {
        for (Clock clock : clocks) {
            clock.update();
        }
    }
}


