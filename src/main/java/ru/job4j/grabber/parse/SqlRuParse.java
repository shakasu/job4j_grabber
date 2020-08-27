package ru.job4j.grabber.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.model.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    /**
     *  Method loads the list of ads using a link
     *  like - https://www.sql.ru/forum/job-offers.
     * @param link
     * @return
     */
    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> result = new ArrayList<>();
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        Elements dates = doc.select("td.altCol");
        int di = 1;
        for (int i = 0; i < row.size() && di < dates.size(); i++) {
            Element href = row.get(i).child(0);
//            result.add(
//              new Post(
//                      href.attr("href"),
//                      href.text(),
//                      dates.get(i).text(),
//
//              )
//            );
            System.out.printf(
                    "%s%n%s%n%s%n",
                    href.attr("href"),
                    href.text(),
                    dates.get(i).text()
            );
            di += 2;
        }
        return result;
    }

    /**
     * Method loads the details of an ad by a link
     * like - https://www.sql.ru/forum/1323839/razrabotchik-java-g-kazan.
     * @param link
     * @return
     */
    @Override
    public Post detail(String link) throws IOException {
        Post result = null;
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        Elements dates = doc.select("td.altCol");
        int di = 1;
        for (int i = 0; i < row.size() && di < dates.size(); i++) {
            Element href = row.get(i).child(0);
            System.out.printf(
                    "%s%n%s%n%s%n",
                    href.attr("href"),
                    href.text(),
                    dates.get(i).text()
            );
            di += 2;

        }
        return result;
    }

}

