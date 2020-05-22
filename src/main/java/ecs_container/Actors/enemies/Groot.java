package ecs_container.Actors.enemies;

import Constants.Constants;
import ecs_container.Actors.Player;
import graphic_context.SpriteSheet;
import graphic_context.Tile;
import utilities.Clock;

import java.util.ArrayList;

public class Groot extends Enemy{
    public Groot(){
        super();
    }
    public Groot(Tile startTile, int height, int width, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile,
                height,
                width,
                sprites,
                clock,
                livesTaken
        );
    }

    public Groot(Tile startTile, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile,
                sprites,
                clock,
                livesTaken
        );
    }

    public void setInactive() {
        super.setInactive();
        Player.addMoney( Constants.GROOT_MONEY_INCREASE_ON_DEATH );
        Player.modifyScore( Constants.GROOT_SCORE_INCREASE );
    }

}
