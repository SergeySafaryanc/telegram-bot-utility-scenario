package org.telegram.utility.scenario.entity;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.telegram.utility.scenario.entity.enums.ParseMode;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
@Root(name = "text")
public class Text {

    @Enumerated(EnumType.STRING)
    private ParseMode parseMode;
    @Lob
    private String text;

    public Text() {
    }

    public Text(ParseMode parseMode, String text) {
        this.parseMode = parseMode;
        this.text = text;
    }

    @Attribute(name = "type", required = false)
    public ParseMode getParseMode() {
        return parseMode;
    }

    @Attribute(name = "type", required = false)
    public void setParseMode(ParseMode parseMode) {
        this.parseMode = parseMode;
    }

    @org.simpleframework.xml.Text
    public String getText() {
        return text;
    }

    @org.simpleframework.xml.Text
    public void setText(String text) {
        this.text = text;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text1 = (Text) o;
        return parseMode == text1.parseMode &&
                Objects.equals(text, text1.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parseMode, text);
    }

    @Override
    public String toString() {
        return "Text{" +
                "parseMode=" + parseMode +
                ", text='" + text + '\'' +
                '}';
    }
}
