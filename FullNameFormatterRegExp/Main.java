import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    while (true) {
      String input = scanner.nextLine();
      if (input.equals("0")) {
        break;
      }
      //String regex = "[а-я]{1,100}[ ]{1}[а-я]{1,100}[ ]{1}[а-я]{1,100}[ ]{1}]";
      String regex = "[а-я]{1,100}";

      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(input);
      String[] text = input.split(" ");
      if (matcher.find() && text.length == 3) {
        System.out.println("Фамилия: " + text[0]);
        System.out.println("Имя: " + text[1]);
        System.out.println("Отчество: " + text[2]);
      } else {
        System.out.println("Введенная строка не является ФИО");
      }
      //TODO:напишите ваш код тут, результат вывести в консоль.
      //При невалидном ФИО вывести в консоль: Введенная строка не является ФИО
    }
  }

}