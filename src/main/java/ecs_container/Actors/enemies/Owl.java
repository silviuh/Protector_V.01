package ecs_container.Actors.enemies;

import Constants.Constants;
import ecs_container.Actors.Player;
import graphic_context.SpriteSheet;
import graphic_context.Tile;
import utilities.Clock;

import java.util.ArrayList;

public class Owl extends Enemy {
    public Owl(){
        super();
    }

    public Owl(Tile startTile, int height, int width, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile,
                height,
                width,
                sprites,
                clock,
                livesTaken
        );
    }

    public Owl(Tile startTile, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile,
                sprites,
                clock,
                livesTaken
        );
    }

    public void setInactive() {
        super.setInactive();
        Player.addMoney( Constants.OWL_MONEY_INCREASE_ON_DEATH );
        Player.modifyScore( Constants.OWL_SCORE_INCREASE );
    }

}
