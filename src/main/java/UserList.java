import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class UserList {
    private static Map<String, User> userMap = new HashMap<>();

    public static void inputData(Message message) {
        if (userMap.isEmpty()) {
            loadMapFromBackUp();
        }
        if (userMap.containsKey(message.getChatId().toString())) {
            LearningSyllablesBot learningSyllablesBot = new LearningSyllablesBot();

            String tempMessage = message.getText();
            String[] messageText = tempMessage.replaceAll(" {2,}", " ").split(" ");

            if (messageText[0].equals("/add")) {

                add(message, messageText);

                backupUser(message);

            } else if (messageText[0].equals("/get")) {
                String printText = userMap.get(message.getChatId().toString()).getAllLetters();
                learningSyllablesBot.sendMsg(message, printText);
            } else if (messageText[0].equalsIgnoreCase("Получить")) {
                String printText = userMap.get(message.getChatId().toString()).getSyllable();
                learningSyllablesBot.sendMsg(message, printText);
            } else if (messageText[0].equalsIgnoreCase("Очистить")) {
                userMap.get(message.getChatId().toString()).clean();
                learningSyllablesBot.sendMsg(message, "Все буквы успешно удалены");
            } else {
                help(message);
            }

        } else {
            LearningSyllablesBot learningSyllablesBot = new LearningSyllablesBot();
            userMap.put(message.getChatId().toString(), new User());

            String tempMessage = message.getText();
            String[] messageText = tempMessage.replaceAll(" {2,}", " ").split(" ");

            if (messageText[0].equals("/add")) {

                add(message, messageText);

                backupUser(message);
            }

        }
    }

    private static void add(Message message, String[] messageText) {
        char latter;
        for (int i = 1; i < messageText.length; i++) {
            if (messageText[i].length() == 1) {
                latter = messageText[i].toUpperCase().charAt(0);
                userMap.get(message.getChatId().toString()).add(message, latter);
            } else {
                for (int j = 0; j < messageText[i].length(); j++) {
                    latter = messageText[i].toUpperCase().charAt(j);
                    userMap.get(message.getChatId().toString()).add(message, latter);
                }
            }
        }
    }


    private static void help(Message message) {
        LearningSyllablesBot learningSyllablesBot = new LearningSyllablesBot();
        learningSyllablesBot.sendMsg(message, "Что бы добавить новые буквы, наберите /add и буквы которую хотите добавить. Например:" +
                "\n /add а" +
                "\n Доступны только буквы русского языка." +
                "\n наберите /get если хотите увидеть все ранее добавленные буквы." +
                "\n для того, чтобы очистить список ранее добавленных букв, напишите в чат \"очистить\"");
    }

    private static void loadMapFromBackUp() {

        File backupFolder = new File("src/main/resources/backup");
        File[] allFiles = backupFolder.listFiles();

        for (File file : allFiles
        ) {
            String key = file.getName().replaceAll(".txt", "");
            try (FileInputStream fileInputStream = new FileInputStream(file);
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                User temp = (User) objectInputStream.readObject();
                userMap.put(key, temp);
            } catch (IOException e) {
                String k = e.getMessage();
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void backupUser(Message message) {
        File file = new File("src/main/resources/backup/" + message.getChatId().toString() + ".txt");

        if (!file.exists()) {
            try {
                PrintWriter writer = new PrintWriter(file);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        try (
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        ) {
            objectOutputStream.writeObject(userMap.get(message.getChatId().toString()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

