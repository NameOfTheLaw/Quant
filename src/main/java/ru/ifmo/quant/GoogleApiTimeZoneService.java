package ru.ifmo.quant;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimeZone;

/**
 * Created by andrey on 20.01.2017.
 */
@Component("timeZoneService")
@Scope("prototype")
public class GoogleApiTimeZoneService extends TimeZoneService {

    @Value("${google.apiurl}")
    private String defaultUrl;
    @Value("${google.apikey}")
    private String googleApiKey;
    @Value("${google.timestamptemplate}")
    private String timeStampArgument;
    @Value("${timezone.default}")
    public String defaultTimeZoneName;

    public TimeZone extract(QuantMessage input) {
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return timeZone;
    }

    public TimeZone getDefault() {
        return TimeZone.getTimeZone(defaultTimeZoneName);
    }

}
