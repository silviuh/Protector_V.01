package game_managers.logicManagers;

import javax.swing.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * used to increase or decrease the game speed
 */
public class AccelerationManager {
    private                 long                acceleration;
    private                 long                gameSpeed;
    private static volatile AccelerationManager accelerationManager;
    private static          ReentrantLock       singletonLock;
    private                 Timer               timer;

    private AccelerationManager(long gameSpeed, Timer timer) {
        this.acceleration = 1;
        this.timer = timer;
        this.gameSpeed = gameSpeed;
    }

    public void doubleAcceleration() {
        this.acceleration >>= 1;
        this.timer.setDelay( ( int ) ( this.gameSpeed * this.acceleration ) );
    }

    public void dichotomizeAcceleration() {
        this.acceleration <<= 1;
        this.timer.setDelay( ( int ) ( this.gameSpeed * this.acceleration ) );
    }

    public Long getacceleration() {
        return acceleration;
    }

    public void setacceleration(Long acceleration) {
        this.acceleration = acceleration;
    }

    public static AccelerationManager getInstance(long gameSpeed, Timer timer) {
        singletonLock = new ReentrantLock();
        if (accelerationManager == null) {
            try {
                singletonLock.lock();
                accelerationManager = new AccelerationManager( gameSpeed, timer );
            } finally {
                singletonLock.unlock();
            }
        }
        return accelerationManager;
    }
}
