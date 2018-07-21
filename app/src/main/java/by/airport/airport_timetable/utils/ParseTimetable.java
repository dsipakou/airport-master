package by.airport.airport_timetable.utils;

import java.net.URL;

import by.airport.airport_timetable.entity.FullFlightInfo;

/**
 * Created by dzianis.sipakou on 5/20/2015.
 */
public interface ParseTimetable<T> {

    FullFlightInfo getArrivalDetailsList(URL url, Class<T> clazz);
    FullFlightInfo getDepartureDetailsList(URL url, Class<T> clazz);
}
