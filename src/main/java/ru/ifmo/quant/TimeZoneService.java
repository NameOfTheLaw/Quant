package ru.ifmo.quant;

import org.springframework.beans.factory.annotation.Value;

import java.util.TimeZone;

/**
 * Created by andrey on 22.01.2017.
 */
public abstract class TimeZoneService {

    public abstract TimeZone getDefault();

    public abstract TimeZone extract(QuantMessage message);

}
