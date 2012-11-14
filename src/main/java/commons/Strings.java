package commons;

public final class Strings {
    private Strings () {}

    public static boolean isBlank (String val) {
        if (val == null || val.length () == 0) {
            return true;
        }
        int i = 0;
        while (i < val.length ()  &&  Character.isWhitespace (val.charAt (i))) {
            ++i;
        }
        return i == val.length ();
    }

    public static String toString (Object obj) {
        String result;
        if (obj == null) {
            result = "";
        } else {
            result = obj.toString ();
        }
        return result;
    }
}
