package rogue.components;

import com.badlogic.ashley.core.Component;

public class RenderableComponent implements Component {
    public boolean visible;

    public RenderableComponent() {
        this(true);
    }

    public RenderableComponent(boolean visible) {
        super();
        this.visible = visible;
    }
}
