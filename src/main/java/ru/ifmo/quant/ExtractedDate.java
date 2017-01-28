package ru.ifmo.quant;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.entities.AccountEntity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by andrey on 08.11.2016.
 */
@Component
@Scope("prototype")
public class ExtractedDate {

    private SimpleDateFormat formatter;

    private String timeString = null;
    private String rawText = null;
    private String text = null;
    private Timestamp date = null;
    private Timestamp serverDate = null;

    private boolean specifiedTime = false;
    private boolean correct = false;

    @Autowired
    private DateTimeService dateTimeService;

    public void extract(String rawText, AccountEntity accountEntity) {
        extract(rawText, TimeZone.getTimeZone(accountEntity.getTimeZone()));
    }

    public void extract(QuantMessage message, AccountEntity accountEntity) {
        extract(message.getText(), TimeZone.getTimeZone(accountEntity.getTimeZone()));
    }

    public void extract(String rawText, TimeZone timeZone) {

        this.rawText = rawText;
        PrettyTimeParser ptp = new PrettyTimeParser(timeZone);
        List<DateGroup> parse = ptp.parseSyntax(rawText);
        Date rawDate = null;
        text = rawText;
        try {
            rawDate = parse.get(0).getDates().get(0);
            timeString = parse.get(0).getText();
            text = rawText.replace(parse.get(0).getText(), "").replaceAll("\\s+", " ").trim();
        } catch (IndexOutOfBoundsException e) {
            //ignore
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (rawDate != null) {
            date = new Timestamp(rawDate.getTime());
            formatter = new SimpleDateFormat(dateTimeService.getDefaultDateFormat());
            formatter.setTimeZone(dateTimeService.getDefaultTimeZone());
            if (isTime(timeString)) {
                specifiedTime = true;
            }
            correct = true;
            try {
                serverDate = new Timestamp(formatter.parse(formatter.format(date)).getTime());
            } catch (Exception e) {
                e.printStackTrace();
                correct = false;
            }
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

    public Timestamp getServerDate() {
        return serverDate;
    }

    public void setServerDate(Timestamp serverDate) {
        this.serverDate = serverDate;
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
