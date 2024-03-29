package by.airport.airport_timetable.utils;

import by.airport.airport_timetable.entity.ArrivalInfo;
import by.airport.airport_timetable.entity.DepartureInfo;

/**
 * Created by dzianis.sipakou on 5/25/2015.
 */
public class Globals {
    public static ArrivalInfo arrivalInfo = new ArrivalInfo();
    public static DepartureInfo departureInfo = new DepartureInfo();
    public static final String SHORT_ARRIVAL_URL = "http:s//airport.by/en";
    public static final String SHORT_DEPARTURE_URL = "https://airport.by/en/Departure";
    public static final String ARRIVAL_URL = "https://airport.by/en/timetable/online-arrival";
    public static final String DEPARTURE_URL = "https://airport.by/en/timetable/online-departure";
}
