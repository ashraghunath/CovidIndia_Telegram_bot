package org.telbots.commands;

import org.telbots.CovidIndiaBotConfig;
import org.telbots.bots.CovidIndiaBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;

public class DevInfoCommand extends CovidIndiaBot {

    Update update=null;
    SendMessage sendMessage=null;

    public DevInfoCommand(Update update)
    {
        this.update = update;
    }


    public void displayDevInfo() throws Exception
    {
        sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.HTML);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new CovidIndiaBotConfig().getPropValue("ASHWINS_INTRODUCTION"));
        stringBuffer.append(new CovidIndiaBotConfig().getPropValue("ASHWIN_LINKEDIN"));
        stringBuffer.append(new CovidIndiaBotConfig().getPropValue("ASHWIN_GITHUB"));
        stringBuffer.append(new CovidIndiaBotConfig().getPropValue("ASHWIN_INSTAGRAM"));

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(update.getMessage().getChatId());
        sendPhoto.setCaption(stringBuffer.toString());
        sendPhoto.setPhoto(new File("src\\main\\resources\\images\\ashwin_linkedin_image.jpeg"));
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
