package by.airport.airport_master.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dzianis.sipakou on 6/2/2015.
 */
public class StringUtils {

    public static String replaceSpecialChars(String input) {
        return input.replaceAll("\\W+", "");
    }
}
