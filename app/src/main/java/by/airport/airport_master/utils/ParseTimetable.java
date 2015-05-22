package by.airport.airport_master.utils;

import java.net.URL;
import java.util.List;

/**
 * Created by dzianis.sipakou on 5/20/2015.
 */
public interface ParseTimetable<T> {

    List<T> getDetailsList(URL url, Class<T> clazz);
}
