package rogue.components;

import com.badlogic.ashley.core.Component;

// Component to house details of entity for examining and menus
public class ExamineComponent implements Component {
    public String name;
    public String plural;
    public String description;

    public ExamineComponent(String name, String plural, String description) {
        this.name = name;
        this.plural = plural;
        this.description = description;
    }
}
