import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;w
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
            setButtons(sendMessage);
            execute (sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons (SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("Помощь"));
        keyboardSecondRow.add(new KeyboardButton("Получить слог"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);

        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    @Override
    public String getBotUsername() {
        return "LearningSyllablesBot";
    }

    @Override
    public String getBotToken() {
        return "5826035760:AAFhIn5zFe_NVcQD5Vk1VmUWU0ERdT5JaeI";
    }
}
