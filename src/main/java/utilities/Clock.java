package utilities;

import Constants.Constants;

public class Clock {
    public long entityUpdateRate;
    public long lastTimeFrame = 0;
    public long currentTime   = 0;
    public long multipler     = 1;
    public long delay         = 0;
    public long delta         = 0;

    public Clock(long multipler, long update_rate) {
        this.entityUpdateRate = update_rate;
    }

    public long getDelta() {
        currentTime = System.currentTimeMillis();
        delta = currentTime - lastTimeFrame;
        lastTimeFrame = System.currentTimeMillis();

        return delta * multipler;
    }

    public void setMultipler(long multipler) {
        this.multipler = multipler;
    }

    public void update() {
        long delta = getDelta();
        delay += delta;
    }

    public long getEntityUpdateRate() {
        return entityUpdateRate;
    }

    public void setEntityUpdateRate(long entityUpdateRate) {
        this.entityUpdateRate = entityUpdateRate;
    }

    public boolean mayUpate() {
        if (delay >= entityUpdateRate) {
            delay = 0;
            return true;
        }
        return false;
    }

    public boolean modifyEntityUpdateRate(int modifierAmount) {
        if (entityUpdateRate + modifierAmount >= 0) {
            entityUpdateRate += modifierAmount;
            return true;
        }
        return false;
    }
}
