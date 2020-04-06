package org.telbots.commands;

import org.telbots.bots.CovidIndiaBot;
import org.telbots.utils.WebScrape;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class CovidIndiaCommand extends CovidIndiaBot {

    SendMessage sendMessage;
    Update update=null;
    public CovidIndiaCommand(Update update)
    {
        this.update=update;
    }

    public void displayCovidIndiaSummary()
    {

        sendMessage= new SendMessage();

        WebScrape webScrape = new WebScrape();
        String coronaindiasummary = webScrape.getCoronaindiasummary();
        String[] coronaSplit = coronaindiasummary.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<pre>");
        stringBuffer.append("Total confirmed cases (including foreign nationals): "+coronaSplit[1]+"\n\n");
        stringBuffer.append("Cured/Discharged/Migrated : "+coronaSplit[2]+"\n\n");
        stringBuffer.append("Fatalities : "+coronaSplit[3]+"\n\n");
        stringBuffer.append("------------------------------\n");
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
