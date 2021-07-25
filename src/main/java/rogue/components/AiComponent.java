package rogue.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import rogue.ai.BaseAi;

public class AiComponent<T extends BaseAi<?>>  implements Component {
    public T ai;

    public AiComponent(T ai) {
        this.ai = ai;
    }
}
