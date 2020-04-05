package org.telbots.commands;

import org.telbots.bots.CovidIndiaBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class DevInfoCommand extends CovidIndiaBot {

    Update update=null;
    SendMessage sendMessage=null;

    public DevInfoCommand(Update update)
    {
        this.update = update;
    }

    public void displayDevInfo()
    {
        sendMessage = new SendMessage();
        sendMessage.setParseMode(ParseMode.HTML);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Hi There! I'm Ashwin Raghunath\nLet's connect on:\n");
        stringBuffer.append("LinkedIn : https://www.linkedin.com/in/araghunath\n");
        stringBuffer.append("GitHub : https://github.com/ashraghunath\n");
        stringBuffer.append("Instagram : https://www.instagram.com/the_ashwin_r/\n");

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(update.getMessage().getChatId());
        sendPhoto.setCaption(stringBuffer.toString());
        sendPhoto.setPhoto("https://pbs.twimg.com/profile_images/842434324427243520/QQvhXPSQ.jpg");
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
