package rogue.ai.pathfinding;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.pfa.Heuristic;
import rogue.components.PositionComponent;
import rogue.components.TileComponent;
import rogue.factories.MapperFactory;
import rogue.util.TileUtil;

public class ManhattanDistanceAvoidLand implements Heuristic<Entity> {

    @Override
    public float estimate(Entity node, Entity endNode) {
        PositionComponent nodePos = MapperFactory.positionComponent.get(node);
        PositionComponent endPos = MapperFactory.positionComponent.get(endNode);
        TileComponent nodeTile = MapperFactory.tileComponent.get(node);
        TileComponent endTile = MapperFactory.tileComponent.get(endNode);

        if(TileUtil.isLand(nodeTile) || TileUtil.isLand(endTile)) {
            return Float.MAX_VALUE;
        }

        return Math.abs(endPos.x - nodePos.x) + Math.abs(endPos.y - nodePos.y);
    }
}
