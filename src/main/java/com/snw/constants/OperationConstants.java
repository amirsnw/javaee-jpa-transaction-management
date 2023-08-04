package com.snw.constants;

import java.util.Arrays;
import java.util.List;

public class OperationConstants {

    public static final String GET = "get";
    public static final String CREATE = "create";
    public static final String EDIT = "edit";
    public static final String REMOVE = "remove";
    public static final String ALL = "all";
    public static final String NONE = "none";
    public static final String EXECUTE = "execute";

    public List<String> operations() {
        return Arrays.asList(GET, CREATE, EDIT, REMOVE, ALL, NONE, EXECUTE);
    }
}
