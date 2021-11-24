package homework104;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Company company = new Company("Экомаш-ИТ", 9000000);

        //Operators
        List<Employee> operators = Arrays.asList(
                new Operator("Иванов Иван", 35000, company),
                new Operator("Васильев Василий", 45000, company),
                new Operator("Бердыев Курбан", 43000, company),
                new Operator("Слепаков Дмитрий", 42000, company),
                new Operator("Караваев Иван", 46000, company)
                );
        company.hireAll(operators);

        List<Employee> managers = Arrays.asList(
                new Manager("Джавадов Ахмад", 20000, company),
                new Manager("Перфильева Екатерина", 21000, company),
                new Manager("Нуриджанян Эрванд", 25000, company),
                new Manager("Грибов Александр", 30000, company),
                new Manager("Кирилл Услоьцев", 26000, company)
        );
        company.hireAll(managers);

        List<Employee> topmanagers = Arrays.asList(
                new TopManager("Чечеткин Александр", 80000, company),
                new TopManager("Саблезубов Мефодий", 120000, company),
                new TopManager("Билимбаев Ефим", 99000, company),
                new TopManager("Иван Бочаров", 200000, company),
                new TopManager("Семен Мильштейн", 100000, company)
        );
        company.hireAll(topmanagers);

        System.out.println("Top Salary Staff List:");
        for (String topsalaries : company.getTopSalaryStaff(5)) {
            System.out.println(topsalaries);
        }

        System.out.println("\n" + "Lowest Salary Staff List:");
        for (String lowestsalaries : company.getLowestSalaryStaff(5)) {
            System.out.println(lowestsalaries);
        }

        }
        /*
        Employee o1 = new Operator("Иванов Иван", 35000, company);
        Employee o2 = new Operator("Васильев Василий", 45000, company);
        Employee o3 = new Operator("Бердыев Курбан", 43000, company);
        Employee o4 = new Operator("Слепаков Дмитрий", 42000, company);
        Employee o5 = new Operator("Караваев Иван", 46000, company);

        //Managers
        Employee m1 = new Manager("Джавадов Ахмад", 20000, company);
        Employee m2 = new Manager("Перфильева Екатерина", 21000, company);
        Employee m3 = new Manager("Нуриджанян Эрванд", 25000, company);
        Employee m4 = new Manager("Грибов Александр", 30000, company);
        Employee m5 = new Manager("Кирилл Услоьцев", 26000, company);

        //TopManagers
        Employee tm1 = new TopManager("Чечеткин Александр", 80000, company);
        Employee tm2 = new TopManager("Саблезубов Мефодий", 120000, company);
        Employee tm3 = new TopManager("Билимбаев Ефим", 99000, company);
        Employee tm4 = new TopManager("Иван Бочаров", 200000, company);
        Employee tm5 = new TopManager("Семен Мильштейн", 100000, company);
         */
}
