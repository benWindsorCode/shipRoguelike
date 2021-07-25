package rogue.entities.buildings;

import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.PositionComponent;
import rogue.components.RenderableComponent;
import rogue.components.TileComponent;
import rogue.components.traits.CanBeCraftedComponent;
import rogue.components.traits.CannotEnterComponent;
import rogue.components.traits.IdComponent;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;
import rogue.util.EntityId;

public class StoneWall extends Entity {
    public StoneWall() {
        this(0, 0);
    }

    public StoneWall(int x, int y) {
        this.add(new CannotEnterComponent());
        this.add(new IdComponent(EntityId.STONE_WALL));
        this.add(new PositionComponent(x, y));
        this.add(new TileComponent(TileFactory.stoneWall.glyph, TileFactory.stoneWall.color));
        this.add(new CanBeCraftedComponent(RecipeFactory.stoneWallRecipe()));
        this.add(new RenderableComponent());
        this.add(new ExamineComponent(
                "Stone Wall",
                "Stone Walls",
                "A tall stone wall. Too tall to traverse."
        ));
    }
}
