package factories;

import Constants.Constants;
import ecs_container.towers.TowerBehaviour;
import utilities.Clock;

public class AttackBehaviours {
    private AttackBehaviours() {


    }

    public static TowerBehaviour createInstance(Constants.attackBehaviour attackBehaviour) {
        TowerBehaviour towerBehaviour = null;

        switch (attackBehaviour) {
            case ARCANE: {
                towerBehaviour = new TowerBehaviour(
                        Constants.attackType.FREEZE,
                        Constants.ARCANE_TOWER_BASE_DAMAGE,
                        Constants.ARCANE_TOWER_DAMAGE_INCREASE_PER_LEVEL,
                        Constants.ARCANE_TOWER_DECREASE_IN_COOLDOWN_PER_LEVEL,
                        Constants.ARCANE_TOWER_INCREASE_IN_RANGE_PER_LEVEL,
                        Constants.ARCANE_TOWER_UPGRADE_COST,
                        Constants.ARCANE_REFUND_VALUE,
                        Constants.ARCANE_MAX_LEVEL
                );
                break;
            }
            case CRANE: {
                towerBehaviour = new TowerBehaviour(
                        Constants.attackType.UNDEFINED,
                        Constants.CRANE_TOWER_BASE_DAMAGE,
                        Constants.CRANE_TOWER_DAMAGE_INCREASE_PER_LEVEL,
                        Constants.CRANE_TOWER_DECREASE_IN_COOLDOWN_PER_LEVEL,
                        Constants.CRANE_TOWER_INCREASE_IN_RANGE_PER_LEVEL,
                        Constants.CRANE_TOWER_UPGRADE_COST,
                        Constants.CRANE_REFUND_VALUE,
                        Constants.CRANE_MAX_LEVEL
                );
                break;
            }
            case CANNON: {
                towerBehaviour = new TowerBehaviour(
                        Constants.attackType.UNDEFINED,
                        Constants.CANNON_TOWER_BASE_DAMAGE,
                        Constants.CANNON_TOWER_DAMAGE_INCREASE_PER_LEVEL,
                        Constants.CANNON_TOWER_DECREASE_IN_COOLDOWN_PER_LEVEL,
                        Constants.CANNON_TOWER_INCREASE_IN_RANGE_PER_LEVEL,
                        Constants.CANNON_TOWER_UPGRADE_COST,
                        Constants.CANNON_REFUND_VALUE,
                        Constants.CANNON_MAX_LEVEL
                );
                break;
            }
            case ZOMBIE: {
                towerBehaviour = new TowerBehaviour(
                        Constants.attackType.UNDEFINED,
                        Constants.ZOMBIE_TOWER_BASE_DAMAGE,
                        Constants.ZOMBIE_TOWER_DAMAGE_INCREASE_PER_LEVEL,
                        Constants.ZOMBIE_TOWER_DECREASE_IN_COOLDOWN_PER_LEVEL,
                        Constants.ZOMBIE_TOWER_INCREASE_IN_RANGE_PER_LEVEL,
                        Constants.ZOMBIE_TOWER_UPGRADE_COST,
                        Constants.ZOMBIE_REFUND_VALUE,
                        Constants.ZOMBIE_MAX_LEVEL
                );
                break;
            }
        }
        return towerBehaviour;
    }
}
