import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class UserList {
    private static Map<String, User> userMap = new HashMap<>();

    public static void inputData(Message message) {
        /*if (userMap.isEmpty()) {
            loadMapFromBackUp();
        }*/
        if (userMap.containsKey(message.getChatId().toString())) {
            LearningSyllablesBot learningSyllablesBot = new LearningSyllablesBot();

            String tempMessage = message.getText();
            String[] messageText = tempMessage.replaceAll(" {2,}", " ").split(" ");
            char latter;
            if (messageText[0].equals("/add")) {

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

            } else if (messageText[0].equals("/get")) {
                String printText = userMap.get(message.getChatId().toString()).getAllLetters();
                learningSyllablesBot.sendMsg(message, printText);
            } else if (messageText[0].equals("Получить")) {
                String printText = userMap.get(message.getChatId().toString()).getSyllable();
                learningSyllablesBot.sendMsg(message, printText);
            } else {
                help(message);
            }

            //backupMap();
        } else {
            LearningSyllablesBot learningSyllablesBot = new LearningSyllablesBot();
            userMap.put(message.getChatId().toString(), new User());

            String tempMessage = message.getText();
            String[] messageText = tempMessage.replaceAll(" {2,}", " ").split(" ");
            char latter;
            if (messageText[0].equals("/add")) {
                if (messageText[1].length() != 1) {
                    learningSyllablesBot.sendMsg(message, "Введены неправильные данные");
                    return;
                }

                messageText[1] = messageText[1].toUpperCase();
                latter = messageText[1].charAt(0);
                userMap.get(message.getChatId().toString()).add(message, latter);
            }
            //backupMap();
        }
    }

    private static void help(Message message){
        LearningSyllablesBot learningSyllablesBot = new LearningSyllablesBot();
        learningSyllablesBot.sendMsg(message, "Что бы добавить новые буквы, наберите \\add и букву которую хотите добавить. Например:" +
                "\n \\add а" +
                "\n Доступны только буквы русского языка." +
                "\n наберите \\get если хотите увидеть все ранее добавленные буквы.");
    }
    private static void loadMapFromBackUp() {

        try (Scanner scanner =new Scanner(new FileInputStream("src/main/resources/backup/keys.txt"));){

            while (scanner.hasNextLine()) {
                String key = scanner.nextLine();
                File file = new File("src/main/resources/backup/" + key + ".txt");

                try (FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
                    User temp = (User) objectInputStream.readObject();
                    userMap.put(key, temp);
                } catch (IOException e) {
                    String k = e.getMessage();
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }



    }

    private static void backupMap() {

        for (Map.Entry<String, User> entry : userMap.entrySet()) {
            String key = entry.getKey();
            User value = entry.getValue();
            File file = new File("src/main/resources/backup/" + key + ".txt");
            try (PrintWriter writer = new PrintWriter(file);
                 FileOutputStream fileOutputStream = new FileOutputStream(file);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                 FileWriter keyWriter = new FileWriter("src/main/resources/backup/keys.txt")
            ) {
                keyWriter.write(key + "\n");
                objectOutputStream.writeObject(value);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
