package by.airport.airport_main.utils;

import java.net.URL;

import by.airport.airport_main.entity.FullFlightInfo;

/**
 * Created by dzianis.sipakou on 5/20/2015.
 */
public interface ParseTimetable<T> {

    FullFlightInfo getDetailsList(URL url, Class<T> clazz);
}
