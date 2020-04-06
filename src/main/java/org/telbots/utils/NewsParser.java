package org.telbots.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.telbots.CovidIndiaBotConfig;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static org.telbots.CovidIndiaBotConfig.NEWS_API_KEY;

public class NewsParser {

    private static String text="";

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
