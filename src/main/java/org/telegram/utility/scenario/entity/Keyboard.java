package org.telegram.utility.scenario.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.telegram.utility.scenario.entity.enums.KeyboardType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
@Root(name = "keyboard")
public class Keyboard {

    @Enumerated(EnumType.STRING)
    private KeyboardType type;
    @ElementCollection(fetch = FetchType.EAGER, targetClass = Row.class)
    @CollectionTable(
            name="scenario_keyboard",
            joinColumns = @JoinColumn(name="scenario_key")
    )
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Row> rows = new ArrayList<>();

    public Keyboard() {
    }

    public Keyboard(KeyboardType type) {
        this.type = type;
    }

    @Attribute(name = "type")
    public KeyboardType getType() {
        return type;
    }

    @Attribute(name = "type")
    public void setType(KeyboardType type) {
        this.type = type;
    }

    @ElementList(inline = true)
    public List<Row> getRows() {
        return rows;
    }

    @ElementList(inline = true)
    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Keyboard keyboard = (Keyboard) o;
        return type == keyboard.type &&
                Objects.equals(rows, keyboard.rows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, rows);
    }

    @Override
    public String toString() {
        return "Keyboard{" +
                "type=" + type +
                ", rows=" + rows +
                '}';
    }
}
