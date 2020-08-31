package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class DateToString {

    static private final HashMap<String, Integer> MONTHS = new HashMap<>() {
        {
            put("янв", 0);
            put("фев", 1);
            put("мар", 2);
            put("апр", 3);
            put("май", 4);
            put("июн", 5);
            put("июл", 6);
            put("авг", 7);
            put("сен", 8);
            put("окт", 9);
            put("ноя", 10);
            put("дек", 11);
        }
    };

    private int[] dateFormatting(String strDate) {
        int year, month, day;
        Calendar current = new GregorianCalendar();
        if (strDate.split(" ").length == 4) {
            day = Integer.parseInt(strDate.split(" ")[0]);
            month = MONTHS.get(strDate.split(" ")[1]);
            year = 100 + Integer.parseInt(strDate.split(" ")[2].split(",")[0]);

        } else {
            if (strDate.startsWith("сегодня") || strDate.startsWith("вчера")) {
                if (strDate.startsWith("вчера")) {
                    current.add(Calendar.DATE, -1);
                }
                day = current.get(Calendar.DAY_OF_MONTH);
                year = current.get(Calendar.YEAR) - 1900;
                month = current.get(Calendar.MONTH);
            } else {
                day = 0;
                year = 0;
                month = 0;
            }
        }
        return new int[]{year, month, day};
    }

    private int[] timeFormatting(String strDate) {
        int hour, minute;
        String[] time;
        if (strDate.split(" ").length == 4) {
            time = strDate.split(" ")[3].split(":");
            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);
        } else {
            if (strDate.startsWith("сегодня") || strDate.startsWith("вчера")) {
                time = strDate.split(" ")[1].split(":");
                hour = Integer.parseInt(time[0]);
                minute = Integer.parseInt(time[1]);
            } else {
                minute = 0;
                hour = 0;
            }
        }
        return new int[]{hour, minute};
    }

    public Timestamp transform(String strDate) {
        int[] time = timeFormatting(strDate);
        int[] date = dateFormatting(strDate);
        return new Timestamp(date[0], date[1], date[2], time[0], time[1], 0, 0);
    }

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements dates = doc.select("td.altCol");
        DateToString dateToString = new DateToString();
        for (Element date : dates) {
            System.out.println(dateToString.transform(date.text()));
            System.out.println(date.text());
        }
    }
}
