public class Main {

  public static void main(String[] args) {

    String text = "Вася заработал 5000 рублей, Петя - 7563 рубля, а Маша - 30000 рублей";
    String money = "рубл";

    int first = text.indexOf(money);
    int second = text.indexOf(money, first + 4);
    int third = text.indexOf(money, second + 4);
    int index = 0;

    int lastindex = first;
    while (Character.isDigit(text.charAt(lastindex - 2))) {
      index = lastindex - 2;
      lastindex--;
    }
    int firstIncome = Integer.parseInt(text.substring(index, first-1));

    lastindex = second;
    while (Character.isDigit(text.charAt(lastindex - 2))) {
      index = lastindex - 2;
      lastindex--;
    }
    int secondIncome = Integer.parseInt(text.substring(index, second-1));

    lastindex = third;
    while (Character.isDigit(text.charAt(lastindex - 2))) {
      index = lastindex - 2;
      lastindex--;
    }
    int thirdIncome = Integer.parseInt(text.substring(index, third-1));

    System.out.println(firstIncome + secondIncome + thirdIncome);

  }
}
