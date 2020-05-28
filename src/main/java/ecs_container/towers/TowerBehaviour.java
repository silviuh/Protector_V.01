package ecs_container.towers;

import Constants.Constants;

/**
 * <p>Utility class, used for keeping track of the relevant information and data a tower posses.</p>
 * <p>It's used in the Tower constructor.</p>
 */
public class TowerBehaviour {
    public Constants.attackType attackType;
    public int                  enemiesAffected;
    public int                  damage;

    public int modifierIncreaseDamage     = 1;
    public int modifierIncreaseInRange    = 1;
    public int modifierDecreaseInCooldown = 1;

    public double refundValue;
    public int    currentLevel = 1;
    public int    maxLevel     = 3;
    public double upgradeCost;

    public TowerBehaviour(Constants.attackType attackType, int damage,
                          int modifierIncreaseDamage,
                          int modifierDecreaseInCooldown,
                          int modifierIncreaseInRange,
                          double givenUpgradeCost,
                          double refundValue,
                          int maxLevel) {

        this.attackType = attackType;
        this.damage = damage;

        this.modifierDecreaseInCooldown = modifierDecreaseInCooldown;
        this.modifierIncreaseDamage = modifierIncreaseDamage;
        this.modifierIncreaseInRange = modifierIncreaseInRange;

        this.upgradeCost = givenUpgradeCost;
        this.maxLevel = maxLevel;
        this.refundValue = refundValue;
    }

}
