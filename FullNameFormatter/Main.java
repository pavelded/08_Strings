import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    while (true) {
      String input = scanner.nextLine();
      if (input.equals("0")) {
        break;
      }
      if (input.indexOf(' ') == -1) {
        System.out.println("Введенная строка не является ФИО");
        continue;
      }

      String Surname = input.substring(0, input.indexOf(' '));

      if (input.indexOf(' ', Surname.length() + 1) == -1) {
        System.out.println("Введенная строка не является ФИО");
        continue;
      }
      String Name = input.substring(Surname.length() + 1, input.indexOf(' ', Surname.length() + 1));

      String MiddleName = input.substring(Surname.length() + Name.length() + 2, input.length());

      if (input.indexOf(' ', Surname.length() + Name.length() + 2) > 0 || Character.isDigit(Name.charAt(0))) {
        System.out.println("Введенная строка не является ФИО");
        continue;
      }

      System.out.println("Фамилия: " + Surname);
      System.out.println("Имя: " + Name);
      System.out.println("Отчество: " + MiddleName);
    }
  }

}