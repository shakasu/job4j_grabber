package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        for (int k = 0; k < 6; k++) {
            StringBuilder url = new StringBuilder("https://www.sql.ru/forum/job-offers");
            if (k > 0) {
                url.append("/");
                url.append(k);
            }
            System.out.println(url.toString());
            Document doc = Jsoup.connect(url.toString()).get();
            Elements row = doc.select(".postslisttopic");
            Elements dates = doc.select("td.altCol");
            int di = 1;
            for (int i = 0; i < row.size() && di < dates.size(); i++) {
                Element href = row.get(i).child(0);
                System.out.printf(
                        "%s%n%s%n%s%n%d%n",
                        href.attr("href"),
                        href.text(),
                        dates.get(i).text(),
                        i
                );
                di += 2;
            }
        }
    }
}