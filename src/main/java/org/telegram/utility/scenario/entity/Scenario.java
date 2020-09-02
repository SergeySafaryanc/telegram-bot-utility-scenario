package org.telegram.utility.scenario.entity;

import org.simpleframework.xml.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Root(name = "scenario")
public class Scenario {

    @Id
    private String key;
    private String description;
    @Embedded
    private Text text;
    @Embedded
    private Keyboard keyboard;

    public Scenario() {
    }

    public Scenario(String key) {
        this.key = key;
    }

    @Attribute(name = "key")
    public String getKey() {
        return key;
    }

    @Attribute(name = "key")
    public void setKey(String key) {
        this.key = key;
    }

    @Attribute(name = "description")
    public String getDescription() {
        return description;
    }

    @Attribute(name = "description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Element(name = "text", data = true)
    public Text getText() {
        return text;
    }

    @Element(name = "text", data = true)
    public void setText(Text text) {
        this.text = text;
    }

    @Element(name = "keyboard", required = false)
    public Keyboard getKeyboard() {
        return keyboard;
    }

    @Element(name = "keyboard", required = false)
    public void setKeyboard(Keyboard keyboard) {
        this.keyboard = keyboard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scenario scenario = (Scenario) o;
        return Objects.equals(key, scenario.key) &&
                Objects.equals(description, scenario.description) &&
                Objects.equals(text, scenario.text) &&
                Objects.equals(keyboard, scenario.keyboard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, description, text, keyboard);
    }

    @Override
    public String toString() {
        return "Scenario{" +
                "key='" + key + '\'' +
                ", description='" + description + '\'' +
                ", text=" + text +
                ", keyboard=" + keyboard +
                '}';
    }
}
