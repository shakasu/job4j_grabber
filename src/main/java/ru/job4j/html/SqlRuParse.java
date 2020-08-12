package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        Elements dates = doc.select("td.altCol");
        for (int i = 0; i < row.size() && i < dates.size(); i++) {
            Element href = row.get(i).child(0);
            System.out.printf(
                    "%s%n%s%n%s%n",
                    href.attr("href"),
                    href.text(),
                    dates.get(i).text()
            );
        }
    }
}