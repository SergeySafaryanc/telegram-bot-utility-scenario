package org.telegram.utility.scenario.entity;

import org.hibernate.annotations.Cascade;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "keyboard")
@Root(name = "row")
public class Row {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name="buttons",
            joinColumns = @JoinColumn(name="keyboard_id")
    )
    private List<Button> buttons;

    public Row() {
    }

    public Row(List<Button> buttons) {
        this.buttons = buttons;
    }

    @Transient
    public Integer getId() {
        return id;
    }

    @Transient
    public void setId(Integer id) {
        this.id = id;
    }

    @ElementList(inline = true)
    public List<Button> getButtons() {
        return buttons;
    }

    @ElementList(inline = true)
    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return Objects.equals(id, row.id) &&
                Objects.equals(buttons, row.buttons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buttons);
    }

    @Override
    public String toString() {
        return "Row{" +
                "id=" + id +
                ", buttons=" + buttons +
                '}';
    }
}
