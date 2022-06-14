package com.HannibalHsiang;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

public class WebScrape {

    static Long removeComma(String numString) throws ParseException {

        Long numLong = (long) NumberFormat.getNumberInstance(Locale.UK).parse(numString);
        return numLong;
    }

    public static ArrayList getBookList(String pathOrUrl, Boolean isPath) throws IOException, ParseException {

        Document doc;

        if (isPath) {
            File input = new File(pathOrUrl);
            doc = Jsoup.parse(input, "GBK");
        } else {
            doc = Jsoup.connect(pathOrUrl).get();
        }

        Elements rows = doc.select("table:nth-of-type(4)");

        ArrayList bookList = new ArrayList();

        for (Element row : rows.select("tr")) {
            if (row.select("td:nth-of-type(1)").text().equals("作品")) {
                System.out.println("Sucessfully reach the table.");
                continue;
            }
            if (row.select("td:nth-of-type(2)").hasText()) {
                Book book = new Book();
                book.initName(row.select("td:nth-of-type(1)").text());
                book.initType(row.select("td:nth-of-type(2)").text());
                book.initStyle(row.select("td:nth-of-type(3)").text());
                book.initProcess(row.select("td:nth-of-type(4)").text());
                book.initWordCount(removeComma(row.select("td:nth-of-type(5)").text()));
                book.initRewardPoint(removeComma(row.select("td:nth-of-type(6)").text()));
                book.initEstablishTime(row.select("td:nth-of-type(7)").text());
                bookList.add(book);
            }
        }
        return bookList;
    }

    public static String getAuthorName(String pathOrUrl, Boolean isPath) throws IOException, ParseException {

        Document doc;

        if (isPath) {
            File input = new File(pathOrUrl);
            doc = Jsoup.parse(input, "GBK");
        } else {
            doc = Jsoup.connect(pathOrUrl).get();
        }

        Elements authorName = doc.select("b > span");

        return authorName.text();
    }
}

