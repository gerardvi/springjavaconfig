package domain;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import commons.Entity;

@javax.persistence.Entity
@Table (name = "words")
public class Word extends Entity {
    @Enumerated (EnumType.STRING)
    @Column (name = "word_type")
    private WordType type;

    @Column (name = "code")
    private String code;

    public Word () {
    }

    public String getCode () {
        return code;
    }

    public WordType getType () {
        return type;
    }

    public void setCode (String val) {
        code = val;
    }

    public void setType (WordType val) {
        type = val;
    }
}
