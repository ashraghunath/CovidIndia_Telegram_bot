package org.telbots.commands;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.telbots.Commands;
import org.telbots.SymbolsAndEmojis;
import org.telbots.bots.CovidIndiaBot;
import org.telbots.utils.NewsParser;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class ArticlesCommand extends CovidIndiaBot {

    Update update = null;
    SendMessage sendMessage = null;

    public ArticlesCommand(Update update) {
        this.update = update;
    }

    public void getArticles() throws Exception{
        sendMessage = new SendMessage();
        sendMessage.setText("Choose your article"
        );
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setReplyMarkup(createMainSelectKeyboard());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboard createMainSelectKeyboard() throws Exception{

        NewsParser newsParser = new NewsParser();
        JSONArray articles = newsParser.getArticles();
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        List keyBoardList = new ArrayList();

        for (Object article : articles) {
            KeyboardRow row = new KeyboardRow();
            row.add((String) ((JSONObject) article).get("title"));
            keyBoardList.add(row);
        }
        KeyboardRow backButton = new KeyboardRow();
        backButton.add(Commands.backButton);
        keyBoardList.add(backButton);
        keyboard.setResizeKeyboard(true);
        keyboard.setKeyboard(keyBoardList);
        return keyboard;
    }

    public boolean fetchArticleUsingTitle() throws Exception
    {
        NewsParser newsParser = new NewsParser();
        JSONArray articles = newsParser.getArticles();
        sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        StringBuffer stringBuffer = new StringBuffer();

        for (Object article : articles) {
            if(((JSONObject) article).get("title").equals(update.getMessage().getText()))
            {
                stringBuffer.append((String) ((JSONObject) article).get("description")+"\n");
                stringBuffer.append("Full article : "+(String) ((JSONObject) article).get("url")+"\n");
                ((JSONObject) article).get("url");
                stringBuffer.append("<a href=\""+((JSONObject) article).get("urlToImage")+"\"></a>");
                sendMessage.enableHtml(true);
                sendMessage.setParseMode(ParseMode.HTML);
                sendMessage.setText(stringBuffer.toString());
                execute(sendMessage);
                return true;
            }
        }
        sendDefaultMessage(update);
        return false;
    }
}
