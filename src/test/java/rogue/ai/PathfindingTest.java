package rogue.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import org.junit.jupiter.api.Test;
import rogue.ai.pathfinding.ManhattanDistanceAvoidLand;
import rogue.ai.pathfinding.PathfindingGraph;
import rogue.components.PositionComponent;
import rogue.entities.WorldTile;
import rogue.factories.MapperFactory;
import rogue.factories.TileFactory;
import rogue.render.RenderGrid;

public class PathfindingTest {
    @Test
    public void basicPathfindingTest() {
        int width = 4;
        int height = 5;
        RenderGrid renderGrid = new RenderGrid(width, height);

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                WorldTile worldTile = new WorldTile(TileFactory.sea.glyph, TileFactory.sea.color, x, y);
                renderGrid.set(x, y, worldTile);
            }
        }

        renderGrid.set(0, 1, new WorldTile(TileFactory.grass.glyph, TileFactory.grass.color, 0, 1));

        PathfindingGraph pathfindingGraph = new PathfindingGraph(renderGrid);
        IndexedAStarPathFinder<Entity> levelPathFinder = new IndexedAStarPathFinder<>(
                pathfindingGraph,
                true
        );

        ManhattanDistanceAvoidLand heuristic = new ManhattanDistanceAvoidLand();
        DefaultGraphPath<Entity> path = new DefaultGraphPath<>();
        levelPathFinder.searchNodePath(renderGrid.get(0, 0), renderGrid.get(2, 3), heuristic, path);

        for(Entity e: path.nodes) {
            PositionComponent pos = MapperFactory.positionComponent.get(e);
            System.out.println(String.format("(%d, %d)", pos.x, pos.y));
        }
        System.out.println(path.nodes);
    }
}
