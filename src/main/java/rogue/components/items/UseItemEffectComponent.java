package rogue.components.items;

import com.badlogic.ashley.core.Component;
import rogue.util.UseTarget;

import java.util.function.Supplier;

// Component to put onto the target entity of the item use, target located by the UseItemActionComponent
// This is a field on the item
public class UseItemEffectComponent implements Component {
    public Supplier<Component> componentSupplier;

    // to describe who to drop this onto
    public UseTarget useTarget;

    public UseItemEffectComponent(UseTarget useTarget, Supplier<Component> componentSupplier) {
        this.componentSupplier = componentSupplier;
        this.useTarget = useTarget;
    }
}
