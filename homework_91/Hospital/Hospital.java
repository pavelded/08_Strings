import java.util.Arrays;

public class Hospital {

    public static float[] generatePatientsTemperatures(int patientsCount) {

        //TODO: напишите метод генерации массива температур пациентов
        float[] temperatures = new float[patientsCount];
        for (int i = 0; i < patientsCount; i++) {
            temperatures[i] = Math.round(((float) Math.random() * 10/1.25f + 32)*10)/10f;
        }
        System.out.println(Arrays.toString(temperatures));
        return temperatures;
    }

    public static String getReport(float[] temperatureData) {
        /*
        TODO: Напишите код, который выводит среднюю температуру по больнице,количество здоровых пациентов,
            а также температуры всех пациентов.
        */
        float averageTemperature = 0;
        for (int i = 0; i < temperatureData.length; i++) {
            if (i == temperatureData.length - 1) {
                averageTemperature = Math.round((averageTemperature+temperatureData[i])/temperatureData.length*100)/100f;
            } else {
                averageTemperature += temperatureData[i];
            }
        }
        String temperatureList = "";
        for (int i = 0; i < temperatureData.length; i++) {
                if (i != 0) {
                    temperatureList = temperatureList + " " + temperatureData[i];
                } else {
                    temperatureList = "" + temperatureData[i];
                }
        }

        int fineTemperatureCount = 0;
        for (int i = 0; i < temperatureData.length; i++) {
            if (temperatureData[i] <= 36.9f && temperatureData[i] >= 36.2f)
            fineTemperatureCount++;
        }

        String report =
                "Температуры пациентов: " + temperatureList +
                        "\nСредняя температура: " + averageTemperature +
                        "\nКоличество здоровых: " + fineTemperatureCount++;

        return report;
    }
}
