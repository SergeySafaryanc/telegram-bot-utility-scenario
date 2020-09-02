package org.telegram.utility.scenario.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.telegram.utility.scenario.entity.enums.ButtonType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

@Embeddable
@Root(name = "button")
public class Button {

    private String title;
    @Enumerated(EnumType.STRING)
    private ButtonType type;
    private String data;

    public Button() {
    }

    public Button(String title) {
        this.title = title;
    }

    public Button(String title, ButtonType type, String data) {
        this.title = title;
        this.type = type;
        this.data = data;
    }

    @Attribute(name = "title")
    public String getTitle() {
        return title;
    }

    @Attribute(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    @Attribute(name = "type", required = false)
    public ButtonType getType() {
        return type;
    }

    @Attribute(name = "type", required = false)
    public void setType(ButtonType type) {
        this.type = type;
    }

    @Attribute(name = "data", required = false)
    public String getData() {
        return data;
    }

    @Attribute(name = "data", required = false)
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Button button = (Button) o;
        return Objects.equals(title, button.title) &&
                type == button.type &&
                Objects.equals(data, button.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, type, data);
    }

    @Override
    public String toString() {
        return "Button{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
