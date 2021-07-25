package rogue.components;

import com.badlogic.ashley.core.Component;

// Provides an entity a faction name
public class FactionComponent implements Component {
    public String faction;

    public FactionComponent(String faction) {
        this.faction = faction;
    }
}
