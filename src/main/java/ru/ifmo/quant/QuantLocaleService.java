package ru.ifmo.quant;

import org.springframework.stereotype.Component;
import ru.ifmo.quant.entities.AccountEntity;

import java.util.Locale;

/**
 * Created by andrey on 18.01.2017.
 */
@Component
public class QuantLocaleService {

    public static Locale EN = Locale.US;
    public static Locale RU = new Locale("ru", "RU");
    public static Locale DEFAULT = EN;

    public static Locale getLocale(String localeString) {
        if (localeString.toLowerCase().equals("en")) {
            return EN;
        }
        if (localeString.toLowerCase().equals("ru")) {
            return RU;
        }
        return DEFAULT;
    }

    public static boolean isLocale(String localeString) {
        if (localeString.toLowerCase().equals("en") || localeString.toLowerCase().equals("ru")) {
            return true;
        }
        return false;
    }

}
