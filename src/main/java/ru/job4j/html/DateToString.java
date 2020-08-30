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

    static private HashMap<String, Integer> months = new HashMap<>();

    public DateToString() {
        months.put("янв", 0);
        months.put("фев", 1);
        months.put("мар", 2);
        months.put("апр", 3);
        months.put("май", 4);
        months.put("июн", 5);
        months.put("июл", 6);
        months.put("авг", 7);
        months.put("сен", 8);
        months.put("окт", 9);
        months.put("ноя", 10);
        months.put("дек", 11);
    }

    public Timestamp transform(String strDate) {
        int year, month, day, hour, minute;
        String[] time;
        Calendar current = new GregorianCalendar();
        if (strDate.split(" ").length == 4) {
            day = Integer.parseInt(strDate.split(" ")[0]);
            month = months.get(strDate.split(" ")[1]);
            year = 100 + Integer.parseInt(strDate.split(" ")[2].split(",")[0]);
            time = strDate.split(" ")[3].split(":");
            hour = Integer.parseInt(time[0]);
            minute = Integer.parseInt(time[1]);
        } else {
            if (strDate.startsWith("сегодня") || strDate.startsWith("вчера")) {
                time = strDate.split(" ")[1].split(":");
                hour = Integer.parseInt(time[0]);
                minute = Integer.parseInt(time[1]);
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
                minute = 0;
                hour = 0;
            }
        }
        return new Timestamp(year, month, day, hour, minute, 0, 0);
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
