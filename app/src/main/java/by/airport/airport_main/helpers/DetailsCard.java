package by.airport.airport_main.helpers;

import android.support.v7.app.AppCompatActivity;

import by.airport.airport_main.entity.FlightInfo;

public class DetailsCard extends AppCompatActivity {

    private FlightInfo activity;

    public DetailsCard(FlightInfo activity) {
        this.activity = activity;
    }

    public String getCity() {
        return this.activity.getCity();
    }


    public String getCode() {
        return this.activity.getCode();
    }

    public String getCompany() {
        return this.activity.getCompany();
    }

    public String getActualTime() {
        return this.activity.getActualTime();
    }

    public String getExpectedTime() {
        return this.activity.getExpectedTime();
    }

    public Statuses getStatus() {
        return this.activity.getStatus();
    }

    public String getGate() {
        return this.activity.getGate();
    }
}
