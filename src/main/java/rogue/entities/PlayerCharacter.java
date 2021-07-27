package rogue.entities;

import com.badlogic.ashley.core.Entity;
import rogue.ai.PlayerCharacterAi;
import rogue.components.*;
import rogue.components.hunger.HungerComponent;
import rogue.components.player.PlayerShipComponent;
import rogue.components.render.RenderableComponent;
import rogue.components.render.TileComponent;
import rogue.components.traits.CanBeAttackedComponent;
import rogue.components.traits.CanHarvestComponent;
import rogue.components.traits.IdComponent;
import rogue.equipment.EquipmentSlot;
import rogue.factories.RecipeFactory;
import rogue.factories.TileFactory;
import rogue.stats.Stat;
import rogue.stats.StatType;
import rogue.util.EntityId;

import java.util.Arrays;
import java.util.HashSet;

public class PlayerCharacter extends Entity {
    public PlayerCharacter(int x, int y, PlayerShip playerShip, boolean inControlOfPlayer, boolean isVisible) {
        super();

        StatsComponent playerStats = new StatsComponent();
        playerStats.addStat(new Stat(StatType.HEALTH, 10, 10));
        playerStats.addStat(new Stat(StatType.STRENGTH, 5, 5));

        this.add(playerStats);
        this.add(new RenderableComponent(isVisible));
        this.add(new IdComponent(EntityId.PLAYER_CHARACTER));
        this.add(new TileComponent(TileFactory.character.glyph, TileFactory.character.color));
        this.add(new PositionComponent(x, y));
        this.add(new AiComponent<>(new PlayerCharacterAi(this)));
        this.add(new CanBeAttackedComponent());
        this.add(new PlayerShipComponent(playerShip));
        this.add(new CanHarvestComponent());
        this.add(new InventoryComponent(12));
        this.add(new HungerComponent(55));
        this.add(new RecipeBookComponent(RecipeFactory.playerBook()));
        this.add(new EquipmentComponent(new HashSet<>(Arrays.asList(EquipmentSlot.ARMS, EquipmentSlot.BODY, EquipmentSlot.FEET))));
        this.add(new ExamineComponent(
                "Player",
                "Players",
                "The player character. Just like looking in the mirror."
        ));

        if(inControlOfPlayer)
            this.add(new PlayerControlledComponent());
    }
}
