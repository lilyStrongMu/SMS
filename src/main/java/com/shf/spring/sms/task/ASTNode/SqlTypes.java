package com.shf.spring.sms.task.ASTNode;

public enum SqlTypes {
    SELECT("select"),
    DELETE("delete"),
    UPDATE("update"),
    REPLACE("replace"),
    INSERT("insert"),
    ERROR("error"),
    OTHER("other"),
    SUBSELECT("SubSelect"),
    ;

    private final String value;

    public String getValue() {
        return value;
    }

    SqlTypes(String value){
        this.value = value;
    }
}
