package rogue.components.items;

import com.badlogic.ashley.core.Component;
import rogue.util.UseTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

// Component to put onto the target entity of the item use, target located by the UseItemActionComponent
// This is a field on the item
public class UseItemEffectComponent implements Component {
    public List<Supplier<Component>> componentSuppliers;

    // to describe who to drop this onto
    public UseTarget useTarget;

    public UseItemEffectComponent(UseTarget useTarget) {
        this.useTarget = useTarget;
        this.componentSuppliers = new ArrayList<>();
    }

    public UseItemEffectComponent(UseTarget useTarget, Supplier<Component> componentSupplier) {
        this.componentSuppliers = new ArrayList<>();
        this.componentSuppliers.add(componentSupplier);
        this.useTarget = useTarget;
    }

    public UseItemEffectComponent(List<Supplier<Component>> componentSuppliers, UseTarget useTarget) {
        this.componentSuppliers = componentSuppliers;
        this.useTarget = useTarget;
    }

    public void addEffect(final Supplier<Component> componentSupplier) {
        this.componentSuppliers.add(componentSupplier);
    }
}
