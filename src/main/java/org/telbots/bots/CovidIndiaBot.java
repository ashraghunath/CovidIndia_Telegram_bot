package org.telbots.bots;

import org.telbots.Commands;
import org.telbots.SymbolsAndEmojis;
import org.telbots.commands.ArticlesCommand;
import org.telbots.commands.DevInfoCommand;
import org.telbots.commands.StartCommand;
import org.telbots.commands.StateWiseCommand;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.telbots.CovidIndiaBotConfig.BOT_TOKEN;


public class CovidIndiaBot extends TelegramLongPollingBot {

    String command = null;
    public void onUpdateReceived(Update update) {

        command = update.getMessage().getText();

        try {
            if (isValid(command)) {
                switch (command) {

                    case Commands.start:
                    case "/" + Commands.hi:
                    case Commands.hi:
                        StartCommand startCommand = new StartCommand(update);
                        startCommand.executeHiCommand();
                        break;
                    case Commands.statewise:
                        StateWiseCommand stateWiseCommand = new StateWiseCommand(update);
                        stateWiseCommand.displayStateWiseInfo();
                        break;
                    case Commands.devInfo:
                        DevInfoCommand devInfoCommand = new DevInfoCommand(update);
                        devInfoCommand.displayDevInfo();
                        break;
                    case Commands.backButton:
                        hideKeyboard(update);
                        break;
                    case Commands.articles:
                        ArticlesCommand articlesCommand = new ArticlesCommand(update);
                            articlesCommand.getArticles();
                        break;


                }
            } else {
                checkForArticle(update);
            }
        }
        catch (Exception e)
        {
            sendDefaultMessage(update);
        }
    }

    public void sendDefaultMessage(Update update)
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        sendMessage.setText("Sorry "+update.getMessage().getFrom().getFirstName()+", I did not under stand that. Try these options :"+"\n"+
                SymbolsAndEmojis.HI.toString()+" /"+Commands.hi+"\n\n" +
                SymbolsAndEmojis.GRAPH.toString()+" "+Commands.statewise+"\n\n" +
                SymbolsAndEmojis.COMPUTER+" "+Commands.devInfo+"\n\n" +
                SymbolsAndEmojis.ARTICLES+" "+Commands.articles);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean isValid(String command)
    {
        if(command.equalsIgnoreCase(Commands.start) || command.equalsIgnoreCase(Commands.statewise) || command.equalsIgnoreCase(Commands.devInfo) || command.equalsIgnoreCase("/"+Commands.hi) || command.equalsIgnoreCase(Commands.articles) || command.contains(Commands.backButton))
            return true;
        return false;
    }

    public boolean checkForArticle(Update update)
    {
        ArticlesCommand articlesCommand = new ArticlesCommand(update);
        try {
            return articlesCommand.fetchArticleUsingTitle();
        } catch (Exception e) {

           return false;
        }
    }

    private void hideKeyboard(Update update) throws Exception
    {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(update.getMessage().getChatId());
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        replyKeyboardRemove.setSelective(true);
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        sendMessage.setText("Back to Main Menu");
        execute(sendMessage);
        sendMessage = new SendMessage();
        sendMessage.setText(
                "Here are commands that you can try out:\n" +
                        SymbolsAndEmojis.HI+" /"+Commands.hi+"\n\n" +
                        SymbolsAndEmojis.GRAPH+" "+Commands.statewise+"\n\n" +
                        SymbolsAndEmojis.COMPUTER+" "+Commands.devInfo+"\n\n" +
                        SymbolsAndEmojis.ARTICLES+" "+Commands.articles
        );
        sendMessage.setChatId(update.getMessage().getChatId());
        execute(sendMessage);
    }

    public String getBotUsername() {
        return "covIndia_bot";
    }

    public String getBotToken() {
        return BOT_TOKEN();
    }
}

