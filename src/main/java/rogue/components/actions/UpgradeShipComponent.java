package rogue.components.actions;

import com.badlogic.ashley.core.Component;

// TODO: make more generic upgrade system, for now just upgrades player ship
// TODO: dont hardcode this resources on this event
// To be dropped on player when upgrading ship
public class UpgradeShipComponent implements Component {
    public int woodUsed;
    public int ironUsed;

    public UpgradeShipComponent(int woodUsed, int ironUsed) {
        this.woodUsed = woodUsed;
        this.ironUsed = ironUsed;
    }
}
