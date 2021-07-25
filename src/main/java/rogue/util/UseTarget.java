package rogue.util;

// mark who item should target, so no need to get the actual entity in the recipe creation for example
public enum UseTarget {
    PLAYER,
    PLAYER_SHIP,
    CURRENT_ENTITY
}
