package org.telbots.utils;

import com.google.gson.JsonElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.telbots.CovidIndiaBotConfig;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

import static org.telbots.CovidIndiaBotConfig.NEWS_API_KEY;

public class NewsParser {

    private static String text="";

    public static void main(String[] args) throws Exception{
        URL url = new URL("http://newsapi.org/v2/top-headlines?q=corona&sources=google-news-in&apiKey=22561afae4fd496d9ab3523b7156699d");
//        URL url = new URL(new CovidIndiaBotConfig().getPropValue("ARTICLES_URL") +NEWS_API_KEY());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if(responsecode==200)
        {
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext())
            {
                text+=sc.nextLine();
            }
            System.out.println("\nJSON data in string format");
            sc.close();
        }
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(text);
        JSONArray articles = (JSONArray) jsonObject.get("articles");

        for (Object article : articles) {
            System.out.println(article);
        }
        Iterator<JsonElement> iterator = articles.iterator();
        while(iterator.hasNext())
        {
            System.out.println(iterator.next());
        }
    }

    public JSONArray getArticles() throws Exception
    {
        URL url = new URL(new CovidIndiaBotConfig().getPropValue("ARTICLES_URL") +NEWS_API_KEY());
        text=new String("");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responsecode = conn.getResponseCode();
        if(responsecode==200)
        {
            Scanner sc = new Scanner(url.openStream());
            while(sc.hasNext())
            {
                text+=sc.nextLine();
            }
            sc.close();
        }
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(text);
        JSONArray articles = (JSONArray) jsonObject.get("articles");
        return articles;
    }
}
