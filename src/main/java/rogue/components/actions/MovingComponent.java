package rogue.components.actions;

import com.badlogic.ashley.core.Component;

// Represents a move event to be executed by MovementSystem
public class MovingComponent implements Component {
    public int mx;
    public int my;

    public MovingComponent(int mx, int my) {
        this.mx = mx;
        this.my = my;
    }
}
