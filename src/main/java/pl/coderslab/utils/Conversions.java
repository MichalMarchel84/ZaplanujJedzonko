package pl.coderslab.utils;

import com.google.common.base.CaseFormat;

public class Conversions {

    public static String camelToUnderscore(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
    }

    public static String fieldToSetter(String fieldName) {
        char firstChar = fieldName.charAt(0);
        return "set" + fieldName.replaceFirst(String.valueOf(firstChar), String.valueOf(Character.toUpperCase(firstChar)));
    }

    public static String fieldToGetter(String fieldName) {
        return "get" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, fieldName);
    }

    public static String columnToGetter(String colName) {
        return "get" + CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, colName);
    }

    public static String fieldToColumn(String fieldName){
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
    }
}
