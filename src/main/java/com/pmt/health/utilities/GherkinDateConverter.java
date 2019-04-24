package com.pmt.health.utilities;

import cucumber.api.Transformer;
import org.testng.log4testng.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GherkinDateConverter extends Transformer<Date> {

    SimpleDateFormat yearMonthDayDash = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat monthDayYearDash = new SimpleDateFormat("MM-dd-yyyy");
    SimpleDateFormat yearMonthDaySlash = new SimpleDateFormat("yyyy/MM/dd");
    SimpleDateFormat monthDayYearSlash = new SimpleDateFormat("MM/dd/yyyy");

    @Override
    public Date transform(String date) {
        try {
            if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return yearMonthDayDash.parse(date);
            }
            if (date.matches("\\d{2}-\\d{2}-\\d{4}")) {
                return monthDayYearDash.parse(date);
            }
            if (date.matches("\\d{4}\\/\\d{2}\\/\\d{2}")) {
                return yearMonthDaySlash.parse(date);
            }
            if (date.matches("\\d{2}\\/\\d{2}\\/\\d{4}")) {
                return monthDayYearSlash.parse(date);
            }
            if (date.matches("\\d+")) {
                return new Date(Long.valueOf(date));
            }
            Logger.getLogger(GherkinDateConverter.class).info("Date '" + date + "' is not a valid date format");
        } catch (ParseException e) {
            Logger.getLogger(GherkinDateConverter.class).info(e.getMessage());
        }
        return new Date();
    }
}
