package rogue.components.traits;

import com.badlogic.ashley.core.Component;
import rogue.equipment.EquipmentSlot;

// TODO: do we want this to be List<EquipmentSlot>? and should perhaps it even fill multiple slots?
// Place on an item that can be equipped by an entity who has the EquipmentComponent
public class CanEquipComponent implements Component {
    public EquipmentSlot slot;

    public CanEquipComponent(EquipmentSlot slot) {
        this.slot = slot;
    }
}
