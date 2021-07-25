package rogue.util;

import com.badlogic.ashley.core.Entity;
import rogue.components.traits.IdComponent;
import rogue.factories.MapperFactory;

public class EntityUtil {
    public static boolean sameEntities(Entity first, Entity second) {
        IdComponent firstId = MapperFactory.idComponent.get(first);
        IdComponent secondId = MapperFactory.idComponent.get(second);

        return firstId.entityId == secondId.entityId;
    }

    public static boolean matchesId(Entity entity, EntityId entityId) {
        IdComponent idComponent = MapperFactory.idComponent.get(entity);

        return idComponent.entityId == entityId;
    }

    public static boolean isSea(Entity entity) {
        IdComponent idComponent = MapperFactory.idComponent.get(entity);

        return idComponent.entityId == EntityId.WORLD_SEA
                || idComponent.entityId == EntityId.WORLD_DEEP_SEA
                || idComponent.entityId == EntityId.WORLD_SHALLOW_SEA;
    }

    public static boolean isMountain(Entity entity) {
        IdComponent idComponent = MapperFactory.idComponent.get(entity);

        return idComponent.entityId == EntityId.WORLD_MOUNTAIN
                || idComponent.entityId == EntityId.WORLD_HIGH_MOUNTAIN;
    }

    public static boolean isLand(Entity entity) {
        return matchesId(entity, EntityId.WORLD_GRASS);
    }

    public static boolean isPlayerCharacter(Entity entity) {
        return matchesId(entity, EntityId.PLAYER_CHARACTER);
    }

    public static boolean isShip(Entity entity) {
        return matchesId(entity, EntityId.PLAYER_SHIP)
                || matchesId(entity, EntityId.ENEMY_SHIP)
                || matchesId(entity, EntityId.ENEMY_SHIP_STRONG)
                || matchesId(entity, EntityId.CIVILIAN_SHIP);
    }

    public static boolean isPortal(final Entity entity) {
        return matchesId(entity, EntityId.PORTAL);
    }
}
