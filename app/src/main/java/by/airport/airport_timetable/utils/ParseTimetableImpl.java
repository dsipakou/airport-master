package by.airport.airport_timetable.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.htmlcleaner.TagNode;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.airport.airport_timetable.MainActivity;
import by.airport.airport_timetable.entity.FlightInfo;
import by.airport.airport_timetable.entity.FullFlightInfo;
import by.airport.airport_timetable.helpers.Period;

/**
 * Created by dzianis.sipakou on 5/21/2015.
 */
public class ParseTimetableImpl<T extends FlightInfo> implements ParseTimetable<T> {

    private static final String TIME_TABLE_TEMPLATE_XPATH = "//table/tbody/tr[@class='%s']";
    private static final String SHORT_TIME_TABLE_TEMPLATE_XPATH = "//div[@class='content']/table/tbody/tr[position() > 1]";
    private static final String STATUS_XPATH = "/td[7]/span[1]";
    private static final String HEADER_DATE_FORMAT = "%s:%s";
    private FullFlightInfo<T> fullFlightInfo;

    public FullFlightInfo<T> getArrivalDetailsList(URL url, Class<T> clazz) {
        Context mainContext = MainActivity.getContextOfApp();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mainContext);
        Period dayMode = Period.fromString(settings.getString("dayMode", Period.NOW.toString()));
        String timeTableXpath = String.format(TIME_TABLE_TEMPLATE_XPATH, dayMode.toString());
        if (dayMode == Period.NOW) {
            timeTableXpath = SHORT_TIME_TABLE_TEMPLATE_XPATH;
        }
        List<T> infos = new ArrayList<>();
        fullFlightInfo = new FullFlightInfo<>();
        try {
            HtmlParser htmlParser = new HtmlParser(url, timeTableXpath);
            HtmlParser status = new HtmlParser(url, timeTableXpath + STATUS_XPATH);
            for(Object row : htmlParser.getTimetable()) {
                T info = clazz.newInstance();
                TagNode cell = (TagNode) row;
                info.setCompany(htmlParser.getCellInner(cell, 0));
                info.setExpectedTime(htmlParser.getCellInner(cell, 1));
                info.setActualTime(htmlParser.getCellInner(cell, 2));
                info.setCode(htmlParser.getCellInner(cell, 3));
                info.setCity(htmlParser.getCellInner(cell, 4));
                info.setGate(htmlParser.getCellInner(cell, 5));
                info.setStatus(status.getCellInner(cell, 6));
                infos.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        fullFlightInfo.setFlightInfo(infos);
        setHeaders(infos);
        return fullFlightInfo;
    }

    public FullFlightInfo<T> getDepartureDetailsList(URL url, Class<T> clazz) {
        Context mainContext = MainActivity.getContextOfApp();
        List<T> infos = new ArrayList<>();
        fullFlightInfo = new FullFlightInfo<>();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mainContext);
        Period dayMode = Period.fromString(settings.getString("dayMode", Period.NOW.toString()));
        String timeTableXpath = String.format(TIME_TABLE_TEMPLATE_XPATH, dayMode.toString());
        if (dayMode == Period.NOW) {
            timeTableXpath = SHORT_TIME_TABLE_TEMPLATE_XPATH;
        }
        try {
            HtmlParser htmlParser = new HtmlParser(url, timeTableXpath);
            HtmlParser status = new HtmlParser(url, timeTableXpath + STATUS_XPATH);
            for(Object row : htmlParser.getTimetable()) {
                T info = clazz.newInstance();
                TagNode cell = (TagNode) row;
                info.setCompany(htmlParser.getCellInner(cell, 0));
                info.setExpectedTime(htmlParser.getCellInner(cell, 1));
                info.setCode(htmlParser.getCellInner(cell, 2));
                info.setCity(htmlParser.getCellInner(cell, 3));
                info.setRegistrationDesk(htmlParser.getCellInner(cell, 4));
                info.setGate(htmlParser.getCellInner(cell, 5));
                info.setStatus(status.getCellInner(cell, 6));
                infos.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        fullFlightInfo.setFlightInfo(infos);
        setHeaders(infos);
        return fullFlightInfo;
    }

    private void setHeaders(List<T> infos) {
        DateFormat format = new SimpleDateFormat("HH:mm");
        List<Date> parsedTimeList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        List<Integer> positions = new ArrayList<>();
        List<String> headers = new ArrayList<>();
        int currentHour = 0;
        int i = 0;

        try {
            for (T info : infos) {
                parsedTimeList.add(format.parse(info.getExpectedTime()));
            }
            for (Date time : parsedTimeList) {
                calendar.setTime(time);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);

                if (i == 0) {
                    positions.add(i);
                    headers.add(getFormatedHeader(hour));
                    currentHour = hour;
                } else {
                    if (hour != currentHour) {
                        headers.add(getFormatedHeader(hour));
                        positions.add(i);
                        currentHour = hour;
                    }
                }
                i ++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        fullFlightInfo.setHeaders(headers);
        fullFlightInfo.setPositions(positions);
    }

    private String getFormatedHeader(int hour) {
        return String.format(HEADER_DATE_FORMAT, Integer.toString(hour), "00");
    }
}
