import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

public class User {
    List<Character> consonantLetters = new ArrayList<>();
    List<Character> vowelLetters = new ArrayList<>();

    public void add(Message message, char input) {
        LearningSyllablesBot learningSyllablesBot = new LearningSyllablesBot();
        if ((input >= 1040 && input <= 1071) || input == 1025) {
            if ((input == 1025 || input == 1040 || input == 1045 || input == 1048 || input == 1054 || input == 1059 ||
                    input == 1067 || input == 1069 || input == 1070 || input == 1071) && !vowelLetters.contains(input)) {
                vowelLetters.add(input);
                learningSyllablesBot.sendMsg(message, "Буква успешка добавлена");
            } else if (input > 1040 && input < 1065 && !consonantLetters.contains(input) && !vowelLetters.contains(input)) {
                consonantLetters.add(input);
                learningSyllablesBot.sendMsg(message, "Буква успешка добавлена");
            } else {
                learningSyllablesBot.sendMsg(message, "Введены неправильные данные");
                return;
            }
        }
    }

    public String getAllLetters() {
        StringBuilder output = new StringBuilder();
        output.append("Согласные: ");
        for (int i = 0; i < consonantLetters.size(); i++) {
            output.append(consonantLetters.get(i).toString());
            if (i != consonantLetters.size() - 1) {
                output.append(", ");
            }
        }
        output.append("\n Гласные: ");
        for (int i = 0; i < vowelLetters.size(); i++) {
            output.append(vowelLetters.get(i).toString());
            if (i != vowelLetters.size() - 1) {
                output.append(", ");
            }
        }

        return output.toString();
    }

    public String getSyllable() {
        int consonant = (int) (Math.random() * consonantLetters.size());
        int vowel = (int) (Math.random() * vowelLetters.size());
        int order = (int) (Math.random() * 2);

        if (order == 0) {
            return "" + consonantLetters.get(consonant) + vowelLetters.get(vowel);
        } else {
            return "" + vowelLetters.get(vowel) + consonantLetters.get(consonant);
        }
    }


}
