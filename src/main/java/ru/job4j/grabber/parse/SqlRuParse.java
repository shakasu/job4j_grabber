package ru.job4j.grabber.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.model.Post;
import ru.job4j.html.DateToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {
    DateToString dateToString = new DateToString();

    /**
     *  Method loads the list of ads using a link
     *  like - https://www.sql.ru/forum/job-offers.
     * @param link
     * @return
     */
    @Override
    public List<Post> list(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
        Elements row = doc.select(".postslisttopic");
        List<Post> result = new ArrayList<>();
        for (Element td : row) {
            Element href = td.child(0);
            Element data = td.parent().child(5);
            String detailLink = href.attr("href");
            Document docForDetailBody = Jsoup.connect(detailLink).get();
            Elements msgBody = docForDetailBody.select(".msgBody");
            String body = msgBody.get(1).text();
            result.add(new Post(
                    href.text(),
                    body,
                    detailLink,
                    dateToString.transform(data.text())
            ));
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
        Document doc = Jsoup.connect(link).get();
        Elements msgBody = doc.select(".msgBody");
        Elements messageHeader = doc.select(".messageHeader");
        Elements date = doc.select(".msgFooter");
        return new Post(
                messageHeader.get(1).text(),
                msgBody.get(1).text(),
                link,
                dateToString.transform(date.text().split("\\[")[0]));
    }

    public static void main(String[] args) throws IOException {
        SqlRuParse sqlRuParse = new SqlRuParse();
        String linkDetail = "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t";
        String link = "https://www.sql.ru/forum/job-offers";
        System.out.println(sqlRuParse.list(link));
        //System.out.println(sqlRuParse.detail(linkDetail).toString());

    }
}

