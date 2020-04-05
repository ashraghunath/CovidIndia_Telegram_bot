package org.telbots.commands;

import org.telbots.bots.CovidIndiaBot;
import org.telbots.model.StateInformation;
import org.telbots.utils.WebScrape;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class StateWiseCommand extends CovidIndiaBot {

    SendMessage sendMessage;
    Update update=null;
    public StateWiseCommand(Update update)
    {
        this.update=update;
    }

    public void displayStateWiseInfo()
    {

        sendMessage= new SendMessage();

        WebScrape webScrape = new WebScrape();
        List<StateInformation> statesInfo = webScrape.getStatesInfo();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<pre>");
        for(StateInformation state : statesInfo)
        {
            stringBuffer.append("State : "+state.getName()+"\n");
            stringBuffer.append("Cases : "+state.getCases()+"\n");
            stringBuffer.append("Recovered : "+state.getCured()+"\n");
            stringBuffer.append("Fatalities : "+state.getDeaths()+"\n");
            stringBuffer.append("------------------------------\n");
        }
        stringBuffer.append("</pre>");
        sendMessage.setText(stringBuffer.toString());
        sendMessage.setParseMode(ParseMode.HTML);

        sendMessage.setChatId(update.getMessage().getChatId());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
