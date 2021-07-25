package rogue.ai.pathfinding;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.pfa.Connection;
import com.badlogic.gdx.ai.pfa.DefaultConnection;
import com.badlogic.gdx.ai.pfa.indexed.IndexedGraph;
import com.badlogic.gdx.utils.Array;
import rogue.components.PositionComponent;
import rogue.factories.MapperFactory;
import rogue.render.RenderGrid;

import java.util.List;

public class PathfindingGraph implements IndexedGraph<Entity> {
    private final RenderGrid renderGrid;

    public PathfindingGraph(RenderGrid renderGrid) {
        super();
        this.renderGrid = renderGrid;
    }

    @Override
    public int getIndex(Entity node) {
        PositionComponent positionComponent = MapperFactory.positionComponent.get(node);
        return positionComponent.x * renderGrid.getHeight() + positionComponent.y;
    }

    @Override
    public int getNodeCount() {
        // number nodes by their number in the flattened array of world tiles
        return renderGrid.getWidth() * renderGrid.getHeight();
    }

    @Override
    public Array<Connection<Entity>> getConnections(Entity fromNode) {
        PositionComponent positionComponent = MapperFactory.positionComponent.get(fromNode);
        List<Entity> surrounding = renderGrid.getCardinalSurroundingEntities(positionComponent.x, positionComponent.y);

        Array<Connection<Entity>> connections = new Array<>(4);
        for(Entity toNode: surrounding) {
            connections.add(new DefaultConnection<>(fromNode, toNode));
        }

        return connections;
    }

    public RenderGrid getRenderGrid() {
        return renderGrid;
    }
}
