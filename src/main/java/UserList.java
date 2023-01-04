import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserList {
    private static Map<String, User> userMap = new HashMap<>();

    public static void inputData (Message message){
        if (userMap.containsKey(message.getChatId().toString())){
            LearningSyllablesBot learningSyllablesBot = new LearningSyllablesBot();

            String tempMessage = message.getText();
            String[] messageText = tempMessage.replaceAll(" {2,}", " ").split(" ");
            char latter;
            if (messageText[0].equals("/add")){
                if (messageText[1].length() != 1){
                    learningSyllablesBot.sendMsg(message, "Введены неправильные данные");
                    return;
                }

                latter = messageText[1].toUpperCase().charAt(0);
                userMap.get(message.getChatId().toString()).add(message, latter);
            } else if (messageText[0].equals("/get")) {
                String printText = userMap.get(message.getChatId().toString()).getAllLetters();
                learningSyllablesBot.sendMsg(message, printText);
            } else if (messageText[0].equals("/getSyllable")) {
                String printText = userMap.get(message.getChatId().toString()).getSyllable();
                learningSyllablesBot.sendMsg(message, printText);
            } else {
                learningSyllablesBot.sendMsg(message, "Введены неправильные данные");
            }
        } else {
            LearningSyllablesBot learningSyllablesBot = new LearningSyllablesBot();
            userMap.put(message.getChatId().toString(), new User());

            String tempMessage = message.getText();
            String[] messageText = tempMessage.replaceAll(" {2,}", " ").split(" ");
            char latter;
            if (messageText[0].equals("/add")){
                if (messageText[1].length() != 1){
                    learningSyllablesBot.sendMsg(message, "Введены неправильные данные");
                    return;
                }

                messageText[1] = messageText[1].toUpperCase();
                latter = messageText[1].charAt(0);
                userMap.get(message.getChatId().toString()).add(message, latter);
            } else  {
                learningSyllablesBot.sendMsg(message, "Введены неправильные данные");
            }
        }
    }
}
