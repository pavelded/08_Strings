import java.util.Arrays;

public class Main {

  public static void main(String[] args) {

  }

  public static String splitTextIntoWords(String text) {
    String newText = text.replaceAll("[.,;0-9]", "").replaceAll("[-]", " ");
    String[] words = newText.split("[\\s]+");
    StringBuilder string = new StringBuilder();
    for (int i = 0; i < words.length; i++) {
      if (i != words.length-1) {
        string.append(words[i]).append("\n");
      } else {
        string.append(words[i]);
      }
    }
    return string.toString();
  }

}