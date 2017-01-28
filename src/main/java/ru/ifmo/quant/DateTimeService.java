package ru.ifmo.quant;

import ru.ifmo.quant.entities.AccountEntity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by andrey on 22.01.2017.
 */
public abstract class DateTimeService {

    public abstract TimeZone getDefaultTimeZone();

    public abstract String getDefaultDateFormat();

    public abstract TimeZone extractTimeZone(QuantMessage message);

    public abstract ExtractedDate extractDate(QuantMessage message, AccountEntity accountEntity);

    public abstract Date convertTimeZone(Date date, AccountEntity accountEntity);

}
