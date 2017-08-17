package by.airport.airport_main.utils;

/**
 * Created by dzianis.sipakou on 6/2/2015.
 */
public class StringUtils {

    public static String replaceSpecialChars(String input) {
        return input.replaceAll("\\W+", "");
    }
}
