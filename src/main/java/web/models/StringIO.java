package web.models;

import java.io.Serializable;

import commons.Strings;

public class StringIO implements Serializable {
    private String value;

    public StringIO (String initialValue) {
        this.value = initialValue;
    }

    public StringIO () {
        this (null);
    }

    public String get () {
        return value;
    }

    public String getValue () {
        return value != null ? value : "";
    }

    public void setValue (String val) {
        value = Strings.isBlank (val) ? null : val;
    }
}
