package by.airport.airport_timetable.entity;

import by.airport.airport_timetable.helpers.Statuses;

/**
 * Created by dzianis.sipakou on 5/19/2015.
 */
public class ArrivalInfo implements FlightInfo {

    private String company;
    private String code;
    private String gate;
    private String expectedTime;
    private String actualTime;
    private String city;
    private Statuses status;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status != null) {
            if (status.contains("LANDED")) {
                this.status = Statuses.LANDED;
            } else if (status.contains("DELAYED")) {
                this.status = Statuses.DELAYED;
            } else if (status.contains("BOARDING")) {
                this.status = Statuses.BOARDING;
            } else if (status.contains("CHECK-IN")) {
                this.status = Statuses.CHECKIN;
            }
        }
    }
}
