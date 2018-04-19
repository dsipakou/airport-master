package by.airport.airport_timetable.utils;

/**
 * Created by dzianis.sipakou on 6/2/2015.
 */
public class StringUtils {

    public static String replaceSpecialChars(String input) {
        if (input != null) {
            return input.replaceAll("\\W+", "");
        }
        return "";
    }
}
