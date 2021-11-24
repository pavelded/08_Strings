package homework104;

public class TopManager extends Employee {

    public TopManager(String name, double permSalary, Company company) {
        super(name, permSalary, company);
    }

    @Override
    public double getMonthSalary() {
        if (company.getIncome() > 10000000) {
            double fullSalary = permSalary + 1.5*permSalary;
            return fullSalary;
        }
        return permSalary;
    }
}
