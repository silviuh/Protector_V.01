package ecs_container.towers;

import Constants.Constants;

public class TowerBehaviour {
    public Constants.attackType attackType;
    public int                  enemiesAffected;
    public int                  damage;

    public int modifierIncreaseDamage     = 1;    //how much range and attack increase by when upgraded
    public int modifierIncreaseInRange    = 1;    //how much range and attack increase by when upgraded
    public int modifierDecreaseInCooldown = 1;    //how much range and attack increase by when upgraded

    public double refundValue;
    public int    currentLevel = 1;
    public int    maxLevel     = 3;
    public double upgradeCost;

    public TowerBehaviour(Constants.attackType attackType, int damage,
                          int modifierIncreaseDamage,    //how much range and attack increase by when upgraded
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
    // to be continued...
}
