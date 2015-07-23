package by.airport.airport_master.entity;

import java.util.List;

/**
 * Created by dzianis.sipakou on 7/23/2015.
 */
public class FullFlightInfo<T extends FlightInfo> {

    private List<T> flightInfo;
    private List<Integer> positions;
    private List<String> headers;

    public List<T> getFlightInfo() {
        return flightInfo;
    }

    public void setFlightInfo(List<T> flightInfo) {
        this.flightInfo = flightInfo;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
