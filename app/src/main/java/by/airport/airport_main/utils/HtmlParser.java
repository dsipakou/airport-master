package by.airport.airport_main.utils;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.net.URL;

public class HtmlParser {

    private TagNode rootNode;
    private String xpath;

    public HtmlParser(URL htmlPage, String xpath) throws IOException {
        HtmlCleaner cleaner = new HtmlCleaner();
        rootNode = cleaner.clean(htmlPage);
        this.xpath = xpath;
    }

    public Object[] getTimetable() {
        Object[] rows = null;
        try {
            rows = rootNode.evaluateXPath(this.xpath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }

    public String getCellInner(TagNode cell, int index) {
        String text = ((TagNode)cell.getAllChildren().get(index)).getText().toString().trim();
        if (text.equals("")) {
            return null;
        } else {
            return text;
        }
    }
}
