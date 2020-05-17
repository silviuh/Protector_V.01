package ecs_container.Actors.enemies;

import graphic_context.SpriteSheet;
import graphic_context.Tile;
import utilities.Clock;

import java.util.ArrayList;

public class EnemySampleAlpha extends Enemy {
    public EnemySampleAlpha(Tile startTile, int height, int width, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile,
                height,
                width,
                sprites,
                clock,
                livesTaken
        );
    }

    public EnemySampleAlpha(Tile startTile, ArrayList< SpriteSheet > sprites, Clock clock, int livesTaken) {
        super(
                startTile,
                sprites,
                clock,
                livesTaken
        );
    }
}
