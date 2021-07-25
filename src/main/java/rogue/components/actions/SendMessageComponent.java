package rogue.components.actions;

import com.badlogic.ashley.core.Component;

import java.util.ArrayList;
import java.util.List;

public class SendMessageComponent implements Component {
    List<String> messages;

    public SendMessageComponent() {
        this.messages = new ArrayList<>();
    }

    public SendMessageComponent(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(final String message) {
        messages.add(message);
    }

    public void addMessages(final List<String> messages) {
        this.messages.addAll(messages);
    }

    public List<String> getMessages() {
        return messages;
    }
}
