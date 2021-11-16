import java.util.Arrays;

public class ReverseArray {

    //TODO: Напишите код, который меняет порядок расположения элементов внутри массива на обратный.
    public static String[] reverse (String[] strings){

        int lastIndex = strings.length-1;

        for (int i = 0; i <= strings.length/2; i++) {
            String tempFirst = strings[i];

            strings[i] = strings[lastIndex];
            strings[lastIndex] = tempFirst;
            lastIndex--;
        }
        return strings;
    }
}
