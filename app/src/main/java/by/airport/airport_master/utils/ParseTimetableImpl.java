package by.airport.airport_master.utils;

import android.support.v4.app.INotificationSideChannel;

import org.htmlcleaner.TagNode;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import by.airport.airport_master.entity.FlightInfo;
import by.airport.airport_master.entity.FullFlightInfo;

/**
 * Created by dzianis.sipakou on 5/21/2015.
 */
public class ParseTimetableImpl<T extends FlightInfo> implements ParseTimetable<T> {

    private static final String TIME_TABLE_XPATH = "//table/tbody/tr";
    private static final String HEADER_DATE_FORMAT = "%s:%s";
    private FullFlightInfo<T> fullFlightInfo;

    public FullFlightInfo<T> getDetailsList(URL url, Class<T> clazz) {
        List<T> infos = new ArrayList<>();
        fullFlightInfo = new FullFlightInfo<>();
        try {
            HtmlParser htmlParser = new HtmlParser(url, TIME_TABLE_XPATH);
            for(Object row : htmlParser.getTimetable()) {
                T info = clazz.newInstance();
                TagNode cell = (TagNode) row;
                info.setCompany(htmlParser.getCellInner(cell, 1));
                info.setExpectedTime(htmlParser.getCellInner(cell, 3));
                info.setActualTime(htmlParser.getCellInner(cell, 5));
                info.setCode(htmlParser.getCellInner(cell, 7));
                info.setCity(htmlParser.getCellInner(cell, 9));
                info.setGate(htmlParser.getCellInner(cell, 11));
                info.setStatus(htmlParser.getCellInner(cell, 13));
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
