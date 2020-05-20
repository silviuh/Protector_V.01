package ecs_container.Actors.enemies;

import Constants.Constants;
import ecs_container.Actors.Player;
import graphic_context.SpriteSheet;
import graphic_context.Tile;
import utilities.Clock;

import java.util.ArrayList;

public class Devil extends Enemy {

    public Devil() {
        super();
    }

    public Devil(Tile startTile, int height, int width, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile,
                height,
                width,
                sprites,
                clock,
                livesTaken
        );
    }

    public Devil(Tile startTile, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile,
                sprites,
                clock,
                livesTaken
        );
    }

    public void setInactive() {
        super.setInactive();
        Player.addMoney( Constants.DEVIL_MONEY_INCREASE_ON_DEATH );
    }

}
