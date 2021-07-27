package rogue.components.traits;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import rogue.equipment.EquipmentSlot;
import rogue.modifier.Modifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

// TODO: do we want this to be List<EquipmentSlot>? and should perhaps it even fill multiple slots?
// Place on an item that can be equipped by an entity who has the EquipmentComponent
public class CanEquipComponent implements Component {
    public EquipmentSlot slot;
    public List<Supplier<Modifier<? extends Entity>>> modifiers;

    public CanEquipComponent(EquipmentSlot slot) {
        this.slot = slot;
        this.modifiers = new ArrayList<>();
    }

    public CanEquipComponent(EquipmentSlot slot, List<Supplier<Modifier<? extends Entity>>> modifiers) {
        this.slot = slot;
        this.modifiers = modifiers;
    }

    public void addModifier(final Supplier<Modifier<? extends Entity>> modifier) {
        modifiers.add(modifier);
    }
}
