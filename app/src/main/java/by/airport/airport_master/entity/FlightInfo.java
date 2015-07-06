package by.airport.airport_master.entity;

import by.airport.airport_master.helpers.Statuses;

/**
 * Created by dzianis.sipakou on 5/21/2015.
 */
public interface FlightInfo {
    String getCompany();
    String getCode();
    String getCity();
    String getExpectedTime();
    String getActualTime();
    Statuses getStatus();
    String getGate();
    void setCompany(String company);
    void setCode(String code);
    void setCity(String city);
    void setExpectedTime(String expectedTime);
    void setActualTime(String actualTime);
    void setStatus(String status);
    void setGate(String gate);
}
