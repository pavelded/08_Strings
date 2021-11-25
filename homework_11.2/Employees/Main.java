import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static final String STAFF_TXT = "D://WorkSpace/11_AdvancedOOPFeatures/homework_11.2/Employees/data/staff.txt";

    public static void main(String[] args) {
        List<Employee> staff = Employee.loadStaffFromFile(STAFF_TXT);
        Employee employeeMaxSalary = findEmployeeWithHighestSalary(staff, 2017);
        System.out.println(employeeMaxSalary);
    }

    public static Employee findEmployeeWithHighestSalary(List<Employee> staff, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.get(Calendar.YEAR);
        List<Employee> employeeWithHighestSalaryList = staff.stream()
                .filter(e -> e.getWorkStart().toString().contains(Integer.toString(year)))
                .max(Comparator.comparing(Employee::getSalary)).stream()
                .collect(Collectors.toList());

        return employeeWithHighestSalaryList.get(0);
    }
}