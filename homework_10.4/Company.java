package homework104;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Company {

    public int companyIncome;
    public String companyName;
    public ArrayList<String> employees = new ArrayList<>();

    public Company(String companyName, int companyIncome) {
        this.companyName = companyName;
        this.companyIncome = companyIncome;
    }

    public void hire(Employee employee) {
        employees.add(employee.getName() + " - " + employee.getMonthSalary());
    }

    public void hireAll(List<Employee> hireList) {
        for (Employee employee : hireList) {
            employees.add(employee.getName() + " - " + employee.getMonthSalary());
        }
    }

    public void fire(Employee employee) {
        employees.remove(employee.getName() + " - " + employee.getMonthSalary());
    }

    public int getIncome() {
        return this.companyIncome;
    }

    public List<String> getTopSalaryStaff(int count) {
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Pattern pattern = Pattern.compile("[0-9]+");
                Matcher matcher = pattern.matcher(o1);
                int first = 0;
                int second = 0;
                if (matcher.reset(o1).find()) {
                    first = Integer.parseInt(matcher.group());
                }
                if (matcher.reset(o2).find()) {
                    second = Integer.parseInt(matcher.group());
                }
                if (first < second) {
                    return 1;
                } else if (first > second) {
                    return -1;
                }
                return 0;
            }
        };
        Collections.sort(employees, comparator);
        return employees.subList(0, count);
    }

    public List<String> getLowestSalaryStaff(int count) {
        Comparator<String> comparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Pattern pattern = Pattern.compile("[0-9]+");
                Matcher matcher = pattern.matcher(o1);
                int first = 0;
                int second = 0;
                if (matcher.reset(o1).find()) {
                    first = Integer.parseInt(matcher.group());
                }
                if (matcher.reset(o2).find()) {
                    second = Integer.parseInt(matcher.group());
                }
                if (first > second) {
                    return 1;
                } else if (first < second) {
                    return -1;
                }
                return 0;
            }
        };
        Collections.sort(employees, comparator);
        return employees.subList(0, count);
    }
}
