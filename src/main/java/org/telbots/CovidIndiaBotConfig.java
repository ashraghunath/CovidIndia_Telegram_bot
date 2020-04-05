package org.telbots;
import java.io.InputStream;
import java.util.Properties;

public class CovidIndiaBotConfig {

    public static String NEWS_API_KEY()
    {
        String news_api_key = System.getenv("NEWS_API_KEY");
        return news_api_key;
    }

    public static String BOT_TOKEN()
    {
        String news_api_key = System.getenv("BOT_TOKEN");
        return news_api_key;
    }


    public String getPropValue(String key)
    {
        InputStream inputStream;
        try
        {
            Properties prop = new Properties();
            String propFileName = "strings.properties";
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            prop.load(inputStream);

            return prop.getProperty(key);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
