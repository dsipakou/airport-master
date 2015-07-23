package by.airport.airport_master.utils;

import java.net.URL;
import java.util.List;

import by.airport.airport_master.entity.FullFlightInfo;

/**
 * Created by dzianis.sipakou on 5/20/2015.
 */
public interface ParseTimetable<T> {

    FullFlightInfo getDetailsList(URL url, Class<T> clazz);
}
