package ru.ifmo.quant;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.ifmo.quant.entities.AccountEntity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by andrey on 20.01.2017.
 */
@Component("dateTimeService")
public class GoogleApiDateTimeService extends DateTimeService implements ApplicationContextAware {

    @Value("${google.apiurl}")
    private String defaultUrl;
    @Value("${google.apikey}")
    private String googleApiKey;
    @Value("${google.timestamptemplate}")
    private String timeStampArgument;
    @Value("${timezone.default}")
    public String defaultTimeZoneName;
    @Value("${timeformat.default}")
    private String defaultDateFormat;

    private ApplicationContext ctx;

    public TimeZone extractTimeZone(QuantMessage input) {
        TimeZone timeZone = null;
        String coords = input.getLocation().getLatitude()+","+input.getLocation().getLongitude();
        String urlString = String.format(defaultUrl, new Object[] {coords, timeStampArgument, googleApiKey});
        try {
            URL url = new URL(urlString);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject object = root.getAsJsonObject();
            String timeZoneId =  object.get("timeZoneId").getAsString();
            timeZone = TimeZone.getTimeZone(timeZoneId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timeZone;
    }

    public ExtractedDate extractDate(QuantMessage message, AccountEntity accountEntity) {
        ExtractedDate extractedDate = ctx.getBean("extractedDate", ExtractedDate.class);
        extractedDate.extract(message, accountEntity);
        return extractedDate;
    }

    public Date convertTimeZone(Date date, AccountEntity accountEntity) {
        ZoneId serverZoneId = ZoneId.of(getDefaultTimeZone().getID());
        ZoneId clientZoneId = ZoneId.of(accountEntity.getTimeZone());

        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), serverZoneId);
        ZonedDateTime serverDateTime = localDateTime.atZone(serverZoneId);
        ZonedDateTime clientDateTime = serverDateTime.withZoneSameInstant(clientZoneId);
        LocalDateTime clientLocalDateTime = clientDateTime.toLocalDateTime();
        return Timestamp.from(clientLocalDateTime.toInstant(serverDateTime.getOffset()));
    }

    public TimeZone getDefaultTimeZone() {
        return TimeZone.getTimeZone(defaultTimeZoneName);
    }

    public String getDefaultDateFormat() {
        return defaultDateFormat;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
