package rogue.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.PathFinderQueue;
import com.badlogic.gdx.ai.pfa.PathFinderRequest;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.utils.Array;
import rogue.ai.pathfinding.ManhattanDistanceAvoidLand;
import rogue.ai.pathfinding.PathfindingGraph;
import rogue.components.PositionComponent;
import rogue.components.TileComponent;
import rogue.components.actions.AttackActionComponent;
import rogue.components.actions.MovingComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.entities.enemies.EnemyShip;
import rogue.factories.MapperFactory;
import rogue.render.RenderGrid;
import rogue.util.TileUtil;

import java.nio.file.Path;
import java.util.Map;

public class AStarPlayerAttackAi extends BaseAi<EnemyShip>  {
    private PathfindingGraph pathfindingGraph;
    private int turnsSincePathfindingGraphUpdated;
    private ManhattanDistanceAvoidLand heuristic;

    // TODO: if current path long and we are early in it then keep going with that rather than recalc
    private DefaultGraphPath<Entity> currentPath;

    public AStarPlayerAttackAi(EnemyShip entity) {
        super(entity);
        turnsSincePathfindingGraphUpdated = 0;
        heuristic = new ManhattanDistanceAvoidLand();
    }

    // TODO: this is just copied from WanderingSeaAi, shouldn't really copy paste
    @Override
    public void onEnter(int x, int y, Entity renderEntity, Entity worldEntity) {
        TileComponent tile = MapperFactory.tileComponent.get(renderEntity);
        // TODO: Need the not equals self check to ensure ships dont attack their own entity, bit awkward their render entity is themselves? Do they have moves of zero?
        // Enemies should be able to attack us and others
        CanBeAttackedComponent canBeAttackedComponent = MapperFactory.canAttackComponent.get(renderEntity);
        if(canBeAttackedComponent != null && !renderEntity.equals(entity)) {
            entity.add(new AttackActionComponent(renderEntity));
            return;
        }

        if(TileUtil.isSea(tile)) {
            PositionComponent pos = MapperFactory.positionComponent.get(entity);
            pos.x = x;
            pos.y = y;
            return;
        }
    }

    // TODO: no need to recalculate path every turn?
    @Override
    public MovingComponent nextMove(RenderGrid renderGrid, Entity renderEntity, Entity targetEntity) {
        if(turnsSincePathfindingGraphUpdated == 0) {
            pathfindingGraph = new PathfindingGraph(renderGrid);
            turnsSincePathfindingGraphUpdated = 10;
        } else {
            turnsSincePathfindingGraphUpdated--;
        }

        PositionComponent pos = MapperFactory.positionComponent.get(entity);
        PositionComponent targetPos = MapperFactory.positionComponent.get(targetEntity);

        IndexedAStarPathFinder<Entity> levelPathFinder = new IndexedAStarPathFinder<>(
                pathfindingGraph,
                true
        );
        // try this https://www.youtube.com/watch?v=LOJJJ91Wr_0&ab_channel=Let%27sMakeAnIndieGame
        DefaultGraphPath<Entity> path = new DefaultGraphPath<>();

        levelPathFinder.searchNodePath(renderGrid.get(pos.x, pos.y), renderGrid.get(targetPos.x, targetPos.y), heuristic, path);

        System.out.println(path.nodes);

        Array<Entity> pathNodes = path.nodes;


        PathFinderQueue<Entity> pathFinderQueue = new PathFinderQueue<>(levelPathFinder);
        pathFinderQueue.run(1000);

        int mx = 0;
        int my = 0;
        if(pathNodes.size <= 1)
            return new MovingComponent(mx, my);

        Entity nextNode = pathNodes.get(1);
        PositionComponent nextPos = MapperFactory.positionComponent.get(nextNode);
        int xDiff = nextPos.x - pos.x;
        int yDiff = nextPos.y - pos.y;

        mx = Integer.compare(xDiff, 0);
        my = Integer.compare(yDiff, 0);

        MovingComponent nextMove = new MovingComponent(mx, my);
        return nextMove;
    }
}

// Note: this works but is slow
//    DefaultGraphPath<Entity> path = new DefaultGraphPath<>();
//
//        levelPathFinder.searchNodePath(renderGrid.get(pos.x, pos.y), renderGrid.get(targetPos.x, targetPos.y), heuristic, path);
//
//                System.out.println(path.nodes);
//
//                Array<Entity> pathNodes = path.nodes;
