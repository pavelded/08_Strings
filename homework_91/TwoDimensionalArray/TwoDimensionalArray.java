public class TwoDimensionalArray {
    public static char symbol = 'X';

    public static char[][] getTwoDimensionalArray(int size) {

        //TODO: Написать метод, который создаст двумерный массив char заданного размера.
        // массив должен содержать символ symbol по диагоналям, пример для size = 3
        // [X,  , X]
        // [ , X,  ]
        // [X,  , X]
        char[][] array = new char[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= i; j++) {
                if (i==j) {
                    array[i][j] = 'X';
                } else {
                    array[i][j] = ' ';
                }
            }
        }

        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j <= size-1; j++) {
                if (j==size-i-1) {
                    array[i][j] = 'X';
                } else if (array[i][j] == 'X') {

                } else {
                    array[i][j] = ' ';
                }

            }
        }
        return array;
    }
}
