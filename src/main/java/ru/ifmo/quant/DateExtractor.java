package ru.ifmo.quant;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import java.util.Date;
import java.util.List;

/**
 * Created by andrey on 08.11.2016.
 */
public class DateExtractor {
    //TODO: test date extracting

    private String rawText;
    private String text;
    private Date date;

    public DateExtractor(String rawText) {
        this.rawText = rawText;
        PrettyTimeParser ptp = new PrettyTimeParser();
        List<DateGroup> parse = ptp.parseSyntax(rawText);
        date = parse.get(0).getDates().get(0);
        text = rawText.replace(parse.get(0).getText(), "").replaceAll("\\s+", " ").trim();
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public String getRawText() {
        return rawText;
    }
}
