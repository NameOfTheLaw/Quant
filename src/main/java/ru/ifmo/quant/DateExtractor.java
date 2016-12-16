package ru.ifmo.quant;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import java.sql.Timestamp;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrey on 08.11.2016.
 */
public class DateExtractor {

    private String timeString;
    private String rawText = null;
    private String text = null;
    private Timestamp date = null;
    private boolean specifiedTime = false;
    private boolean correct = false;

    public DateExtractor(String rawText) {
        //TODO: Add timezone handling

        this.rawText = rawText;
        PrettyTimeParser ptp = new PrettyTimeParser();
        List<DateGroup> parse = ptp.parseSyntax(rawText);
        java.util.Date rawDate = null;
        text = rawText;
        try {
            rawDate = parse.get(0).getDates().get(0);
            timeString = parse.get(0).getText();
            text = rawText.replace(parse.get(0).getText(), "").replaceAll("\\s+", " ").trim();
        } catch (IndexOutOfBoundsException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rawDate != null) {
            date = new Timestamp(rawDate.getTime());
            if (isTime(timeString)) {
                specifiedTime = true;
            }
            correct = true;
        }

    }

    public String getRawText() {
        return rawText;
    }

    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public boolean isCorrect() {
        return correct;
    }

    private boolean isTime(String string) {
        if (string != null) {
            Pattern pattern = Pattern.compile("[0-9]{1,2}:[0-9]{2}");
            Matcher matcher = pattern.matcher(string);
            boolean answer = matcher.find();
            if (!answer) {
                string = string.replaceFirst("[0-9]{1,2}","");
                pattern = Pattern.compile("[0-9]{1,2}");
                matcher = pattern.matcher(string);
                answer = matcher.find();
            }
            return answer;
        }
        return false;
    }

    public boolean isSpecifiedTime() {
        return specifiedTime;
    }
}
