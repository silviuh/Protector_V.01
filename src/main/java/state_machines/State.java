package state_machines;

import Constants.Constants;

public interface State {
    void next(Constants.Image Package);

    void prev(Constants.Image Package);

    void printStatus();
}
