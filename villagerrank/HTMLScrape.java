/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package villagerrank;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author owner
 */
public class HTMLScrape {
//
//    public static void main(String[] args) throws IOException {
//        String value = scrapeImage("http://animalcrossing.wikia.com/wiki/Alfonso");
//        saveImage(value, "Alfonso.png");
//    }

    public static String scrapeImage(String url, String name) throws IOException {
        
        // Backfill some names, no longer needed and will work without this
//        switch(name) {
//            case "Blanche":
//            case "Chops":
//            case "Coach":
//            case "Deli":
//            case "Frank":
//            case "Kevin":
//            case "Marcie":
//            case "Molly":
//            case "Patty":
//            case "Sally":
//            case "Aurora_(villager)":
//            case "Hazel_(New_Leaf)":
//            case "Carmen_(rabbit)":
//            case "Cherry_(villager)":
//                break;
//            default:
//                return null;
//        }
        try {
            Document page = Jsoup.connect(url).get();

            // We choose Leaf because it's the New Leaf image
            Elements links = page.select("a[href*=?cb]a[href*=.png],a[href*=?cb]a[href*=.jpg]"); // Using selector css

            for (Element el : links) {
                if (el.hasClass("image") && el.hasClass("image-thumbnail")) {
                    return el.attr("href");
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveImage(String url, String name) throws IOException {
        URL imageLocation = new URL(url);
        try (ReadableByteChannel rbc = Channels.newChannel(imageLocation.openStream())) {
            FileOutputStream outputStream = new FileOutputStream(name);
            outputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }
}
