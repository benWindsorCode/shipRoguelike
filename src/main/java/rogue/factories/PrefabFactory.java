package rogue.factories;

import rogue.entities.buildings.HorizontalRail;
import rogue.entities.buildings.RailCross;
import rogue.entities.buildings.StoneWall;
import rogue.entities.buildings.VerticalRail;
import rogue.entities.crafting.Chest;
import rogue.environment.prefabs.Prefab;

import java.util.function.Supplier;

public class PrefabFactory {
    public static Prefab smallHouse() {
        Prefab wallSquare = new Prefab(5, 5);

        Supplier<StoneWall> wallSupplier = StoneWall::new;

        // top left corner
        wallSquare.add(0, 0, wallSupplier);

        // vertical left wall
        wallSquare.add(0, 1, wallSupplier);
        wallSquare.add(0, 2, wallSupplier);
        wallSquare.add(0, 3, wallSupplier);

        // horizontal top wall
        wallSquare.add(1, 0, wallSupplier);
        wallSquare.add(2, 0, wallSupplier);
        wallSquare.add(3, 0, wallSupplier);
        wallSquare.add(4, 0, wallSupplier);

        // vertical right wall
        wallSquare.add(4, 1, wallSupplier);
        wallSquare.add(4, 2, wallSupplier);
        wallSquare.add(4, 3, wallSupplier);

        // entrance door
        wallSquare.add(1, 3, wallSupplier);
        wallSquare.add(3, 3, wallSupplier);

        return wallSquare;
    }

    public static Prefab bigHouse() {
        Prefab bigHouse = new Prefab(10, 10);

        Supplier<StoneWall> wallSupplier = StoneWall::new;

        // top left corner
        bigHouse.add(0, 0, wallSupplier);

        // vertical left wall
        bigHouse.add(0, 1, wallSupplier);
        bigHouse.add(0, 2, wallSupplier);
        bigHouse.add(0, 3, wallSupplier);

        // horizontal top wall
        bigHouse.add(1, 0, wallSupplier);
        bigHouse.add(2, 0, wallSupplier);
        bigHouse.add(3, 0, wallSupplier);
        bigHouse.add(4, 0, wallSupplier);
        bigHouse.add(5, 0, wallSupplier);
        bigHouse.add(6, 0, wallSupplier);
        bigHouse.add(7, 0, wallSupplier);
        bigHouse.add(8, 0, wallSupplier);
        bigHouse.add(9, 0, wallSupplier);

        // vertical middle wall with door gap
        bigHouse.add(4, 1, wallSupplier);
        // bigHouse.add(4, 2, wallSupplier); door gap
        bigHouse.add(4, 3, wallSupplier);

        // vertical far right wall
        bigHouse.add(9, 1, wallSupplier);
        bigHouse.add(9, 2, wallSupplier);
        bigHouse.add(9, 3, wallSupplier);

        // chest in corner
        bigHouse.add(8, 2, Chest::new);

        // entrance door
        bigHouse.add(1, 3, wallSupplier);
        // bigHouse.add(2, 3, wallSupplier); door gap
        bigHouse.add(3, 3, wallSupplier);
        bigHouse.add(4, 3, wallSupplier);
        bigHouse.add(5, 3, wallSupplier);
        bigHouse.add(6, 3, wallSupplier);
        bigHouse.add(7, 3, wallSupplier);
        bigHouse.add(8, 3, wallSupplier);
        bigHouse.add(9, 3, wallSupplier);


        return bigHouse;
    }

    public static Prefab abandonedRails() {
        Prefab abandonedRails = new Prefab(20, 10);

        // straight section
        abandonedRails.add(0, 4, HorizontalRail::new);
        abandonedRails.add(1, 4, HorizontalRail::new);
        abandonedRails.add(2, 4, HorizontalRail::new);
        abandonedRails.add(3, 4, HorizontalRail::new);
        abandonedRails.add(4, 4, HorizontalRail::new);

        abandonedRails.add(3, 4, RailCross::new);

        // cross over
        abandonedRails.add(3, 5, VerticalRail::new);
        abandonedRails.add(3, 3, VerticalRail::new);

        // second cross
        abandonedRails.add(3, 2, RailCross::new);
        abandonedRails.add(4,2, HorizontalRail::new);
        abandonedRails.add(5,2, HorizontalRail::new);
        abandonedRails.add(6,2, HorizontalRail::new);
        abandonedRails.add(7,2, HorizontalRail::new);
        abandonedRails.add(8,2, HorizontalRail::new);
        abandonedRails.add(9, 2, HorizontalRail::new);
        abandonedRails.add(10, 2, HorizontalRail::new);
        abandonedRails.add(11, 2, HorizontalRail::new);
        abandonedRails.add(12, 2, HorizontalRail::new);
        abandonedRails.add(13, 2, HorizontalRail::new);
        abandonedRails.add(14, 2, HorizontalRail::new);

        return abandonedRails;
    }
}
