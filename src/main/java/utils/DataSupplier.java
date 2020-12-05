package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSupplier {

    static List<String> symbols;

    public static String getSideSize() {
        return "4;5;6";
    }

    public static String getNegativeSize() {
        return "-4;-5;-6";
    }

    public static String getSeparator() {
        return "!";
    }

    private  static List<String> getSeparators() {
        symbols = new ArrayList<>();
        for (int i = 33; i < 48; i++) {
            symbols.add(Character.toString((char) i));
        }
        for (int i = 58; i < 65; i++) {
            symbols.add(Character.toString((char) i));
        }
        for (int i = 93; i < 97; i++) {
            symbols.add(Character.toString((char) i));
        }
        for (int i = 123; i < 127; i++) {
            symbols.add(Character.toString((char) i));
        }
        return symbols;
    }

    public static Map<String, String> getSideSizeWithSeparator(String f, String s, String t) {
        getSeparators();
        Map<String, String> sideSizes = new HashMap<>();
        for (String separator : symbols) {
            sideSizes.put(separator, (f + separator + s + separator + t));
        }
        return sideSizes;
    }
}
