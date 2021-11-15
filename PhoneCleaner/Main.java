import java.util.Scanner;

public class Main {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    while (true) {
      String input = scanner.nextLine();
      if (input.equals("0")) {
        break;
      }
      String regex = "[^0-9]";
      String phoneNumber = input.replaceAll(regex, "");
      if (phoneNumber.length() > 11 || phoneNumber.length() < 10 || (phoneNumber.charAt(0) != '7' & phoneNumber.charAt(0) != '8' && phoneNumber.length() != 10)) {
        System.out.println("Неверный формат номера");
      } else if (phoneNumber.length() == 10) {
        System.out.println("7" + phoneNumber);
      } else if (phoneNumber.length() == 11 ) {
        System.out.println("7" + phoneNumber.substring(1,11));
      }
    }
  }

}
