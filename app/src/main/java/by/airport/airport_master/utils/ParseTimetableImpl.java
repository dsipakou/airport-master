package by.airport.airport_master.utils;

import org.htmlcleaner.TagNode;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import by.airport.airport_master.entity.FlightInfo;

/**
 * Created by dzianis.sipakou on 5/21/2015.
 */
public class ParseTimetableImpl<T extends FlightInfo> implements ParseTimetable<T> {

    private static final String TIME_TABLE_XPATH = "//table/tbody/tr";

    public List<T> getDetailsList(URL url, Class<T> clazz) {
        List<T> infos = new ArrayList<>();
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

        return infos;
    }
}
