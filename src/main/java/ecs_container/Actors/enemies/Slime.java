package ecs_container.Actors.enemies;

import Constants.Constants;
import ecs_container.Actors.Player;
import graphic_context.SpriteSheet;
import graphic_context.Tile;
import utilities.Clock;

import java.util.ArrayList;

/**
 * Slime class, inherits Enemy
 */
public class Slime extends Enemy {

    public Slime(){
        super();
    }

    public Slime(Tile startTile, int height, int width, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile,
                height,
                width,
                sprites,
                clock,
                livesTaken
        );
    }

    public Slime(Tile startTile, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile,
                sprites,
                clock,
                livesTaken
        );
    }

    public void setInactive() {
        super.setInactive();
        Player.addMoney( Constants.SLIME_MONEY_INCREASE_ON_DEATH );
        Player.modifyScore( Constants.SLIME_SCORE_INCREASE );
    }

}
