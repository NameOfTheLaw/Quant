package ru.ifmo.quant;

import org.springframework.stereotype.Component;
import ru.ifmo.quant.entities.AccountEntity;

import java.util.Locale;

/**
 * Created by andrey on 18.01.2017.
 */
@Component
public class QuantLocaleService {

    private Locale EN = Locale.US;
    private Locale RU = new Locale("ru", "RU");
    private Locale DEFAULT = EN;

    public Locale getLocale(String localeString) {
        switch (localeString) {
            case "en":
                return EN;
            case "ru":
                return RU;
            default:
                return DEFAULT;
        }
    }

    public Locale getLocale(AccountEntity accountEntity) {
        return getLocale(accountEntity.getLanguage());
    }

    public boolean isLocale(String localeString) {
        switch (localeString) {
            case "en":
            case "ru":
                return true;
            default:
                return false;
        }
    }

    public Locale getDefault() {
        return DEFAULT;
    }

}
