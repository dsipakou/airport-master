package by.airport.airport_master.utils;

import by.airport.airport_master.entity.ArrivalInfo;
import by.airport.airport_master.entity.DepartureInfo;

/**
 * Created by dzianis.sipakou on 5/25/2015.
 */
public class Globals {
    public static ArrivalInfo arrivalInfo = new ArrivalInfo();
    public static DepartureInfo departureInfo = new DepartureInfo();
    public static final String ARRIVAL_URL = "http://airport.by/en/timetable/online-arrival";
    public static final String DEPARTURE_URL = "http://airport.by/en/timetable/online-departure";
}