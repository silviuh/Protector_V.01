package ecs_container.towers;

import game_managers.logicManagers.EnemyManager;
import graphic_context.SpriteSheet;
import utilities.Clock;

import java.awt.*;

public class ArcaneTower extends Tower {
    public ArcaneTower(int xPos, int yPos, SpriteSheet sprite, Color innerCirclecColor, Color outerCircleColor,
                       Color textInfoColor, int range, String name, TowerBehaviour towerBehaviour,
                       Clock clock, EnemyManager enemyManager) {
        super(
                xPos,
                yPos,
                sprite,
                innerCirclecColor,
                outerCircleColor,
                textInfoColor,
                range,
                name,
                towerBehaviour,
                clock,
                enemyManager
        );
    }

    @Override
    public SpriteSheet getSprite() {
        return super.getSprite();
    }

    @Override
    public void render(Graphics graphicsContext) {
        super.render( graphicsContext );
    }

    @Override
    public void update() {
        super.update();
    }
}
