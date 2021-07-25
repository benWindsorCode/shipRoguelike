package rogue.environment;

import com.badlogic.ashley.core.Entity;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EntityListGridTest {

    @Test
    public void gridTest() {
        EntityListGrid grid = new EntityListGrid(4, 5);
        System.out.println(grid);

        // So now we have make 4 x 5 grid with two entities:
        // .  .  .  .
        // .  .  .  .
        // .  .  .  e
        // .  .  .  e
        // .  .  .  .

        grid.addEntity(3, 3, new Entity());
        grid.addEntity(3, 2, new Entity());

        // First ask for entities surrounding corner:
        // x  .  .  .
        // .  .  .  .
        // .  .  .  e
        // .  .  .  e
        // .  .  .  .

        List<Entity> surroundingCorner = grid.surroundingEntities(0, 0);
        System.out.println(surroundingCorner);

        // Now ask for entities surrounding more central square:
        // .  .  .  .
        // .  .  .  .
        // .  .  x  e
        // .  .  .  e
        // .  .  .  .
        List<Entity> surroundingClose = grid.surroundingEntities(2, 2);
        System.out.println(surroundingClose);

        // Now ask for entities on one of the squares already populated:
        // .  .  .  .
        // .  .  .  .
        // .  .  .  x
        // .  .  .  e
        // .  .  .  .
        List<Entity> surroundingEntity = grid.surroundingEntities(3, 3);
        System.out.println(surroundingEntity);
    }
}
