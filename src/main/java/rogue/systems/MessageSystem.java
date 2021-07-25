package rogue.systems;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.utils.ImmutableArray;
import rogue.components.actions.SendMessageComponent;
import rogue.factories.FamilyFactory;
import rogue.factories.MapperFactory;

import java.util.ArrayList;
import java.util.List;

// TODO: this doesn't work :( I suspect to do with how/when repaint() is called in RoguelikeMain, perhaps wait until LibGDX
public class MessageSystem extends EntitySystem {
    private int screenHeight;
    private AsciiPanel terminal;
    private ImmutableArray<Entity> messagesWaiting;

    public MessageSystem(AsciiPanel terminal) {
        super();
        this.terminal = terminal;
    }


    public void addedToEngine(Engine engine) {
        messagesWaiting = engine.getEntitiesFor(FamilyFactory.messagesWaiting);
    }

    public void update(float deltaTime) {
        List<String> allMessages = new ArrayList<>();

        for(Entity e: messagesWaiting) {
            System.out.println("Processing message");
            SendMessageComponent sendMessageComponent = MapperFactory.sendMessageComponent.get(e);
            allMessages.addAll(sendMessageComponent.getMessages());
            e.remove(SendMessageComponent.class);
        }

        int top = terminal.getHeightInCharacters() - allMessages.size();
        int lastLine = terminal.getHeightInCharacters() - 1;
        for(int i = 0; i < allMessages.size(); i++) {
            System.out.println(String.format("Writing message to %d: %s", top + i, allMessages.get(i)));
            //terminal.writeCenter(allMessages.get(i), top + i);
            terminal.write(allMessages.get(i), 2, lastLine);
        }
    }
}