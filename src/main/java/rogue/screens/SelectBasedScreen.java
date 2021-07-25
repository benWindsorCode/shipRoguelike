package rogue.screens;

import asciiPanel.AsciiPanel;
import com.badlogic.ashley.core.Entity;
import rogue.components.ExamineComponent;
import rogue.components.items.UseItemEffectComponent;
import rogue.components.traits.CanBeDeconstructedComponent;
import rogue.factories.MapperFactory;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class SelectBasedScreen implements Screen {
    protected abstract Entity[] getItems();
    protected abstract String getMessage();

    // responses to key input. If returns null, the input will be processed by respondToUserInput here
    protected abstract Screen extraInputResponses(KeyEvent key);

    protected int currentItem;
    protected AsciiPanel terminal;
    protected Entity player;

    public SelectBasedScreen(AsciiPanel terminal, Entity player) {
        this.terminal = terminal;
        this.player = player;
    }

    // Most common list is just the description of the items
    protected ArrayList<String> getList() {
        ArrayList<String> list = new ArrayList<>();

        for(Entity e: getItems()) {
            ExamineComponent examineComponent = MapperFactory.examineComponent.get(e);
            UseItemEffectComponent useItemEffectComponent = MapperFactory.useItemEffectComponent.get(e);
            CanBeDeconstructedComponent canBeDeconstructedComponent = MapperFactory.canBeDeconstructedComponent.get(e);

            if(examineComponent != null) {
                String itemText = examineComponent.name;
                List<String> actionsAvailable = new ArrayList<>();

                if(useItemEffectComponent != null)
                    actionsAvailable.add("usable");

                if(canBeDeconstructedComponent != null)
                    actionsAvailable.add("deconstructable");

                if(actionsAvailable.size() > 0) {
                    String actionsAvailableString = String.join(", ", actionsAvailable);
                    itemText = String.format("%s (%s)", itemText, actionsAvailableString);
                }

                list.add(itemText);
            } else {
                list.add("No Description available");
            }
        }

        return list;
    }

    @Override
    public void displayOutput() {

        ArrayList<String> lines = getList();

        int lastLine = terminal.getHeightInCharacters() - 1;
        // use '-1' at end to ensure last line doesn't get obscured by the below clear
        int y = lastLine - lines.size() - 1;
        int x = 4;

        if (lines.size() > 0)
            terminal.clear(' ', x, y, 20, lines.size());

        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if(i == currentItem) {
                line = String.format("--> %s", line);
            }

            terminal.write(line, x, y++);
        }

        terminal.clear(' ', 0, lastLine - 1, terminal.getWidthInCharacters(), 1);
        terminal.write(getMessage(), 2, lastLine );

        terminal.repaint();
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
        Screen response = extraInputResponses(key);

        if(response != null)
            return response;

        switch(key.getKeyCode()) {
            case KeyEvent.VK_UP:
                currentItem = Math.max(0, currentItem - 1);
                return this;
            case KeyEvent.VK_DOWN:
                currentItem = Math.min(getItems().length - 1, currentItem + 1);
                return this;
            case KeyEvent.VK_ESCAPE:
                return null;
        }

        return null;
    }
}
