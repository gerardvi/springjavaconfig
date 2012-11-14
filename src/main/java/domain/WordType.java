package domain;

public enum WordType {
    N ("Noun"), V ("Verb"), A ("Adjective"), P ("Preposition");

    private final String strval;

    private WordType (String strval) {
        this.strval = strval;
    }

    public String toString () {
        return strval;
    }
}
