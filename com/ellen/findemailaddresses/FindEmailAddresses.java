package com.ellen.findemailaddresses;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindEmailAddresses {

    private static HashSet<String> urls = new HashSet();
    private static HashSet<String> emails = new HashSet();

    public static void main(String[] args) throws Exception {
        String url = "http://" + args[0];
        try {
            Document doc = Jsoup.connect(url).get();
            findEmails(url, doc);
        } catch (IOException e) {
            System.out.println("Error for url " + url + "\n" + e.getMessage());
        }
    }

    public static void findEmails(String url, Document doc) {
        Elements elements = doc.getAllElements();
        for (Element element : elements) {
            String elementUrl = "";
            try {
                elementUrl = element.absUrl("abs:href");
                if (urls.contains(elementUrl)) {
                    continue;
                }
                urls.add(elementUrl);
                if (elementUrl.startsWith(url) && elementUrl.length() > 0) {
                    Document elementDoc = Jsoup.connect(elementUrl).ignoreContentType(true).get();
                    String text = elementDoc.text();
                    emails.addAll(find(text));
                }
            } catch (MalformedURLException e) {
                System.out.println("Error for element url " + elementUrl + "\n" + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error for element url " + elementUrl + "\n" + e.getMessage());
            }
        }
        if (emails.size() > 0) {
            System.out.println("\nEmails found: \n");
            for (String email : emails) {
                System.out.println("    " + email);
            }
        } else {
            System.out.println("no emails found");
        }
    }

    public static Set find(String inputString) {
        HashSet result = new HashSet<String>();
        Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9]+").matcher(inputString);
        while (m.find()) {
            String email = m.group();
            result.add(email);
        }
        return result;
    }
}