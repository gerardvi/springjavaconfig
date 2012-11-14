package dao;

import java.util.List;

final class Parameter {
    final String name;
    final Object value;

    private Parameter (String name, Object value) {
        this.value = value;
        this.name = name;
    }

    /** Returns the name of the parameter that was added. */
    static String add (List <Parameter> params, Object value) {
        String name = "__param__".concat (Integer.toString (params.size ()));
        params.add (new Parameter (name, value));
        return name;
    }
}
