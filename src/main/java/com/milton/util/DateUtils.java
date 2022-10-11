package com.milton.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);

    public DateUtils() {
    }

    public static Date strToTime(String strTime) {
        try {
            return ConstantUtils.SIMPLE_DATETIME_FORMAT.parse(dateConvert(strTime));
        } catch (Exception var2) {
            return strToDate(strTime);
        }
    }

    public static Date strToDate(String strDate) {
        try {
            return ConstantUtils.SIMPLE_DATE_FORMAT.parse(dateConvert(strDate));
        } catch (Exception var2) {
            return null;
        }
    }

    public static Date strToDate(String strDate, String pattern) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(strDate);
        } catch (Exception var3) {
            return null;
        }
    }

    public static String millisecondToDate(long millisecond) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(millisecond / 1000L, 0, ZoneOffset.ofHours(8));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ConstantUtils.getDateFormat());
        return localDateTime.format(dateTimeFormatter);
    }

    public static String millisecondToTime(long millisecond) {
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(millisecond / 1000L, 0, ZoneOffset.ofHours(8));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ConstantUtils.getTimeFormat());
        return localDateTime.format(dateTimeFormatter);
    }

    public static String dateToStr(Date date, String pattern) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(dateTimeFormatter);
    }

    public static String dateToStr(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ConstantUtils.getDateFormat());
        return localDateTime.format(dateTimeFormatter);
    }

    public static String timeToStr(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ConstantUtils.getTimeFormat());
        return localDateTime.format(dateTimeFormatter);
    }

    public static String getNowStrByPattern(String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    public static String getNowStr() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ConstantUtils.getTimeFormat());
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    public static String cutLongDateStr(String longDate) {
        return StringUtils.isNotEmpty(longDate) && longDate.length() > 19 ? longDate.substring(0, 19) : longDate;
    }

    public static Date truncTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date lastTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 999);
        return cal.getTime();
    }

    public static long until(LocalDate endDate) {
        return LocalDate.now().until(endDate, ChronoUnit.DAYS);
    }

    public static long until(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

    public static Long until(String startDate, String endDate) {
        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(ConstantUtils.getDateFormat());
            return LocalDate.parse(startDate.substring(0, 10), dateTimeFormatter).until(LocalDate.parse(endDate.substring(0, 10), dateTimeFormatter), ChronoUnit.DAYS);
        } else {
            return null;
        }
    }

    public static List<String> getDatesInterval(String beginDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantUtils.getDateFormat());
        List<String> lDate = new ArrayList();

        try {
            lDate.add(beginDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(beginDate));

            while(true) {
                cal.add(5, 1);
                if (!sdf.parse(endDate).after(cal.getTime())) {
                    lDate.add(endDate);
                    break;
                }

                lDate.add(sdf.format(cal.getTime()));
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return lDate;
    }

    public static List<String> getMonthInterval(String minDate, String maxDate) {
        List<String> result = new ArrayList();

        try {
            Date d1 = (new SimpleDateFormat(ConstantUtils.getDateFormat())).parse(minDate);
            Date d2 = (new SimpleDateFormat(ConstantUtils.getDateFormat())).parse(maxDate);
            Calendar dd = Calendar.getInstance();
            dd.setTime(d1);

            while(dd.getTime().before(d2)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                String str = sdf.format(dd.getTime());
                result.add(str);
                dd.add(2, 1);
            }
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return result;
    }

    public static Date getDateLastDay(String dateStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConstantUtils.getDateFormat());
        Calendar date = Calendar.getInstance();

        try {
            date.setTime(simpleDateFormat.parse(dateStr));
            date.add(2, 1);
            date.set(5, 1);
            date.add(5, -1);
            return date.getTime();
        } catch (ParseException var4) {
            return null;
        }
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, days);
        return calendar.getTime();
    }

    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, months);
        return calendar.getTime();
    }

    public static Date addYears(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(1, years);
        return calendar.getTime();
    }

    public static Date add(Date date, int count, String type) {
        if (StringUtils.isNotBlank(type) && type.toUpperCase().equals("Y")) {
            return addYears(date, count);
        } else {
            return StringUtils.isNotBlank(type) && type.toUpperCase().equals("M") ? addMonths(date, count) : addDays(date, count);
        }
    }

    public static Date subDays(Date date, int days) {
        return addDays(date, 0 - days);
    }

    public static int calAge(String birthDay) {
        return calAge(birthDay, ConstantUtils.getDateFormat());
    }

    public static int calAge(String birthDay, String patten) {
        try {
            Calendar bir = Calendar.getInstance();
            bir.setTime((new SimpleDateFormat(patten)).parse(birthDay));
            return calAge(bir.getTime());
        } catch (ParseException var3) {
            return -2;
        }
    }

    public static int calAge(Date birthday) {
        Calendar cal = Calendar.getInstance();
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTime(birthday);
        if (cal.before(birthDay)) {
            return -1;
        } else {
            return birthDay.get(2) <= cal.get(2) && (birthDay.get(2) != cal.get(2) || birthDay.get(5) < cal.get(5)) ? cal.get(1) - birthDay.get(1) - 1 : cal.get(1) - birthDay.get(1);
        }
    }

    public static int dateCompare(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        int year1 = c1.get(1);
        int month1 = c1.get(2);
        int day1 = c1.get(5);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        int year2 = c2.get(1);
        int month2 = c2.get(2);
        int day2 = c2.get(5);
        if (year1 == year2 && month1 == month2 && day1 == day2) {
            return 0;
        } else {
            return year1 <= year2 && (year1 != year2 || month1 <= month2) && (year1 != year2 || month1 != month2 || day1 <= day2) ? -1 : 1;
        }
    }

    public static int timeCompare(Date d1, Date d2) {
        return d1.compareTo(d2);
    }

    public static String dateConvert(String str) {
        StringBuffer date = new StringBuffer();
        if (StringUtils.isNotBlank(str)) {
            String day;
            if (str.contains("年")) {
                day = str.substring(0, str.indexOf("年"));
                if (day.length() == 2) {
                    date.append("20").append(day).append("-");
                } else {
                    date.append(day).append("-");
                }

                str = str.substring(str.indexOf("年") + 1);
            }

            if (str.contains("月")) {
                day = str.substring(0, str.indexOf("月"));
                if (day.length() == 1) {
                    date.append("0").append(day).append("-");
                } else {
                    date.append(day).append("-");
                }

                str = str.substring(str.indexOf("月") + 1);
            }

            if (str.contains("日")) {
                day = str.substring(0, str.indexOf("日"));
                if (day.length() == 1) {
                    date.append("0").append(day);
                } else {
                    date.append(day);
                }
            }

            return date.length() > 0 ? date.toString() : str.replace(".", "-");
        } else {
            return str;
        }
    }

    public static Date dateDeserializer(String dateValue) {
        if (StringUtils.isBlank(dateValue)) {
            return null;
        } else {
            Date date = null;
            if (StringUtils.isNumeric(dateValue)) {
                date = new Date(Long.parseLong(dateValue));
            }

            if (date == null && dateValue.contains("-")) {
                date = strToTime(dateValue);
                if (date == null) {
                    date = strToDate(dateValue);
                }
            }

            if (date == null && dateValue.contains(".")) {
                date = strToDate(dateValue, "yyyy.MM.dd HH:mm:ss");
                if (date == null) {
                    date = strToDate(dateValue, "yyyy.MM.dd");
                }
            }

            if (date == null && dateValue.contains("/")) {
                date = strToDate(dateValue, "yyyy/MM/dd HH:mm:ss");
                if (date == null) {
                    date = strToDate(dateValue, "yyyy/MM/dd");
                }

                if (date == null) {
                    date = strToDate(dateValue, "MM/dd/yyyy HH:mm:ss");
                }

                if (date == null) {
                    date = strToDate(dateValue, "MM/dd/yyyy");
                }

                if (date == null) {
                    date = strToDate(dateValue, "dd/MM/yyyy HH:mm:ss");
                }

                if (date == null) {
                    date = strToDate(dateValue, "dd/MM/yyyy");
                }
            }

            if (date == null) {
                date = strToDate(dateValue, "yyyyMMddHHmmss");
            }

            if (date == null) {
                date = strToDate(dateValue, "yyyyMMdd");
            }

            if (date == null && StringUtils.isNumeric(dateValue)) {
                date = new Date(Long.parseLong(dateValue));
            }

            return date;
        }
    }
}
