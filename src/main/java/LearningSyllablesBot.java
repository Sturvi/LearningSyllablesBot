import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class LearningSyllablesBot extends TelegramLongPollingBot {
    public static void main(String[] args) {
        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new LearningSyllablesBot());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        Message message = update.getMessage();

        UserList.inputData(message);
    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {

            execute (sendMessage);
            logData(sendMessage.getText());

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "LearningSyllablesBot";
    }

    @Override
    public String getBotToken() {
        return "5826035760:AAFhIn5zFe_NVcQD5Vk1VmUWU0ERdT5JaeI";
    }

    private void logData (String message){
        try (FileWriter fileWriter = new FileWriter("src/main/resources/Logs.txt", true)){
            fileWriter.write("Input: " + message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
