package org.telbots.commands;

import org.telbots.Commands;
import org.telbots.SymbolsAndEmojis;
import org.telbots.bots.CovidIndiaBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand extends CovidIndiaBot {

    Update update=null;
    SendMessage sendMessage=null;
    public StartCommand(Update update)
    {
      this.update = update;
    }

    public void executeHiCommand() throws Exception
    {
        sendMessage = new SendMessage();
        sendMessage.setText("Hi "+update.getMessage().getFrom().getFirstName()+"!" + SymbolsAndEmojis.HI + "\n\n" +
                "Here are commands that you can try out:\n" +
                SymbolsAndEmojis.HI+" /"+Commands.hi+" -Help command\n\n" +
                SymbolsAndEmojis.GRAPH+" "+Commands.statewise+" -State-wise information about Covid19-India\n\n" +
                SymbolsAndEmojis.COMPUTER+" "+Commands.devInfo+" -Developer information\n\n" +
                SymbolsAndEmojis.ARTICLES+" "+Commands.articles+" -Latest articles and news about Covid19-India\n\n" +
                SymbolsAndEmojis.SUMMATION+"  "+Commands.covidindia+" -Summary of Covid19 in India\n\n"
        );
        sendMessage.setChatId(update.getMessage().getChatId());
        execute(sendMessage);
    }
}
