package rogue.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import rogue.equipment.EquipmentSlot;

import java.util.*;

public class EquipmentComponent implements Component {
    Map<EquipmentSlot, Entity> equipped;
    Set<EquipmentSlot> slots;
    Set<EquipmentSlot> slotsAvailable;
    Set<EquipmentSlot> slotsTaken;

    public EquipmentComponent(Set<EquipmentSlot> slots) {
        this.slots = slots;
        this.equipped = new HashMap<>();
        this.slotsAvailable = slots;
        this.slotsTaken = new HashSet<>();
    }

    public boolean slotFull(EquipmentSlot slot) {
        return equipped.containsKey(slot);
    }

    public Optional<Entity> equipItem(Entity entity, EquipmentSlot slot) {
        Optional<Entity> existingEquipment = Optional.empty();
        if(slotFull(slot))
            existingEquipment = Optional.of(equipped.get(slot));

        equipped.put(slot, entity);
        slotsTaken.add(slot);
        slotsAvailable.remove(slot);
        return existingEquipment;
    }
}
